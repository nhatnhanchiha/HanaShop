<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nhatn
  Date: 1/16/2021
  Time: 7:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${sessionScope.firstName}</title>
    <jsp:include page="shared/_BootstrapCss.jsp"/>
</head>
<body>
<c:set var="model" value="${requestScope.model}"/>
<jsp:include page="shared/_Navbar.jsp"/>
<div class="container">
    <hr>
    <form action="DispatcherServlet" method="get" class="row">
        <div class="form-group col-4">
            <label for="Input_FoodName">Food Name</label>
            <input name="Input.FoodName" class="form-control" id="Input_FoodName" type="text" value="${param['Input.FoodName']}">
        </div>
        <div class="form-group col-4">
            <label for="Input_ShoppingDate">Shopping Date</label>
            <input type="date" name="Input.ShoppingDate" class="form-control" id="Input_ShoppingDate" value="${param['Input.ShoppingDate']}">
        </div>
        <input type="hidden" name="action" value="view-list-invoice">
        <div class="form-group col-2">
            <label>Search Button</label>
            <button type="submit" class="form-control btn btn-info">Search</button>
        </div>
    </form>
    <hr>
    <c:if test="${empty model.invoices}">
        <h1 class="text-danger">Không có đơn hàng nào cả</h1>
    </c:if>
    <c:if test="${not empty model.invoices}">
        <table class="table table-hover">
            <tr>
                <th>Id</th>
                <th>Date</th>
                <th>Paid</th>
                <th>Detail</th>
            </tr>
            <c:forEach var="invoice" items="${model.invoices}">
                <tr>
                    <td>${invoice.id}</td>
                    <td>${invoice.createDate}</td>
                    <td>${invoice.paid ? 'Done' : 'Not yet'}</td>
                    <td><a href="DispatcherServlet?action=get-detail-of-invoice&invoiceId=${invoice.id}">Detail</a></td>
                </tr>
            </c:forEach>
        </table>
        <div class="d-flex justify-content-between">
            <div>
                <c:if test="${model.page == 1}">
                    <a><h3>Prev</h3></a>
                </c:if>
                <c:if test="${model.page > 1}">
                    <a href="DispatcherServlet?action=view-list-invoice&page=${model.page - 1}&Input.FoodName=${param['Input.FoodName']}&Input.ShoppingDate=${param['Input.ShoppingDate']}"><h3>Prev</h3></a>
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
                    <a href="DispatcherServlet?action=view-list-invoice&page=${model.page + 1}&Input.FoodName=${param['Input.FoodName']}&Input.ShoppingDate=${param['Input.ShoppingDate']}"><h3>Next</h3></a>
                </c:if>
            </div>
        </div>
    </c:if>
</div>
<jsp:include page="shared/_Jquery.jsp"/>
<jsp:include page="shared/_BootstrapJs.jsp"/>
</body>
</html>
