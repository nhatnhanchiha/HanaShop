package com.bac.controllers.account;

import com.bac.models.entities.Account;
import com.bac.models.entities.Category;
import com.bac.models.pages.LoginPage;
import com.bac.models.services.AccountService;
import com.bac.models.services.ProductService;
import com.bac.models.services.impl.AccountServiceImpl;
import com.bac.models.services.impl.ProductServiceImpl;
import com.bac.models.utilities.HanaShopContext;
import org.apache.log4j.Logger;

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
import java.util.List;

/**
 * @author nhatn
 */
@WebServlet(name = "AuthenticationServlet", value = "/AuthenticationServlet")
public class AuthenticationServlet extends HttpServlet {
    private final static Logger logger = Logger.getLogger(AuthenticationServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HanaShopContext hanaShopContext = null;
        try {
            hanaShopContext = new HanaShopContext();
            ProductService productService = new ProductServiceImpl(hanaShopContext);
            List<Category> categories = productService.getAllCategory();
            LoginPage loginPage = new LoginPage(categories);
            request.setAttribute("model", loginPage);
            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
            rd.forward(request, response);
        } catch (SQLException | NamingException throwables) {
            try {
                if (hanaShopContext != null) {
                    hanaShopContext.rollback();
                }
            } catch (SQLException e) {
                logger.error(e.getCause());
            }
            logger.error(throwables.getCause());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
            if (hanaShopContext != null) {
                try {
                    hanaShopContext.closeConnection();
                } catch (SQLException throwables) {
                    logger.error(throwables.getCause());
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HanaShopContext hanaShopContext = null;
        try {
            hanaShopContext = new HanaShopContext();
            AccountService accountService = new AccountServiceImpl(hanaShopContext);
            String username = request.getParameter("Input.Username");
            String password = request.getParameter("Input.Password");
            Account account = accountService.login(username, password);
            if (account == null) {
                String message = "Wrong username or password";
                request.setAttribute("message", message);
                ProductService productService = new ProductServiceImpl(hanaShopContext);
                List<Category> categories = productService.getAllCategory();
                LoginPage loginPage = new LoginPage(categories, message);
                request.setAttribute("model", loginPage);
                RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                rd.forward(request, response);
            } else {
                if (!account.getStatus()) {
                    String message = "Your account is locked";
                    request.setAttribute("message", message);
                    ProductService productService = new ProductServiceImpl(hanaShopContext);
                    List<Category> categories = productService.getAllCategory();
                    LoginPage loginPage = new LoginPage(categories, message);
                    request.setAttribute("model", loginPage);
                    RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                    rd.forward(request, response);
                } else {
                    HttpSession session = request.getSession();
                    session.setAttribute("username", account.getUsername());
                    session.setAttribute("firstName", account.getFirstName());
                    RequestDispatcher rd = request.getRequestDispatcher("IndexServlet");
                    rd.forward(request, response);
                }
            }
        } catch (SQLException | NamingException throwables) {
            try {
                if (hanaShopContext != null) {
                    hanaShopContext.rollback();
                }
            } catch (SQLException e) {
                logger.error(e.getCause());
            }
            logger.error(throwables.getCause());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
            if (hanaShopContext != null) {
                try {
                    hanaShopContext.closeConnection();
                } catch (SQLException throwables) {
                    logger.error(throwables.getCause());
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            }
        }
    }
}

