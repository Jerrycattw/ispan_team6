<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>租借訂單</title>
<link rel="stylesheet" href="../template/template.css">
<link rel="stylesheet" href="../template/table1.css">
<link rel="stylesheet" href="../template/rent.css">
<script src="../template/template.js"></script>

</head>
<body>
	<jsp:include page="../../../HomePage.jsp"></jsp:include>
	<div class="content" id="content">
		<h2>輸入搜尋庫存資料</h2>
		<form method="get" class="btn"
			action="/EEIT187-6/TablewareStock/getAll">
			<input type="submit" value="返回">
		</form>
		<form method="get" action="/EEIT187-6/TablewareStock/search">
			<table>
				<tr>
					<td>用具編號 
					<select name="tablewareId" id="tablewareId">
							<option value="" selected disabled>請選擇餐具編號</option>
							<c:forEach items="${tablewareIds}" var="tablewareId">
								<option value="${tablewareId}">${tablewareId}</option>
							</c:forEach>
					</select>
					</td>
				</tr>
				<tr>
					<td>餐廳編號 
					<select name="restaurantName" id="restaurantName">
							<option value="" selected disabled>請選擇餐廳名稱</option>
							<c:if test="${not empty param.restaurantName}">
								<option value="${param.restaurantName}" selected>${param.restaurantName}</option>
							</c:if>
							<c:forEach items="${restaurantNames}" var="restaurantName">
								<option value="${restaurantName}">${restaurantName}</option>
							</c:forEach>
					</select>
					</td>
				</tr>
			</table>
			<input type="submit" value="確定" />
		</form>
			<script>
		document.addEventListener("DOMContentLoaded", function() {
			showSidebar('rental');
		});
	</script>
</body>
</html>
