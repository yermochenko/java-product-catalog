<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:choose>
	<c:when test="${not empty product}">
		<c:set var="title" value="Редактирование товара &laquo;${product.name}&raquo;"/>
	</c:when>
	<c:otherwise>
		<c:set var="title" value="Добавление нового товара"/>
		<jsp:useBean id="product" class="org.itstep.domain.Product"/>
	</c:otherwise>
</c:choose>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>${title}</title>
	</head>
	<body>
		<h1 style="color: green">${title}</h1>
		<c:url var="saveUrl" value="/product/save.html"/>
		<form action="${saveUrl}" method="post">
			<c:if test="${not empty product.id}">
			<input type="hidden" name="id" value="${product.id}">
			</c:if>
			<label for="category">Категория:</label>
			<br>
			<select id="category" name="category">
				<c:forEach var="category" items="${categories}">
					<c:choose>
						<c:when test="${category.id == product.category.id}">
							<c:set var="selected" value="selected"/>
						</c:when>
						<c:otherwise>
							<c:remove var="selected"/>
						</c:otherwise>
					</c:choose>
					<option value="${category.id}" ${selected}>${category.name}</option>
				</c:forEach>
			</select>
			<br>
			<br>
			<label for="name">Название</label>
			<br>
			<input type="text" id="name" name="name" value="${product.name}">
			<br>
			<br>
			<label for="price">Цена</label>
			<br>
			<input type="text" id="price" name="price" value="${product.price}">
			<br>
			<br>
			<label for="amount">Количество</label>
			<br>
			<input type="text" id="amount" name="amount" value="${product.amount}">
			<br>
			<br>
			<button type="submit">Сохранить</button>
		</form>
	</body>
</html>
