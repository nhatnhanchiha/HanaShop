<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="DispatcherServlet">HanaShop</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="DispatcherServlet">Home <span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                       data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        More
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <c:forEach items="${requestScope.model.categories}" var="category">
                            <a href="DispatcherServlet?action=search-product-by-category&categoryId=${category.categoryId}"
                               class="dropdown-item">${category.categoryName}</a>
                        </c:forEach>
                    </div>
                </li>
            </ul>
        </div>
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div class="navbar-nav ml-auto">
                <c:if test="${not empty sessionScope.username}">
                    <a class="nav-item nav-link" href="DispatcherServlet?action=view-list-invoice">${sessionScope.firstName}</a>
                    <a class="nav-item nav-link" href="DispatcherServlet?action=view-cart">View Cart</a>
                    <a href="DispatcherServlet?action=log-out" class="nav-item nav-link">Log Out</a>
                </c:if>
                <c:if test="${null or empty sessionScope.username}">
                    <a class="nav-item nav-link" href="DispatcherServlet?action=login">Login</a>
                    <a class="nav-item nav-link" href="DispatcherServlet?action=register">Register</a>
                </c:if>
            </div>
        </div>
    </div>
</nav>