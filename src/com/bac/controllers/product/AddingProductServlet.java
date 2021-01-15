package com.bac.controllers.product;

import com.bac.models.entities.Category;
import com.bac.models.entities.Product;
import com.bac.models.entities.builder.ProductBuilder;
import com.bac.models.pages.AddProductPage;
import com.bac.models.services.ProductService;
import com.bac.models.services.ValidatorService;
import com.bac.models.services.impl.ProductServiceImpl;
import com.bac.models.utilities.HanaShopContext;
import com.oreilly.servlet.MultipartRequest;
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

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author nhatn
 */
@WebServlet(name = "AddingProductServlet", value = "/AddingProductServlet")
public class AddingProductServlet extends HttpServlet {

    private final static Logger logger = Logger.getLogger(AddingProductServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HanaShopContext hanaShopContext = null;
        try {
            hanaShopContext = new HanaShopContext();
            ProductService productService = new ProductServiceImpl(hanaShopContext);
            List<Category> categories = productService.getAllCategory();
            AddProductPage addProductPage = new AddProductPage(categories);
            request.setAttribute("model", addProductPage);
            RequestDispatcher rd = request.getRequestDispatcher("add-product.jsp");
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
        HttpSession session = request.getSession();
        String admin = (String) session.getAttribute("admin");
        HanaShopContext hanaShopContext = null;
        //todo
        MultipartRequest multi = (MultipartRequest) request.getAttribute("multi");
        if (multi == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Boolean isValid = ValidatorService.validateAddProductRequest(multi);
        if (!isValid) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String imageName = (String) request.getAttribute("imageName");

        Product product = ProductBuilder.aProduct()
                .withName(convert(multi.getParameter("Input.Name")))
                .withShortDescription(convert(multi.getParameter("Input.ShortDescription")))
                .withImageUrl(Product.PREFIX_IMAGE + imageName)
                .withLongDescription(convert(multi.getParameter("Input.LongDescription")))
                .withCategoryId(Integer.parseInt(multi.getParameter("Selected.CategoryId")))
                .withPrice(Double.parseDouble(multi.getParameter("Input.Price")))
                .withQuantity(Integer.parseInt(multi.getParameter("Input.Quantity")))
                .withStatus(multi.getParameter("Checked.Status") != null)
                .build();

        try {
            hanaShopContext = new HanaShopContext();
            ProductService productService = new ProductServiceImpl(hanaShopContext);
            Product addedProduct = productService.addProduct(product, admin);
            if (addedProduct != null) {
                response.sendRedirect("DispatcherServlet?action=show-manage-list-product");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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

    private static String convert(String s) {
        byte[] ptext = s.getBytes(ISO_8859_1);
        return new String(ptext, UTF_8);
    }
}
