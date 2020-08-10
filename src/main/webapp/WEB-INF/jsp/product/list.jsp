<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<u:page title="Список товаров" categories="${categories}">
	<c:url var="deleteUrl" value="/product/delete.html"/>
	<c:url var="noImageUrl" value="/image/no-image.jpg"/>
	<form action="${deleteUrl}" method="post">
		<c:forEach var="item" items="${products}">
			<h3 class="end">Товары категории &laquo;${item.key.name}&raquo;</h3>
			<c:forEach var="product" items="${item.value}">
				<div class="product">
					<h3 class="product__title"><input type="checkbox" name="id" value="${product.id}"> ${product.name}</h3>
					<img class="product__photo" src="${noImageUrl}">
					<div class="product__price">$<fmt:formatNumber value="${product.price / 100.0}" minFractionDigits="2" maxFractionDigits="2"/></div>
					<div class="product__amount">${product.amount}&nbsp;шт.</div>
					<div class="product__date"><fmt:formatDate value="${product.date}" pattern="dd.MM.yyyy"/></div>
					<div>
						<c:url var="editUrl" value="/product/edit.html">
							<c:param name="id" value="${product.id}"/>
						</c:url>
						<a href="${editUrl}" class="product__button">Редактировать</a>
					</div>
				</div>
			</c:forEach>
			<p class="end">Итого товаров в категории&nbsp;&mdash; ${fn:length(item.value)}</p>
		</c:forEach>
		<c:url var="editUrl" value="/product/edit.html"/>
		<a href="${editUrl}" class="form__button">Добавить</a>
		<button type="submit" class="form__button_danger">Удалить</button>
	</form>
</u:page>
