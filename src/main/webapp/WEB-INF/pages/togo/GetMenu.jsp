<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.TogoOrder.bean.MenuBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>查詢結果</title>
<link rel="stylesheet" href="../template/template.css">
<script src="../template/template.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<link rel="stylesheet" href="../template/table.css">
</head>
<body>
<jsp:include page="../../../HomePage.jsp"></jsp:include>

	<div class="content" id="content">
	<h2>查詢結果</h2>
	
	<form action="/EEIT187-6/MenuController/getAll" method="get">
        <button type="submit">返回菜單</button>
    </form><p>
	<form action="../Togo/GetMenu.html" method="get">
        <button type="submit">重新查詢</button>
    </form><p>
	
<c:choose>
	<c:when test="${not empty menu}">
	
	<table border = "1">
	<tr style = "background-color: #a8fefa">
		<th>編號</th><th>名稱</th><th>圖片</th><th>價格</th><th>種類</th><th>庫存</th>
		<th>說明</th><th>狀態</th><th>刪除</th><th>更新</th></tr>	
		<c:forEach var="food" items="${menu}">
		<tr>
            <td>${food.foodId}</td>
            <td>${food.foodName}</td>
            <td><img src="${food.foodPicture}" alt="${food.foodName}" width="100" height="100"/></td>
            <td>${food.foodPrice}</td>
            <td>${food.foodKind}</td>
            <td>${food.foodStock}</td>
            <td>${food.foodDescription}</td>
            <td>
                <c:choose>
                    <c:when test="${food.foodStatus == 2}">停售</c:when>
                    <c:when test="${food.foodStatus == 1}">正常供應</c:when>                    
                </c:choose>
            </td>
            
            <td><form action="/EEIT187-6/MenuController/delete" method="get">
                	<input type="hidden" name="foodId" value="${food.foodId}">
                	<button type="submit">刪除</button>
                </form></td>
            <td><form action="/EEIT187-6/MenuController/getForUpdate" method="get">
                    	<input type="hidden" name="foodId" value="${food.foodId}">
                        <input type="submit" value="更新" />
                    </form></td>
		</tr>
		</c:forEach>
	</table>
	</c:when>
        <c:otherwise>
            <p>查無結果。</p>
        </c:otherwise>
</c:choose> 
    </div>
<script>
    document.addEventListener("DOMContentLoaded", function() {
        showSidebar('order');
    });
</script>   
</body>
</html>