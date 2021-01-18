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
    <c:if test="${empty model.invoices}">
        <h1 class="text-danger">Bạn chưa bao giờ mua trên hệ thống này</h1>
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
                    <a href="DispatcherServlet?action=view-list-invoice&page=${model.page - 1}"><h3>Prev</h3></a>
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
                    <a href="DispatcherServlet?action=view-list-invoice&page=${model.page + 1}"><h3>Next</h3></a>
                </c:if>
            </div>
        </div>
    </c:if>
</div>
</body>
</html>
