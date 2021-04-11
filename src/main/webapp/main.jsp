<%-- 
    Document   : main
    Created on : Feb 21, 2021, 10:45:35 PM
    Author     : ACER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Main Page</title>
    </head>
    <body>
        <c:set var="role" value="${sessionScope.USER_DTO.role}"/>
        <c:if test="${empty sessionScope.USER_DTO}">
            <jsp:forward page="guest.jsp"/>
        </c:if>
        <c:if test="${role eq 1}">
            <jsp:forward page="admin.jsp"/>
        </c:if>
        <c:if test="${role eq 0}">
            <jsp:forward page="user.jsp"/>
        </c:if>
    </body>
</html>
