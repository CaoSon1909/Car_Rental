<%-- 
    Document   : user
    Created on : Feb 21, 2021, 10:49:27 PM
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
        <title>User Page</title>
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
        <br/> <br/>
        <div class="container">
            <form class="form-inline" action="search">
                <input id="searchname" type="hidden" name="txtSearchType" value="search-by-name" />
                <div class="form-group">
                    <label for="searchname">Search car name:</label>
                    <input id="searchname" type="text" name="txtSearchValue" value="${param.txtSearchValue}" />
                </div>
                <button type="submit" class="btn btn-default" name="btAction" value="Search">Search</button>
            </form>
            <br/>
            <form class="form-inline" action="search">
                <input type="hidden" name="txtSearchType" value="search-by-category-and-rental-date" />
                <div class="form-group">
                    <label for="cate">Category:</label>
                    <select id="cate" name="txtCategory">
                        <c:forEach var="category" items="${requestScope.CATEGORY_LIST}">
                            <option value="${category.ID}">${category.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="rentaldate">Create year:</label>
                    <input id="rentaldate" type="text" name="txtYear" value="${param.txtYear}" pattern="^[0-9]{1,4}$" min="1990" max="2020" required="required"/>
                </div>
                <input type="submit" value="Search" name="btAction" />
            </form>
            <br/>
            <br/>
            <c:if test="${not empty requestScope.CAR_LIST}">
                <table class="table">
                    <caption>List of available cars</caption>
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Name</th>
                            <th>Color</th>
                            <th>Year</th>
                            <th>Category</th>
                            <th>Price</th>
                            <th>Quantity</th>
                            <th>Add to cart</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="car" items="${requestScope.CAR_LIST}" varStatus="counter">
                        <form action="lease">
                            <tr>
                                <td>${counter.count}</td>
                                <td>${car.name}</td>
                                <td>${car.color}</td>
                                <td>${car.createYear}</td>
                                <td>
                                    <c:if test="${car.categoryID eq 1}">
                                        Toyota
                                    </c:if>
                                    <c:if test="${car.categoryID eq 2}">
                                        Audi
                                    </c:if>
                                    <c:if test="${car.categoryID eq 3}">
                                        Lamborghini
                                    </c:if>
                                    <c:if test="${car.categoryID eq 4}">
                                        Ford
                                    </c:if>
                                    <c:if test="${car.categoryID eq 5}">
                                        Mercedes_Benz
                                    </c:if>
                                </td>
                                <td>${car.price}</td>
                                <td>${car.currentQuantity}</td>
                                <td>
                                    <input type="hidden" name="txtCarID" value="${car.ID}" />
                                    <input type="submit" value="Add to cart" name="btAction" />
                                </td>
                            </tr>
                        </form>
                    </c:forEach>
                    </tbody>
                </table>

            </c:if>
            <c:if test="${empty requestScope.CAR_LIST}">
                There is nothing to show
            </c:if>
            <jsp:include page="paging-footer.jsp"/>
        </div>
    </body>
</html>
