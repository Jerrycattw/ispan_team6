<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>修改管理員權限</title>
<link rel="stylesheet" href="/EEIT187-6/template/template.css">
<script src="/EEIT187-6/template/template.js"></script>
</head>
<body>
<jsp:include page="../../../HomePage.html"></jsp:include>

    <!-- 把JSP的內容放到這個div content裡面!!! -->
    <div class="content" id="content">
    	<jsp:include page="views/adminPermissionResult.jsp"></jsp:include>
    </div>
<script>
document.addEventListener("DOMContentLoaded", function() {
    showSidebar('member');
    
});
</script>
</body>
</html>