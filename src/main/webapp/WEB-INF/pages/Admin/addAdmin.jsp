<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新增管理員</title>
<link rel="stylesheet" href="/EEIT187-6/template/template.css">
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

            <form id="addAdminForm" action="${pageContext.request.contextPath}/admin/add" method="POST">
                <div class="form-group">
                    <label for="adminName">管理員名稱</label>
                    <input type="text" id="adminName" name="adminName" placeholder="輸入管理員名稱" required>
                </div>
                <div class="form-group">
                    <label for="account">帳號</label>
                    <input type="text" id="account" name="account" placeholder="輸入帳號" required>
                </div>
                <div class="form-group">
                    <label for="password">密碼</label>
                    <input type="password" id="password" name="password" placeholder="輸入密碼" required>
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