<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>環保用具庫存資料</title>
<link rel="stylesheet" href="../template/template.css">
<link rel="stylesheet" href="../template/table1.css">
<link rel="stylesheet" href="../template/rent.css">
<script src="../template/template.js"></script>
</head>
<body>
	<jsp:include page="../HomePage.html"></jsp:include>
	<div class="content" id="content">
		<h2>環 保 用 具 庫 存 資 料</h2>
		<form method="get" class="btn"
			action="/EEIT187-6/tablewareStockController/getAll">
			<input type="submit" value="返回">
		</form>
		<form method="get" action="/EEIT187-6/tablewareStockController/update" onsubmit="return validateForm()">
			<jsp:useBean id="stock" scope="request"
				class="com.rent.bean.TablewareStock" />
			<table>
				<tr>
					<td>用具編號 <input type="text" name="tableware_id" readonly
						value="<%=stock.getTablewareId()%>">
					</td>
				</tr>
				<tr>
					<td>餐廳編號 <input type="text" name="restaurant_id" readonly
						value="<%=stock.getRestaurantId()%>">
					</td>
				</tr>
				<tr>
					<td>用具庫存 <input type="text" name="stock" id="stock"
						value="<%=stock.getStock()%>">
					</td>
				</tr>
			</table>
			<input type="submit" value="確定更改">
			
		</form>
	</div>
		<script>
		document.addEventListener("DOMContentLoaded", function() {
			showSidebar('rental');
		});
		
		function validateForm() {
			const stock = document.getElementById("stock").value;
			if (stock === "") {
		        alert("請輸入庫存數量");
		        return false;
		    }
			if (isNaN(stock) || stock < 0) {
				alert("請輸入有效的庫存數量");
				return false;
			}
			return true;
		}
	</script>
</body>
</html>