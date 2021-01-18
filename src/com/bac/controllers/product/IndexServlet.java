package com.bac.controllers.product;

import com.bac.models.components.carousel.Carousel;
import com.bac.models.entities.Category;
import com.bac.models.pages.IndexPage;
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
@WebServlet(name = "IndexServlet", value = "/IndexServlet")
public class IndexServlet extends HttpServlet {
    private final static Logger logger = Logger.getLogger(IndexServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HanaShopContext hanaShopContext = null;
        try {
            hanaShopContext = new HanaShopContext();
            ProductService productService = new ProductServiceImpl(hanaShopContext);
            List<Carousel> carousels = productService.getAllCarouselOnIndex();
            List<Category> categories = productService.getAllCategory();
            IndexPage indexPage = new IndexPage(carousels, categories);
            request.setAttribute("model", indexPage);
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
