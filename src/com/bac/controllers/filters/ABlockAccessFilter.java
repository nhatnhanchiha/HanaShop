package com.bac.controllers.filters;


import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;

/**
 * @author nhatn
 */
@WebFilter(filterName = "ABlockAccessFilter", urlPatterns = "/*")
public class ABlockAccessFilter implements Filter {
    HashSet<String> urlSet;

    @Override
    public void init(FilterConfig config) throws ServletException {
        urlSet = new HashSet<>();
        urlSet.add("AuthenticationServlet");
        urlSet.add("LogoutServlet");
        urlSet.add("RegistrationServlet");
        urlSet.add("FileUploadServlet");
        urlSet.add("/login-google");
        urlSet.add("CheckingOutServlet");
        urlSet.add("AddingCartItemServlet");
        urlSet.add("AddingProductServlet");
        urlSet.add("AdminSearchProductServlet");
        urlSet.add("CartDetailServlet");
        urlSet.add("ChangingCategoryOfProductServlet");
        urlSet.add("ChangingStatusProductServlet");
        urlSet.add("DeletingProductServlet");
        urlSet.add("DetailProductServlet");
        urlSet.add("EditingProductServlet");
        urlSet.add("FindingProductByTypeServlet");
        urlSet.add("GettingManageListServlet");
        urlSet.add("IndexServlet");
        urlSet.add("RemovingCartItemServlet");
        urlSet.add("UserSearchProductServlet");

        urlSet.add("_BootstrapCss.jsp");
        urlSet.add("_BootstrapJs.jsp");
        urlSet.add("_BootstrapSocial.jsp");
        urlSet.add("_FontAwesome.jsp");
        urlSet.add("_Footer.jsp");
        urlSet.add("_Jquery.jsp");
        urlSet.add("_JqueryValidation.jsp");
        urlSet.add("_Navbar.jsp");
        urlSet.add("_SearchBar.jsp");
        urlSet.add("_ToastrCss.jsp");
        urlSet.add("_ToastrJs.jsp");

        urlSet.add("add-product.jsp");
        urlSet.add("cart-detail.jsp");
        urlSet.add("edit-product.jsp");
        urlSet.add("index.jsp");
        urlSet.add("list-by-category.jsp");
        urlSet.add("list-product-for-admin.jsp");
        urlSet.add("login.jsp");
        urlSet.add("product-detail.jsp");
        urlSet.add("register.jsp");
        urlSet.add("search-result-page.jsp");
        urlSet.add("admin-login.jsp");
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String requestUri = req.getRequestURI();
        String resource = requestUri.substring(requestUri.lastIndexOf('/') + 1);
        if (urlSet.contains(resource)) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {
            chain.doFilter(request, response);
        }
    }
}
