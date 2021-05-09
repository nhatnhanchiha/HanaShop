package com.bac.controllers.product;

import com.bac.models.components.carousel.Carousel;
import com.bac.models.components.carousel.FoodCard;
import com.bac.models.entities.Category;
import com.bac.models.entities.Product;
import com.bac.models.pages.ProductDetailPage;
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
import java.util.List;

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
            if (product == null || !product.getStatus()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            } else {
                List<Category> categories = productService.getAllCategory();
                List<Product> products = productService.getListUserOrderTogetherProductId(productId);
                Carousel carousel;
                if (products.isEmpty()) {
                    carousel = productService.getHotCarouselOfCategory(product.getCategoryId());
                    carousel.setName("Mọi người cũng mua");
                } else {
                    List<FoodCard> foodCards = FoodCard.mapping(products);
                    carousel = new Carousel("Mọi người cũng mua", 0, foodCards);
                }
                ProductDetailPage productDetailPage = new ProductDetailPage(categories, product, carousel);
                request.setAttribute("model", productDetailPage);
                RequestDispatcher rd = request.getRequestDispatcher("product-detail.jsp");
                rd.forward(request, response);
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
