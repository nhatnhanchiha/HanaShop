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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author nhatn
 */
@WebFilter(filterName = "AuthenticationUserFilter", value = "/*")
public class AuthenticationUserFilter implements Filter {
    private Set<String> handleActions;

    @Override
    public void init(FilterConfig config) throws ServletException {
        handleActions = new HashSet<>();
        handleActions.add("add-to-cart");
        handleActions.add("view-cart");
        handleActions.add("remove-cart-item");
        handleActions.add("check-out");
        handleActions.add("get-detail-of-invoice");
        handleActions.add("view-list-invoice");
        handleActions.add("add-info-google");
        handleActions.add("confirm-paypal");
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        String requestURI = req.getRequestURI();

        if (requestURI.contains("DispatcherServlet")) {
            String action = req.getParameter("action");
            if (handleActions.contains(action)) {
                HttpSession session = req.getSession();
                String username = (String) session.getAttribute("username");
                if (username == null) {
                    HttpServletResponse res = (HttpServletResponse) response;
                    res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
            }
        }

        chain.doFilter(request, response);
    }
}
