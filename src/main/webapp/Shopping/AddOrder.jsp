<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>AddOrder</title>
    <link rel="stylesheet" href="../template/template.css">
    <link rel="stylesheet" href="../template/table.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <script src="../template/template.js"></script>
    <jsp:include page="../HomePage.html"></jsp:include>
    <style>
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
<body>
    <div class="content" id="content">
        <div align="center">
            <h2>新增訂單明細</h2>
            <form method="post" action="/EEIT187-6/ShoppingController/AddOrder">
                請輸入以下資料 :<p>
                會員: 
                <select name="memberId">
                    <option value="">請選擇會員</option>
                    <c:forEach var="member" items="${members}">
                        <option value="${member.memberId}">
                            ${member.memberId}. ${member.memberName}. ${member.phone}
                        </option>
                    </c:forEach>
                </select><p>

                購買商品: 
                <select name="productId" id="productId">
                    <option value="">請選擇商品</option>
                    <c:forEach var="product" items="${products}">
                        <option value="${product.productId}">
                            ${product.productId}. ${product.productName}
                        </option>
                    </c:forEach>
                </select><p>

                產品數量: 
                <input type="text" id="shoppingItemQuantity" name="shoppingItemQuantity" /><p>
                <div id="error-container-quantity" class="error-container">
                    <i class="fa-regular fa-circle-xmark error-icon"></i>
                    <span id="error-message-quantity">請輸入正確數字</span>
                </div>
                <p>
                <input type="submit" value="確定" />
            </form>
        </div>
    </div>

    <script>
    document.addEventListener("DOMContentLoaded", function() {
        showSidebar('store');
        
        const quantityInput = document.getElementById('shoppingItemQuantitys');
        const errorContainerQuantity = document.getElementById('error-container-quantity');

        function validateNumber(input, errorContainer) {
            const value = input.value;
            // 檢查輸入值是否為數字
            if (/^\d+$/.test(value)) {
                errorContainer.style.display = 'none';
            } else {
                errorContainer.style.display = 'flex';
            }
        }

        quantityInput.addEventListener('input', function() {
            validateNumber(quantityInput, errorContainerQuantity);
        });
    });
    </script>
</body>
</html>