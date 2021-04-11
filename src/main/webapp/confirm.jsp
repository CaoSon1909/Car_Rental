<%-- 
    Document   : confirm
    Created on : Mar 5, 2021, 7:33:33 AM
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
        <title>Confirm Page</title>
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
        <br/>
        <font color="red"><h3>Please confirm your cart again</h3> </font>
        <c:if test="${not empty requestScope.CART_DETAIL_LIST}">
            <form action="checkout">
                User email: <font color="red">${sessionScope.USER_DTO.fullname}</font> <br/>
                Start date: <input type="text" name="txtStartDate" value="${requestScope.TODAY}" readonly="readonly"/>  <br/>
                SubTotal: <input type="text" name="txtSubTotal" value="${requestScope.SUB_TOTAL}" readonly="readonly" />  <br/>
                <input type="submit" value="Confirm to checkout" name="btAction" />
                <input type="hidden" name="txtDiscount" value="${requestScope.DISCOUNT_CODE}" />
            </form>
            <form action="discount">
                Discount Code: <input type="text" name="txtDiscountCode" value="" />
                <input type="hidden" name="txtSubTotal" value="${requestScope.SUB_TOTAL}" min="1" max="65536" required="required"/>
                <input type="submit" value="Use" name="btAction" />
            </form>
            <c:if test="${not empty requestScope.DISCOUNT_MESSAGE}">
                ${requestScope.DISCOUNT_MESSAGE}
            </c:if>
            <table class="table">
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>Car ID</th>
                        <th>Quantity</th>
                        <th>Price</th>
                        <th>Total</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="carInCart" items="${requestScope.CART_DETAIL_LIST}" varStatus="counter">
                        <tr>
                            <td>${counter.count}</td>
                            <td>${carInCart.carID}</td>
                            <td>${carInCart.quantity}</td>
                            <td>${carInCart.price}</td>
                            <td>${carInCart.price * carInCart.quantity}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        ${requestScope.SUCCESS_MESSAGE}
        <br/>
        <c:if test="${empty requestScope.CART_DETAIL_LIST}">
            You cart is clear! <br/>
            <a href="main">Back to main page</a>
        </c:if>

    </body>
</html>
