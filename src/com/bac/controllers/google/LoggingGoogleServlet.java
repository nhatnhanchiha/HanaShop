package com.bac.controllers.google;

import com.bac.common.GooglePojo;
import com.bac.common.GoogleUtils;
import com.bac.models.entities.Account;
import com.bac.models.entities.GoogleUser;
import com.bac.models.entities.builder.AccountBuilder;
import com.bac.models.entities.builder.GoogleUserBuilder;
import com.bac.models.services.AccountService;
import com.bac.models.services.impl.AccountServiceImpl;
import com.bac.models.utilities.HanaShopContext;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;


@WebServlet(name = "LoggingGoogleServlet ", value = "/LoggingGoogleServlet")
public class LoggingGoogleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        if (code == null || code.isEmpty()) {
            RequestDispatcher rd = request.getRequestDispatcher("LoginServlet");
            rd.forward(request, response);
        } else {
            String accessToken = GoogleUtils.getToken(code);
            GooglePojo googlePojo = GoogleUtils.getUserInfo(accessToken);
            HanaShopContext hanaShopContext = null;

            try {
                hanaShopContext = new HanaShopContext();
                AccountService accountService = new AccountServiceImpl(hanaShopContext);
                GoogleUser googleUser = accountService.loginWithGoogle(googlePojo.getEmail());
                HttpSession session = request.getSession();
                if (googleUser != null && googleUser.getAccount().getStatus()) {
                    session.setAttribute("firstName", googleUser.getAccount().getFirstName());
                    session.setAttribute("username", googleUser.getUsername());
                    response.sendRedirect("DispatcherServlet");
                }

                if (googleUser != null && !googleUser.getAccount().getStatus()) {

                }

                if (googleUser == null) {
                    int idInt = googlePojo.getEmail().hashCode();
                    String id;
                    if (idInt >= 0) {
                        id = idInt + "";
                    } else {
                        id = (idInt * -1) + "_h";
                    }
                    googleUser = GoogleUserBuilder.aGoogleUser().withGmail(googlePojo.getEmail())
                            .withUsername(id).build();
                    Account account = AccountBuilder.anAccount()
                            .withUsername(id)
                            .withFirstName(id)
                            .build();
                    googleUser.setAccount(account);
                    googleUser = accountService.addAGoogleUser(googleUser);
                    if (googleUser == null) {
                        hanaShopContext.rollback();
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        return;
                    }

                    hanaShopContext.saveChanges();

                    session.setAttribute("firstName", googleUser.getUsername());
                    session.setAttribute("username", googleUser.getUsername());
                    RequestDispatcher rd = request.getRequestDispatcher("register-google.jsp");
                    rd.forward(request, response);
                }
            } catch (SQLException | NamingException throwables) {
                throwables.printStackTrace();
            } finally {
                try {
                    if (hanaShopContext != null) {
                        hanaShopContext.closeConnection();
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }




        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        doGet(request, response);
    }
}
