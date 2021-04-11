<%-- 
    Document   : history
    Created on : Mar 6, 2021, 4:20:01 AM
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
        <title>Order History Page</title>
    </head>
    <body>
        <script type="text/javascript">
            function confirmDelete() {
                let a = confirm("Are you sure?");
                return a;
            }
        </script>
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
        <form action="search-history">
            Search by car name: <input type="text" name="txtCarName" value="${param.txtCarName}" />
            <input type="submit" value="Search" name="btAction" />
        </form> <br/><br/>
        <form action="search-by-order-date">
            Order Date: <input type="text" name="txtOrderDate" value="${param.txtOrderDate}" /> <br/>
            <input type="submit" value="Search" name="btAction" />
        </form>
        <br/>
        <font color="red">${requestScope.MESSAGE}</font> <br/>
        <c:if test="${not empty requestScope.ORDER_LIST}">
            <table class="table">
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>Order ID</th>
                        <th>Sub total</th>
                        <th>Status</th>
                        <th>Order date</th>
                        <th>Discount ID</th>
                        <th>View detail</th>
                        <th>Delete</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="order" items="${requestScope.ORDER_LIST}" varStatus="counter">
                        <tr>
                            <td>${counter.count}</td>
                            <td>${order.ID}</td>
                            <td>${order.subTotal}</td>
                            <td>${order.status}</td>
                            <td>
                                ${order.dateString}
                            </td>
                            <td>${order.discountID}</td>
                            <td>
                                <c:url var="viewDetaill" value="view-details">
                                    <c:param name="txtOrderID" value="${order.ID}"/>
                                </c:url>
                                <a href="${viewDetaill}">View details</a>
                            </td>
                            <td>
                                <c:url var="deleteOrder" value="delete-order">
                                    <c:param name="txtOrderID" value="${order.ID}"/>
                                </c:url>
                                <a href="${deleteOrder}" onclick="return confirmDelete()">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <br/>
        </c:if>
        ${requestScope.NOT_FOUND}
        <c:if test="${empty requestScope.ORDER_LIST}">
            Hmm you haven't bought anything yet. Do you want to <a href="main">buy</a> some?
        </c:if>
        <br/>
        <a href="main">Back to main page</a>
    </body>
</html>
