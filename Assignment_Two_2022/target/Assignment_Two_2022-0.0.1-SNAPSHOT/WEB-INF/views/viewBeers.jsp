<%-- 
    Document   : viewBeers
    Created on : 31-Mar-2022, 14:37:42
    Author     : calum
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>  


<html>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.min.css">
    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.js"></script>
    <script type="text/javascript">
        $(document).ready( function () {
            $('#beersTable').DataTable();
        } );
    </script>
    <head>
        <title>View Beer</title>
    </head>

    <h1>All Beers</h1>
        <table id="beersTable" class="display">
            <thead>
                <tr>
                    <th align="left">ID</th>
                    <th align="left">Name</th>
                    <th align="left">Buy Price</th>
                    <th align="left">Sell Price</th>
                    <th align="left">ABV</th>
                    <th align="left">IBU</th>
                    <th align="left">SRM</th>
                    <th align="left">Self Link</th>
                    <th align="left">Beer Info</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${beers}" var="beer">
                <tr>
                    <td>${beer.id}</td>
                    <td>${beer.name}</a></td>
                    <td>${beer.buy_price}</td>
                    <td>${beer.sell_price}</td>
                    <td>${beer.abv}</td>
                    <td>${beer.ibu}</td>
                    <td>${beer.srm}</td>
<!-- <%--                   <td><a href="${beer.selfLink}">${beer.selfLink}</a></td>
                    <td><a href="${beer.beerInfo}">${beer.beerInfo}</a></td> --%> -->
                </tr>
                </c:forEach>
            </tbody>
            <tfoot>
            <tr>
                <th align="left">ID</th>
                    <th align="left">Name</th>
                    <th align="left">Buy Price</th>
                    <th align="left">Sell Price</th>
                    <th align="left">ABV</th>
                    <th align="left">IBU</th>
                    <th align="left">SRM</th>
            </tr>
        </tfoot>
        </table>
</html>
