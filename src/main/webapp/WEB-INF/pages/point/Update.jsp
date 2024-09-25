<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.*,com.point.bean.PointBean"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>員工資料</title>
<link rel="stylesheet" href="../template/template.css">
<script src="../template/template.js"></script>
<style>
table {
    width: 50%;
    border-collapse: collapse;
    margin-top: 20px; /* 讓出空間給上方按鈕 */
}
th, td {
    border: 1px solid #ddd;
    padding: 8px;
    text-align: center; /* 文字置中 */
}
th {
    background-color: #f2f2f2;
}
</style>
</head>
<body >
<jsp:include page="../../../HomePage.jsp"></jsp:include>
<div class="content" id="content">
<div>
<h2>更新點數資料</h2>
<form method="post" action="/EEIT187-6/Point/UpdatePoint">
<jsp:useBean id="point" scope="request" class="com.point.bean.PointBean" />
<table>
	<tr><td>點數紀錄<td><input type="text"  disabled value="<%= point.getPointID() %>"><input type="hidden" name="pointID" value="<%= point.getPointID() %>">
	<tr><td>會員<td><input type="text"  disabled name="memberID" value="<%= point.getMemberID() %>"><input type="hidden" name="memberID" value="<%= point.getMemberID() %>">
	<tr><td>變動點數<td><input type="text" name="pointChange" value="<%= point.getPointChange() %>">
	<tr><td>創建日期<td><input type="text" name="createDate" value="<%= point.getCreateDate() %>">	
	<tr><td>到期日<td><input type="text" name="expiryDate" value="<%= point.getExpiryDate() %>">
	<tr><td>剩餘點數<td><input type="text" name="pointUsage" value="<%= point.getPointUsage() %>">
	<tr><td>交易來源<td><input type="text" name="transactionID" value="<%= point.getTransactionID() %>">
	<tr><td>交易類型<td><input type="text" name="transactionType" value="<%= point.getTransactionType() %>">
	<tr><td><td align="center"><input type="submit" value="確定" />
</table>
</form>
</div>
</div>
<script>
document.addEventListener("DOMContentLoaded", function() {
     showSidebar('points');
});
</script>
</body>
</html>