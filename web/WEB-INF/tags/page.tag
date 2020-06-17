<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="title" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="css" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>
			Интернет-магазин &laquo;Всякая всячина&raquo;
			<c:if test="${not empty title}">:: ${title}</c:if>
		</title>
		<c:url var="mainCssUrl" value="/main.css"/>
		<link rel="stylesheet" href="${mainCssUrl}" type="text/css">
		<c:if test="${not empty css}">
			<link rel="stylesheet" href="${css}" type="text/css">
		</c:if>
		<c:url var="searchJsonUrl" value="/product/search.json"/>
		<script type="text/javascript">
			var searchUrl = '${searchJsonUrl}';
		</script>
		<c:url var="searchJsUrl" value="/script/dynamic-search.js"/>
		<script type="text/javascript" src="${searchJsUrl}"></script>
	</head>
	<body>
		<div class="wrapper">
			<div class="header">
				<form class="search-form" action="#">
					<input id="search-text" type="text" name="search" list="search-text-datalist" autocomplete="off">
					<datalist id="search-text-datalist"></datalist>
					<button type="submit">Найти</button>
				</form>
				<c:if test="${not empty sessionUser}">
					<ul class="main-menu">
						<!-- TODO: add main menu -->
						<c:url var="logoutUrl" value="/logout.html"/>
						<li class="main-menu__item"><a class="main-menu__item_link" href="${logoutUrl}">выход</a></li>
						<li class="end"></li>
					</ul>
				</c:if>
				<h1 class="site-title">Интернет-магазин &laquo;Всякая всячина&raquo;</h1>
			</div>
			<!-- TODO: add side block -->
			<div class="main-content">
				<c:if test="${not empty title}"><h2 class="page-title">${title}</h2></c:if>
				<jsp:doBody/>
			</div>
			<div class="pusher end"></div>
		</div>
		<div class="footer">&copy; Интернет-магазин &laquo;Всякая всячина&raquo;</div>
	</body>
</html>
