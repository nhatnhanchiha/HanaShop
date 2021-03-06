package com.bac.controllers.google;

import com.bac.models.entities.Account;
import com.bac.models.entities.builder.AccountBuilder;
import com.bac.models.services.AccountService;
import com.bac.models.services.ValidatorService;
import com.bac.models.services.impl.AccountServiceImpl;
import com.bac.models.utilities.HanaShopContext;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
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
@WebServlet(name = "UpdatingInformationGoogleUserServlet", value = "/UpdatingInformationGoogleUserServlet")
public class UpdatingInformationGoogleUserServlet extends HttpServlet {
    private final static Logger logger = Logger.getLogger(UpdatingInformationGoogleUserServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Boolean isValid = ValidatorService.validateUpdateInformationGoogleUser(request);
        if (!isValid) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        HanaShopContext hanaShopContext = null;
        try {
            HttpSession session = request.getSession();
            hanaShopContext = new HanaShopContext();
            Account account = AccountBuilder.anAccount()
                    .withUsername((String) session.getAttribute("username"))
                    .withFirstName(request.getParameter("Input.FirstName"))
                    .withLastName(request.getParameter("Input.LastName"))
                    .build();
            AccountService accountService = new AccountServiceImpl(hanaShopContext);
            account = accountService.addInfo(account);
            if (account == null) {
                hanaShopContext.rollback();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }

            hanaShopContext.saveChanges();
            session.setAttribute("firstName", account.getFirstName());
            response.sendRedirect("DispatcherServlet");
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
