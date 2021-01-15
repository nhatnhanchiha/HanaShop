package com.bac.controllers.product;

import com.bac.models.entities.Product;
import com.bac.models.services.ProductService;
import com.bac.models.services.ValidatorService;
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
@WebServlet(name = "AdminSearchProductServlet", value = "/AdminSearchProductServlet")
public class AdminSearchProductServlet extends HttpServlet {
    private static final int SIZE_OF_LIST_PRODUCTS = 20;
    private final static Logger logger = Logger.getLogger(AdminSearchProductServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HanaShopContext hanaShopContext = null;
        String productName = request.getParameter("Input.ProductName");
        if (productName == null || productName.trim().isEmpty()
                || productName.length() > ValidatorService.MAX_LENGTH_OF_TEXT) {
            RequestDispatcher rd = request.getRequestDispatcher("list-product-for-admin.jsp");
            rd.forward(request, response);
            return;
        }

        String pageStr = request.getParameter("page");
        int page = 1;

        try {
            page = Integer.parseInt(pageStr);
        } catch (NumberFormatException ignored) {
        }

        if (page <= 0) {
            page = 1;
        }

        try {
            hanaShopContext = new HanaShopContext();
            ProductService productService = new ProductServiceImpl(hanaShopContext);
            List<Product> products = productService.searchProductByName(productName, SIZE_OF_LIST_PRODUCTS, (page - 1) * SIZE_OF_LIST_PRODUCTS);
//            List<Category> categories = productService.getAllCategory();
//            ListProductManagementPage listProductManagementPage = new ListProductManagementPage(categories, products);
            request.setAttribute("products", products);
            RequestDispatcher rd = request.getRequestDispatcher("list-product-for-admin.jsp");
            rd.forward(request, response);
        } catch (SQLException | NamingException throwables) {
            throwables.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
