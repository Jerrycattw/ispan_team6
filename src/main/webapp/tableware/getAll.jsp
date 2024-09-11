<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.ArrayList"%>
<%@page import="com.rent.bean.Tableware"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>環保用具資料</title>
<link rel="stylesheet" href="../template/template.css">
<link rel="stylesheet" href="../template/table1.css">
<link rel="stylesheet" href="../template/rent.css">
<script src="../template/template.js"></script>
</head>
<body >
<jsp:include page="../HomePage.html"></jsp:include>
	<div class="content" id="content">
	<h2>環 保 用 具 資 料</h2>
	<form method="get" class="btn" action="/EEIT187-6/tableware/insert.html">
	    <input type="submit" value="新增用具">
	</form>
	<form method="get" class="btn" action="/EEIT187-6/tableware/search.html">
	    <input type="submit" value="搜尋用具">
	</form>
	<form method="get" class="btn" action="/EEIT187-6/tablewareController/getAll">
	    <input type="submit" value="返回全資料">
	</form>
	<table class="table">
		<tr>
		<th >用具編號<th>用具名稱<th>押金金額<th>用具圖片<th>用具描述<th>用具狀態 (1:上架 2:未上架)<th>功能
		<% List<Tableware> tablewares = (ArrayList<Tableware>)request.getAttribute("tablewares");
		for(Tableware tableware:tablewares){%>
			<tr><td><%=tableware.getTablewareId()%>
			<td><%=tableware.getTablewareName()%>
			<td><%=tableware.getTablewareDeposit()%>
			<td><img src="<%= tableware.getTablewareImage() %>"  width="100" height="100" />
			<td><%=tableware.getTablewareDescription()%>
			<td><%=tableware.getTablewareStatus()%>
			<td>
			<form class="btn" method="get" action="/EEIT187-6/tablewareController/updateStatus">
				<input type="submit" value="更改狀態" />
				<input type="hidden" name="tableware_id" value="<%=tableware.getTablewareId()%>">
			</form>
			<form class="btn" method="get" action="/EEIT187-6/tablewareController/get">
				<input type="submit" value="修正更新資料" />
				<input type="hidden" name="tableware_id" value="<%=tableware.getTablewareId()%>">
			</form>
		<%} %>
	</table>
	<h3>共<%=tablewares.size()%>筆環保用具資料</h3>
	
	</div>
	
		<script>
		document.addEventListener("DOMContentLoaded", function() {
			showSidebar('rental');
		});
	</script>
</body>
</html>