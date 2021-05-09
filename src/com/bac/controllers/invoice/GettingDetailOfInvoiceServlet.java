package com.bac.controllers.invoice;

import com.bac.models.entities.Invoice;
import com.bac.models.entities.InvoiceDetail;
import com.bac.models.pages.InvoicePage;
import com.bac.models.services.InvoiceDetailService;
import com.bac.models.services.InvoiceService;
import com.bac.models.services.impl.InvoiceDetailServiceImpl;
import com.bac.models.services.impl.InvoiceServiceImpl;
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

@WebServlet(name = "GettingDetailOfInvoiceServlet", value = "/GettingDetailOfInvoiceServlet")
public class GettingDetailOfInvoiceServlet extends HttpServlet {
    private final static Logger logger = Logger.getLogger(GettingDetailOfInvoiceServlet.class);

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
