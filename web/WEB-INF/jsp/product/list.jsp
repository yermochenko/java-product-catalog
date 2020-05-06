<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Products list</title>
	</head>
	<body>
		<h1 style="color: green">Products list</h1>
		<table border="1">
			<tr>
				<th>Категория</th>
				<th>Название</th>
				<th>Цена</th>
				<th>Количество</th>
				<th>Дата добавления</th>
			</tr>
			<c:forEach var="product" items="${products}">
			<tr>
				<td>${product.category.name}</td>
				<td>${product.name}</td>
				<td>${product.price} коп.</td>
				<td>${product.amount} шт.</td>
				<td>${product.date}</td>
			</tr>
			</c:forEach>
		</table>
		<p>Итого ${products.size()} товаров</p>
		<p><a href="add.html">Добавить</a></p>
	</body>
</html>
