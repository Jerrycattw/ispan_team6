<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.rent.bean.TablewareStock"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>環保用具庫存資料</title>
<link rel="stylesheet" href="../template/template.css">
<link rel="stylesheet" href="../template/table1.css">
<link rel="stylesheet" href="../template/rent.css">
<script src="../template/template.js"></script>
</head>
<body>
	<jsp:include page="../HomePage.html"></jsp:include>
	<div class="content" id="content">
		<h2>用 具 庫 存 資 料</h2>
		<form method="GET" class="btn"
			action="/EEIT187-6/tableware_stock/insert.html">
			<input type="submit" value="新增用具庫存">
		</form>
		<form method="get" class="btn"
			action="/EEIT187-6/tableware_stock/search.html">
			<input type="submit" value="搜尋用具庫存">
		</form>
		<form method="get" class="btn"
			action="/EEIT187-6/tablewareStockController/getAll">
			<input type="submit" value="返回全資料">
		</form>
		<table class="table">
			<tr>
				<th>用具編號
				<th>餐廳編號
				<th>用具庫存
				<th>功能 <%
				List<TablewareStock> tablewareStocks = (ArrayList<TablewareStock>) request.getAttribute("stock");
				for (TablewareStock tablewareStock : tablewareStocks) {
				%>
			<tr>
				<td><%=tablewareStock.getTablewareId()%>
				<td><%=tablewareStock.getRestaurantId()%>
				<td><%=tablewareStock.getStock()%>
				<td>
					<form class="update" method="get"
						action="/EEIT187-6/tablewareStockController/get">
						<input type="submit" value="庫存調整" /> <input type="hidden"
							name="restaurant_id"
							value="<%=tablewareStock.getRestaurantId()%>"> <input
							type="hidden" name="tableware_id"
							value="<%=tablewareStock.getTablewareId()%>">
					</form> <%
 }
 %>
				
		</table>
		<h3>
			共<%=tablewareStocks.size()%>筆環保用具庫存資料
		</h3>
	</div>
		<script>
		document.addEventListener("DOMContentLoaded", function() {
			showSidebar('rental');
		});
	</script>
</body>
</html>