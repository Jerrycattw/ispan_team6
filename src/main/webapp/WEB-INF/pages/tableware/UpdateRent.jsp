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
	<jsp:include page="../../../HomePage.jsp"></jsp:include>
	<div class="content" id="content">
		<h2>租 借 訂 單 資 料</h2>
		<form method="get" class="btn"
			action="/EEIT187-6/Rent/getAll">
			<input type="submit" value="返回">
		</form>
		<form method="get" action="/EEIT187-6/Rent/update">
			<jsp:useBean id="rent" scope="request" class="com.rent.bean.Rent" />
			<table>
				<tr>
					<td>訂單編號<input type="text" name="rent_id" disabled value="${rent.rentId}"></td>
					<input type="hidden" name="rent_id"  value="${rent.rentId}">
				</tr>
				<tr>
					<td>租借押金<input type="text" name="rent_deposit" id="rent_deposit" value="${rent.rentDeposit}"></td>
				</tr>
				<tr>
					<td>租借日期<input type="date" name="rent_date" id="rent_date" value="${rent.rentDate}"></td>
				</tr>
				<tr>
					<td>餐廳編號
					<select name="restaurantName" id="restaurantName">
						<c:if test="${not empty param.restaurantName}">
							<option value="${param.restaurantName}" selected>${param.restaurantName}</option>
						</c:if>
						<c:forEach items="${restaurantNames}" var="restaurantName">
							<option value="${restaurantName}">${restaurantName}</option>
						</c:forEach>
					</select>
					</td>
				</tr>
				<tr>
					<td>會員編號<select name="member_id" id="member_id">
						<c:forEach var="i" begin="1" end="10">
							<option value="${i}" ${i == rent.memberId ? 'selected' : ''}>${i}</option>
						</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>預定歸還<input type="date" name="due_date" id="due_date" value="${rent.dueDate}"></td>
				</tr>
				<tr>
					<td>實際歸還<input type="date" name="return_date" id="return_date" required value="${rent.returnDate}"></td>
				</tr>
				<tr>
					<td>租借狀態
						<input type="radio" name="rent_status" id="rent_status" value="1" ${rent.rentStatus == 1 ? 'checked' : ''}>1:未歸還
                		<input type="radio" name="rent_status" id="rent_status" value="2" ${rent.rentStatus == 2 ? 'checked' : ''}>2:歸還
					</td>
				</tr>
				<tr>
					<td>訂單備註<input type="text" name="rent_memo" id="rent_memo" value="${rent.rentMemo}"></td>
				</tr>
				<tr>
					<td>歸還餐廳
					<select name="returnRestaurantName" id="returnRestaurantName" required>
						<option value="" selected disabled>請選擇歸還餐廳</option>
						<c:if test="${not empty param.restaurantName}">
							<option value="${param.restaurantName}" selected>${param.restaurantName}</option>
						</c:if>
						<c:forEach items="${restaurantNames}" var="restaurantName">
							<option value="${restaurantName}">${restaurantName}</option>
						</c:forEach>
					</select>
			<p id="disabledMsg" style="color: red; display: none;">訂單已完全歸還，無法再更改。</p>
					</td>
				</tr>
			</table>
			<input type="submit" id="submitBtn" value="確定更改">
		</form>
	</div>
	<script>
		document.addEventListener("DOMContentLoaded", function() {
			showSidebar('rental');
		});
		
		window.onload = function() {
		    const rentStatusElement = document.querySelector('input[name="rent_status"]:checked');
		    if (rentStatusElement) {
		        const rentStatus = rentStatusElement.value;
		        if (rentStatus === '2') {
		            document.getElementById('submitBtn').disabled = true;
		            document.getElementById('return_date').disabled = true;
		            document.getElementById('returnRestaurantName').disabled = true;
		            document.getElementById('restaurantName').disabled = true;
		            document.getElementById('rent_deposit').disabled = true;
		            document.getElementById('member_id').disabled = true;
		            document.getElementById('rent_date').disabled = true;
		            document.getElementById('due_date').disabled = true;
		            document.getElementById('rent_memo').disabled = true;
		            document.getElementById('rent_status').disabled = true;
		            document.getElementById('disabledMsg').style.display = 'block';
		        }
		    }
		};
	</script>
</body>
</html>