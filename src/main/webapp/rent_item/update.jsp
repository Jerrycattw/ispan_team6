<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>租借訂單項目資料</title>
<link rel="stylesheet" href="../template/template.css">
<link rel="stylesheet" href="../template/table1.css">
<link rel="stylesheet" href="../template/rent.css">
<script src="../template/template.js"></script>

</head>
<body>
	<jsp:include page="../HomePage.html"></jsp:include>
	<div class="content" id="content">
		<h2>租 借 訂 單 項 目 資 料</h2>
		<form method="get" class="btn"
			action="/EEIT187-6/rentItemController/getAll">
			<input type="submit" value="返回">
		</form>
		<form method="get" action="/EEIT187-6/rentItemController/update" onsubmit="return validateForm()">
			<jsp:useBean id="rentItem" scope="request"
				class="com.rent.bean.RentItem" />
			<table>
				<tr>
					<td>訂單編號<select type="text" name="new_rent_id" required
						value="<%=rentItem.getRentId()%>">
						<option value="<%=rentItem.getRentId()%>"  selected><%=rentItem.getRentId()%></option>
						<option value="30000" <%= "30000".equals(rentItem.getRentId()) ? "selected" : "" %>>30000</option>
						<option value="30001" <%= "30001".equals(rentItem.getRentId()) ? "selected" : "" %>>30001</option>
                    	<option value="30002" <%= "30002".equals(rentItem.getRentId()) ? "selected" : "" %>>30002</option>
                    	<option value="30003" <%= "30003".equals(rentItem.getRentId()) ? "selected" : "" %>>30003</option>
                    	<option value="30004" <%= "30004".equals(rentItem.getRentId()) ? "selected" : "" %>>30004</option>
					</select>
				<tr>
					<td>用具編號<select type="text" name="new_tableware_id" required
						value="<%=rentItem.getTablewareId()%>">
						<option value="<%=rentItem.getTablewareId()%>"  selected><%=rentItem.getTablewareId()%></option>
						<option value="1" <%= "1".equals(rentItem.getTablewareId()) ? "selected" : "" %>>1</option>
						<option value="2" <%= "2".equals(rentItem.getTablewareId()) ? "selected" : "" %>>2</option>
                    	<option value="3" <%= "3".equals(rentItem.getTablewareId()) ? "selected" : "" %>>3</option>
                    	<option value="4" <%= "4".equals(rentItem.getTablewareId()) ? "selected" : "" %>>4</option>
                    	<option value="5" <%= "5".equals(rentItem.getTablewareId()) ? "selected" : "" %>>5</option>
					</select>
				<tr>
					<td>品項數量<input type="text" name="rent_item_quantity" id="rent_item_quantity"
						value="<%=rentItem.getRentItemQuantity()%>">
				<tr>
					<td>項目押金<input type="text" name="rent_item_deposit" id="rent_item_deposit"
						value="<%=rentItem.getRentItemDeposit()%>">
				<tr>
					<td>歸還備註<input type="text" name="return_memo" id="return_memo"
						value="<%=rentItem.getReturnMemo()%>">
				<tr>
					<td>歸還狀態
   				<input type="radio" name="return_status" value="1" <%=rentItem.getReturnStatus() == 1 ? "checked" : ""%>>1:未歸還
                <input type="radio" name="return_status" value="2" <%=rentItem.getReturnStatus() == 2 ? "checked" : ""%>>2:完全歸還
                <input type="radio" name="return_status" value="3" <%=rentItem.getReturnStatus() == 3 ? "checked" : ""%>>3:完全歸還
</td>
			<input type="hidden" name="rent_id"
					value="<%=rentItem.getRentId()%>">
			<input type="hidden" name="tableware_id"
					value="<%=rentItem.getTablewareId()%>">
			</table>
			<input type="submit" value="確定更改">
		</form>
	</div>
		<script>
		document.addEventListener("DOMContentLoaded", function() {
			showSidebar('rental');
		});
		
		function validateForm() {
            // 获取表单字段的值
            var rentItemQuantity = document.getElementById("rent_item_quantity").value;
            var rentItemDeposit = document.getElementById("rent_item_deposit").value;
            var returnStatus = document.getElementById("return_status").value;

            // 检查是否为空
            if (rentItemQuantity === "" || rentItemDeposit === "" || returnStatus === "") {
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
	</script>
	
</body>
</html>