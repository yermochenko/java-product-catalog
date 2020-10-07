<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<u:page title="Ошибка">
	<c:choose>
		<c:when test="${not empty exception}">
			<p>Ошибка работы с базой данных</p>
		</c:when>
		<c:when test="${pageContext.errorData.statusCode == 404}">
			<p>Запрошенная страница не найдена на сервере</p>
		</c:when>
		<c:when test="${pageContext.errorData.statusCode == 400}">
			<p>Ошибочные данные в запросе</p>
		</c:when>
		<c:otherwise>
			<p>Непредвиденная ошибка работы приложения</p>
		</c:otherwise>
	</c:choose>
	<c:url var="mainUrl" value="/index.html"/>
	<p><a href="${mainUrl}" class="form__button">Назад</a></p>
</u:page>
