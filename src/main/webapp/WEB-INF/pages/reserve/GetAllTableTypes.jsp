<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%! @SuppressWarnings("unchecked") %>
<html>
<head>
<meta charset="UTF-8">
<title>桌位種類資料</title>
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
	<h2>餐 廳 桌 位 種 類</h2>
	<span class="addTableType">新增桌位種類: <a href="/EEIT187-6/reserve/AddTableType"><i class="fa-solid fa-plus"></i></a></span>
	<table border="1">
	<tr style="background-color:#a8fefa">
	<th>桌位種類代碼<th>桌位種類座位數<th>刪除該種桌位
	
	<c:forEach items="${tableTypes}" var="table" varStatus="r">
		<tr>
			<td>${table.tableTypeId}
			<td>${table.tableTypeName}
			<td><form action="/EEIT187-6/TableType/del" method="get">
                    <input type="hidden" name="tableTypeId" value="${table.tableTypeId}">
                    <input type="submit" value="刪除">
                </form>
		</tr>
		<c:set var="rows" value="${r.count}"/>
	</c:forEach>
	
	</table>
	<h3>共${rows}筆桌位種類資料</h3>
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