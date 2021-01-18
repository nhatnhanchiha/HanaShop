<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nhatn
  Date: 1/16/2021
  Time: 6:04 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <jsp:include page="shared/_BootstrapCss.jsp"/>
</head>
<body>
<c:set value="${requestScope.invoicePage}" var="model"/>
<div class="container">
    <hr>
    <h1 style="color: #00b3ee; text-align:center;">Cảm ơn bạn đã mua hàng</h1>
    <hr>
    <h2>Username: ${model.invoice.username}</h2>
    <h2>InvoiceId: ${model.invoice.id}</h2>
    <h2>Date: ${model.invoice.createDate}</h2>
    <h3>Address: ${model.fullAddress}</h3>
    <div class="container">
        <table class="table">
            <tr>
                <th>ProductId</th>
                <th>Name</th>
                <th>Price</th>
                <th>Quantity</th>
            </tr>
            <c:forEach var="invoiceDetail" items="${model.invoiceDetails}">
                <tr>
                    <td>${invoiceDetail.idProduct}</td>
                    <td>${invoiceDetail.product.name}</td>
                    <td>$${invoiceDetail.price}</td>
                    <td>${invoiceDetail.quantity}</td>
                </tr>
            </c:forEach>
        </table>
        <h2>Sum: ${model.sum}$</h2>
        <h2>Paid: ${model.invoice.paid ? 'Done' : 'Not yet'}</h2>
        <h3><a href="DispatcherServlet">Về trang chủ</a></h3>
    </div>
</div>
</body>
</html>
