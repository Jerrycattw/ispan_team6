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
		<form method="get" action="/EEIT187-6/rentController/insert" onsubmit="return validateForm()">
			<jsp:useBean id="rent" scope="request" class="com.rent.bean.Rent" />
			<table>
				<tr>
					<td>租借押金:<input type="text" name="rent_deposit" id="rent_deposit"/>
					</td>
				</tr>
				<tr>
					<td>租借餐廳:
					<select name="restaurantName" id="restaurantName">
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
					<td>租借會員:<select id="member_id" name="member_id">
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
			</table>
			<h3>租借訂單明細</h3>
			<div id="rentItemsContainer">
				<!-- 动态添加订单明细的部分 -->
				<div class="rent-item">
					<table>
						<tr>
							<td>餐具編號: <select name="tablewareId0" id="tablewareId0">
									<option value="" selected disabled>請選擇餐具編號</option>
									<c:forEach items="${tablewareIds}" var="tablewareId">
										<option value="${tablewareId}">${tablewareId}</option>
									</c:forEach>
							</select>
							</td>
							<td>數量:<input type="number" name="rentItemQuantity0" min="1" /></td>
							<td>押金:<input type="number" name="rentItemDeposit0" min="0" /></td>
							<td><button type="button" onclick="removeRentItem(this)">移除</button></td>
						</tr>
					</table>
				</div>
			</div>

			<button type="button" onclick="addRentItem()">新增訂單明細</button>
			<input type="submit" value="確定" />
		</form>
	</div>
	<script>
		function validateForm() {
			// 获取表单字段的值
			var deposit = document.getElementById("rent_deposit").value;
			var restaurant = document.getElementById("restaurant_id").value;
			var member = document.getElementById("member_id").value;

			// 检查是否为空
			if (deposit === "" || restaurant === "" || member === "") {
				alert("所有字段都必須填寫");
				return false; // 阻止表单提交
			}

			// 检查押金是否为数字
			if (isNaN(deposit)) {
				alert("押金必須為數字");
				return false; // 阻止表单提交
			}
			return true; // 表单验证通过，允许提交
		}
		
		let rentItemIndex = 1;

	    function addRentItem() {
	        const container = document.getElementById('rentItemsContainer');
	        const newItem = document.createElement('div');
	        newItem.classList.add('rent-item');
	        newItem.innerHTML = `
	            <table>
	                <tr>
	                    <td>餐具編號:
	                    	<select name="tablewareId${rentItemIndex}" id="tablewareId${rentItemIndex}">
							<option value="" selected disabled>請選擇餐具編號</option>
							<c:forEach items="${tablewareIds}" var="tablewareId">
								<option value="${tablewareId}">${tablewareId}</option>
							</c:forEach>
							</select>
						</td>
	                    <td>數量:<input type="number" name="rentItemQuantity${rentItemIndex}" min="1" /></td>
	                    <td>押金:<input type="number" name="rentItemDeposit${rentItemIndex}" min="0" /></td>
	                    <td><button type="button" onclick="removeRentItem(this)">移除</button></td>
	                </tr>
	            </table>
	        `;
	        container.appendChild(newItem);
	        rentItemIndex++;
	    }

	    // 删除订单明细
	    function removeRentItem(button) {
	        const rentItem = button.closest('.rent-item');
	        rentItem.remove();
	    }

	</script>
	<script>
		document.addEventListener("DOMContentLoaded", function() {
			showSidebar('rental');
		});
	</script>
</body>
</html>