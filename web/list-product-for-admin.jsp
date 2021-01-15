<%@ page import="com.bac.controllers.product.ChangingStatusProductServlet" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nhatn
  Date: 1/6/2021
  Time: 9:28 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <jsp:include page="shared/_BootstrapCss.jsp"/>
</head>
<body>
<c:set var="model" value="${requestScope.model}"/>
<jsp:include page="shared/_Navbar.jsp"/>
<div class="container">
    <h1 class="text-center">Product Management</h1>
    <h3>${model.message}</h3>
    <div class="row justify-content-center">
        <div class="col-10">
            <form method="get" action="DispatcherServlet" id="category-form">
                <div class="row">
                    <div class="col-3">
                        <div class="form-group">
                            <label>Category</label>
                            <select name="Select.CategoryId" class="form-control submit-form-search">
                                <option value="0">All</option>
                                <c:forEach items="${model.categories}" var="category">
                                    <option value="${category.categoryId}" ${category.categoryId == param['Select.CategoryId'] ? 'selected' : ''}>${category.categoryName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="col-3">
                        <div class="form-group">
                            <label>Status</label>
                            <select name="Select.Status" class="form-control submit-form-search">
                                <option value="0">All</option>
                                <option value="1" ${param['Select.Status'] == 1 ? "selected" : ''}>True</option>
                                <option value="2" ${param['Select.Status'] == 2 ? "selected" : ''}>False</option>
                            </select>
                        </div>
                    </div>
                    <input type="hidden" name="action" value="show-manage-list-product">
                </div>
            </form>
            <c:if test="${empty model.products}">
                <hr>
                <h1>Do not find out any product</h1>
                <hr>
            </c:if>
            <a href="DispatcherServlet?action=add-product" class="btn btn-info">Add product</a>
            <hr>
            <table class="table table-hover text-center">
                <tr>
                    <th>Id</th>
                    <th>Name</th>
                    <th>Category</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
                <c:forEach items="${model.products}" var="product">
                    <tr>
                        <td>${product.productId}</td>
                        <td>${product.name}</td>
                        <td><select name="Select.CategoryId" class="submit-form-update-category"
                                    id="product-${product.productId}-category" data-product-id="${product.productId}">
                            <c:forEach items="${model.categories}" var="category">
                                <option value="${category.categoryId}" ${product.categoryId == category.categoryId ? 'selected' : ''}>${category.categoryName}</option>
                            </c:forEach>
                        </select></td>
                        <td><select name="Select.Status" class="submit-form-update-status"
                                    id="product-${product.productId}-status" data-product-id="${product.productId}">
                            <option value="<%=ChangingStatusProductServlet.STATUS_TRUE%>">true</option>
                            <option value="<%=ChangingStatusProductServlet.STATUS_FALSE%>"  ${!product.status ? 'selected': ""}>
                                false
                            </option>
                        </select></td>
                        <td>
                            <a href="DispatcherServlet?action=view-detail-product&productId=${product.productId}">Detail</a>
                            |
                            <a href="DispatcherServlet?action=edit-product&productId=${product.productId}">Edit</a>
                            |
                            <a href="DispatcherServlet?action=delete-product&productId=${product.productId}&Select.CategoryId=${param['Select.Category']}&Select.Status=${param['Select.Status']}&page=${model.page}"
                               onclick="return confirm('Are you sure?')">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <div class="d-flex justify-content-between">
                <div>
                    <c:if test="${model.page == 1}">
                        <a><h3>Prev</h3></a>
                    </c:if>
                    <c:if test="${model.page > 1}">
                        <a href="DispatcherServlet?action=show-manage-list-product&Select.CategoryId=${param['Select.Category']}&Select.Status=${param['Select.Status']}&page=${model.page - 1}">
                            <h3>Prev</h3>
                        </a>
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
                        <a href="DispatcherServlet?action=show-manage-list-product&Select.CategoryId=${param['Select.Category']}&Select.Status=${param['Select.Status']}&page=${model.page + 1}">
                            <h3>Next</h3>
                        </a>

                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="shared/_Jquery.jsp"/>
<jsp:include page="shared/_BootstrapJs.jsp"/>
<script>
    $(document).ready(function () {
        $('.submit-form-search').change(function () {
            $('#category-form').submit()
        })

        const $submitCategory = $('.submit-form-update-category');
        $submitCategory.each(function () {
            $(this).data('last-value', $(this).val())
        })

        $submitCategory.on('change', function () {
            const lastValue = $(this).data('last-value')
            const sure = confirm("Are you sure?");
            if (sure) {
                const productId = $(this).data('product-id')
                const categoryId = $(this).val()
                window.location.href = 'DispatcherServlet?action=update-category-product&page=${param['page']}&productId='
                    + productId + '&categoryId=' + categoryId + '&Select.CategoryId=${param['Select.CategoryId']}' +
                    '&Select.Status=${param['Select.Status']}';
            } else {
                $(this).val(lastValue)
            }
        })


        const $submitStatus = $('.submit-form-update-status');

        $submitStatus.each(function () {
            $(this).data('last-value', $(this).val())
        })

        $submitStatus.on('change', function () {
            const sure = confirm("Are you sure?");
            if (sure) {
                const productId = $(this).data('product-id')
                const status = $(this).val()
                window.location.href = 'DispatcherServlet?action=update-status-product&page=${param['page']}&productId='
                    + productId + '&status=' + status + '&Select.CategoryId=${param['Select.CategoryId']}' +
                    '&Select.Status=${param['Select.Status']}';
            } else {
                $(this).val($(this).data('last-value'))
            }
        })
    })
</script>
</body>
</html>