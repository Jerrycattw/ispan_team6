<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.*,com.point.bean.PointMemberBean"%>
    
<% ServletContext context = getServletContext(); %>
<% String[] selectedMembers = request.getParameterValues("selectedMembers"); %>
<% context.setAttribute("selectedMembers", selectedMembers); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>批次新增點數</title>
<link rel="stylesheet" href="../template/template.css">
<script src="../template/template.js"></script>
<style>
	.message{margin-bottom:20px;}
</style>
</head>
<body >
<jsp:include page="../HomePage.html"></jsp:include>
<div class="content" id="content">
<div >
<h2>新增點數資料</h2>
<form method="post" action="/EEIT187-6/Point/InsertBatchPoint" >
<div class="message"><%=context.getAttribute("message") %></div>
<table>
	<tr><td>輸入變動點數<td><input type="text" name="pointChange" >
	<tr><td>輸入創建日期<td><input type="text" name="createDate" placeholder="yyyy-mm-dd">
	<tr><td>輸入到期日<td><input type="text"  disabled name="expiryDate" >
	<tr><td>輸入剩餘點數<td><input type="text" disabled name="pointUsage" >
	<tr><td>輸入交易來源<td><input type="text" name="transactionID" >
	<tr><td>輸入交易類型<td><input type="text" name="transactionType" >
	<tr><td><td align="center"><input type="submit" value="確定" />
	
</table>
</div>
</div>
<script>
document.addEventListener("DOMContentLoaded", function() {
     showSidebar('points');
});
</script>
</body>
</html>