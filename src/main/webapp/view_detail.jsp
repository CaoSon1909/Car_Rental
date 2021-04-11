<%-- 
    Document   : view_detail
    Created on : Mar 6, 2021, 8:19:20 PM
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
        <title>View Detail Page</title>
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
        <c:if test="${not empty requestScope.ORDER_DETAIL}">
            <table class="table">
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>Order Id</th>
                        <th>Car Id</th>
                        <th>Quantity</th>
                        <th>Price</th>
                        <th>Take feedback</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="orderdetail" items="${requestScope.ORDER_DETAIL}" varStatus="counter">
                        <tr>
                            <td>${counter.count}</td>
                            <td>${orderdetail.orderID}</td>
                            <td>${orderdetail.carID}</td>
                            <td>${orderdetail.quantity}</td>
                            <td>${orderdetail.price}</td>
                            <td>
                                <c:url var="show_feed_back_form" value="show-feed-back-page">
                                    <c:param name="txtCarID" value="${orderdetail.carID}"/>
                                    <c:param name="txtEmail" value="${sessionScope.USER_DTO.email}"/>
                                    <c:param name="txtOrderID" value="${orderdetail.orderID}"/>
                                </c:url>
                                <a href="${show_feed_back_form}">Take feedback</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        <c:if test="${empty requestScope.ORDER_DETAIL}">
            This order do not contain any order details because of errors.
        </c:if>
        <a href="show-history">Back to history page</a>
    </body>
</html>
