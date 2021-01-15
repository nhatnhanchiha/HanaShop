package com.bac.controllers.account;

import com.bac.models.entities.Admin;
import com.bac.models.services.AdminService;
import com.bac.models.services.impl.AdminServiceImpl;
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

/**
 * @author nhatn
 */
@WebServlet(name = "AuthenticationAdminServlet", value = "/AuthenticationAdminServlet")
public class AuthenticationAdminServlet extends HttpServlet {
    private final static Logger logger = Logger.getLogger(AuthenticationAdminServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("admin-login.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String adminUsername = request.getParameter("Input.AdminUsername");
        if (adminUsername != null && !adminUsername.isEmpty()) {
            String adminPassword = request.getParameter("Input.AdminPassword");
            if (adminPassword != null && !adminPassword.isEmpty()) {
                HanaShopContext hanaShopContext = null;
                try {
                    hanaShopContext = new HanaShopContext();
                    AdminService adminService = new AdminServiceImpl(hanaShopContext);
                    Admin admin = adminService.login(adminUsername, adminPassword);
                    if (admin != null) {
                        HttpSession session = request.getSession();
                        session.setAttribute("admin", adminUsername);
                        RequestDispatcher rd = request.getRequestDispatcher("GettingManageListServlet");
                        rd.forward(request, response);
                        return;
                    }

                    String message = "Wrong username or password";
                    request.setAttribute("message", message);
                    RequestDispatcher rd = request.getRequestDispatcher("admin-login.jsp");
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
        }
    }
}