<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>AddProduct</title>
	<link rel="stylesheet" href="../template/template.css">
	<link rel="stylesheet" href="../template/table.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
	<script src="../template/template.js"></script>
	<jsp:include page="../../../HomePage.jsp"></jsp:include>
<style>
.content {
    text-align: center; 
}
.form-container {
    position: relative;
    display: inline-block; 
}
.error-container {
    display: flex;
    align-items: center;
    color: red;
    display: none; 
}

.error-icon {
    margin-right: 5px;
}
</style>
</head>
<!--<body style="background-color: #ACD6FF">-->
		<div align="center">
		<div class="content">
			<h2>新增商品資料</h2>
			<form method="post" action="/EEIT187-6/ProductController/addProduct"
				enctype="multipart/form-data">
				  <div class="form-container">
				請輸入以下資料 :
				<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				商品名稱: <input type="text" name="productName" />
				<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				
                商品金額: <input type="text" id="productPrice" name="productPrice" />
                 <div id="error-container-price" class="error-container">
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                    <i class="fa-regular fa-circle-xmark error-icon"></i>
                    <span id="error-message-price">請輸入正確數字</span>
                </div>
				<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				
				商品照片: <input type="file" name="productPicture" />
				<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				
				  商品庫存: <input type="text" id="productStock" name="productStock" />
	            <div id="error-container-stock" class="error-container">
                   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                <i class="fa-regular fa-circle-xmark error-icon"></i>
                <span id="error-message-stock">請輸入正確數字</span>
            </div>
				<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				
				商品狀態: <input type="text" id="productStatus" name="productStatus" />
				<div id="error-container-status" class="error-container">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<i class="fa-regular fa-circle-xmark error-icon"></i>
                    <span id="error-message-status">請以數字輸入正確商品狀態</span><P>
                </div>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				(1.已上架 2.已停售)
				<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				
				商品描述: <input type="text" name="productDescription" />
				<p>&nbsp;&nbsp;&nbsp;&nbsp;
				
				商品類別編號: <input type="text" name="productTypeId" /><P>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				(1.商品 2.甜點 3.客製化蛋糕)
				<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				商品類別名稱: <input type="text" name="productTypeName" />
				<p><input type="submit" value="確定" />
			</form>
			</div>
			</div>
</body>
<script>
document.addEventListener("DOMContentLoaded", function() {
    showSidebar('store');

    const priceInput = document.getElementById('productPrice');
    const stockInput = document.getElementById('productStock');
    const statusInput = document.getElementById('productStatus');
    const errorContainerPrice = document.getElementById('error-container-price');
    const errorContainerStock = document.getElementById('error-container-stock');
    const errorContainerStatus = document.getElementById('error-container-status');

    function validateNumber(input, errorContainer) {
        const value = input.value;
        // 檢查輸入值是否為數字
        if (/^\d*\.?\d+$/.test(value)) {
            errorContainer.style.display = 'none';
        } else {
            errorContainer.style.display = 'flex';
        }
    }

    function validateStatus(input, errorContainer) {
        const value = input.value;
        // 檢查輸入值是否為 1 或 2
        if (value === '1' || value === '2') {
            errorContainer.style.display = 'none';
        } else {
            errorContainer.style.display = 'flex';
        }
    }

    priceInput.addEventListener('input', function() {
        validateNumber(priceInput, errorContainerPrice); 
    });

    stockInput.addEventListener('input', function() {
        validateNumber(stockInput, errorContainerStock);
    });

    statusInput.addEventListener('input', function() {
        validateStatus(statusInput, errorContainerStatus); 
    });
});
</script>
<script>
document.addEventListener("DOMContentLoaded", function() {
    showSidebar('store');
    
});
</script>
</html>
