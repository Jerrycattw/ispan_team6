<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.*, com.shopping.bean.ShoppingBean, com.shopping.dao.ShoppingDao,
    com.shopping.bean.ItemBean, com.shopping.dao.ItemDao,
    com.shopping.bean.ProductBean, com.shopping.dao.ProductDao"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Update Item</title>
    <link rel="stylesheet" href="../template/template.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <script src="../template/template.js"></script>
    <jsp:include page="../../../HomePage.jsp"></jsp:include>
    <script>
    function updateItemPrice() {
        var quantity = document.getElementById("shoppingItemQuantity").value;
        var price = document.getElementById("productPrice").value;
        var totalPrice = quantity * price;
        document.getElementById("shoppingItemPrice").value = totalPrice;
    }
    </script>
    <style>
    .error-container {
        display: flex;
        align-items: center;
        color: red;
        display: none; /* 預設隱藏錯誤消息 */
    }
    
    .error-icon {
        margin-right: 5px;
    }
    </style>
</head>
<body>
<div class="content" id="content">
    <div align="center">
        <h2>更新訂單項目</h2>
        <form method="post" action="/EEIT187-6/ItemController/updateItem">
            <c:choose>
            <c:when test="${not empty item}">
                    <input type="hidden" name="shoppingId" value="${item.shoppingId}" />
                    <p>訂單編號: <input type="text" name="shoppingId" value="${item.shoppingId}" disabled/></p>

                    
                    <input type="hidden" name="productId" value="${item.productId}" />
                    <p>產品編號: <input type="text" name="productId" value="${item.productId}" disabled/></p>
                    
                    <input type="hidden" name="productName" value="${item.product.productName}" />
                    <p>產品名稱: <input type="text" name="productName" value="${item.product.productName}" disabled/></p>
                    
                    <p>產品項目數量: <input type="text" id="shoppingItemQuantity" name="shoppingItemQuantity" value="${item.shoppingItemQuantity}" oninput="updateItemPrice()"/></p>
                    
                    <div id="error-container-quantity" class="error-container">
                        <i class="fa-regular fa-circle-xmark error-icon"></i>
                        <span id="error-message-quantity">請輸入正確數字</span>
                    </div>
                    
                    <input type="hidden" id="productPrice" name="productPrice" value="${item.product.productPrice}" />
                    <p>產品價格: <input type="text" id="productPrice" name="productPrice" value="${item.product.productPrice}" disabled/></p>
                    
                    <input type="hidden" id="shoppingItemPrice" name="shoppingItemPrice" value="${item.shoppingItemPrice}" />
                    <p>產品項目金額: <input type="text" id="shoppingItemPrice" name="shoppingItemPrice" value="${item.shoppingItemPrice}" disabled/></p>
                    
                    <input type="submit" value="確定" />
                </c:when>
                <c:otherwise>
                    <p>No items found for this order.</p>
                </c:otherwise>
            </c:choose>
        </form>
    </div>
</div>
<script>
function updateItemPrice() {
    var quantity = document.getElementById("shoppingItemQuantity").value;
    var price = document.getElementById("productPrice").value;
    var totalPrice = quantity * price;
    document.getElementById("shoppingItemPrice").value = totalPrice;
}

function validateNumber(input, errorContainer) {
    const value = input.value;
    // 檢查輸入值是否為數字
    if (/^\d+$/.test(value)) {
        errorContainer.style.display = 'none';
    } else {
        errorContainer.style.display = 'flex';
    }
}

document.addEventListener("DOMContentLoaded", function() {
    showSidebar('store');

    const quantityInput = document.getElementById('shoppingItemQuantity');
    const errorContainerQuantity = document.getElementById('error-container-quantity');

    quantityInput.addEventListener('input', function() {
        validateNumber(quantityInput, errorContainerQuantity);
        updateItemPrice();
    });
});
</script>
</body>
</html>