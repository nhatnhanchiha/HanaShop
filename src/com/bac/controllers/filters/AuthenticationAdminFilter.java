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

@WebFilter(filterName = "AdminAuthenticationFilter", urlPatterns = "/*")
public class AuthenticationAdminFilter implements Filter {
    private Set<String> handleActions;

    @Override
    public void init(FilterConfig config) throws ServletException {
        handleActions = new HashSet<>();
        handleActions.add("admin-search-name-product");
        handleActions.add("show-manage-list-product");
        handleActions.add("update-category-product");
        handleActions.add("update-status-product");
        handleActions.add("add-product");
        handleActions.add("edit-product");
        handleActions.add("delete-product");
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
        if ("DispatcherServlet".equalsIgnoreCase(resource)) {
            String action = req.getParameter("action");
            if (action != null && handleActions.contains(action)) {
                HttpSession session = req.getSession();
                String admin = (String) session.getAttribute("admin");
                if (admin == null) {
                    res.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
            }
        }
        chain.doFilter(request, response);
    }
}
