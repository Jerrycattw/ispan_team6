<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Table data</title>
<link rel="stylesheet" href="../template/template.css">
<link rel="stylesheet" href="../template/add.css">
<script src="../template/template.js"></script>
</head>
<body>
<jsp:include page="../../../HomePage.jsp"></jsp:include>

    <!-- 把JSP的內容放到這個div content裡面!!! -->
    <div class="content" id="content">
    
		<h2>新增餐廳桌位種類資料</h2>
		<form method="post" action="/EEIT187-6/Table/add2">
			餐廳名稱 : <input type="text" disabled name="restaurantName" value="${restaurantName}"/><p>
			<input type="hidden" name="restaurantName"  value="${restaurantName}"/><p>
			
			選擇桌位代號 : <select name="tableTypeId">
				<c:forEach items="${allTypes}" var="type">
					<option value="${type.tableTypeId}">${type.tableTypeId} (${type.tableTypeName} 人桌)</option>
				</c:forEach>
			</select>
			
			
			輸入桌位數量 : <input type="Number" name="tableTypeNumber"  min="0" max="30" step="1" /><p>
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