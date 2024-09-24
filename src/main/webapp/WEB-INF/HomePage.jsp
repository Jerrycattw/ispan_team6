<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home</title>
<link rel="stylesheet" href="./template/template.css">
<script src="./template/template.js"></script>
</head>
<body>
    <!-- 上方選單 -->
    <div class="top-menu">
        <a href="#" onclick="showSidebar('member')">會員管理</a>
        <a href="#" onclick="showSidebar('store')">商城管理</a>
        <a href="#" onclick="showSidebar('order')">訂餐管理</a>
        <a href="#" onclick="showSidebar('rental')">租借用具管理</a>
        <a href="#" onclick="showSidebar('reservation')">餐廳訂位管理</a>
        <a href="#" onclick="showSidebar('points')">會員積分優惠券管理</a>
    </div>
<div class="sidebar" id="sidebar"></div>
<script>
document.addEventListener("DOMContentLoaded", function() {
    showSidebar('');
    
});
</script>
</body>
</html>