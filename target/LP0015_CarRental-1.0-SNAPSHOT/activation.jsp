<%-- 
    Document   : activation
    Created on : Mar 1, 2021, 11:00:37 PM
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
        <title>Activation Page</title>
    </head>
    <body>

        <nav class="navbar navbar-inverse">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand" href="main">Car Rental Service - Activation Page</a>
                </div>
            </div>
        </nav>
        <form action="activation" method="POST">
            <div class="input-group">
                <span class="input-group-addon"><i class="glyphicon glyphicon-globe"></i></span>
                <input id="email" type="text" class="form-control" name="txtUserEmail" value="${param.userEmail}" style="width: 50%;" />
            </div>
            <div class="input-group">
                <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                <input id="activation-code" type="text" class="form-control" name="txtActivation" value="" placeholder="Your activation code: " required style="width: 50%;" />
            </div>
            <button type="text" class="btn btn-primary">Activate</button>
        </form>
        <font color="red">
        <c:if test="${not empty requestScope.STATUS_EXPIRED_MESSAGE}">
            ${requestScope.STATUS_EXPIRED_MESSAGE}
        </c:if>
        <c:if test="${not empty requestScope.INCORRECT_ACTIVATION_CODE}">
            ${requestScope.INCORRECT_ACTIVATION_CODE}
        </c:if>
        <c:if test="${not empty requestScope.UNAVAILABLE_MESSAGE}">
            ${requestScope.UNAVAILABLE_MESSAGE}
        </c:if>
        </font>
    </body>
</html>
