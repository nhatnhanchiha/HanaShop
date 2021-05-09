<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nhatn
  Date: 1/5/2021
  Time: 10:51 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hana Shop</title>
    <jsp:include page="shared/_BootstrapCss.jsp"/>
    <jsp:include page="shared/_ToastrCss.jsp" />
    <jsp:include page="shared/_FontAwesome.jsp"/>
    <jsp:include page="shared/_BootstrapSocial.jsp"/>
    <link rel="stylesheet" href="shared/style.css">
</head>
<body>
<c:set var="model" value="${requestScope.model}"/>
<jsp:include page="shared/_Navbar.jsp"/>
<div class="container">
    <div class="row">
        <div class="col-3 text-center">
            <img src="shared/images.jpg" alt="" class="img-thumbnail mt-5">
            <h1 class="mt-3"><img src="shared/HanaShop.png" alt="" class="w-75"></h1>
            <h5>The Food Will Choose You</h5>
        </div>
        <div class="col-9">
            <div id="demo" class="carousel slide mt-3" data-ride="carousel">

                <!-- Indicators -->
                <ul class="carousel-indicators">
                    <li data-target="#demo" data-slide-to="0" class="active"></li>
                    <li data-target="#demo" data-slide-to="1"></li>
                    <li data-target="#demo" data-slide-to="2"></li>
                </ul>

                <!-- The slideshow -->
                <!--CarouselImpl Wrapper-->
                <div id="carousel-example-1z" class="carousel slide carousel-fade z-depth-1-half" data-ride="carousel">
                    <!--Indicators-->
                    <ol class="carousel-indicators">
                        <li data-target="#carousel-example-1z" data-slide-to="0" class="active"></li>
                        <li data-target="#carousel-example-1z" data-slide-to="1"></li>
                        <li data-target="#carousel-example-1z" data-slide-to="2"></li>
                    </ol>
                    <!--/.Indicators-->
                    <!--Slides-->
                    <div class="carousel-inner" role="listbox">
                        <!--First slide-->
                        <div class="carousel-item active ">
                            <img class="img-on-carousel"
                                 src="shared/carousel1.jpg"
                                 alt="First slide">
                        </div>
                        <!--/First slide-->
                        <!--Second slide-->
                        <div class="carousel-item">
                            <img class="img-on-carousel"
                                 src="shared/carousel2.jpg"
                                 alt="Second slide">
                        </div>
                        <!--/Second slide-->
                        <!--Third slide-->
                        <div class="carousel-item ">
                            <img class="img-on-carousel" src="shared/carousel3.jpg" alt="Third slide">
                        </div>
                        <!--/Third slide-->
                    </div>
                    <!--/.Slides-->
                    <!--Controls-->
                    <a class="carousel-control-prev" href="#carousel-example-1z" role="button" data-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="sr-only">Previous</span>
                    </a>
                    <a class="carousel-control-next" href="#carousel-example-1z" role="button" data-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="sr-only">Next</span>
                    </a>
                    <!--/.Controls-->
                </div>
                <!--/.CarouselImpl Wrapper-->
            </div>
        </div>
    </div>
    <hr>
    <jsp:include page="shared/_SearchBar.jsp"/>
    <c:forEach items="${model.carousels}" var="carousel">
        <c:if test="${not empty carousel.slides}">
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
                            <a href="DispatcherServlet?action=search-product-by-category&categoryId=${carousel.categoryId}">View
                                all</a>
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
                                                <%--<a class="btn btn-primary" href="DispatcherServlet?action=add-to-cart&productId=${card.productId}">Add to card</a>--%>
                                            <br>
                                            <span class="text-danger">$${card.price}</span>
                                            <button type="button" class="btn btn-info add-button ml-1"
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
        </c:if>
    </c:forEach>
</div>
<jsp:include page="shared/_Footer.jsp"/>
<jsp:include page="shared/_Jquery.jsp"/>
<jsp:include page="shared/_ToastrJs.jsp" />
<jsp:include page="shared/_BootstrapJs.jsp"/>
<jsp:include page="shared/_JqueryValidation.jsp"/>
<script src="shared/script.js"></script>
</body>
</html>