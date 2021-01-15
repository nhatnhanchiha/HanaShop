package com.bac.controllers.product;

import com.bac.models.components.cart.CartObject;
import com.bac.models.entities.Product;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author nhatn
 */
@WebServlet(name = "RemovingCartItemServlet", value = "/RemovingCartItemServlet")
public class RemovingCartItemServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productIdStr = request.getParameter("productId");
        HttpSession session = request.getSession();
        CartObject cart = (CartObject) session.getAttribute("cart");
        if (cart == null) {
            response.sendRedirect("DispatcherServlet");
            return;
        }

        int productId;
        try {
            productId = Integer.parseInt(productIdStr);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Product product = new Product();
        product.setProductId(productId);
        if (!cart.containsKey(product)) {
            request.setAttribute("message", "This product does not exist in your cart!");
        } else {
            cart.removeProduct(productId);
            request.setAttribute("message", "Removing product successful!");
        }

        RequestDispatcher rd = request.getRequestDispatcher("CartDetailServlet");
        rd.forward(request, response);
    }
}
