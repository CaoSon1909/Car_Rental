<%-- 
    Document   : welcome-header
    Created on : Feb 21, 2021, 10:12:04 PM
    Author     : ACER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <c:set var="user" value="${sessionScope.USER_DTO.fullName}"/>
        <h1>
            <font color="blue">Car Rental</font></br>
            Welcome , ${user}
        </h1>
        <a href="logout">Log Out</a>
    </body>
</html>
