<%@page contentType="text/html" pageEncoding="utf-8"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Order coffee</title>
    <link rel="stylesheet" href="/coffee-shop/style.css">
</head>
<body>

<h1>Order coffee</h1>

<p><a href="/coffee-shop/coffee/index.html">Show all orders</a></p>

<c:if test="${failed}">
<p class="error">Could not create the order. Please select all values properly.</p>
</c:if>

<form action="/coffee-shop/coffee/order.html" method="post">
    <div class="grid">
        <select name="type" required="required">
            <option value="">Select type</option>
            <c:forEach var="type" items="${types}">
            <option value="${type.name()}">${type.description}</option>
            </c:forEach>
        </select>
        <select name="origin" disabled="disabled" required="required">
            <option value="">Select origin</option>
        </select>
        <button disabled="disabled" type="submit">Create</button>
    </div>
</form>

<script src="/coffee-shop/form.js"></script>
</body>
</html>