<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.ArrayList"%>
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
		<h2>輸 入 租 借 訂 單 資 料</h2>
		<form method="get" class="btn"
			action="/EEIT187-6/rentController/getAll">
			<input type="submit" value="返回">
		</form>
		<form method="get" action="/EEIT187-6/rentController/restore">
			<jsp:useBean id="rent" scope="request" class="com.rent.bean.Rent" />
			<table>
				<tr>
					<td>訂單編號<input type="text" name="rent_id" disabled
						value="<%=rent.getRentId()%>">
				<tr>
					<td>租借押金<input type="text" name="rent_deposit" disabled
						value="<%=rent.getRentDeposit()%>">
				<tr>
					<td>租借日期<input type="text" name="rent_date" disabled
						value="<%=rent.getRentDate()%>">
				<tr>
					<td>租借餐廳<input type="text" name="restaurant_id" disabled
						value="<%=rent.getRestaurantId()%>">
				<tr>
					<td>租借會員<input type="text" name="member_id" disabled
						value="<%=rent.getMemberId()%>">
				<tr>
					<td>預定歸還<input type="text" name="due_date" disabled
						value="<%=rent.getDueDate()%>">
				<tr>
					<td>實際歸還<input type="date" name="return_date" id="return_date" required
						value="<%=rent.getReturnDate()%>">
				<tr>
					<td>租借狀態<input type="text" name="rent_status" id="rent_status" disabled value="<%=rent.getRentStatus()%>">
				<tr>
					<td>訂單備註<input type="text" name="rent_memo" disabled
						value="<%=rent.getRentMemo()%>">
				<tr>
					<td>歸還餐廳<select id="return_restaurant_id" name="return_restaurant_id" required>
					<option value="<%=rent.getReturnRestaurantId()%>" selected disabled><%=rent.getReturnRestaurantId()%></option>
						<option value="1" <%= "1".equals(rent.getReturnRestaurantId()) ? "selected" : "" %>>1</option>
                    	<option value="2" <%= "2".equals(rent.getReturnRestaurantId()) ? "selected" : "" %>>2</option>
                    	<option value="3" <%= "3".equals(rent.getReturnRestaurantId()) ? "selected" : "" %>>3</option>
                    	<option value="4" <%= "4".equals(rent.getReturnRestaurantId()) ? "selected" : "" %>>4</option>
                    	<option value="5" <%= "5".equals(rent.getReturnRestaurantId()) ? "selected" : "" %>>5</option>
                    	<option value="6" <%= "6".equals(rent.getReturnRestaurantId()) ? "selected" : "" %>>6</option>
                    	<option value="7" <%= "7".equals(rent.getReturnRestaurantId()) ? "selected" : "" %>>7</option>
                    	<option value="8" <%= "8".equals(rent.getReturnRestaurantId()) ? "selected" : "" %>>8</option>
                    	<option value="9" <%= "9".equals(rent.getReturnRestaurantId()) ? "selected" : "" %>>9</option>
                    	<option value="10" <%= "10".equals(rent.getReturnRestaurantId()) ? "selected" : "" %>>10</option>
					</select>
					<p id="disabledMsg" style="color:red; display:none;">訂單已完全歸還，無法再更改。</p>
			</table>
			<input type="submit" id="submitBtn" value="確定更改"> <input type="hidden"
				name="rent_id" value="<%=rent.getRentId()%>"> <input
				type="hidden" name="rent_deposit" value="<%=rent.getRentDeposit()%>">
			<input type="hidden" name="rent_date" value="<%=rent.getRentDate()%>">
			<input type="hidden" name="restaurant_id"
				value="<%=rent.getRestaurantId()%>"> <input type="hidden"
				name="member_id" value="<%=rent.getMemberId()%>"> <input
				type="hidden" name="due_date" value="<%=rent.getDueDate()%>">
			<input type="hidden" name="rent_status"
				value="<%=rent.getRentStatus()%>"> <input type="hidden"
				name="rent_memo" value="<%=rent.getRentMemo()%>">
		</form>
	</div>
		<script>
		document.addEventListener("DOMContentLoaded", function() {
			showSidebar('rental');
		});
		
		window.onload = function() {
	        const rentStatus = document.getElementById('rent_status').value;
	        if (rentStatus === '2') {
	            document.getElementById('submitBtn').disabled = true;
	            document.getElementById('disabledMsg').style.display = 'block';
	        }
	    };
	</script>
</body>
</html>