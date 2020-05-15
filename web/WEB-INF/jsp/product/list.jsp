<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<u:page title="Список товаров">
	<c:url var="deleteUrl" value="/product/delete.html"/>
	<form action="${deleteUrl}" method="post">
		<table class="table">
			<tr>
				<th class="table__header"></th>
				<th class="table__header">Категория</th>
				<th class="table__header">Название</th>
				<th class="table__header">Цена</th>
				<th class="table__header">Количество</th>
				<th class="table__header">Дата добавления</th>
				<th class="table__header"></th>
			</tr>
			<c:forEach var="product" items="${products}">
			<tr>
				<td class="table__cell"><input type="checkbox" name="id" value="${product.id}"></td>
				<td class="table__cell">${product.category.name}</td>
				<td class="table__cell">${product.name}</td>
				<td class="table__cell_numeric">${product.price} коп.</td>
				<td class="table__cell_numeric">${product.amount} шт.</td>
				<td class="table__cell"><fmt:formatDate value="${product.date}" pattern="dd.MM.yyyy"/></td>
				<c:url var="editUrl" value="/product/edit.html">
					<c:param name="id" value="${product.id}"/>
				</c:url>
				<td class="table__cell"><a href="${editUrl}">Редактировать</a></td>
			</tr>
			</c:forEach>
		</table>
		<p>Итого ${fn:length(products)} товаров</p>
		<c:url var="editUrl" value="/product/edit.html"/>
		<a href="${editUrl}" class="form__button">Добавить</a>
		<button type="submit" class="form__button_danger">Удалить</button>
	</form>
</u:page>
