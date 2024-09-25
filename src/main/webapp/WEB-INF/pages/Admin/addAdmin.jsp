<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新增管理員</title>
<link rel="stylesheet" href="/EEIT187-6/template/template.css">
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #121212; /* 深黑色背景 */
	color: #e0e0e0; /* 淺灰色文字 */
	justify-content: center;
	align-items: center;
	height: 100vh;
	margin: 0;
}

.login-container {
	background-color: #1f1f1f; /* 深灰色背景 */
	padding: 20px;
	border-radius: 8px;
	box-shadow: 0 0 10px rgba(255, 255, 255, 0.2); /* 白色陰影 */
	width: 100%;
	max-width: 400px;
}

h1 {
	text-align: center;
	margin-bottom: 20px;
	color: #ffffff; /* 白色文字 */
}

.form-group {
	margin-bottom: 15px;
}

label {
	display: block;
	margin-bottom: 5px;
	color: #b0b0b0; /* 較淺的灰色文字 */
}

input[type="text"], input[type="password"], select {
	width: 100%;
	padding: 10px;
	border: 1px solid #333; /* 更深的灰色邊框 */
	border-radius: 4px;
	background-color: #2c2c2c; /* 較深的背景色 */
	color: #e0e0e0; /* 淺灰色文字 */
	box-sizing: border-box; /* 包含邊框和內邊距在總寬度內 */
}

input[type="text"]::placeholder, input[type="password"]::placeholder {
	color: #777; /* 較淺的灰色佔位文字 */
}

button {
	width: 100%;
	padding: 10px;
	background-color: #333; /* 深灰色按鈕背景 */
	border: none;
	border-radius: 4px;
	color: #ffffff; /* 白色文字 */
	font-size: 16px;
	cursor: pointer;
	box-sizing: border-box; /* 包含邊框和內邊距在總寬度內 */
	transition: background-color 0.3s; /* 平滑的顏色過渡效果 */
}

button:hover {
	background-color: #444; /* 按鈕懸停時變為較淺的灰色 */
}
</style>
<script src="/EEIT187-6/template/template.js"></script>
</head>
<body>
	<jsp:include page="../../../HomePage.jsp"></jsp:include>
	<div class="content" id="content">
		<div class="login-container">
			<h1>新增管理員</h1>

			<!-- 顯示成功或錯誤訊息 -->
			<c:if test="${not empty message}">
				<div style="color: green;">${message}</div>
			</c:if>
			<c:if test="${not empty error}">
				<div style="color: red;">${error}</div>
			</c:if>

			<form id="addAdminForm"
				action="${pageContext.request.contextPath}/admin/add" method="POST">
				<div class="form-group">
					<label for="adminName">管理員名稱</label> <input type="text"
						id="adminName" name="adminName" placeholder="輸入管理員名稱" required>
				</div>
				<div class="form-group">
					<label for="account">帳號</label> <input type="text" id="account"
						name="account" placeholder="輸入帳號" required>
				</div>
				<div class="form-group">
					<label for="password">密碼</label> <input type="password"
						id="password" name="password" placeholder="輸入密碼" required>
				</div>
				<button type="submit">新增管理員</button>
			</form>
		</div>
	</div>

	<script>
		document.addEventListener("DOMContentLoaded", function() {
			showSidebar('member');
		});
	</script>
</body>
</html>