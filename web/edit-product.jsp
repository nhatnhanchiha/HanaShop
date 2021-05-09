<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nhatn
  Date: 1/8/2021
  Time: 8:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.bac.models.services.ValidatorService" %>
<html>
<head>
    <title>Add product</title>
    <jsp:include page="shared/_BootstrapCss.jsp"/>
    <jsp:include page="shared/_FontAwesome.jsp"/>
    <jsp:include page="shared/_BootstrapSocial.jsp"/>
</head>
<body>
<c:set var="model" value="${requestScope.model}" />
<jsp:include page="shared/_Navbar.jsp"/>
<div class="container">
    <a href="DispatcherServlet?action=log-out" class="btn btn-danger">Log Out</a>
    <h2 class="mt-3">Update product</h2>
    <div class="row">
        <div class="col-md-7">
            <form method="post" action="DispatcherServlet" enctype="multipart/form-data">
                <hr>
                <h4>Update a food/drinks.</h4>
                <img src="${model.product.imageUrl}" alt="${model.product.name}" />
                <input type="hidden" name="productId" value="${model.product.productId}">
                <hr/>
                <div class="form-group">
                    <label for="Input_Name">Name</label>
                    <input class="form-control" type="text" data-val="true"
                           data-val-length="The Name must be at least <%=ValidatorService.MIN_LENGTH_OF_PRODUCT_NAME%> and at max <%=ValidatorService.MAX_LENGTH_OF_PRODUCT_NAME%> characters long."
                           data-val-length-max="<%=ValidatorService.MAX_LENGTH_OF_PRODUCT_NAME%>"
                           data-val-length-min="<%=ValidatorService.MIN_LENGTH_OF_PRODUCT_NAME%>"
                           data-val-required="The Name field is required." id="Input_Name"
                           maxlength="<%=ValidatorService.MAX_LENGTH_OF_PRODUCT_NAME%>"
                           name="Input.Name" value="${model.product.name}"/>
                    <span class="text-danger field-validation-valid" data-valmsg-for="Input.Name"
                          data-valmsg-replace="true"></span>
                </div>
                <div class="form-group">
                    <label for="Input_ShortDescription">Short description</label>
                    <input class="form-control" type="text" data-val="true"
                           data-val-length="The Short description must be at least <%=ValidatorService.MIN_LENGTH_OF_TEXT%> and at max <%=ValidatorService.MAX_LENGTH_OF_SHORT_DESCRIPTION%> characters long."
                           data-val-length-max="<%=ValidatorService.MAX_LENGTH_OF_SHORT_DESCRIPTION%>"
                           data-val-length-min="<%=ValidatorService.MIN_LENGTH_OF_TEXT%>"
                           data-val-required="The Short description field is required." id="Input_ShortDescription"
                           maxlength="<%=ValidatorService.MAX_LENGTH_OF_SHORT_DESCRIPTION%>"
                           name="Input.ShortDescription" value="${model.product.shortDescription}"/>
                    <span class="text-danger field-validation-valid" data-valmsg-for="Input.ShortDescription"
                          data-valmsg-replace="true"></span>
                </div>
                <div class="form-group">
                    <label for="File_Image">New Image</label>
                    <input data-val="true"
                           class="file-upload form-control"
                           accept="image/png, image/jpeg"
                           id="File_Image" name="File.Image" type="file" value/>
                    <span class="text-danger field-validation-valid" data-valmsg-for="File.Image"
                          data-valmsg-replace="true"></span>
                </div>
                <div class="form-group">
                    <label for="Input_LongDescription">Long description</label>
                    <textarea class="form-control" data-val="true"
                              data-val-length="The Long description must be at least <%=ValidatorService.MIN_LENGTH_OF_TEXT%> and at max <%=ValidatorService.MAX_LENGTH_OF_LONG_DESCRIPTION%> characters long."
                              data-val-length-max="<%=ValidatorService.MAX_LENGTH_OF_LONG_DESCRIPTION%>" data-val-length-min="<%=ValidatorService.MIN_LENGTH_OF_TEXT%>"
                              data-val-required="The Long description field is required." id="Input_LongDescription"
                              maxlength="<%=ValidatorService.MAX_LENGTH_OF_LONG_DESCRIPTION%>"
                              name="Input.LongDescription">${model.product.longDescription}</textarea>
                    <span class="text-danger field-validation-valid" data-valmsg-for="Input.LongDescription"
                          data-valmsg-replace="true"></span>
                </div>
                <%--                need validate--%>
                <div class="form-group">
                    <label for="Selected_CategoryId">Category</label>
                    <select class="form-control" id="Selected_CategoryId" name="Selected.CategoryId">
                        <c:forEach var="category" items="${model.categories}">
                            <option value="${category.categoryId}" ${category.categoryId == model.product.categoryId ? 'selected' : ''}>${category.categoryName}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="Input_Price">Price ($)</label>
                    <input class="form-control" data-msg-number="The field Price must be a number."
                           data-msg-range="The field Price must be between <%=ValidatorService.MIN_VALUE_OF_PRICE%> and <%=ValidatorService.MAX_VALUE_OF_PRICE%>."
                           data-msg-required="The Price field is required."
                           data-rule-number="true"
                           data-rule-range="[<%=ValidatorService.MIN_VALUE_OF_PRICE%>,<%=ValidatorService.MAX_VALUE_OF_PRICE%>]"
                           data-rule-required="true"
                           id="Input_Price" name="Input.Price" type="text" value="${model.product.price}"/>
                    <span class="text-danger field-validation-valid" data-valmsg-for="Input.Price"
                          data-valmsg-replace="true"></span>
                </div>
                <div class="form-group">
                    <label for="Input_Quantity">Quantity</label>
                    <input class="form-control" data-msg-required="The Quantity field is required"
                           data-rule-required="true"
                           data-rule-range="[<%=ValidatorService.MIN_VALUE_OF_QUANTITY%>,<%=ValidatorService.MAX_VALUE_OF_QUANTITY%>]"
                           data-msg-range="The field Quantity must be between <%=ValidatorService.MIN_VALUE_OF_QUANTITY%> and <%=ValidatorService.MAX_VALUE_OF_QUANTITY%>."
                           data-rule-intNumber="true" name="Input.Quantity" id="Input_Quantity" value="${model.product.quantity}">
                    <span class="text-danger field-validation-valid" data-valmsg-for="Input.Quantity"
                          data-valmsg-replace="true"></span>
                </div>
                <div class="form-group row">
                    <div class="col-sm-2">Status</div>
                    <div class="col-sm-10">
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" id="Checked_Status"
                                   name="Checked.Status" ${model.product.status ? 'checked' : ''}>
                            <label class="form-check-label" for="Checked_Status">
                                true
                            </label>
                        </div>
                    </div>
                </div>
                <input type="hidden" name="action" value="edit-product">
                <button type="submit" class="btn btn-danger">Save</button>
                <button type="reset" class="btn btn-warning">Reset</button>
            </form>
        </div>
    </div>
</div>
<jsp:include page="shared/_Footer.jsp"/>
<jsp:include page="shared/_Jquery.jsp"/>
<jsp:include page="shared/_BootstrapJs.jsp"/>
<jsp:include page="shared/_JqueryValidation.jsp"/>
<script src="${pageContext.request.contextPath}/shared/jquery.form.js"></script>
<script src="${pageContext.request.contextPath}/shared/additional-methods.js"></script>
</body>
</html>