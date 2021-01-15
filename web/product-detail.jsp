<%--
  Created by IntelliJ IDEA.
  User: nhatn
  Date: 1/7/2021
  Time: 9:46 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${requestScope.product.name}</title>
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
            <img src="${requestScope.product.imageUrl}" alt="${requestScope.product.name}" class="img-detail">
        </div>
        <div class="col-6 mt-5">
            <h2>${requestScope.product.name}</h2>
            <h3 class="text-danger">&lt;${requestScope.product.price}$&gt;</h3>
            <button type="button" class="btn btn-info add-button" data-product-id="${requestScope.product.productId}">
                Add to card
            </button>
            <p>${requestScope.product.longDescription}</p>
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
