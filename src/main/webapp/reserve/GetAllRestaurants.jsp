<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%! @SuppressWarnings("unchecked") %>
<html>
<head>
<meta charset="UTF-8">
<title>餐廳資料</title>
<link rel="stylesheet" href="../template/template.css">
<link rel="stylesheet" href="../template/table.css">
<!-- 引入 DataTables CSS -->
<link rel="stylesheet" href="//cdn.datatables.net/1.13.4/css/jquery.dataTables.min.css">
<script src="../template/template.js"></script>
</head>
<body>
<jsp:include page="../HomePage.html"></jsp:include>

	<div class="content" id="content">

	<h2>餐 廳 資 料</h2>
	<table id="restaurantTable">
    <thead>
    <tr>
        <!-- <th>餐廳編號</th>  -->
        <th>餐廳名稱</th>
        <th>餐廳地址</th>
        <th>餐廳電話</th>
        <th>營業時間</th>
        <!-- <th>餐廳用餐時間限制</th>  -->
        <th>餐廳狀態</th>
        <th>修改餐廳資料</th>
        <th>刪除餐廳資料</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${restaurants}" var="rest" varStatus="r">
        <tr>
            <!-- <td>${rest.restaurantId}</td>  -->
            <td><a href="/EEIT187-6/Restaurant/get?restaurantId=${rest.restaurantId}">${rest.restaurantName}</a></td>
            <td>${rest.restaurantAddress}</td>
            <td>${rest.restaurantPhone}</td>
            <td>${rest.restaurantOpentime}~${rest.restaurantClosetime}</td>
            <!-- <td>${rest.eattime}</td>  -->
            <td>
                <c:choose>
                    <c:when test="${rest.restaurantStatus == 1}">營業中</c:when>
                    <c:when test="${rest.restaurantStatus == 2}">已歇業</c:when>
                    <c:when test="${rest.restaurantStatus == 3}">籌備中</c:when>
                    <c:otherwise>未知狀態</c:otherwise>
                </c:choose>
            </td>
            <td>
                <form action="/EEIT187-6/Restaurant/set" method="get">
                    <input type="hidden" name="restaurantId" value="${rest.restaurantId}">
                    <input type="submit" value="修改">
                </form>
            </td>
            <td>
                <form action="/EEIT187-6/Restaurant/del" method="get">
                    <input type="hidden" name="restaurantId" value="${rest.restaurantId}">
                    <input type="submit" value="刪除">
                </form>
            </td>
        </tr>
        <c:set var="rows" value="${r.count}"/>
    </c:forEach>
    </tbody>
</table>
<h3>共${rows}筆餐廳資料</h3>
</div>

<!-- 引入 jQuery 和 DataTables 的 JavaScript 文件 -->
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="//cdn.datatables.net/1.13.4/js/jquery.dataTables.min.js"></script>

<script>
    document.addEventListener("DOMContentLoaded", function() {
        showSidebar('reservation');
        
        // 導入DataTable
        $('#restaurantTable').DataTable();
        
    });
</script>
</body>
</html>