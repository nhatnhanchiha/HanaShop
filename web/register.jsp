<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nhatn
  Date: 1/6/2021
  Time: 5:24 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="com.bac.models.services.ValidatorService" %>
<html>
<head>
    <title>Register</title>
    <jsp:include page="shared/_BootstrapCss.jsp"/>
</head>
<body>
<c:set var="model" value="${requestScope.model}"/>
<jsp:include page="shared/_Navbar.jsp"/>
<div class="container">
    <h2>Register</h2>
    <div class="row">
        <div class="col-md-4">
            <form method="post" action="DispatcherServlet">
                <hr>
                <h4>Create a new account.</h4>
                <span class="text-danger">${model.registerAccountDoubleErr}</span>
                <hr/>
                <div class="form-group">
                    <label for="Input_Username">Username</label>
                    <input class="form-control" type="text" data-val="true"
                           data-val-required="The Username field is required."
                           data-val-length="The Password must be at least <%=ValidatorService.MIN_LENGTH_OF_USERNAME%> and at max <%=ValidatorService.MAX_LENGTH_OF_USERNAME%> characters long."
                           data-val-length-max="<%=ValidatorService.MAX_LENGTH_OF_USERNAME%>"
                           data-val-length-min="<%=ValidatorService.MIN_LENGTH_OF_USERNAME%>"
                           id="Input_Username" name="Input.Username"
                           maxlength="<%=ValidatorService.MAX_LENGTH_OF_USERNAME%>"
                           value="${param['Input.Username']}"/>
                    <span class="text-danger field-validation-valid" data-valmsg-for="Input.Username"
                          data-valmsg-replace="true"></span>
                </div>
                <div class="form-group">
                    <label for="Input_Password">Password</label>
                    <input class="form-control" type="password" data-val="true"
                           data-val-length="The Password must be at least <%=ValidatorService.MIN_LENGTH_OF_PASSWORD%> and at max <%=ValidatorService.MAX_LENGTH_OF_PASSWORD%> characters long."
                           data-val-length-max="<%=ValidatorService.MAX_LENGTH_OF_PASSWORD%>" data-val-length-min="<%=ValidatorService.MIN_LENGTH_OF_PASSWORD%>"
                           data-val-required="The Password field is required." id="Input_Password" maxlength="<%=ValidatorService.MAX_LENGTH_OF_PASSWORD%>"
                           name="Input.Password"/>
                    <span class="text-danger field-validation-valid" data-valmsg-for="Input.Password"
                          data-valmsg-replace="true"></span>
                </div>
                <div class="form-group">
                    <label for="Input_ConfirmPassword">Confirm password</label>
                    <input class="form-control" type="password" data-val="true"
                           data-val-equalto="The password and confirmation password do not match."
                           maxlength="<%=ValidatorService.MAX_LENGTH_OF_PASSWORD%>"
                           data-val-equalto-other="*.Password" id="Input_ConfirmPassword" name="Input.ConfirmPassword"/>
                    <span class="text-danger field-validation-valid" data-valmsg-for="Input.ConfirmPassword"
                          data-valmsg-replace="true"></span>
                </div>
                <div class="form-group">
                    <label for="Input_FirstName">First name</label>
                    <input class="form-control" type="text" data-val="true"
                           data-val-length="The First name must be at least <%=ValidatorService.MIN_LENGTH_OF_FIRST_NAME%> and at max <%=ValidatorService.MAX_LENGTH_OF_FIRST_NAME%> characters long."
                           data-val-length-max="<%=ValidatorService.MAX_LENGTH_OF_FIRST_NAME%>" data-val-length-min="<%=ValidatorService.MIN_LENGTH_OF_FIRST_NAME%>"
                           data-val-required="The First name field is required." id="Input_FirstName" maxlength="<%=ValidatorService.MAX_LENGTH_OF_FIRST_NAME%>"
                           name="Input.FirstName" value="${param['Input.FirstName']}">
                    <span class="text-danger field-validation-valid" data-valmsg-for="Input.FirstName"
                          data-valmsg-replace="true"></span>
                </div>
                <div class="form-group">
                    <label for="Input_LastName">Last name</label>
                    <input class="form-control" type="text" data-val="true"
                           data-val-length="The Last name must be at least <%=ValidatorService.MIN_LENGTH_OF_LAST_NAME%> and at max <%=ValidatorService.MAX_LENGTH_OF_LAST_NAME%> characters long."
                           data-val-length-max="<%=ValidatorService.MAX_LENGTH_OF_LAST_NAME%>" data-val-length-min="<%=ValidatorService.MIN_LENGTH_OF_LAST_NAME%>"
                           data-val-required="The Last name field is required." id="Input_LastName" maxlength="<%=ValidatorService.MAX_LENGTH_OF_LAST_NAME%>"
                           name="Input.LastName" value="${param['Input.LastName']}">
                    <span class="text-danger field-validation-valid" data-valmsg-for="Input.LastName"
                          data-valmsg-replace="true"></span>
                </div>
                <input type="hidden" name="action" value="register">
                <button type="submit" class="btn btn-outline-primary">Register</button>
                <button type="reset" class="btn btn-outline-warning">Reset</button>
            </form>
        </div>
    </div>
</div>
<jsp:include page="shared/_Footer.jsp"/>
<jsp:include page="shared/_Jquery.jsp"/>
<jsp:include page="shared/_BootstrapJs.jsp"/>
<jsp:include page="shared/_JqueryValidation.jsp"/>
</body>
</html>
