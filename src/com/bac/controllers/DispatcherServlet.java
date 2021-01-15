package com.bac.controllers;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author nhatn
 */
@WebServlet(name = "DispatcherServlet", value = "/DispatcherServlet")
public class DispatcherServlet extends HttpServlet {
    private Map<String, String> urlMapping;
    @Override
    public void init() throws ServletException {
        super.init();
        urlMapping = new HashMap<>(64);
        //Guest
        urlMapping.put("register", "RegistrationServlet");
        urlMapping.put("login", "AuthenticationServlet");
        urlMapping.put("view-detail-product", "DetailProductServlet");

        //google

        //User
        urlMapping.put("search-product-by-category", "FindingProductByTypeServlet"); //ok
        urlMapping.put("add-to-cart", "AddingCartItemServlet"); //ok
        urlMapping.put("user-search-product", "UserSearchProductServlet"); //ok
        urlMapping.put("view-cart", "CartDetailServlet");
        urlMapping.put("remove-cart-item", "RemovingCartItemServlet"); //ok
        urlMapping.put("check-out", "CheckingOutServlet");
        urlMapping.put("log-out", "LogoutServlet"); //ok

        //Admin - done
        urlMapping.put("admin-search-name-product", "AdminSearchProductServlet");
        urlMapping.put("show-manage-list-product", "GettingManageListServlet");
        urlMapping.put("update-category-product", "ChangingCategoryOfProductServlet");
        urlMapping.put("update-status-product", "ChangingStatusProductServlet");
        urlMapping.put("add-product", "AddingProductServlet");
        urlMapping.put("edit-product", "EditingProductServlet");
        urlMapping.put("delete-product", "DeletingProductServlet");


        //Incomplete
        urlMapping.put("log-out-admin", "DeletingProductServlet");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String url = "IndexServlet";
        if (action != null) {
            url = urlMapping.get(action);
        }
        System.out.println(url);
        RequestDispatcher rd = request.getRequestDispatcher(url);
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String contentType = request.getContentType();
        if (contentType != null && contentType.contains("multipart/form-data"))  {
            RequestDispatcher rd = request.getRequestDispatcher("FileUploadServlet");
            rd.forward(request, response);
        } else {
            doGet(request, response);
        }
    }
}
