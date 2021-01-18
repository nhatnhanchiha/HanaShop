package com.bac.controllers.product;

import com.bac.models.components.carousel.Carousel;
import com.bac.models.entities.Category;
import com.bac.models.entities.Product;
import com.bac.models.pages.ProductsByCategoryPage;
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
@WebServlet(name = "FindingProductByTypeServlet", value = "/FindingProductByTypeServlet")
public class FindingProductByTypeServlet extends HttpServlet {

    private final static Logger logger = Logger.getLogger(FindingProductByTypeServlet.class);


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HanaShopContext hanaShopContext = null;
        String categoryIdStr = request.getParameter("categoryId");
        int categoryId = 0;
        try {
            categoryId = Integer.parseInt(categoryIdStr);
        } catch (NumberFormatException ignored) {
        }

        if (categoryId <= 0) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
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
            List<Product> products =
                    productService
                            .getProductByTypeOrderByCreateDate(categoryId, ProductsByCategoryPage.SIZE_OF_CARDS + 1,
                                    (page - 1) * ProductsByCategoryPage.SIZE_OF_CARDS);

            Carousel carousel =
                    productService.getHotCarouselOfCategory(categoryId);
            List<Category> categories = productService.getAllCategory();
            ProductsByCategoryPage productsByCategoryPage =
                    new ProductsByCategoryPage(products, carousel, categories, page);
            for (Category category : categories) {
                if (category.getCategoryId() == categoryId) {
                    productsByCategoryPage.setCategory(category);
                    break;
                }
            }

            request.setAttribute("model", productsByCategoryPage);
            RequestDispatcher rd = request.getRequestDispatcher("list-by-category.jsp");
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
