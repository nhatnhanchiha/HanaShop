<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nhatn
  Date: 1/7/2021
  Time: 9:46 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<c:set var="model" value="${requestScope.productDetailPage}"/>
<head>
    <title>${model.product.name}</title>
    <jsp:include page="shared/_BootstrapCss.jsp"/>
    <jsp:include page="shared/_ToastrCss.jsp" />
    <jsp:include page="shared/_FontAwesome.jsp"/>
    <jsp:include page="shared/_BootstrapSocial.jsp"/>
    <link rel="stylesheet" href="shared/style.css">
</head>
<body>
<jsp:include page="shared/_Navbar.jsp"/>
<div class="container">
    <div class="row mt-5">
        <div class="col-6">
            <img src="${model.product.imageUrl}" alt="${model.product.name}" class="img-detail">
        </div>
        <div class="col-6 mt-5">
            <h2>${model.product.name}</h2>
            <h3 class="text-danger">&lt;${model.product.price}$&gt;</h3>
            <button type="button" class="btn btn-info add-button" data-product-id="${model.product.productId}">
                Add to card
            </button>
            <p>${model.product.longDescription}</p>
        </div>
    </div>
    <hr>
    <div id="carousel-${model.carousel.categoryId}" class="carousel carousel-multi-item row-content mt-5"
         data-interval="false"
         data-wrap="false">
        <div class="row">
            <div class="col-6">
                <h3>${model.carousel.name}</h3>
            </div>
            <div class="col">
                <div class="controls-top text-right mt-3">
                    <a class="btn-floating carousel-prev" href="#carousel-${model.carousel.categoryId}"
                       data-carousel-target="carousel-${model.carousel.categoryId}"
                       data-slide="prev">&lt;</a>
                    <span data-page="1" data-count="${model.carousel.size}"
                          id="carousel-text-carousel-${model.carousel.categoryId}">1/${model.carousel.size}</span>
                    <a class="btn-floating carousel-next" href="#carousel-${model.carousel.categoryId}"
                       data-carousel-target="carousel-${model.carousel.categoryId}"
                       data-slide="next">&gt;</a>
                </div>
            </div>
        </div>
        <div class="carousel-inner" role="listbox">
            <c:set var="slides" value="${model.carousel.slides}"/>
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
<jsp:include page="shared/_Footer.jsp"/>
<jsp:include page="shared/_Jquery.jsp"/>
<jsp:include page="shared/_ToastrJs.jsp" />
<jsp:include page="shared/_BootstrapJs.jsp"/>
<script src="shared/script.js"></script>
</body>
</html>
