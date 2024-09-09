<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Reserve data</title>
<link rel="stylesheet" href="../template/template.css">
<link rel="stylesheet" href="../template/add.css">
<script src="../template/template.js"></script>
</head>
<body>
<jsp:include page="../HomePage.html"></jsp:include>

	<div class="content" id="content">
<h2>訂 位 資 料</h2>
<form method="post" action="/EEIT187-6/Reserve/set2">

	<input type="hidden" name="reserveId" value="${reserve.reserveId}"/>
	訂位編號 : <input type="text" disabled value="${reserve.reserveId}"/><p>
	<input type="hidden" name="memberId" value="${reserve.memberId}"/>
	會員編號 : <input type="text" name="memberId" disabled value="${reserve.memberId}"/><p>
	<input type="hidden" name="restaurantId" value="${reserve.restaurantId}"/>
	餐廳編號 : <input type="text" name="restaurantId" disabled value="${reserve.restaurantId}"/><p>
	用餐人數 :
	<select name="reserveSeat">
    <c:forEach var="i" begin="2" end="10">
        <option value="${i}">${i}</option>
    </c:forEach>
	</select><p><br>
	用餐時間 : <input type="datetime-local" name="reserveTime" value="${reserve.formattedReserveTime}"  id="datetimePicker"/><p>
	
	<input type="submit" value="確定" />
</form>
</div>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<script>
    document.addEventListener("DOMContentLoaded", function() {
        showSidebar('reservation');
    });
    
$(function () {
    	
        // 获取当前日期和时间
        const now = new Date();
        console.log(now);
        const year = now.getFullYear();
        console.log(year);
        const month = String(now.getMonth() + 1).padStart(2, '0');
        console.log(month);
        const day = String(now.getDate()).padStart(2, '0');
        console.log(day);
        const hours = String(now.getHours()).padStart(2, '0');
        console.log(hours);
        const minutes = String(now.getMinutes()).padStart(2, '0');
        console.log(minutes);
        
        // 格式化为 YYYY-MM-DDTHH:MM
        const minDateTime = year+"-"+month+"-"+day+"T"+hours+":"+minutes;
        console.log(minDateTime);
        
     	// 設定最大日期時間為未來一個月
        const futureDate = new Date();
        futureDate.setMonth(futureDate.getMonth() + 1);
        const futureYear = futureDate.getFullYear();
        const futureMonth = String(futureDate.getMonth() + 1).padStart(2, '0');
        const futureDay = String(futureDate.getDate()).padStart(2, '0');
        const futureHours = String(futureDate.getHours()).padStart(2, '0');
        const futureMinutes = String(futureDate.getMinutes()).padStart(2, '0');
        
        const maxDateTime = futureYear+"-"+futureMonth+"-"+futureDay+"T"+futureHours+":"+futureMinutes;
        
        // 設定 input 元素的 min 和 max 屬性
        const dateTimePicker = document.getElementById('datetimePicker');
        dateTimePicker.setAttribute('min', minDateTime);
        dateTimePicker.setAttribute('max', maxDateTime);
    	
    });
</script>
</body>
</html>