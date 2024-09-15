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
		<h2>輸 入 租 借 訂 單 資 料</h2>
		<form method="get" class="btn"
			action="/EEIT187-6/rentController/getAll">
			<input type="submit" value="返回">
		</form>
		<form method="get" id="form" action="/EEIT187-6/rentController/search">
			<table>
				<tr>
					<td>訂單編號:
					<select id="rentId" name="rentId">
							<option value="" selected disabled>請選擇訂單編號</option>
							<c:forEach items="${rentIds}" var="rentId">
								<option value="${rentId}">${rentId}</option>
							</c:forEach>
					</select>
					</td>
				</tr>
				<tr>
					<td>租借會員:<select id="member_id" name="member_id" >
					<option value="" selected disabled>請選擇會員編號</option>
						<option value="1">1</option>
                		<option value="2">2</option>
                		<option value="3">3</option>
		                <option value="4">4</option>
		                <option value="5">5</option>
		                <option value="6">6</option>
		                <option value="7">7</option>
		                <option value="8">8</option>
		                <option value="9">9</option>
		                <option value="10">10</option>
					</select>
					</td>
				</tr>
				<tr>
					<td>租借餐廳: 
					<select name="restaurantName" id="restaurantName">
							<option value="" selected disabled>請選擇餐廳編號</option>
							<c:if test="${not empty param.restaurantName}">
								<!-- 顯示 URL 中的餐廳名稱 -->
								<option value="${param.restaurantName}" selected>${param.restaurantName}</option>
							</c:if>
							<c:forEach items="${restaurantNames}" var="restaurantName">
								<option value="${restaurantName}">${restaurantName}</option>
							</c:forEach>
					</select>
					</td>
				</tr>
				<tr>
					<td>租借狀態: <input type="radio" name="rent_status" value="1" id="status_1"/>1:出租中
                <input type="radio" name="rent_status" value="2" id="status_2"/>2:已歸還
					</td>
				</tr>
			</table>
			<table>
				<tr>
					<td>查找日期: 開始<input type="date" name="rent_date_start" /> 結束<input
						type="date" name="rent_date_end" />
					</td>
				</tr>
			</table>
			<input type="submit" value="確定" />
	</form>
	</div>

	<script>
		document.addEventListener("DOMContentLoaded", function() {
			showSidebar('rental');
		});
	</script>
</body>
</html>
</html>