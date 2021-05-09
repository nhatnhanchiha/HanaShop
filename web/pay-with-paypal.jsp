<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nhatn
  Date: 1/18/2021
  Time: 9:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Thanh toán</title>
    <jsp:include page="shared/_BootstrapCss.jsp"/>
</head>
<body>
<script
        src="https://www.paypal.com/sdk/js?client-id=ATwKS79o9AlP5Gt1sjrLix5VUFR4W8phdIrqYYm05CPrqMmIFVXdxy8NxVYNpGH-L8PzUgWuUxgEWEp7"> // Required. Replace SB_CLIENT_ID with your sandbox client ID.
</script>
<div class="container">
    <div class="row">
        <div class="col-6">
            <h1>InvoiceId: ${sessionScope.invoice.id}</h1>
            <table class="table table-hover">
                <tr>
                    <th>Name</th>
                    <th>Price</th>
                    <th>Quantity</th>
                </tr>
                <c:forEach var="invoiceDetail" items="${sessionScope.invoiceDetails}">
                    <tr>
                        <td>${invoiceDetail.product.name}</td>
                        <td>${invoiceDetail.price}</td>
                        <td>${invoiceDetail.quantity}</td>
                    </tr>
                </c:forEach>
            </table>
            <h3>Sum: $${sessionScope.sum}</h3>
        </div>
        <div class="col-6">
            <div id="paypal-button-container"></div>
        </div>
    </div>
</div>
<jsp:include page="shared/_Jquery.jsp"/>
<script>
    paypal.Buttons({
        createOrder: function (data, actions) {
            // This function sets up the details of the transaction, including the amount and line item details.
            return actions.order.create({
                purchase_units: [{
                    amount: {
                        value: '${sessionScope.sum}'
                    }
                }]
            });
        },
        onApprove: function (data, actions) {
            // This function captures the funds from the transaction.
            return actions.order.capture().then(function (details) {
                // This function shows a transaction success message to your buyer.
                const id = details.purchase_units[0].payments.captures[0].id
                $.get("DispatcherServlet?action=confirm-paypal&transactionId=" + id, function (data, status) {
                    if (status === 'success') {
                        alert('Paypal thanh cong, don hang se duoc cap nhat som nhat co the')
                        window.location.href = "DispatcherServlet?action=get-detail-of-invoice&invoiceId=${sessionScope.invoice.id}"
                    }
                }).fail(function (jqXHR, status, errorThrown) {
                    if (jqXHR.status === 500) {
                        alert("Khong thanh cong, tien se duoc hoan som nhat co the")
                    }
                })
            });
        }
    }).render('#paypal-button-container');
    //This function displays Smart Payment Buttons on your web page.
    //This function displays Smart Payment Buttons on your web page.
</script>
</body>
</html>
