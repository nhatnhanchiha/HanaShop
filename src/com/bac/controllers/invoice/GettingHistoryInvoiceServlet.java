package com.bac.controllers.invoice;

import com.bac.models.entities.Category;
import com.bac.models.entities.Invoice;
import com.bac.models.pages.UserInvoicesPage;
import com.bac.models.services.InvoiceService;
import com.bac.models.services.ProductService;
import com.bac.models.services.impl.InvoiceServiceImpl;
import com.bac.models.services.impl.ProductServiceImpl;
import com.bac.models.utilities.HanaShopContext;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "GettingHistoryInvoiceServlet", value = "/GettingHistoryInvoiceServlet")
public class GettingHistoryInvoiceServlet extends HttpServlet {
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
        try {
            hanaShopContext = new HanaShopContext();
            InvoiceService invoiceService = new InvoiceServiceImpl(hanaShopContext);
            List<Invoice> invoices = invoiceService.getAllInvoice(username, UserInvoicesPage.SIZE_OF_INVOICES + 1, (page - 1) * UserInvoicesPage.SIZE_OF_INVOICES);
            ProductService productService = new ProductServiceImpl(hanaShopContext);
            List<Category> categories = productService.getAllCategory();
            UserInvoicesPage userInvoicesPage = new UserInvoicesPage(categories, invoices, page);
            request.setAttribute("model", userInvoicesPage);
            RequestDispatcher rd = request.getRequestDispatcher("user-detail.jsp");
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
