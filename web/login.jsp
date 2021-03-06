<%--
  Created by IntelliJ IDEA.
  User: nhatn
  Date: 1/6/2021
  Time: 9:42 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <jsp:include page="shared/_BootstrapCss.jsp"/>
    <link rel="stylesheet" href="shared/google-button.css">
</head>
<body>
<jsp:include page="shared/_Navbar.jsp"/>
<div class="container">
    <div class="row">
        <form action="DispatcherServlet" method="post">
            <hr>
            <h4>Login to buy something.</h4>
            <h5 class="text-danger">${requestScope.message}</h5>
            <hr>
            <div class="form-group">
                <label for="Input_Username">Username</label>
                <input class="form-control" type="text" id="Input_Username" name="Input.Username"/>
            </div>
            <div class="form-group">
                <label for="Input_Password">Password</label>
                <input class="form-control" type="password" id="Input_Password" name="Input.Password"/>
            </div>
            <input type="hidden" value="login" name="action">
            <button type="submit" class="btn btn-outline-primary">Login</button>
            <div class="google-btn mt-3">
                <a href="https://accounts.google.com/o/oauth2/auth?scope=email&redirect_uri=http://localhost:8080/HanaShop/DispatcherServlet?action=login-google&response_type=code
    &client_id=569642017800-5n5pu8r3jp7h64aspqthfdsnk1qputf4.apps.googleusercontent.com&approval_prompt=force">
                    <div class="google-icon-wrapper">
                        <img class="google-icon"
                             src="https://upload.wikimedia.org/wikipedia/commons/5/53/Google_%22G%22_Logo.svg"/>
                    </div>
                    <p class="btn-text"><b>Sign in with google</b></p>
                </a>
            </div>
        </form>
        <%-- <a href="https://accounts.google.com/o/oauth2/auth?scope=email&redirect_uri=http://localhost:8080/HanaShop/DispatcherServlet?action=login-google&response_type=code
     &client_id=569642017800-5n5pu8r3jp7h64aspqthfdsnk1qputf4.apps.googleusercontent.com&approval_prompt=force">Login With Google</a>--%>

    </div>
</div>
<jsp:include page="shared/_Footer.jsp"/>
<jsp:include page="shared/_Jquery.jsp"/>
<jsp:include page="shared/_BootstrapJs.jsp"/>
</body>
</html>
