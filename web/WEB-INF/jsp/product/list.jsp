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
		<c:url var="deleteUrl" value="/product/delete.html"/>
		<form action="${deleteUrl}" method="post">
		<table border="1">
			<tr>
				<th></th>
				<th>Категория</th>
				<th>Название</th>
				<th>Цена</th>
				<th>Количество</th>
				<th>Дата добавления</th>
				<th></th>
			</tr>
			<c:forEach var="product" items="${products}">
			<tr>
				<td><input type="checkbox" name="id" value="${product.id}"></td>
				<td>${product.category.name}</td>
				<td>${product.name}</td>
				<td>${product.price} коп.</td>
				<td>${product.amount} шт.</td>
				<td>${product.date}</td>
				<c:url var="editUrl" value="/product/edit.html">
					<c:param name="id" value="${product.id}"/>
				</c:url>
				<td><a href="${editUrl}">Редактировать</a></td>
			</tr>
			</c:forEach>
		</table>
		<p>Итого ${products.size()} товаров</p>
		<c:url var="editUrl" value="/product/edit.html"/>
		<p><a href="${editUrl}">Добавить</a></p>
		<p><button type="submit">Удалить</button></p>
		</form>
	</body>
</html>
