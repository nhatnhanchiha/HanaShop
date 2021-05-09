<%@ page import="com.bac.models.services.ValidatorService" %><%--
  Created by IntelliJ IDEA.
  User: nhatn
  Date: 1/17/2021
  Time: 10:54 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
    <jsp:include page="shared/_BootstrapCss.jsp"/>
</head>
<body>
<jsp:include page="shared/_Navbar.jsp"/>
<div class="container">
    <div class="row">
        <div class="col-md-4">
            <form method="post" action="DispatcherServlet">
                <hr>
                <h3>Hello ${sessionScope.firstName}</h3>
                <h4>We need more information. <br> <small>Skip it if you're fine when we call you ${sessionScope.firstName}</small></h4>
                <hr/>
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
                <input type="hidden" name="action" value="add-info-google">
                <button type="submit" class="btn btn-outline-primary">Save</button>
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
