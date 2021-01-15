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

/**
 * @author nhatn
 */
@WebServlet(name = "DeletingProductServlet", value = "/DeletingProductServlet")
public class DeletingProductServlet extends HttpServlet {
    private final static Logger logger = Logger.getLogger(DeletingProductServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String admin = (String) session.getAttribute("admin");
        HanaShopContext hanaShopContext = null;
        String productIdStr = request.getParameter("productId");
        int productId = 0;
        try {
            productId = Integer.parseInt(productIdStr);
        } catch (NumberFormatException ignored) {
        }

        if (productId == 0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            hanaShopContext = new HanaShopContext();
            ProductService productService = new ProductServiceImpl(hanaShopContext);
            Product product = productService.deleteProduct(productId, admin);
            String message;
            if (product == null) {
                message = "Không thể xóa được sản phẩm, vì nó đã bị xóa!";
            } else {
                message = "Xóa thành công!";
            }
            request.setAttribute("message", message);
            RequestDispatcher rd = request.getRequestDispatcher("GettingManageListServlet");
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
