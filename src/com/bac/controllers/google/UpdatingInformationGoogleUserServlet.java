package com.bac.controllers.google;

import com.bac.models.entities.Account;
import com.bac.models.entities.builder.AccountBuilder;
import com.bac.models.services.AccountService;
import com.bac.models.services.ValidatorService;
import com.bac.models.services.impl.AccountServiceImpl;
import com.bac.models.utilities.HanaShopContext;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author nhatn
 */
@WebServlet(name = "UpdatingInformationGoogleUserServlet", value = "/UpdatingInformationGoogleUserServlet")
public class UpdatingInformationGoogleUserServlet extends HttpServlet {
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
            System.out.println(account.getUsername());
            System.out.println(account.getFirstName());
            System.out.println(account.getLastName());
            AccountService accountService = new AccountServiceImpl(hanaShopContext);
            account = accountService.addInfo(account);
            if (account == null) {
                hanaShopContext.rollback();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }

            hanaShopContext.saveChanges();
            session.setAttribute("firstName", account.getFirstName());
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
