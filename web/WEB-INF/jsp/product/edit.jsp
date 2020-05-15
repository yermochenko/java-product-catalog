<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<c:choose>
	<c:when test="${not empty product}">
		<c:set var="title" value="Редактирование товара &laquo;${product.name}&raquo;"/>
	</c:when>
	<c:otherwise>
		<c:set var="title" value="Добавление нового товара"/>
		<jsp:useBean id="product" class="org.itstep.domain.Product"/>
	</c:otherwise>
</c:choose>

<u:page title="${title}">
	<c:url var="saveUrl" value="/product/save.html"/>
	<form action="${saveUrl}" method="post" class="form">
		<c:if test="${not empty product.id}">
			<input type="hidden" name="id" value="${product.id}">
		</c:if>

		<label class="form__label" for="category">Категория:</label>
		<select class="form__text-input" id="category" name="category">
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

		<label class="form__label" for="name">Название:</label>
		<input class="form__text-input" type="text" id="name" name="name" value="${product.name}">

		<label class="form__label" for="price">Цена:</label>
		<input class="form__text-input" type="text" id="price" name="price" value="${product.price}">

		<label class="form__label" for="amount">Количество:</label>
		<input class="form__text-input" type="text" id="amount" name="amount" value="${product.amount}">

		<button class="form__button" type="submit">Сохранить</button>
		<c:url var="listUrl" value="/product/list.html"/>
		<a class="form__button" href="${listUrl}">Отмена</a>
	</form>
</u:page>
