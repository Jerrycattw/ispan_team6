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
		<form method="get" action="/EEIT187-6/rentItemController/restore">
			<jsp:useBean id="rentItem" scope="request"
				class="com.rent.bean.RentItem" />
			<table>
				<tr>
					<td>訂單編號<input type="text" name="rent_id" disabled
						value="<%=rentItem.getRentId()%>">
				<tr>
					<td>用具編號<input type="text" name="tableware_id" disabled
						value="<%=rentItem.getTablewareId()%>">
				<tr>
					<td>品項數量<input type="text" name="rent_item_quantity"
						disabled value="<%=rentItem.getRentItemQuantity()%>">
				<tr>
					<td>項目押金<input type="text" name="rent_item_deposit" disabled
						value="<%=rentItem.getRentItemDeposit()%>">
				<tr>
					<td>歸還備註<input type="text" name="return_memo"
						value="<%=rentItem.getReturnMemo()%>">
				<tr>
					<td>歸還狀態
        <input type="radio" name="return_status" value="2"  required
            <%= "2".equals(rentItem.getReturnStatus()) ? "checked" : "" %>> 2:完全歸還
        <input type="radio" name="return_status" value="3"  required
            <%= "3".equals(rentItem.getReturnStatus()) ? "checked" : "" %>> 3:部分歸還
    	<p id="disabledMsg" style="color:red; display:none;">訂單已完全歸還，無法再更改。</p>
			</table>
			<input type="submit" id="submitBtn" value="確定更改"> <input type="hidden"
				name="rent_id" value="<%=rentItem.getRentId()%>"> <input
				type="hidden" name="tableware_id" 
				value="<%=rentItem.getTablewareId()%>"> <input type="hidden"
				name="rent_item_quantity"
				value="<%=rentItem.getRentItemQuantity()%>"> <input
				type="hidden" name="rent_item_deposit"
				value="<%=rentItem.getRentItemDeposit()%>">
		</form>
	</div>
		<script>
		document.addEventListener("DOMContentLoaded", function() {
			showSidebar('rental');
		});
		
		window.onload = function() {
	        const returnStatus = '<%=rentItem.getReturnStatus()%>';
	        if (returnStatus === '2') {
	            document.getElementById('submitBtn').disabled = true;
	            document.getElementById('disabledMsg').style.display = 'block';
	        }
	    };
	</script>
	
</body>
</html>