<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%! @SuppressWarnings("unchecked") %>
<html>
<head>
<meta charset="UTF-8">
<title>餐廳餐桌資料</title>
<link rel="stylesheet" href="../template/template.css">
<link rel="stylesheet" href="../template/table.css">
<script src="../template/template.js"></script>
<style>
.addTableType{
	position: absolute;
	right: 20px;
	top:110px
}
i{
	font-size: 24px;
}
</style>
</head>
<body>
<jsp:include page="../../../HomePage.jsp"></jsp:include>

	<div class="content" id="content">
	<h2>餐 廳 桌 位 資 料</h2>
	<span class="addTableType">新增餐廳桌位種類: <a href="/EEIT187-6/Table/add1?restaurantName=${name}"><i class="fa-solid fa-plus"></i></a></span>
	<table border="1">
	<tr style="background-color:#a8fefa">
	<th>餐廳名稱<th>桌位種類代碼<th>桌位種類座位數<th>桌位數量<th>修改桌位數量<th>刪除該種桌位
	
	<c:forEach items="${tableTypes}" var="table" varStatus="r">
		<tr>
			<td>${name}
			<td>${table.tableTypeId}
			<td>${table.tableTypeName}
			<td>${table.tableTypeNumber}
			<td><form action="/EEIT187-6/Table/set1" method="get">
                    <input type="hidden" name="restaurantId" value="${table.restaurantId}">
                    <input type="hidden" name="restaurantName" value="${name}">
                    <input type="hidden" name="tableTypeId" value="${table.tableTypeId}">

                    <input type="hidden" name="tableTypeNumber" value="${table.tableTypeNumber}">
                    <input type="submit" value="修改">
                </form>
			<td><form action="/EEIT187-6/Table/del" method="get">
                    <input type="hidden" name="restaurantId" value="${table.restaurantId}">
                    <input type="hidden" name="restaurantName" value="${name}">
                    
                    <input type="hidden" name="tableTypeId" value="${table.tableTypeId}">
                    <input type="submit" value="刪除">
                </form>
		</tr>
		<c:set var="rows" value="${r.count}"/>
	</c:forEach>
	
	</table>
	<h3>共${rows}筆餐廳桌位種類資料</h3>
</div>
<script src="https://kit.fontawesome.com/a260a05f7c.js" crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script>
    document.addEventListener("DOMContentLoaded", function() {
        showSidebar('reservation');
    });
</script>
</body>
</html>