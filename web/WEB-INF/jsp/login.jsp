<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<u:page title="Вход в систему" simple="true">
	<c:if test="${not empty param.message}">
		<div class="message">${param.message}</div>
	</c:if>
	<c:url var="loginUrl" value="/login.html"/>
	<form action="${loginUrl}" method="post" class="form">
		<label class="form__label" for="login">Имя пользователя:</label>
		<input class="form__text-input" type="text" id="login" name="login">
		<label class="form__label" for="password">Пароль:</label>
		<input class="form__text-input" type="password" id="password" name="password">
		<button class="form__button" type="submit">Войти</button>
	</form>
</u:page>
