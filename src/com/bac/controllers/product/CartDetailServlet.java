
package com.bac.controllers.product;

import com.bac.models.components.Cart;
import com.bac.models.components.cart.CartObject;
import com.bac.models.entities.Category;
import com.bac.models.entities.Product;
import com.bac.models.pages.CartDetailPage;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author nhatn
 */
@WebServlet(name = "CartDetailServlet", value = "/CartDetailServlet")
public class CartDetailServlet extends HttpServlet {
    private final static Logger logger = Logger.getLogger(CartDetailServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HanaShopContext hanaShopContext = null;
        HttpSession session = request.getSession();
        CartObject cartObject = (CartObject) session.getAttribute("cart");
        try {
            hanaShopContext = new HanaShopContext();
            ProductService productService = new ProductServiceImpl(hanaShopContext);
            List<Category> categories = productService.getAllCategory();
            CartDetailPage cartDetailPage = new CartDetailPage(categories);
            if (cartObject != null && !cartObject.isEmpty()) {
                List<Product> products = productService.getProductDetails(cartObject.keySet());
                if (products != null && !products.isEmpty()) {
                    cartObject.loadProductDetails(products);
                    Cart cart = new Cart(cartObject);
                    session.setAttribute("cart", cartObject);
                    cartDetailPage.setCart(cart);
                }
            }
            request.setAttribute("model", cartDetailPage);
            RequestDispatcher rd = request.getRequestDispatcher("cart-detail.jsp");
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
