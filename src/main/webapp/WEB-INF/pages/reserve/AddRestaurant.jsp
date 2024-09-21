<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Restaurant</title>
<link rel="stylesheet" href="../template/template.css">
<link rel="stylesheet" href="../template/add.css">
<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.css">
<script src="../template/template.js"></script>
<style>
span{
float: right;
}
</style>
</head>

<body>
<jsp:include page="../../../HomePage.jsp"></jsp:include>

    <!-- 把JSP的內容放到這個div content裡面!!! -->
    <div class="content" id="content">
    
	<h2>新增餐廳資料</h2>
	<form method="post" action="/EEIT187-6/Restaurant/add" enctype="multipart/form-data">
    <label for="rname">輸入餐廳名稱 : <span id="rnamespan"></span></label>
    <input type="text" name="rname" id="rname" />
	
    <label for="raddress">輸入餐廳地址 : <span id="raddressspan"></span></label>
    <input type="text" name="raddress" id="raddress"/>

    <label for="rphone">輸入餐廳電話 : <span id="rphonespan"></span></label>
    <input type="text" name="rphone" id="rphone"/>

    <label>輸入餐廳開始營業時間 :</label>
    <input name="ropen" class="timepicker"/>
    

    <label>輸入餐廳結束營業時間 :</label>
    <input name="rclose" class="timepicker"/>

    <label>輸入餐廳用餐時間限制 :</label>
    <input type="number" name="reattime" min="30" max="180" step="30"/>
    
    <label>上傳餐廳照片 :<input type="file" name="rimg" /></label>

    <input type="submit" value="確定" />
	</form>

	</div>
	
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.js"></script>
<script>
    document.addEventListener("DOMContentLoaded", function() {
        showSidebar('reservation');
    });
    
    $(function(){
    	
    	
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
	   
	   
    	
    })
    

</script>

</body>
</html>