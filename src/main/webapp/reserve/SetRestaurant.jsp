<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Restaurant data</title>
<link rel="stylesheet" href="../template/template.css">
<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.css">
<script src="../template/template.js"></script>
<style>
h2 {
    text-align: center;
    color: #d4a14e;
}

.restaurant-info {
    display: flex;
    background-color: #2c2c2c;
    padding: 20px;
    border-radius: 10px;
    color: #ffffff;
}

.restaurant-img {
    flex: 1;
    margin-right: 20px;
}

.restaurant-img img {
    width: 100%;
    border-radius: 10px;
}

.restaurant-details {
    flex: 1.5;
}

.restaurant-details form {
    display: flex;
    flex-direction: column;
}

.restaurant-details label {
    font-weight: bold;
    margin-bottom: 5px;
}

.restaurant-details input.timepicker {
    width: 46%;
    background-color: #444444;
    color: #ffffff;
    border: none;
    padding: 10px;
    margin-bottom: 20px;
    border-radius: 5px;
}
.restaurant-details input[type="time"] {
    width: 46%;
    background-color: #444444;
    color: #ffffff;
    border: none;
    padding: 10px;
    margin-bottom: 20px;
    border-radius: 5px;
}

.restaurant-details input[type="Number"] {
    width: 46%;
    background-color: #444444;
    color: #ffffff;
    border: none;
    padding: 10px;
    margin-bottom: 20px;
    border-radius: 5px;
}

.restaurant-details input[type="text"] {
    width: 50%;
    background-color: #444444;
    color: #ffffff;
    border: none;
    padding: 10px;
    margin-bottom: 20px;
    border-radius: 5px;
}

.restaurant-details input[type="text"]:disabled {
    background-color: #444444;
    color: #d4a14e;
}

.restaurant-details input[type="text"]::placeholder {
    color: #d4a14e;
}
</style>
</head>
<body>
<jsp:include page="../HomePage.html"></jsp:include>

	<div class="content" id="content">
	


<h2>餐 廳 資 料</h2>
<form method="post" action="/EEIT187-6/Restaurant/set2" enctype="multipart/form-data">
<div class="restaurant-info">
    <div class="restaurant-img">
        <img src="${restaurant.restaurantImg}" alt="Restaurant Image"/>
        <label>上傳餐廳照片 :<input type="file" name="rimg" /></label>
    </div>
    <div class="restaurant-details">
        
            <label>餐廳編號 : 
            <input type="text" name="restaurantId" disabled value="${restaurant.restaurantId}"/>
            <input type="hidden" name="restaurantId" value="${restaurant.restaurantId}"/><br>
        	</label>
        	
            <label>餐廳名稱 : 
            <input type="text" name="rname" id="rname" value="${restaurant.restaurantName}"/><span id="rnamespan"></span><br>
            </label>
            
            <label>餐廳地址 : 
            <input type="text" name="raddress" id="raddress" value="${restaurant.restaurantAddress}"/><span id="raddressspan"></span><br>
            </label>
            
            <label>餐廳電話 :
            <input type="text" name="rphone" id="rphone" value="${restaurant.restaurantPhone}"/><span id="rphonespan"></span><br>
            </label> 
            
            <label>開始營業時間 : 
            <input class="timepicker" name="ropen" value="${restaurant.restaurantOpentime}"/><br>
            </label>
            
            <label>結束營業時間 : 
            <input class="timepicker" name="rclose" value="${restaurant.restaurantClosetime}"/><br>
            </label>
            
            <label>用餐時間限制 : 
            <input type="number" name="reattime" min="30" max="180" step="30" value="${restaurant.eattime}"/><br>
            </label>
            
            <label>餐廳狀態 : 
            <c:choose>
		    <c:when test="${restaurant.restaurantStatus == 1}">
		        <input type="radio" name="restaurantStatus" value="1" checked> 營業中
		        <input type="radio" name="restaurantStatus" value="2"> 已歇業
		        <input type="radio" name="restaurantStatus" value="3"> 籌備中<br>
		    </c:when>
		    <c:when test="${restaurant.restaurantStatus == 2}">
		        <input type="radio" name="restaurantStatus" value="1"> 營業中
		        <input type="radio" name="restaurantStatus" value="2" checked> 已歇業
		        <input type="radio" name="restaurantStatus" value="3"> 籌備中<br>
		    </c:when>
		    <c:when test="${restaurant.restaurantStatus == 3}">
		        <input type="radio" name="restaurantStatus" value="1"> 營業中
		        <input type="radio" name="restaurantStatus" value="2"> 已歇業
		        <input type="radio" name="restaurantStatus" value="3" checked> 籌備中<br>
		    </c:when>
			</c:choose>
			</label>
            
    <input type="submit" value="確定修改" />
    </div>
</div>
</form>



</div>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.js"></script>
<script>
    document.addEventListener("DOMContentLoaded", function() {
        showSidebar('reservation');
    });
    
    
    $(document).ready(function(){
        
        $('.timepicker').timepicker({
       	    timeFormat: 'HH:mm',
       	    interval: 30,
       	    dynamic: false,
       	    dropdown: true,
       	    scrollbar: true
       	});
    });
    
    
    $('#rname').on('blur', function() {
        let theNameVal = $(this).val();
        let $rnameSpan = $('#rnamespan');
	
        if (theNameVal.length == 0) {
            $rnameSpan.text("餐廳名稱不能空白").css({"color": "red", "font-style": "italic"});
        }else if (theNameVal.length < 3) {
            $rnameSpan.text("餐廳名稱長度須大於3").css({"color": "red", "font-style": "italic"});
        } else {
            $rnameSpan.text("輸入正確").css({"color": "green", "font-style": "italic"});
        }
    });
    
    
    $('#raddress').on('blur', function() {
        let theAddressVal = $(this).val();
        let $raddressSpan = $('#raddressspan');
        let addressRegex = /(?<city>\D+?[縣市])(?<district>\D+?[鄉鎮市區])(?<others>.+)/;

        if (theAddressVal.length == 0) {
            $raddressSpan.text("餐廳地址不能空白").css({"color": "red", "font-style": "italic"});
        } else if (!addressRegex.test(theAddressVal)) {
            $raddressSpan.text("餐廳地址格式不正確，請包含城市、區域和其他部分").css({"color": "red", "font-style": "italic"});
        } else {
            $raddressSpan.text("輸入正確").css({"color": "green", "font-style": "italic"});
        }
    });
    
    
    $('#rphone').on('blur', function() {
        let thePhoneVal = $(this).val();
        let $rphoneSpan = $('#rphonespan');
        let phoneRegex = /^(\d{2,3}-?\d{7,8}|\d{10})$/;

        if (thePhoneVal.length == 0) {
            $rphoneSpan.text("餐廳電話不能空白").css({"color": "red", "font-style": "italic"});
        } else if (!phoneRegex.test(thePhoneVal)) {
            $rphoneSpan.text("餐廳電話格式不正確").css({"color": "red", "font-style": "italic"});
        } else {
            $rphoneSpan.text("輸入正確").css({"color": "green", "font-style": "italic"});
        }
    });
</script>
</body>
</html>