<%-- 
    Document   : viewBrewerys
    Created on : 31-Mar-2022, 22:57:54
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
            $('#brewerysTable').DataTable();
        } );
    </script>
    <head>
        <title>View Brewerys</title>
    </head>

    <h1>All Brewerys</h1>
        <table id="brewerysTable" class="display">
            <thead>
                <tr>
                    <th align="left">ID</th>
                    <th align="left">Name</th>
                    <th align="left">City</th>
                    <th align="left">State</th>
                    <th align="left">Country</th>
                    <th align="left">Phone</th>
                    <th align="left">Website</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${brewerys}" var="brewery">
                <tr>
                    <td>${brewery.id}</td>
                    <td>${brewery.name}</a></td>
                    <td>${brewery.city}</td>
                    <td>${brewery.state}</td>
                    <td>${brewery.country}</td>
                    <td>${brewery.phone}</td>
                    <td><a href="${brewery.website}">${brewery.website}</a></td>
                </tr>
                </c:forEach>
            </tbody>
            <tfoot>
                <tr>
                    <th align="left">ID</th>
                    <th align="left">Name</th>
                    <th align="left">City</th>
                    <th align="left">State</th>
                    <th align="left">Country</th>
                    <th align="left">Phone</th>
                    <th align="left">Website</th>
                </tr>
            </tfoot>
        </table>
</html>