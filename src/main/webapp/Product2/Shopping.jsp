<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import="com.shopping.bean.ProductBean, com.shopping.dao.ProductDao" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Shopping</title>
	<link rel="stylesheet" href="../template/template.css">
    <script src="../template/template.js"></script>
    <jsp:include page="../HomePage.html"></jsp:include>
	<style>
 .button-container {
            display: flex;
            justify-content: center;
            gap: 10px;
        }
	
	</style>
</head>
<div class="content" id="content">
<body style="background-color: #ACD6FF">
    <div align="center">
<h2>Shopping</h2>
	請輸入選擇欲使用功能 :<br><br>
<div id="Shopping" class="button-container">

	<form method="post" action="../Shopping/AddOrder.jsp">
	 <input type="submit" value="新增訂單" />
	</form>
	
	 &nbsp;&nbsp;&nbsp;&nbsp;
	<form method="post" action="/EEIT187-6/ShoppingController/SearchAllShopping">
	 <input type="submit" value ="查詢所有訂單" />
	</form>
</div>
</div>
 </div>
 <script>
document.addEventListener("DOMContentLoaded", function() {
    showSidebar('store');
    
});
</script>
</body>
</html>
