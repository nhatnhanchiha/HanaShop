package com.bac.controllers.product;

import com.bac.models.entities.Product;
import com.bac.models.services.ProductService;
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

/**
 * @author nhatn
 */
@WebServlet(name = "DetailProductServlet", value = "/DetailProductServlet")
public class DetailProductServlet extends HttpServlet {
    private final static Logger logger = Logger.getLogger(DetailProductServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HanaShopContext hanaShopContext = null;
        String productIdStr = request.getParameter("productId");
        int productId = 0;
        try {
            productId = Integer.parseInt(productIdStr);
        } catch (NumberFormatException ignored) {
        }

        if (productId == 0) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        try {
            hanaShopContext = new HanaShopContext();
            ProductService productService = new ProductServiceImpl(hanaShopContext);
            Product product = productService.getProductById(productId);
            if (product == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            } else {
                request.setAttribute("product", product);
                RequestDispatcher rd = request.getRequestDispatcher("product-detail.jsp");
                rd.forward(request, response);
            }
        } catch (SQLException | NamingException throwables) {
            try {
                if (hanaShopContext != null) {
                    hanaShopContext.rollback();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            logger.error(throwables);
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
}
