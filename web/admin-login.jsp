<%--
  Created by IntelliJ IDEA.
  User: nhatn
  Date: 1/14/2021
  Time: 10:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Admin login</h1>
<h3 style="color: red">${requestScope.message}</h3>
<form action="AuthenticationAdminServlet" method="post">
    <table>
        <tr>
            <td><label for="admin-username">Username</label></td>
            <td><input type="text" name="Input.AdminUsername" id="admin-username"></td>
        </tr>
        <tr>
            <td><label for="admin-password">Password</label></td>
            <td><input type="password" name="Input.AdminPassword" id="admin-password"></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" value="Login">
                <input type="reset" value="Reset"></td>
        </tr>
    </table>
</form>
</body>
</html>
