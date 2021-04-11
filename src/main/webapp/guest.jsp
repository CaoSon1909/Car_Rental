<%-- 
    Document   : guest
    Created on : Feb 21, 2021, 10:53:30 PM
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
        <title>Guest Page</title>
    </head>
    <body>
        <nav class="navbar navbar-inverse">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand" href="main">Car Rental Service</a>
                </div> 
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="register-page"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
                    <li><a href="login-page"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
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
                <input type="submit" value="Search" name="btAction" />
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
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Name</th>
                            <th>Color</th>
                            <th>Year</th>
                            <th>Category</th>
                            <th>Price</th>
                            <th>Quantity</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="car" items="${requestScope.CAR_LIST}" varStatus="counter">
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
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

            </c:if>
            <c:if test="${empty requestScope.CAR_LIST}">
                There is nothing left!!!
            </c:if>
            <jsp:include page="paging-footer.jsp"/>
        </div>
    </body>
</html>
