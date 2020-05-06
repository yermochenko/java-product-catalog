<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>New product</title>
	</head>
	<body>
		<h1 style="color: green">New product</h1>
		<form action="save.html" method="get">
			<label for="category">Категория:</label>
			<br>
			<select id="category" name="category">
				<c:forEach var="category" items="${categories}">
				<option value="${category.id}">${category.name}</option>
				</c:forEach>
			</select>
			<br>
			<br>
			<label for="name">Название</label>
			<br>
			<input type="text" id="name" name="name">
			<br>
			<br>
			<label for="price">Цена</label>
			<br>
			<input type="text" id="price" name="price">
			<br>
			<br>
			<label for="amount">Количество</label>
			<br>
			<input type="text" id="amount" name="amount">
			<br>
			<br>
			<button type="submit">Сохранить</button>
		</form>
	</body>
</html>
