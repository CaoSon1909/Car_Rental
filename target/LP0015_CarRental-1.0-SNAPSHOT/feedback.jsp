<%-- 
    Document   : feedback
    Created on : Mar 6, 2021, 8:54:02 PM
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
        <title>Feedback Page</title>
    </head>
    <style>
        .dropdown {
            position: relative;
            display: inline-block;
        }

        .dropdown-content {
            display: none;
            position: absolute;
            background-color: #f9f9f9;
            min-width: 160px;
            box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
            padding: 12px 16px;
            z-index: 1;
        }

        .dropdown:hover .dropdown-content {
            display: block;
        }
    </style>
    <body>
        <header>
            <nav class="navbar navbar-inverse">
                <div class="container-fluid">
                    <div class="navbar-header">
                        <a class="navbar-brand" href="main">Car Rental Service - User Page</a>
                    </div>
                    <ul class="nav navbar-nav navbar-right" style="padding-right: 10%;">
                        <div class="dropdown">
                            <span><font color="red">Welcome, ${sessionScope.USER_DTO.fullname}</font></span>
                            <div class="dropdown-content">
                                <div class="list-group">
                                    <a href="show-cart" class="list-group-item">Show your cart</a>
                                    <a href="show-history" class="list-group-item">Show your order history</a>
                                    <a href="logout" class="list-group-item">Log out</a>
                                </div>
                            </div>
                        </div>
                    </ul>
                </div>
            </nav>
        </header>
        <br/> <br/>
        <form action="rating">
            <font color="red">Thank you <3, ${requestScope.EMAIL} </font><br/>
            Car ID: ${requestScope.CAR_ID} <br/>
            Your rating: <input type="text" name="txtScale" value="${param.txtScale}" required="required" pattern="[1-5]"/> <br/>
            <input type="hidden" name="txtEmail" value="${requestScope.EMAIL}" />
            <input type="hidden" name="txtCarID" value="${requestScope.CAR_ID}" />
            <input type="hidden" name="txtOrderID" value="${requestScope.ORDER_ID}" />
            <input type="submit" value="Submit" name="btAction" />
        </form>
        <br/>

        <font color="red">${requestScope.MESSAGE}<font>

        <c:url var="viewDetail" value="view-details">
            <c:param name="txtOrderID" value="${requestScope.ORDER_ID}"/>
        </c:url>
        <a href="${viewDetail}">Back</a>
    </body>
</html>
