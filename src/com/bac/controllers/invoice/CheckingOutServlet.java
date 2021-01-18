package com.bac.controllers.invoice;

import com.bac.models.components.cart.CartObject;
import com.bac.models.entities.Invoice;
import com.bac.models.entities.InvoiceDetail;
import com.bac.models.entities.Product;
import com.bac.models.entities.builder.InvoiceBuilder;
import com.bac.models.entities.builder.InvoiceDetailBuilder;
import com.bac.models.pages.CartDetailPage;
import com.bac.models.services.InvoiceService;
import com.bac.models.services.ProductService;
import com.bac.models.services.ValidatorService;
import com.bac.models.services.impl.InvoiceServiceImpl;
import com.bac.models.services.impl.ProductServiceImpl;
import com.bac.models.utilities.HanaShopContext;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author nhatn
 */
@WebServlet(name = "CheckingOutServlet", value = "/CheckingOutServlet")
public class CheckingOutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        HanaShopContext hanaShopContext = null;
        Boolean isValid = ValidatorService.validateCheckingOutRequest(request);
        if (!isValid) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        HttpSession session = request.getSession();
        CartObject cart = (CartObject) session.getAttribute("cart");


        //User cannot check out if cart is not exist or empty
        if (cart == null || cart.isEmpty()) {
            response.sendRedirect("DispatcherServlet");
            return;
        }

        //User cannot check out without viewing cart before
        if (!cart.isHasDetail()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String quantityStr;
        int quantity;
        //Change quantity of cart
        for (Iterator<Map.Entry<Product, Integer>> i = cart.entrySet().iterator(); i.hasNext(); ) {
            Map.Entry<Product, Integer> e = i.next();

            quantity = -1;
            quantityStr = request.getParameter("Input.Quantity-" + e.getKey().getProductId());
            if (quantityStr == null) {
                response.sendRedirect("DispatcherServlet?action=view-cart");
                return;
            }
            try {
                quantity = Integer.parseInt(quantityStr);
            } catch (NumberFormatException ignored) {
            }

            if (quantity < 0) {
                response.sendRedirect("DispatcherServlet?action=view-cart");
                return;
            }

            if (quantity == 0) {
                i.remove();
            } else {
                cart.changeQuantity(e.getKey().getProductId(), quantity);
            }
        }


        try {
            hanaShopContext = new HanaShopContext();
            //valid cart before check out
            ProductService productService = new ProductServiceImpl(hanaShopContext);
            boolean valid = productService.validCartObject(cart);
            if (!valid) {
                System.out.println(1);
                response.sendRedirect("DispatcherServlet?action=view-cart&message=Some%20items%20changed%20status");
                return;
            }

            InvoiceService invoiceService = new InvoiceServiceImpl(hanaShopContext);
            String payMethodStr = request.getParameter("Select.PayMethod");
            int payMethod = Integer.parseInt(payMethodStr);

            Invoice invoice = InvoiceBuilder.anInvoice()
                    .withAddressLine(request.getParameter("Input.AddressLine"))
                    .withUsername((String) session.getAttribute("username"))
                    .withBlock(request.getParameter("Input.Block"))
                    .withDistrict(request.getParameter("Input.District"))
                    .withProvince(request.getParameter("Input.Province"))
                    .withPhoneNumber(request.getParameter("Input.PhoneNumber"))
                    .withCreateDate(LocalDate.now())
                    .build();

            if (payMethod == CartDetailPage.CASH_PAYMENT_UPON_DELIVERY) {
                invoice.setPaid(false);
            }

            List<InvoiceDetail> invoiceDetails = new ArrayList<>();
            for (Product product : cart.keySet()) {
                if (product.getStatus()) {
                    InvoiceDetail invoiceDetail = InvoiceDetailBuilder.anInvoiceDetail()
                            .withIdProduct(product.getProductId())
                            .withPrice(product.getPrice())
                            .withQuantity(cart.get(product))
                            .build();
                    invoiceDetails.add(invoiceDetail);
                }
            }

            Invoice invoiceInDb = null;
            if (invoiceDetails.size() > 0) {
                invoiceInDb = invoiceService.createAnInvoice(invoice, invoiceDetails);
            }

            if (invoiceInDb != null) {
//                response.sendRedirect("DispatcherServlet?action=get-detail-of-invoice&invoiceId=" + invoiceInDb.getId());
                boolean result = productService.updateQuantities(cart);
                if (!result) {
                    response.sendRedirect("DispatcherServlet?action=view-cart&message=Some%20items%20changed%20status");
                    hanaShopContext.rollback();
                    return;
                } else {
                    //todo: update quantitySold
                    hanaShopContext.saveChanges();
                    session.removeAttribute("cart");
                    response.sendRedirect("DispatcherServlet?action=get-detail-of-invoice&invoiceId=" + invoiceInDb.getId());
                }
            } else {
                hanaShopContext.rollback();
                System.out.println(3);
                response.sendRedirect("DispatcherServlet?action=view-cart&message=Some%20items%20changed%20status");
            }
        } catch (SQLException | NamingException throwables) {
            throwables.printStackTrace();
        } finally {
            if (hanaShopContext != null) {
                try {
                    hanaShopContext.closeConnection();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }
}
