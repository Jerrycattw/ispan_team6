<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>環保用具資料</title>
<link rel="stylesheet" href="../template/template.css">
<link rel="stylesheet" href="../template/table1.css">
<link rel="stylesheet" href="../template/rent.css">
<script src="../template/template.js"></script>
</head>
<body>
	<jsp:include page="../../../HomePage.jsp"></jsp:include>
	<div class="content" id="content">
		<h2>環 保 用 具 資 料</h2>
		<form method="get" class="btn"
			action="/EEIT187-6/Tableware/getAll">
			<input type="submit" value="返回">
		</form>
		<form method="post" action="/EEIT187-6/Tableware/update" id="tablewareForm" enctype="multipart/form-data" onsubmit="return validateForm()">
			<jsp:useBean id="tableware" scope="request"
				class="com.rent.bean.Tableware" />
			<table>
				<tr>
					<td>用具編號<input type="text" name="tableware_id" disabled value="<%=tableware.getTablewareId()%>"> 
					<input type="hidden" name="tableware_id" value="<%=tableware.getTablewareId()%>">
				<tr>
					<td>用具名稱<input type="text" name="tableware_name" id="tableware_name"
						value="<%=tableware.getTablewareName()%>">
				<tr>
					<td>用具押金<input type="text" name="tableware_deposit" id="tableware_deposit"
						value="<%=tableware.getTablewareDeposit()%>">
				<tr>
					<td>用具圖片<input type="text" name="tableware_image_path" disabled value="<%=tableware.getTablewareImage()%>">
					<input type="hidden" name="original_tableware_image" value="<%=tableware.getTablewareImage()%>">
					<input type="file" name="tableware_image" value="<%=tableware.getTablewareImage()%>">
				<tr>
					<td>用具描述<input type="text" name="tableware_description" id="tableware_description"
						value="<%=tableware.getTablewareDescription()%>">
				<tr>
					<td>用具狀態<input type="radio" name="tableware_status" value="1" <%=tableware.getTablewareStatus() == 1 ? "checked" : ""%>>1:上架
                <input type="radio" name="tableware_status" value="2" <%=tableware.getTablewareStatus() == 2 ? "checked" : ""%>>2:未上架
			</table>
			<input type="submit" value="確定更改">
		</form>
	</div>
		<script>
		document.addEventListener("DOMContentLoaded", function() {
			showSidebar('rental');
		});
	</script>
	<script>
function validateForm() {
    // 获取表单字段的值
    var name = document.getElementById("tableware_name").value;
    var deposit = document.getElementById("tableware_deposit").value;
    var description = document.getElementById("tableware_description").value;

    // 检查是否有空值
    if (name === "" || deposit === "" || description === "") {
        alert("所有字段都必须填写。");
        return false;
    }

    // 检查押金金额是否为数字
    if (isNaN(deposit)) {
        alert("押金金額必须为数字。");
        return false;
    }

    // 如果所有验证通过，返回true允许表单提交
    return true;
}
</script>
</body>
</html>