<%-- 
    Document   : cart
    Created on : Mar 4, 2021, 12:30:37 PM
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
        <title>Cart Page</title>
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
        <div class="container">
            <c:set var="cart" value="${requestScope.CAR_IN_CART}"/>
            <c:if test="${not empty cart}">
                <c:if test="${not empty requestScope.UNVAILABLE_CAR_ID}">
                    The car has ID: ${requestScope.UNVAILABLE_CAR_ID} is unavailable. Please remove this item
                </c:if>
                <table class="table">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Car ID</th>
                            <th>Quantity</th>
                            <th>Price</th>
                            <th>Total</th>
                            <th>Update quantity</th>
                            <th>Remove</th>

                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="map" items="${requestScope.CART_MAP}" varStatus="counter">
                            <c:set var="cartDTO" value="${map.key}"/>
                        <form action="action-in-cart">
                            <tr>
                                <td>${counter.count}</td>
                                <td>
                                    ${cartDTO.carID}
                                </td>
                                <td>
                                    <input type="number" name="txtQuantity" value="${cartDTO.quantity}" min="1" max="${map.value}" required="required"/>
                                </td>
                                <td>${cartDTO.price}</td>
                                <td>${cartDTO.quantity * cartDTO.price}</td>
                                <td>
                                    <input type="submit" value="Update Quantity" name="btAction" />
                                    <input type="hidden" name="txtCartID" value="${cartDTO.ID}" />
                                    <input type="hidden" name="txtCarID" value="${cartDTO.carID}" />
                                </td>
                                <td>
                                    <input type="submit" value="Remove" name="btAction" />
                                    <input type="hidden" name="txtCarID" value="${cartDTO.carID}" />
                                </td>
                            </tr>
                        </form>
                    </c:forEach>

                    <tr style="text-align: left;">
                        <td>
                            Subtotal(${requestScope.SUM_OF_QUANTITY} items): $${requestScope.SUM_OF_CART} 
                        </td>
                        <td>
                            <form action="confirm">
                                <input type="submit" value="Confirm" name="btAction"/>
                            </form>
                        </td>
                    </tr>
                    </tbody>
                </table>

            </c:if>
            <c:if test="${empty cart}">
                Hmm you don't have any car in your cart!!
            </c:if>
            <a href="main">Back to main page</a>
        </div>
    </body>
</html>
