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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ChangingCategoryOfProductServlet", value = "/ChangingCategoryOfProductServlet")
public class ChangingCategoryOfProductServlet extends HttpServlet {
    private final static Logger logger = Logger.getLogger(ChangingCategoryOfProductServlet.class);


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String admin = (String) session.getAttribute("admin");
        String productIdStr = request.getParameter("productId");
        int productId = 0;
        if (productIdStr != null) {
            try {
                productId = Integer.parseInt(productIdStr);
            } catch (NumberFormatException ignored) {
            }
        }

        if (productId > 0) {
            String categoryIdStr = request.getParameter("categoryId");
            int categoryId = 0;
            if (categoryIdStr != null) {
                try {
                    categoryId = Integer.parseInt(categoryIdStr);
                } catch (NumberFormatException ignored) {
                }
                if (categoryId >= 0) {
                    HanaShopContext hanaShopContext = null;
                    try {
                        hanaShopContext = new HanaShopContext();
                        ProductService productService = new ProductServiceImpl(hanaShopContext);
                        Product product = productService.updateCategory(productId, categoryId, admin);
                        if (product != null) {
                            hanaShopContext.saveChanges();
                        } else {
                            hanaShopContext.rollback();
                        }
                        RequestDispatcher rd = request.getRequestDispatcher("GettingManageListServlet");
                        rd.forward(request, response);
                        return;
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
        }

        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }
}
