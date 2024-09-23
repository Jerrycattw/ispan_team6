<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Restaurant data</title>
<link rel="stylesheet" href="../template/template.css">
<link rel="stylesheet" href="../template/add.css">
<script src="../template/template.js"></script>
</head>
<body>
<jsp:include page="../../../HomePage.jsp"></jsp:include>

	<div class="content" id="content">
<h2>修 改 餐 廳 桌 位 數 量</h2>
<form method="post" action="/EEIT187-6/Table/set2">
	<input type="hidden" name="restaurantId" value="${table.id.restaurantId}"/>
	<input type="hidden" name="restaurantName" value="${table.restaurant.restaurantName}"/>
	<input type="hidden" name="tableTypeId" value="${table.id.tableTypeId}"/>
	<input type="hidden" name="tableTypeName" value="${table.tableType.tableTypeName}"/>
	餐廳編號 : <input type="text" disabled value="${table.id.restaurantId}"/><p>
	餐廳名稱 : <input type="text" disabled value="${table.restaurant.restaurantName}"/><p>
	餐桌代碼 : <input type="text" disabled value="${table.id.tableTypeId}"/><p>
	餐桌名稱 : <input type="text" disabled value="${table.tableType.tableTypeName}"/><p>
	修改餐桌種類數量 : <input type="Number" name="tableTypeNumber" value="${table.tableTypeNumber}" min="0" max="30" step="1"/><p>
	<input type="submit" value="確定" />
</form>
</div>
</body>
<script>
    document.addEventListener("DOMContentLoaded", function() {
        showSidebar('reservation');
    });
</script>
</html>