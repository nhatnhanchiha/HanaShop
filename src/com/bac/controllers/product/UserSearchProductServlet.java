package com.bac.controllers.product;

import com.bac.models.entities.Category;
import com.bac.models.entities.Product;
import com.bac.models.pages.SearchingPage;
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
@WebServlet(name = "UserSearchProductServlet", value = "/UserSearchProductServlet")
public class UserSearchProductServlet extends HttpServlet {
    private final static Logger logger = Logger.getLogger(UserSearchProductServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchValue = request.getParameter("Input.SearchValue");
        String categoryIdStr = request.getParameter("Select.Category");
        int categoryId = 0;
        String minPriceStr = request.getParameter("Input.MinPrice");
        double minPrice = 0.0;
        String maxPriceStr = request.getParameter("Input.MaxPrice");
        double maxPrice = ValidatorService.MAX_VALUE_OF_PRICE;
        String pageStr = request.getParameter("page");
        int page = 1;

        if (searchValue == null) {
            searchValue = "";
        } else if (searchValue.length() > ValidatorService.MAX_LENGTH_OF_TEXT) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if (categoryIdStr != null && !categoryIdStr.isEmpty()) {
            try {
                categoryId = Integer.parseInt(categoryIdStr);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
        }

        if (minPriceStr != null && !minPriceStr.isEmpty()) {
            try {
                minPrice = Double.parseDouble(minPriceStr);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
        }

        if (maxPriceStr != null && !maxPriceStr.isEmpty()) {
            try {
                maxPrice = Double.parseDouble(maxPriceStr);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
        }

        if (minPrice > maxPrice) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if (pageStr != null) {
            page = Integer.parseInt(pageStr);
        }

        HanaShopContext hanaShopContext = null;
        try {
            hanaShopContext = new HanaShopContext();
            ProductService productService = new ProductServiceImpl(hanaShopContext);
            List<Product> products = productService.searchProductByNameOrCategoryOrMoneyRange(searchValue, categoryId, minPrice, maxPrice, SearchingPage.SIZE_OF_PRODUCTS + 1, (page - 1) * SearchingPage.SIZE_OF_PRODUCTS);
            List<Category> categories = productService.getAllCategory();
            SearchingPage searchingPage = new SearchingPage(products, categories, page);
            request.setAttribute("model", searchingPage);
            RequestDispatcher rd = request.getRequestDispatcher("search-result-page.jsp");
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
