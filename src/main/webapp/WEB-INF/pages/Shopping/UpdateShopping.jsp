<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.*, com.shopping.bean.ShoppingBean, com.shopping.dao.ShoppingDao"%>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Update Product</title>
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
<link rel="stylesheet" href="../template/template.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
<script src="../template/template.js"></script>
<jsp:include page="../../../HomePage.jsp"></jsp:include>
<!--<body style="background-color: #ACD6FF">  -->
<div class="content" id="content">
    <div align="center">
        <h2>更新訂單資料</h2>
        <form method="post" action="/EEIT187-6/ShoppingController/updateDataShopping">
        
            <input type="hidden" name="shoppingId" value="${shoppingBean.shoppingId}" />
            
            <c:if test="${not empty shoppingBean}">
            	&nbsp;&nbsp;&nbsp;&nbsp;訂單編號:
                <input type="hidden" name="shoppingId" value="${shoppingBean.shoppingId}" disabled/><p>
                訂單總金額:
                <input type="hidden" name="shoppingTotal" value="${shoppingBean.shoppingTotal}"/>
                <input type="text" name="shoppingTotal" value="${shoppingBean.shoppingTotal}" disabled/><p>
                &nbsp;&nbsp;&nbsp;&nbsp;訂單日期:
                <input type="hidden" name="shoppingDate" value="${shoppingBean.formattedShoppingDate}"/>
                <input type="text" name="shoppingDate" value="${shoppingBean.formattedShoppingDate}" disabled/><p>
                 &nbsp;&nbsp;&nbsp;&nbsp;會員編號:
                <input type="hidden" name="memberId" value="${shoppingBean.member.memberId}" />
                <input type="text" name="memberId" value="${shoppingBean.member.memberId}" disabled/><p>
                &nbsp;&nbsp;&nbsp;&nbsp;會員姓名:
                <input type="hidden" name="memberName" value="${shoppingBean.member.memberName}" />
                <input type="text" name="memberName" value="${shoppingBean.member.memberName}" disabled/><p>
                &nbsp;&nbsp;&nbsp;&nbsp;訂單狀況:
                <input type="text" id="orderStatus" name="shoppingStatus" value="${shoppingBean.shoppingStatus}"/><p>
                <div id="error-container-status" class="error-container">
                    <i class="fa-regular fa-circle-xmark error-icon"></i>
                    <span id="error-message-status">請以數字輸入正確訂單狀態</span>
                </div>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                (1.待出貨 2.已出貨 3.其他)<p>
                &nbsp;&nbsp;&nbsp;&nbsp;訂單備註:
                <textarea name="shoppingMemo" rows="4" cols="20">${shoppingBean.shoppingMemo}</textarea><p>
            </c:if>
            
            <input type="submit" value="確定" />
        </form>
    </div>
</div>
<script>
document.addEventListener("DOMContentLoaded", function() {
    showSidebar('store');

    const statusInput = document.getElementById('orderStatus');
    const errorContainerStatus = document.getElementById('error-container-status');

    function validateStatus(input, errorContainer) {
        const value = input.value;
        // 檢查輸入值是否為 1、2 或 3
        if (value === '1' || value === '2' || value === '3') {
            errorContainer.style.display = 'none';
        } else {
            errorContainer.style.display = 'flex';
        }
    }

    statusInput.addEventListener('input', function() {
        validateStatus(statusInput, errorContainerStatus); 
    });
});
</script>
</body>
</html>