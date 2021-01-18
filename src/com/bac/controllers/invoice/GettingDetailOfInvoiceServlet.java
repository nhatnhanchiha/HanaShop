package com.bac.controllers.invoice;

import com.bac.models.entities.Invoice;
import com.bac.models.entities.InvoiceDetail;
import com.bac.models.pages.InvoicePage;
import com.bac.models.services.InvoiceDetailService;
import com.bac.models.services.InvoiceService;
import com.bac.models.services.impl.InvoiceDetailServiceImpl;
import com.bac.models.services.impl.InvoiceServiceImpl;
import com.bac.models.utilities.HanaShopContext;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "GettingDetailOfInvoiceServlet", value = "/GettingDetailOfInvoiceServlet")
public class GettingDetailOfInvoiceServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        String invoiceIdStr = request.getParameter("invoiceId");
        int invoiceId = 0;

        try {
            invoiceId = Integer.parseInt(invoiceIdStr);
        } catch (NumberFormatException ignored) {
        }

        if (invoiceId == 0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        HanaShopContext hanaShopContext = null;
        try {
            hanaShopContext = new HanaShopContext();
            InvoiceService invoiceService = new InvoiceServiceImpl(hanaShopContext);
            Invoice invoice = invoiceService.getInvoiceByIdAndUsername(invoiceId, username);
            if (invoice == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            InvoiceDetailService invoiceDetailService = new InvoiceDetailServiceImpl(hanaShopContext);
            List<InvoiceDetail> invoiceDetails = invoiceDetailService.getListInvoiceDetailByInvoiceId(invoice.getId());
            InvoicePage invoicePage = new InvoicePage(invoice, invoiceDetails);
            request.setAttribute("invoicePage", invoicePage);
            RequestDispatcher rd = request.getRequestDispatcher("invoice-detail.jsp");
            rd.forward(request, response);
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
