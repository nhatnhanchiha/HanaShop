<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nhatn
  Date: 1/12/2021
  Time: 6:00 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>HanaShop</title>
    <jsp:include page="shared/_BootstrapCss.jsp"/>
    <jsp:include page="shared/_ToastrCss.jsp"/>
    <jsp:include page="shared/_FontAwesome.jsp"/>
    <jsp:include page="shared/_BootstrapSocial.jsp"/>
    <link rel="stylesheet" href="shared/style.css">
</head>
<body>
<c:set var="model" value="${requestScope.model}"/>
<jsp:include page="shared/_Navbar.jsp"/>

<div class="container">
    <hr>
    <jsp:include page="shared/_SearchBar.jsp"/>
    <div class="row">
        <div class="col-8">
            <c:if test="${empty model.products}">
                <h1 class="text-center">Không tìm thấy sản phẩm phù hợp</h1>
            </c:if>
            <c:forEach items="${model.products}" var="product">
                <div class="row mt-3">
                    <div class="col-4">
                        <a href=""></a>
                        <img src="${product.imageUrl}" alt="${product.name}" class="card-img-top">
                    </div>
                    <div class="col-8">
                        <a href="DispatcherServlet?action=view-detail-product&productId=${product.productId}">
                            <h1>${product.name}</h1></a>
                        <h3>$${product.price}</h3>
                        <h5>Created date: ${product.createDate}</h5>
                        <button type="button" class="btn btn-info add-button ml-1"
                                data-product-id="${product.productId}">Add to card
                        </button>
                        <p>${product.shortDescription}</p>
                    </div>
                </div>
            </c:forEach>
            <hr>
            <div class="d-flex justify-content-between">
                <div>
                    <c:if test="${model.page == 1}">
                        <a><h3>Prev</h3></a>
                    </c:if>
                    <c:if test="${model.page > 1}">
                        <a href="DispatcherServlet?action=user-search-product&page=${model.page - 1}&Input.SearchValue=${param['Input.SearchValue']}&Select.Category=${param['Select.Category']}&Input.MinPrice=${param['Input.MinPrice']}&Input.MaxPrice=${param['Input.MaxPrice']}">
                            <h3>Prev</h3></a>
                    </c:if>
                </div>
                <div>
                    <h3>${model.page}</h3>
                </div>
                <div>
                    <c:if test="${!model.hasNextPage}">
                        <a><h3>Next</h3></a>
                    </c:if>
                    <c:if test="${model.hasNextPage}">
                        <a href="DispatcherServlet?action=user-search-product&page=${model.page + 1}&Input.SearchValue=${param['Input.SearchValue']}&Select.Category=${param['Select.Category']}&Input.MinPrice=${param['Input.MinPrice']}&Input.MaxPrice=${param['Input.MaxPrice']}">
                            <h3>Next</h3></a>
                    </c:if>
                </div>
            </div>
        </div>
        <div class="col-4">
            <h2>Có thể bạn sẽ mua:</h2>
            <ol>
                <c:forEach items="${model.listSuggestion}" var="product">
                    <li>
                        <div>
                            <a href="#">
                                <h4>${product.name}</h4>
                                <p class="short-description-min">${product.shortDescription}</p>
                            </a>
                        </div>
                    </li>
                </c:forEach>
            </ol>
        </div>
    </div>
</div>
<jsp:include page="shared/_Footer.jsp"/>
<jsp:include page="shared/_Jquery.jsp"/>
<jsp:include page="shared/_ToastrJs.jsp"/>
<jsp:include page="shared/_BootstrapJs.jsp"/>
<jsp:include page="shared/_JqueryValidation.jsp"/>
<script src="shared/script.js"></script>
</body>
</html>
