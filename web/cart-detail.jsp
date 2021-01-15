<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nhatn
  Date: 1/9/2021
  Time: 10:40 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <jsp:include page="shared/_BootstrapCss.jsp"/>
    <jsp:include page="shared/_FontAwesome.jsp"/>
    <jsp:include page="shared/_BootstrapSocial.jsp"/>
</head>
<body>
<jsp:include page="shared/_Navbar.jsp"/>
<div class="container">
    <hr>
    <c:if test="${null or empty requestScope.cart.cartObject}">
        <h1>Không có gì cả, mời bạn qua trang chủ chọn món</h1>
        <hr>
    </c:if>
    <c:if test="${not null and not empty requestScope.message}">
        <div class="alert alert-info" role="alert">
                ${requestScope.message}
        </div>
    </c:if>
    <c:if test="${not null and not empty requestScope.cart}">
        <div class="row justify-content-center">
            <table class="table table-hover text-center">
                <tr>
                    <th>Name</th>
                    <th>Price</th>
                    <th>Quantity</th>
                    <th>Status</th>
                    <th>Delete</th>
                </tr>
                <c:forEach items="${requestScope.cart.cartObject}" var="product">
                    <tr>
                        <td>
                            <a href="DispatcherServlet?action=view-detail-product&productId=${product.key.productId}">${product.key.name}</a>
                        </td>
                        <td>$${product.key.price}</td>
                        <td><input type="number" name="Input.Quantity" value="${product.value}"></td>
                        <td>${product.key.status}</td>
                        <td><a href="DispatcherServlet?action=remove-cart-item&productId=${product.key.productId}" onclick="return confirm('Are you sure')">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <td></td>
                    <td></td>
                    <td>Sum:</td>
                    <td>$${requestScope.cart.sum}</td>
                    <td></td>
                </tr>
            </table>
        </div>
        <div class="row justify-content-center">
            <form action="DispatcherServlet?action=check-out" method="post" class="col-8">
                <div class="form-group">
                    <label for="Input_AddressLine">Address Line</label>
                    <input class="form-control" data-val="true"
                           data-val-length="The Address line must be at least 1 and at max 100 characters."
                           data-val-length-max="100"
                           data-val-length-min="1"
                           data-val-required="The Address line is required."
                           id="Input_AddressLine"
                           maxlength="100"
                           name="Input.AddressLine">
                    <span class="text-danger field-validation-valid" data-valmsg-for="Input.AddressLine"
                          data-valmsg-replace="true"></span>
                </div>

                <div class="form-group">
                    <label for="Input_Block">Block</label>
                    <input class="form-control" data-val="true"
                           data-val-length="The Block must be at least 1 and at max 100 characters."
                           data-val-length-max="100"
                           data-val-length-min="1"
                           data-val-required="The Block is required."
                           id="Input_Block"
                           maxlength="100"
                           name="Input.Block">
                    <span class="text-danger field-validation-valid" data-valmsg-for="Input.Block"
                          data-valmsg-replace="true"></span>
                </div>

                <div class="form-group">
                    <label for="Input_District">District</label>
                    <input class="form-control" data-val="true"
                           data-val-length="The District must be at least 1 and at max 100 characters."
                           data-val-length-max="100"
                           data-val-length-min="1"
                           data-val-required="The District is required."
                           id="Input_District"
                           maxlength="100"
                           name="Input.District">
                    <span class="text-danger field-validation-valid" data-valmsg-for="Input.District"
                          data-valmsg-replace="true"></span>
                </div>

                <div class="form-group">
                    <label for="Input_Province">Province</label>
                    <input class="form-control" data-val="true"
                           data-val-length="The Province must be at least 1 and at max 100 characters."
                           data-val-length-max="100"
                           data-val-length-min="1"
                           data-val-required="The Province is required."
                           id="Input_Province"
                           maxlength="100"
                           name="Input.Province">
                    <span class="text-danger field-validation-valid" data-valmsg-for="Input.Province"
                          data-valmsg-replace="true"></span>
                </div>

                <div class="form-group">
                    <label for="Input_PhoneNumber">PhoneNumber</label>
                    <input class="form-control" data-val="true"
                           data-rule-phoneVN="true"
                           data-msg-phoneVN="Must be a phone number"
                           data-val-required="The Phone number is required."
                           id="Input_PhoneNumber"
                           maxlength="12"
                           name="Input.PhoneNumber">
                    <span class="text-danger field-validation-valid" data-valmsg-for="Input.PhoneNumber"
                          data-valmsg-replace="true"></span>
                </div>
                <button class="btn btn-warning" type="submit">Thanh Toán</button>
            </form>
        </div>
    </c:if>
</div>
<jsp:include page="shared/_Footer.jsp"/>
<jsp:include page="shared/_Jquery.jsp"/>
<jsp:include page="shared/_JqueryValidation.jsp" />
<jsp:include page="shared/_BootstrapJs.jsp"/>
<script src="shared/script.js"></script>
</body>
</html>
