<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>訂位錯誤</title>
</head>
<body>

<c:if test="${not empty message}">
    <script>
        alert("${message}");
        // 重定向到訂位頁面或其他適當的頁面
        window.location.href = "/EEIT187-6/reserve/AddReserve.jsp"; // 修改為實際的訂位頁面URL
    </script>
</c:if>

</body>
</html>