<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%! @SuppressWarnings("unchecked") %>
<html>
<head>
<meta charset="UTF-8">
<title>訂位資料</title>
<link rel="stylesheet" href="../template/template.css">
<link rel="stylesheet" href="../template/table.css">
<script src="../template/template.js"></script>
</head>
<body>
<jsp:include page="../HomePage.html"></jsp:include>

	<div class="content" id="content">
	<h2>訂 位 資 料</h2>
	
	<table border="1">
	<tr style="background-color:#a8fefa">
	<th>訂位編號<th>會員姓名<th>會員電話<th>餐廳名稱<th>訂位人數<th>桌位種類<th>用餐開始時間<th>用餐結束時間<th>修改<th>刪除
	
	<c:forEach items="${reserves}" var="reserve" varStatus="r">
		<tr>
			<td><a href="/EEIT187-6/Reserve/get?reserveId=${reserve.reserveId}">${reserve.reserveId}</a>
			<td>${reserve.member.memberName}
			<td>${reserve.member.phone}
			<td>${reserve.restaurant.restaurantName}
			<td>${reserve.reserveSeat}
			<td>${reserve.tableTypeId} 桌
			<td>${reserve.formattedReserveTime}
			<td>${reserve.formattedFinishedTime}
			<td><form action="/EEIT187-6/Reserve/set" method="get">
                    <input type="hidden" name="reserveId" value="${reserve.reserveId}">
                    <input type="submit" value="修改">
                </form>
			<td><form action="/EEIT187-6/Reserve/del" method="get">
                    <input type="hidden" name="reserveId" value="${reserve.reserveId}">
                    <input type="submit" value="刪除">
                </form>
		</tr>
		<c:set var="rows" value="${r.count}"/>
	</c:forEach>
	
	</table>
	<h3>共${rows}筆訂位資料</h3>
</div>
<script>
    document.addEventListener("DOMContentLoaded", function() {
        showSidebar('reservation');
    });
</script>
</body>
</html>