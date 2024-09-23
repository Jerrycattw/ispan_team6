<%@page import="com.rent.bean.RentItem"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>租借訂單項目資料</title>
<link rel="stylesheet" href="../template/template.css">
<link rel="stylesheet" href="../template/table1.css">
<link rel="stylesheet" href="../template/rent.css">
<script src="../template/template.js"></script>

</head>
<body>
	<jsp:include page="../../../HomePage.jsp"></jsp:include>
	<div class="content" id="content">
		<h2>租 借 訂 單 項 目 資 料</h2>
		<form method="GET" class="btn"
			action="/EEIT187-6/tableware/InsertRentItem.html">
			<input type="submit" value="新增訂單">
		</form>
		<form method="get" class="btn"
			action="/EEIT187-6/tableware/SearchRentItem.html">
			<input type="submit" value="搜尋用具">
		</form>
		<form method="get" class="btn"
			action="/EEIT187-6/RentItem/getAll">
			<input type="submit" value="返回全資料">
		</form>
		<table class="table">
			<tr>
				<th>訂單編號
				<th>用具編號
				<th>品項數量
				<th>項目押金
				<th>歸還備註
				<th>歸還狀態(1:未歸還 2:完全歸還 3:部分歸還)
				<th>功能 <%
				List<RentItem> rentItems = (ArrayList<RentItem>) request.getAttribute("rentItem");
				for (RentItem rentItem : rentItems) {
				%>
			<tr class="tr">
				<td><%=rentItem.getRentId()%>
				<td><%=rentItem.getTablewareId()%>
				<td><%=rentItem.getRentItemQuantity()%>
				<td><%=rentItem.getRentItemDeposit()%>
				<td><%=rentItem.getReturnMemo()%>
				<td><%=rentItem.getReturnStatus()%>
				<td>
					<form class="btn" method="get" action="/EEIT187-6/RentItem/get">
    					<input type="submit" value="更新資料" />
					    <input type="hidden" name="rent_id" value="<%=rentItem.getRentId()%>">
					    <input type="hidden" name="tableware_id" value="<%=rentItem.getTablewareId()%>">
					    <input type="hidden" name="action" value="update">
					</form>
					<form class="btn" method="get" id="submitBtn" action="/EEIT187-6/RentItem/get">
					    <input type="submit" value="歸還" />
					    <input type="hidden" name="rent_id" value="<%=rentItem.getRentId()%>">
					    <input type="hidden" name="tableware_id" value="<%=rentItem.getTablewareId()%>">
					    <input type="hidden" name="action" value="restore">
					</form> 
 				<%}%>
 </table>
		<h3>
			共<%=rentItems.size()%>筆訂單項目資料
		</h3>
	</div>
	<script>
		document.addEventListener("DOMContentLoaded", function() {
			showSidebar('rental');
		});
	</script>
</body>
</html>