package com.bac.controllers.paypal;

import com.bac.models.entities.Invoice;
import com.bac.models.entities.PaypalPay;
import com.bac.models.entities.builder.PaypalPayBuilder;
import com.bac.models.services.PaypalService;
import com.bac.models.services.impl.PaypalServiceImpl;
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

@WebServlet(name = "ProcessingPaypalServlet", value = "/ProcessingPaypalServlet")
public class ProcessingPaypalServlet extends HttpServlet {
    private final static Logger logger = Logger.getLogger(ProcessingPaypalServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Invoice invoice = (Invoice) session.getAttribute("invoice");
        String transactionId = request.getParameter("transactionId");

        if (invoice == null || transactionId == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        Integer invoiceId = invoice.getId();

        PaypalPay paypalPay = PaypalPayBuilder.aPaypalPay()
                .withInvoiceId(invoiceId)
                .withTransactionId(transactionId).build();
        HanaShopContext hanaShopContext = null;

        try {
            hanaShopContext = new HanaShopContext();
            PaypalService paypalService = new PaypalServiceImpl(hanaShopContext);
            paypalPay = paypalService.save(paypalPay);
            if (paypalPay != null) {
                hanaShopContext.saveChanges();
                session.removeAttribute("invoice");
                session.removeAttribute("invoiceDetails");
                session.removeAttribute("sum");
            } else {
                hanaShopContext.rollback();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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
