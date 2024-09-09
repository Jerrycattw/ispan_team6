<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.*,com.point.bean.PointMemberBean"%>  
<%! @SuppressWarnings("unchecked") %>      
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>員工資料</title>
</head>
<body style="background-color:#fdf5e6">
<div align="center">
<h2>查詢點數</h2>
<table border="1">
<tr style="background-color:#a8fefa">
<th>#<th>會員<th>電話<th>總點數<th>即將到期<th>到期日
<% PointMemberBean pointMember=(PointMemberBean)request.getAttribute("pointMember");%>
	<tr><td>
	<td><%= pointMember.getMemberID() %>
	<td><%= pointMember.getPhone() %>
	<td><%= pointMember.getTotalPoint() %>
	<td><%= pointMember.getExpiriyPoint() %>
	<td><%= pointMember.getExpiryDate() %>	
</table>
<button onclick="history.back()">返回上一頁</button>
</div>
<script>
document.addEventListener("DOMContentLoaded", function() {
     showSidebar('points');
});
</script>
</body>
</html>