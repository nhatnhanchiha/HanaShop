package com.bac.controllers.product;

import com.bac.models.components.cart.CartObject;
import com.bac.models.entities.Product;
import com.bac.models.services.ProductService;
import com.bac.models.services.impl.ProductServiceImpl;
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
@WebServlet(name = "AddingCartItemServlet", value = "/AddingCartItemServlet")
public class AddingCartItemServlet extends HttpServlet {
    private final static Logger logger = Logger.getLogger(AddingCartItemServlet.class);

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
            if (product != null && product.getStatus() && product.getQuantity() > 0) {
                HttpSession session = request.getSession();
                CartObject cartObject = (CartObject) session.getAttribute("cart");
                if (cartObject == null) {
                    cartObject = new CartObject();
                }
                cartObject.addProduct(productId);
                session.setAttribute("cart", cartObject);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
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