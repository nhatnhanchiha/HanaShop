<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.bac.models.services.ValidatorService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form action="#" method="get" class="row" onsubmit="return handleClick(event)">
    <div class="form-group col-4">
        <input type="text" name="Input.SearchValue" id="Input_SearchValue" placeholder="Name of food"
               class="form-control" data-val="true"
               data-val-length="The Name value must be at least <%=ValidatorService.MIN_LENGTH_OF_TEXT%> and at max <%=ValidatorService.MAX_LENGTH_OF_TEXT%> characters long."
               data-val-length-max="<%=ValidatorService.MAX_LENGTH_OF_TEXT%>" data-val-length-min="<%=ValidatorService.MIN_LENGTH_OF_TEXT%>"
               maxlength="50" value="${param['Input.SearchValue']}">
        <span class="text-danger field-validation-valid" data-valmsg-for="Input.SearchValue"
              data-valmsg-replace="true"></span>
    </div>
<%--    Need handle--%>
    <div class="form-group col-2">
        <select type="text" name="Select.Category" id="Select_Category" class="form-control">
            <option value="0">All</option>
            <c:forEach items="${requestScope.model.categories}" var="category">
                <option value="${category.categoryId}" ${category.categoryId == param['Select.Category'] ? 'selected' : ''}> ${category.categoryName}</option>
            </c:forEach>
        </select>
    </div>
    <div class="form-group col-2">
        <input type="text" name="Input.MinPrice" id="Input_Min" class="form-control"
               placeholder="Min Price" value="${param['Input.MinPrice']}">
    </div>
    <div class="form-group col-2">
        <input type="text" name="Input.MaxPrice" id="Input_Max" class="form-control"
               placeholder="Max Price" value="${param['Input.MaxPrice']}">
    </div>
    <div class="col-2">
        <input type="hidden" name="action" value="user-search-product">
        <button type="submit" class="btn btn-info">Search</button>
    </div>
    <h5 id="error" class="text-danger"></h5>
</form>
<script>
    function handleClick(e) {
        const $min = $('#Input_Min')
        const $max = $('#Input_Max')
        if (($min.val() > $max.val()) && max.val() !== '') {
            e.preventDefault()
            $('#error').text('Min price phải nhỏ hơn hoặc bằng Max price')
            return false
        }
    }
</script>