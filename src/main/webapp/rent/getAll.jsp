<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.rent.bean.Rent"%>
<%@page import="java.util.List"%>
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
	<jsp:include page="../HomePage.html"></jsp:include>
	<div class="content" id="content">
		<h2>租 借 訂 單 資 料</h2>
		<form method="GET" class="btn"
			action="/EEIT187-6/rent/insert.html">
			<input type="submit" value="新增訂單">
		</form>
		<form method="get" class="btn" action="/EEIT187-6/rent/search.html">
			<input type="submit" value="訂單搜尋">
		</form>
		<form method="get" class="btn"
			action="/EEIT187-6/rentController/getOver">
			<input type="submit" value="超時未歸還">
		</form>
		<form method="get" class="btn"
			action="/EEIT187-6/rentController/getAll">
			<input type="submit" value="返回全資料">
		</form>
		<table class="table">
			<tr>
				<th>訂單編號
				<th>租借押金
				<th>租借日期
				<th>租借餐廳
				<th>租借會員
				<th>預定歸還
				<th>實際歸還
				<th>租借狀態(1:出租中 2:已歸還)
				<th>訂單備註
				<th>歸還餐廳
				<th>功能 <%
				List<Rent> rents = (ArrayList<Rent>) request.getAttribute("rent");
				for (Rent rent : rents) {
				%>
			<tr>
				<td><%=rent.getRentId()%>
				<td><%=rent.getRentDeposit()%>
				<td><%=rent.getRentDate()%>
				<td><%=rent.getRestaurantId()%>
				<td><%=rent.getMemberId()%>
				<td><%=rent.getDueDate()%>
				<td><%=rent.getReturnDate()%>
				<td><%=rent.getRentStatus()%>
				<td><%=rent.getRentMemo()%>
				<td><%=rent.getReturnRestaurantId()%>
				<td>
					<form class="btn" method="get"
						action="/EEIT187-6/rentController/getById">
						<input type="submit" value="更新資料" /> 
						<input type="hidden" name="rent_id" value="<%=rent.getRentId()%>">
						<input type="hidden" name="action" value="update">
					</form>
					<form class="btn" method="get"
						action="/EEIT187-6/rentController/getById">
						<input type="submit" value="歸還" /> 
						<input type="hidden" name="rent_id" value="<%=rent.getRentId()%>">
						<input type="hidden" name="action" value="restore">
					</form> 
					<form class="btn" method="get"
						action="/EEIT187-6/rentController/delete">
						<input type="submit" value="刪除" /> 
						<input type="hidden" name="rent_id" value="<%=rent.getRentId()%>">
					</form> 
					<%}%>
				
		</table>
		<h3>
			共<%=rents.size()%>筆訂單資料
		</h3>
	</div>
	<script>
		document.addEventListener("DOMContentLoaded", function() {
			showSidebar('rental');
		});
	</script>
</body>
</html>