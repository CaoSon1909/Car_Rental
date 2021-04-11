<%-- 
    Document   : paging-footer
    Created on : Feb 22, 2021, 12:56:05 AM
    Author     : ACER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page session="false"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    </head>
    <body>
        <c:if test="${not empty requestScope.PRE_PAGE}">
            <c:url var="previousPage" value="search">
                <c:param name="page" value="${requestScope.PRE_PAGE}"/>
                <c:param name="txtSearchType" value="${param.txtSearchType}"/>
                <c:param name="txtSearchValue" value="${param.txtSearchValue}"/>
                <c:param name="txtRentalDate" value="${param.txtRentalDate}"/>
            </c:url>
            <ul class="pager">
                <li class="previous"> <a href="${previousPage}">Previous</a></li>
            </ul>
        </c:if>
        <c:if test="${not empty requestScope.NEXT_PAGE}">
            <c:url var="nextPage" value="search">
                <c:param name="page" value="${requestScope.NEXT_PAGE}"/>
                <c:param name="txtSearchType" value="${param.txtSearchType}"/>
                <c:param name="txtSearchValue" value="${param.txtSearchValue}"/>
                <c:param name="txtRentalDate" value="${param.txtRentalDate}"/>
                <c:param name="btAction" value="Search"/>
            </c:url>
            <ul class="pager">
                <li class="next"><a href="${nextPage}">Next</a></li>
            </ul>
        </c:if>
        <p class="text-warning" style="text-align: center">Page ${requestScope.PAGE}</p>
    </body>
</html>






