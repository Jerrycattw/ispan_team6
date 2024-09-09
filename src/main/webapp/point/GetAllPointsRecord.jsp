<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.*,com.point.bean.PointBean"%>  
<%! @SuppressWarnings("unchecked") %>      
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>員工資料</title>
<link rel="stylesheet" href="../template/template.css">
<script src="../template/template.js"></script>
</head>
<body >
<jsp:include page="../HomePage.html"></jsp:include>
<div class="content" id="content">
<div >
<h2>點數紀錄</h2>
<table border="1">
<tr style="background-color:#a8fefa">
<th>點數紀錄<th>會員<th>變動點數<th>創建日期<th>到期日<th>剩餘點數<th>交易來源<th>交易類型
<% List<PointBean> points=(ArrayList<PointBean>)request.getAttribute("points");
	for(PointBean point:points) {%>
	<tr><td><%= point.getPointID() %>
	<td><%= point.getMemberID() %>
	<td><%= point.getPointChange() %>
	<td><%= point.getCreateDate() %>
	<td><%= point.getExpiryDate() %>
	<td><%= point.getPointUsage() %>
	<td><%= point.getTransactionID() %>
	<td><%= point.getTransactionType() %>
	<%}%>
</table>
<h3>共<%=points.size() %>筆員工資料</h3>
</div>
</div>
</body>
</html>