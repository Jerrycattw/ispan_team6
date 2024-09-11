<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>管理員一覽</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/template/template.css">
<script src="${pageContext.request.contextPath}/template/template.js"></script>
</head>
<body>
	<jsp:include page="../HomePage.html"></jsp:include>

	<!-- 把JSP的內容放到這個div content裡面!!! -->
	<div class="content" id="content">
		<jsp:include page="views/findAllAdminsResult.jsp"></jsp:include>
	</div>
<script>
document.addEventListener("DOMContentLoaded", function() {
    showSidebar('member');
    
});
</script>
</body>
</html>