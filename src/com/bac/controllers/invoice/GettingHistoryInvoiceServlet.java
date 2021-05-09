package com.bac.controllers.invoice;

import com.bac.models.entities.Category;
import com.bac.models.entities.Invoice;
import com.bac.models.pages.UserInvoicesPage;
import com.bac.models.services.InvoiceService;
import com.bac.models.services.ProductService;
import com.bac.models.services.impl.InvoiceServiceImpl;
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
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@WebServlet(name = "GettingHistoryInvoiceServlet", value = "/GettingHistoryInvoiceServlet")
public class GettingHistoryInvoiceServlet extends HttpServlet {
    private final static Logger logger = Logger.getLogger(GettingHistoryInvoiceServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        HanaShopContext hanaShopContext = null;
        String pageStr = request.getParameter("page");
        int page = 1;
        try {
            page = Integer.parseInt(pageStr);
        } catch (NumberFormatException ignored) {
        }

        if (page < 1) {
            page = 1;
        }

        String productName = request.getParameter("Input.FoodName");
        String shoppingDateStr = request.getParameter("Input.ShoppingDate");

        LocalDate shoppingDate = null;

        if (shoppingDateStr != null && !"".equals(shoppingDateStr)) {
            try {
                shoppingDate = LocalDate.parse(shoppingDateStr);
            } catch (DateTimeParseException ignored) {
            }
        }

        try {
            hanaShopContext = new HanaShopContext();
            InvoiceService invoiceService = new InvoiceServiceImpl(hanaShopContext);
            List<Invoice> invoices = invoiceService.getAllInvoice(username, productName, shoppingDate,UserInvoicesPage.SIZE_OF_INVOICES + 1, (page - 1) * UserInvoicesPage.SIZE_OF_INVOICES);
            ProductService productService = new ProductServiceImpl(hanaShopContext);
            List<Category> categories = productService.getAllCategory();
            UserInvoicesPage userInvoicesPage = new UserInvoicesPage(categories, invoices, page);
            request.setAttribute("model", userInvoicesPage);
            RequestDispatcher rd = request.getRequestDispatcher("user-detail.jsp");
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
