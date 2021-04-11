<%-- 
    Document   : register
    Created on : Feb 21, 2021, 11:02:11 PM
    Author     : ACER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--bootstrap 3.3.5 cdn-->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
        <title>Register Page</title>
    </head>
    <body>
        <nav class="navbar navbar-inverse">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand" href="main">Car Rental Service - Register Page</a>
                </div>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="login-page"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
                </ul>
            </div>
        </nav>
        <form action="register" method="POST" onsubmit="validate()">
            <div class="input-group">
                <span class="input-group-addon"><i class="glyphicon glyphicon-globe"></i></span>
                <input id="email" type="text" class="form-control" name="txtEmail" value="${param.txtEmail}" placeholder="Ex: example@gmail.com" pattern="^([\w!.%+\-])+@([\w\-])+(?:\.[\w\-]+)+$" required style="width: 50%;" />
            </div>
            <font color="red">
            <c:if test="${not empty requestScope.ERROR['EMAIL_REGEX_ERR']}">
                ${requestScope.ERROR['EMAIL_REGEX_ERR']}
            </c:if>
            </font> <br/>
            <div class="input-group">
                <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                <input id="password" type="password" class="form-control" name="txtPassword" placeholder="Password at least 8 characters" minlength="8" required style="width: 50%;">
            </div>
            <font color="red">
            <c:if test="${not empty requestScope.ERROR['PASSWORD_LENGTH_ERR']}">
                ${requestScope.ERROR['PASSWORD_LENGTH_ERR']}
            </c:if>
            </font> <br/>
            <div class="input-group">
                <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                <input id="confirm_password" type="password" class="form-control" name="txtConfirm" placeholder="Confirm your password" minlength="8" required style="width: 50%;">
            </div>
            <c:if test="${not empty requestScope.ERROR['CONFIRM_PASS_ERR']}">
                ${requestScope.ERROR['CONFIRM_PASS_ERR']}
            </c:if>
            </font> <br/>
            <div id="message"></div>
            <div class="input-group">
                <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                <input id="fullname" type="text" class="form-control" name="txtFullName" value="${param.txtFullName}" placeholder="Your full name" minlength="1" maxlength="150" required style="width: 50%;">
            </div>
            <c:if test="${not empty requestScope.ERROR['FULL_NAME_ERR']}">
                ${requestScope.ERROR['FULL_NAME_ERR']}
            </c:if>
            </font> <br/>
            <div class="input-group">
                <span class="input-group-addon"><i class="glyphicon glyphicon-phone"></i></span>
                <input id="phonenumber" type="text" class="form-control" name="txtPhone" value="${param.txtPhone}" placeholder="Your phone number" minlength="10" pattern="[0-9]{10,11}" required style="width: 50%;">
            </div>
            <c:if test="${not empty requestScope.ERROR['DUPLICATE_EMAIL']}">
                ${requestScope.ERROR['DUPLICATE_EMAIL']}
            </c:if>
            </font> <br/>
            <div class="input-group">
                <span class="input-group-addon"><i class="glyphicon glyphicon-home"></i></span>
                <input id="address" type="text" class="form-control" name="txtAddress" value="${param.txtAddress}" placeholder="Your address" required style="width: 50%;">
            </div>
            <button type="text" class="btn btn-primary">Register</button>
            <button type="reset" class="btn btn-default">Reset</button>
        </form>
    </body>
</html>
