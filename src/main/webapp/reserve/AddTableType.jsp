<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add TableType data</title>
<link rel="stylesheet" href="../template/template.css">
<link rel="stylesheet" href="../template/add.css">
<script src="../template/template.js"></script>
</head>
<body>
<jsp:include page="../HomePage.html"></jsp:include>

    <!-- 把JSP的內容放到這個div content裡面!!! -->
    <div class="content" id="content">
    
		<h2>新增桌位種類資料</h2>
		<form method="post" action="/EEIT187-6/TableType/add">
			輸入桌位代號 : <input type="text" name="tableTypeId" /><p>
			輸入桌位人數 : <input type="number" name="tableTypeName" min="2" max="20" step="2"/><p>
			<input type="submit" value="確定" />
		</form>
	
    </div>
<script>
    document.addEventListener("DOMContentLoaded", function() {
        showSidebar('reservation');
    });
</script>
</body>
</html>