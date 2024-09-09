<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Restaurant data</title>
<link rel="stylesheet" href="../template/template.css">
<script src="../template/template.js"></script>
</head>
<body>
<jsp:include page="../HomePage.html"></jsp:include>

	<div class="content" id="content">
		<h2>餐 廳 桌 位 資 料</h2>
		<form method="post" action="/EEIT187-6/Table/getAll">
			請選擇想操作的餐廳名稱 : <select name="restaurantName">
				<c:forEach items="${restaurantNames}" var="restaurantName">
					<option value="${restaurantName}">${restaurantName}</option>
				</c:forEach>
			</select><input type="submit" value="送出" /><br>
		</form>
	</div>
</body>
<script>
    document.addEventListener("DOMContentLoaded", function() {
        showSidebar('reservation');
    });
</script>
</html>