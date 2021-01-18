package com.bac.controllers.account;

import com.bac.models.entities.Account;
import com.bac.models.entities.Category;
import com.bac.models.entities.builder.AccountBuilder;
import com.bac.models.pages.RegisterPage;
import com.bac.models.services.AccountService;
import com.bac.models.services.ProductService;
import com.bac.models.services.ValidatorService;
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

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author nhatn
 */
@WebServlet(name = "RegistrationServlet", value = "/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
    private final static Logger logger = Logger.getLogger(RegistrationServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HanaShopContext hanaShopContext = null;
        try {
            hanaShopContext = new HanaShopContext();
            ProductService productService = new ProductServiceImpl(hanaShopContext);
            List<Category> categories = productService.getAllCategory();
            RegisterPage registerPage = new RegisterPage(categories, null);
            request.setAttribute("model", registerPage);
            RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
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
        Boolean isValid = ValidatorService.validateRegisterRequest(request);
        HanaShopContext hanaShopContext = null;
        if (!isValid) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            Account account = AccountBuilder.anAccount()
                    .withUsername(request.getParameter("Input.Username"))
                    .withPassword(request.getParameter("Input.Password"))
                    .withFirstName(request.getParameter("Input.FirstName"))
                    .withLastName(request.getParameter("Input.LastName"))
                    .build();
            try {
                hanaShopContext = new HanaShopContext();
                AccountService accountService = new AccountServiceImpl(hanaShopContext);
                Account register = accountService.register(account);
                if (register != null) {
                    RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                    rd.forward(request, response);
                } else {
                    ProductService productService = new ProductServiceImpl(hanaShopContext);
                    List<Category> categories = productService.getAllCategory();
                    String message = "This username has already existed!";
                    RegisterPage registerPage = new RegisterPage(categories, message);
                    request.setAttribute("model", registerPage);
                    RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
                    rd.forward(request, response);
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
}
