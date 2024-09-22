<%@page import="com.TogoOrder.bean.MenuBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>查詢全部菜單</title>
<link rel="stylesheet" href="../template/template.css">
<script src="../template/template.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<link rel="stylesheet" href="../template/table.css">
</head>

<body>
<jsp:include page="../../../HomePage.jsp"></jsp:include>
	<div class="content" id="content">
	<h2>菜單</h2>
	<form action="../Togo/AddMenu.html" method="get" style="margin-bottom: 20px;">
	    <button type="submit">新增菜單</button>
	</form>
	
	<table border = "1">
		<tr style = "background-color: #a8fefa">
			<th>編號</th><th>名稱</th><th>圖片</th><th>價格</th><th>種類</th><th>庫存</th>
			<th>說明</th><th>狀態</th><th>刪除</th><th>更新</th></tr>	
			<c:forEach var="food" items="${foodList}">
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
                    	<c:otherwise></c:otherwise>
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
	
	<h3>共<c:out value="${fn:length(foods)}"/>筆資料</h3>
	</div>
<script>
    document.addEventListener("DOMContentLoaded", function() {
        showSidebar('order');

        const deleteSuccess = "${deleteSuccess}";
        const updateSuccess = "${updateSuccess}"
        if (deleteSuccess === 'true') {
        	console.log("123");
            Swal.fire({
                icon: 'success',
                title: '成功刪除!',
                text: '菜單項目已經成功刪除。',
                showConfirmButton: false,
                timer: 1500
            }).then(function() {
                window.location.href = '/EEIT187-6/MenuController/getAllMenu';
            });
        } else if (deleteSuccess === 'false') {
            Swal.fire({
                icon: 'error',
                title: '刪除失敗!',
                text: '菜單項目刪除失敗，請稍後再試。',
                showConfirmButton: false,
                timer: 1500
            }).then(function() {
                window.location.href = '/EEIT187-6/MenuController/getAllMenu';
            });
        }
        if (updateSuccess === 'true') {
            Swal.fire({
                icon: 'success',
                title: '成功更新!',
                text: '菜單更新成功。',
                showConfirmButton: false,
                timer: 1500
            }).then(function() {
                window.location.href = '/EEIT187-6/MenuController/getAll';
            });
        }   
    });
</script>
</body>
</html>