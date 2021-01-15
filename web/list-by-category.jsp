<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nhatn
  Date: 1/10/2021
  Time: 2:28 PM
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
<c:if test="${empty model.foodCards}">
    <hr>
    <h1>Không tồn tại sản phẩm phù hợp</h1>
    <hr>
</c:if>
<c:if test="${not empty model.foodCards}">
    <c:set var="carousel" value="${model.hotCarousel}"/>
    <c:set var="cards" value="${model.foodCards}"/>
    <div class="container">
        <hr>
        <h1>${model.category.categoryName}</h1>
        <h3>${model.category.description}</h3>
        <hr>
        <div class="row">
            <c:forEach var="card" items="${cards}">
                <div class="col-3">
                    <div class="card mb-2">
                        <a href="DispatcherServlet?action=view-detail-product&productId=${card.productId}">
                            <img class="card-img-top"
                                 src="${card.imageUrl}"
                                 alt="${card.name}">
                        </a>
                        <div class="card-body">
                            <a href="DispatcherServlet?action=view-detail-product&productId=${card.productId}">
                                <h4 class="card-title food-name-min">${card.name}</h4>
                            </a>
                            <br>
                            <p class="card-text short-description-min">${card.shortDescription}</p>
                            <br>
                            <span class="text-danger">$${card.price}</span>
                            <button type="button" class="btn btn-info add-button"
                                    data-product-id="${card.productId}">Add to card
                            </button>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        <hr>
        <div class="d-flex justify-content-between">
            <div>
                <c:if test="${model.page == 1}">
                    <a><h3>Prev</h3></a>
                </c:if>
                <c:if test="${model.page > 1}">
                    <a href="DispatcherServlet?action=search-product-by-category&categoryId=${param['categoryId']}&page=${model.page - 1}">
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
                    <a href="DispatcherServlet?action=search-product-by-category&categoryId=${param['categoryId']}&page=${model.page + 1}">
                        <h3>Next</h3></a>
                </c:if>
            </div>
        </div>
        <hr>
        <div id="carousel-${carousel.categoryId}" class="carousel carousel-multi-item row-content mt-5"
             data-interval="false"
             data-wrap="false">
            <div class="row">
                <div class="col-6">
                    <h3>${carousel.name}</h3>
                </div>
                <div class="col">
                    <div class="controls-top text-right mt-3">
                        <a class="btn-floating carousel-prev" href="#carousel-${carousel.categoryId}"
                           data-carousel-target="carousel-${carousel.categoryId}"
                           data-slide="prev">&lt;</a>
                        <span data-page="1" data-count="${carousel.size}"
                              id="carousel-text-carousel-${carousel.categoryId}">1/${carousel.size}</span>
                        <a class="btn-floating carousel-next" href="#carousel-${carousel.categoryId}"
                           data-carousel-target="carousel-${carousel.categoryId}"
                           data-slide="next">&gt;</a>
                    </div>
                </div>
            </div>
            <div class="carousel-inner" role="listbox">
                <c:set var="slides" value="${carousel.slides}"/>
                <c:forEach var="slide" items="${slides}" varStatus="i">
                    <div class="carousel-item ${i.count == 1 ? 'active' : ''}">
                        <c:forEach var="card" items="${slide.cards}">
                            <div class="col-md-3" style="float:left">
                                <div class="card mb-2">
                                    <a href="DispatcherServlet?action=view-detail-product&productId=${card.productId}">
                                        <img class="card-img-top"
                                             src="${card.imageUrl}"
                                             alt="${card.name}">
                                    </a>
                                    <div class="card-body">
                                        <a href="DispatcherServlet?action=view-detail-product&productId=${card.productId}">
                                            <h4 class="card-title food-name-min">${card.name}</h4>
                                        </a>
                                        <br>
                                        <p class="card-text short-description-min">${card.shortDescription}</p>
                                        <br>
                                        <span class="text-danger">$${card.price}</span>
                                        <button type="button" class="btn btn-info add-button"
                                                data-product-id="${card.productId}">Add to card
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</c:if>
<jsp:include page="shared/_Footer.jsp"/>
<jsp:include page="shared/_Jquery.jsp"/>
<jsp:include page="shared/_ToastrJs.jsp"/>
<jsp:include page="shared/_BootstrapJs.jsp"/>
<script src="shared/script.js"></script>
</body>
</html>