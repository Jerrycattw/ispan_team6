<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.TogoOrder.bean.TogoBean"%>
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
	
	<form action="/EEIT187-6/TogoController/getAll" method="get">
        <button type="submit">返回全部訂單</button>
    </form><p>
    <form action="../togo/GetTogo.html" method="get">
        <button type="submit">重新查詢</button>
    </form><p>
<c:choose>
    <c:when test="${not empty togoList}">
	<table border = "1">
	<tr style = "background-color: #a8fefa">
		<th>訂單編號</th><th>會員編號</th><th>姓名</th><th>電話</th><th>總金額</th><th>成立時間</th>
		<th>訂單狀態</th><th>餐廳編號</th><th>備註</th><th>刪除</th><th>更新</th></tr>	
		<c:forEach var="togo" items="${togoList}">
		<tr>
            <td>${togo.togoId}</td>
            <td>${togo.memberId}</td>
            <td>${togo.tgName}</td>
            <td>${togo.tgPhone}</td>
        	<td>${togo.totalPrice}</td>
            <td>${togo.formattedtogoCreateTime}</td>  
            <td>
                <c:choose>
                    <c:when test="${togo.togoStatus == 1}">未取餐</c:when>
                    <c:when test="${togo.togoStatus == 2}">已取餐</c:when>
                    <c:when test="${togo.togoStatus == 3}">取消訂單</c:when> 
            	</c:choose>
            </td>
            <td>${togo.restaurantId}</td>
            <td>${togo.togoMemo}</td>
            
            <td><form action="/EEIT187-6/TogoController/delete" method="get">
                	<input type="hidden" name="togoId" value="${togo.togoId}">
                	<button type="submit">刪除</button>
                </form></td>
            <td><form action="/EEIT187-6/TogoController/getForUpdate" method="get">
                    	<input type="hidden" name="togoId" value="${togo.togoId}">
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