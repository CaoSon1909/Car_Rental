<%-- 
    Document   : user-activation
    Created on : Mar 1, 2021, 3:08:52 PM
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
        <title>User Activate Page</title>
    </head>
    <body>
        <nav class="navbar navbar-inverse">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand" href="main">Car Rental Service - Confirm Activation Account Page</a>
                </div>
            </div>
        </nav>
        <ul class="list-group">
            <li class="list-group-item list-group-item-success">Email:${requestScope.USER_EMAIL}</li>
            <li class="list-group-item list-group-item-info">Password:${requestScope.USER_PASSWORD}</li>
            <li class="list-group-item list-group-item-warning">Full name:${requestScope.USER_FULL_NAME}</li>
            <li class="list-group-item list-group-item-danger">Phone number:${requestScope.USER_PHONE}</li>
            <li class="list-group-item list-group-item-success">Address: ${requestScope.USER_ADDERSS}</li>
            <li class="list-group-item list-group-item-info">Activation code:${requestScope.TOKEN_STRING}</li>
        </ul>

        <form action="activatation-page" method="POST">
            <input type="hidden" name="userEmail" value="${requestScope.USER_EMAIL}" />
            <button type="text" class="btn btn-primary">Go to activation page</button>
        </form>

        <a href="login-page" style="float: right;">Back to login page</a> 
    </body>
</html>
