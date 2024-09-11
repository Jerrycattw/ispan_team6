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
<title>Item Detail</title>
<link rel="stylesheet" href="../template/template.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
<script src="../template/template.js"></script>
<jsp:include page="../HomePage.html"></jsp:include>
<meta charset="UTF-8">
<script src="https://kit.fontawesome.com/a260a05f7c.js" crossorigin="anonymous"></script>
<style>
#add {
    background: none;
    border: none;
    cursor: pointer;
    padding: 0;
}
</style>
</head>
<body>
<div class="content" id="content">
    <div align="center">
        <h2>訂單明細</h2>
        <form method="post" action="/EEIT187-6/ItemController/AddItem">
            <input type="hidden" name="shoppingId" value="${shopping.shoppingId}">
           <!-- <input type="text" name="shoppingId" value="${shopping.shoppingId}"> -->
            <select name="productId" id="productId">
                <option value="">請選擇商品</option>
                <c:forEach var="product" items="${productList}">
                    <option value="${product.productId}">
                        ${product.productId}. ${product.productName}
                    </option>
                </c:forEach>
            </select> &nbsp;&nbsp;
            <input type="text" name="shoppingItemQuantity" id="shoppingItemQuantity" placeholder="請輸入加購商品數量"> &nbsp;&nbsp;
            <button id="add"><i class="fa-solid fa-cart-plus fa-xl" style="color: #223658;"></i></button>
        </form>
        
        <c:if test="${not empty items}">
            <br><input type="hidden" id="shoppingId" value="${shopping.shoppingId}">
            <table id="items_table" border="1">
                <tr style="background-color:#9D9D9D">
                    <th>訂單編號</th>
                    <th>產品編號</th>
                    <th>產品名稱</th>
                    <th>產品項目數量</th>
                    <th>產品價格</th>
                    <th>產品項目金額</th>
                    <th>修改項目</th>
                    <th>刪除項目</th>
                </tr>
                <c:forEach var="item" items="${items}">
                    <tr>
                        <td>${item.shoppingId}</td>
                        <td>${item.productId}</td>
                        <td>${item.product.productName}</td>
                        <td>${item.shoppingItemQuantity}</td>
                        <td>${item.product.productPrice}</td>
                        <td>${item.shoppingItemQuantity * item.product.productPrice}</td>
                        <td>
                            <form method="post" action="/EEIT187-6/ItemController/ShowUpdateItem">
                                <input type="hidden" name="shoppingId" value="${item.shoppingId}">
                                <input type="hidden" name="productId" value="${item.productId}">
                                <button type="submit" style="background: none; border: none; cursor: pointer; color: #9d80f4;">
                                    <i class="fa-regular fa-pen-to-square fa-xl" style="color: #9d80f4;"></i>
                                </button>
                            </form>
                        </td>
                        <td>
                            <form method="post" action="/EEIT187-6/ItemController/DelItem">
                                <input type="hidden" name="shoppingId" value="${item.shoppingId}">
                                <input type="hidden" name="productId" value="${item.productId}">
                                <!-- <input type="hidden" name="shoppingItemQuantity" value="${item.shoppingItemQuantity}"> -->
                                <button type="submit" style="background: none; border: none; cursor: pointer; color: #9d80f4;">
                                    <i class="fa-solid fa-trash-arrow-up fa-xl" style="color: #6140c4;"></i>
                                </button>
                            </form>
                        </td>
                    </tr>
                   <c:set var="totalAmount" value="${(item.shoppingItemQuantity * item.product.productPrice)}" />
                </c:forEach>
            </table>
            <h4>訂單總金額: ${shopping.shoppingTotal}</h4>
        </c:if>
        <c:if test="${empty items}">
            <p>No items found</p>
        </c:if>
    </div>
</div>

<script>
document.addEventListener("DOMContentLoaded", function() {
    showSidebar('store');
});
</script>
</body>
</html>