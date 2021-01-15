package com.bac.controllers.product;

import com.bac.models.entities.Category;
import com.bac.models.entities.Product;
import com.bac.models.pages.AdminManagePage;
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
@WebServlet(name = "GettingManageListServlet", value = "/GettingManageListServlet")
public class GettingManageListServlet extends HttpServlet {
    private final static int STATUS_ALL = 0;
    private final static int STATUS_TRUE = 1;
    private final static int STATUS_FALSE = 2;
    private final static Logger logger = Logger.getLogger(GettingManageListServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HanaShopContext hanaShopContext = null;
        String pageStr = request.getParameter("page");
        if (pageStr == null) {
            pageStr = (String) request.getAttribute("page");
        }
        int page;
        if (pageStr == null) {
            page = 1;
        } else {
            try {
                page = Integer.parseInt(pageStr);
                if (page < 1) {
                    page = 1;
                }
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        String categoryIdStr = request.getParameter("Select.CategoryId");
        int categoryId;
        if (categoryIdStr == null) {
            categoryId = 0;
        } else {
            try {
                categoryId = Integer.parseInt(categoryIdStr);
                if (categoryId < 0) {
                    categoryId = 0;
                }
            } catch (NumberFormatException e) {
                categoryId = 0;
            }
        }

        String statusStr = request.getParameter("Select.Status");
        int status;
        if (statusStr == null) {
            status = STATUS_ALL;
        } else {
            try {
                status = Integer.parseInt(statusStr);
                switch (status) {
                    case STATUS_TRUE:
                        status = STATUS_TRUE;
                        break;
                    case STATUS_FALSE:
                        status = STATUS_FALSE;
                        break;
                    default:
                        status = STATUS_ALL;
                }
            } catch (NumberFormatException e) {
                status = STATUS_ALL;
            }
        }


        try {
            hanaShopContext = new HanaShopContext();
            ProductService productService = new ProductServiceImpl(hanaShopContext);
            List<Product> products = productService.getAllProductByLimitAndOffset(categoryId, status, AdminManagePage.SIZE_OF_PRODUCTS + 1, (page - 1) * AdminManagePage.SIZE_OF_PRODUCTS);
            List<Category> categories = productService.getAllCategory();
            String message = (String) request.getAttribute("message");
            AdminManagePage adminManagePage = new AdminManagePage(products, categories, page);
            adminManagePage.setMessage(message);
            request.setAttribute("model", adminManagePage);
            RequestDispatcher rd = request.getRequestDispatcher("list-product-for-admin.jsp");
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
