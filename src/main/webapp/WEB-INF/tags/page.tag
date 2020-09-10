<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="title" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="css" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="simple" required="false" rtexprvalue="true" type="java.lang.Boolean" %>
<%@ attribute name="categories" required="false" rtexprvalue="true" type="java.util.List" %>
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
				<c:if test="${empty simple or not simple}">
					<c:url var="searchUrl" value="/product/search.html"/>
					<form class="search-form" action="${searchUrl}">
						<input id="search-text" type="text" name="query" list="search-text-datalist" autocomplete="off">
						<datalist id="search-text-datalist"></datalist>
						<button type="submit">Найти</button>
					</form>
				</c:if>
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
			<c:choose>
				<c:when test="${not empty categories}">
					<div class="side">
						<div class="side-content">
							<ul class="categories">
								<c:forEach var="category" items="${categories}">
									<c:url var="productListUrl" value="/product/list.html">
										<c:param name="category" value="${category.id}"/>
									</c:url>
									<li><a href="${productListUrl}" class="categories__item">${category.name}</a></li>
								</c:forEach>
							</ul>
						</div>
					</div>
					<div class="main">
						<div class="main-content">
							<c:if test="${not empty title}"><h2 class="page-title">${title}</h2></c:if>
							<jsp:doBody/>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<div class="main-content">
						<c:if test="${not empty title}"><h2 class="page-title">${title}</h2></c:if>
						<jsp:doBody/>
					</div>
				</c:otherwise>
			</c:choose>
			<div class="pusher end"></div>
		</div>
		<div class="footer">&copy; Интернет-магазин &laquo;Всякая всячина&raquo;</div>
	</body>
</html>
