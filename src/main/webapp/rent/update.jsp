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
	<jsp:include page="../HomePage.html"></jsp:include>
	<div class="content" id="content">
		<h2>租 借 訂 單 資 料</h2>
		<form method="get" class="btn"
			action="/EEIT187-6/rentController/getAll">
			<input type="submit" value="返回">
		</form>
		<form method="get" action="/EEIT187-6/rentController/update">
			<jsp:useBean id="rent" scope="request" class="com.rent.bean.Rent" />
			<table>
				<tr>
					<td>訂單編號<input type="text" name="rent_id" readonly
						value="<%=rent.getRentId()%>">
				<tr>
					<td>租借押金<input type="text" name="rent_deposit"
						value="<%=rent.getRentDeposit()%>">
				<tr>
					<td>租借日期<input type="date" name="rent_date"
						value="<%=rent.getRentDate()%>">
				<tr>
					<td>餐廳編號
					<select name="restaurantName" id="restaurantName">
							<c:if test="${not empty param.restaurantName}">
								<!-- 顯示 URL 中的餐廳名稱 -->
								<option value="${param.restaurantName}" selected>${param.restaurantName}</option>
							</c:if>
							<c:forEach items="${restaurantNames}" var="restaurantName">
								<option value="${restaurantName}">${restaurantName}</option>
							</c:forEach>
					</select>
				<tr>
					<td>會員編號<select type="text" name="member_id"
						value="<%=rent.getMemberId()%>">
						<option value="1" <%= "1".equals(rent.getMemberId()) ? "selected" : "" %>>1</option>
                    	<option value="2" <%= "2".equals(rent.getMemberId()) ? "selected" : "" %>>2</option>
                    	<option value="3" <%= "3".equals(rent.getMemberId()) ? "selected" : "" %>>3</option>
                    	<option value="4" <%= "4".equals(rent.getMemberId()) ? "selected" : "" %>>4</option>
                    	<option value="5" <%= "5".equals(rent.getMemberId()) ? "selected" : "" %>>5</option>
                    	<option value="6" <%= "6".equals(rent.getMemberId()) ? "selected" : "" %>>6</option>
                    	<option value="7" <%= "7".equals(rent.getMemberId()) ? "selected" : "" %>>7</option>
                    	<option value="8" <%= "8".equals(rent.getMemberId()) ? "selected" : "" %>>8</option>
                    	<option value="9" <%= "9".equals(rent.getMemberId()) ? "selected" : "" %>>9</option>
                    	<option value="10" <%= "10".equals(rent.getMemberId()) ? "selected" : "" %>>10</option>
					</select>
				<tr>
					<td>預定歸還<input type="date" name="due_date"
						value="<%=rent.getDueDate()%>">
				<tr>
					<td>實際歸還<input type="date" name="return_date" required
						value="<%=rent.getReturnDate()%>">
				<tr>
					<td>租借狀態
						<input type="radio" name="rent_status" value="1" <%=rent.getRentStatus() == 1 ? "checked" : ""%>>1:未歸還
                		<input type="radio" name="rent_status" value="2" <%=rent.getRentStatus() == 2 ? "checked" : ""%>>2:歸還
				<tr>
					<td>訂單備註<input type="text" name="rent_memo"
						value="<%=rent.getRentMemo()%>">
				<tr>
					<td>歸還餐廳
					<select name="restaurantName" id="restaurantName" required>
							<c:if test="${not empty param.restaurantName}">
								<!-- 顯示 URL 中的餐廳名稱 -->
								<option value="${param.restaurantName}" selected>${param.restaurantName}</option>
							</c:if>
							<c:forEach items="${restaurantNames}" var="restaurantName">
								<option value="${restaurantName}">${restaurantName}</option>
							</c:forEach>
					</select>
			</table>
			<input type="submit" value="確定更改">
		</form>
	</div>
	<script>
		document.addEventListener("DOMContentLoaded", function() {
			showSidebar('rental');
		});
	</script>
</body>
</html>