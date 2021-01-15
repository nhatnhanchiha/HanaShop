package com.bac.controllers.invoice;

import com.bac.models.components.cart.CartObject;
import com.bac.models.entities.Invoice;
import com.bac.models.entities.builder.InvoiceBuilder;
import com.bac.models.services.InvoiceService;
import com.bac.models.services.ValidatorService;
import com.bac.models.services.impl.InvoiceServiceImpl;
import com.bac.models.utilities.HanaShopContext;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "CheckingOutServlet", value = "/CheckingOutServlet")
public class CheckingOutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HanaShopContext hanaShopContext = null;
        Boolean isValid = ValidatorService.validateCheckingOutRequest(request);
        if (!isValid) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }


        HttpSession session = request.getSession();
        CartObject cart = (CartObject) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            System.out.println("CheckingOutServlet.doPost");
            response.sendRedirect("DispatcherServlet");
            return;
        }

        try {
            hanaShopContext = new HanaShopContext();
            InvoiceService invoiceService = new InvoiceServiceImpl(hanaShopContext);
            Invoice invoice = InvoiceBuilder.anInvoice()
                    .withAddressLine(request.getParameter("Input.AddressLine"))
                    .withBlock(request.getParameter("Input.Block"))
                    .withDistrict(request.getParameter("Input.District"))
                    .withProvince(request.getParameter("Input.Province"))
                    .withEmail((String) session.getAttribute("email"))
                    .withPhoneNumber(request.getParameter("Input.PhoneNumber"))
                    .build();
            Invoice invoiceInDb = invoiceService.createAnInvoice(invoice);
            if (invoiceInDb != null) {
                hanaShopContext.saveChanges();
                System.out.println(invoiceInDb.getId());
            }

        } catch (SQLException | NamingException throwables) {
            throwables.printStackTrace();
        } finally {
            if (hanaShopContext != null) {
                try {
                    hanaShopContext.closeConnection();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }
}
