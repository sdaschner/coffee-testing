<%@page contentType="text/html" pageEncoding="utf-8"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Coffee orders</title>
    <link rel="stylesheet" href="/coffee-shop/style.css">
</head>
<body>

<h1>All coffee orders</h1>

<p><a href="/coffee-shop/coffee/order.html">Create coffee order</a></p>

<table>
    <tr>
        <th>Status</th>
        <th>Type</th>
        <th>Origin</th>
    </tr>
    <c:forEach var="order" items="${orders}">
    <tr>
        <td>${order.status.name()}</td>
        <td>${order.type.description}</td>
        <td>${order.origin.name}</td>
    </tr>
    </c:forEach>
</table>

</body>
</html>