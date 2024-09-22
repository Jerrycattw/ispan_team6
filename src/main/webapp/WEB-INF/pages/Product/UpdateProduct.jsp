<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>更新商品</title>
    <link rel="stylesheet" href="../template/template.css">
    <link rel="stylesheet" href="../template/table.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <script src="../template/template.js"></script>
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
<body>
    <jsp:include page="../../../HomePage.jsp"></jsp:include>

    <div align="center">
        <div class="content">
            <h2>更新商品資料</h2>
            <form method="post" action="/EEIT187-6/ProductController/updateDataProduct" enctype="multipart/form-data">
                <c:choose>
                    <c:when test="${not empty product}">
                        <p>
                            <div class="form-group">
                                <label for="productName">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;商品名稱:</label>
                                <input type="text" id="productName" name="productName" value="${product.productName}" />
                            </div>
                        </p>
                        <p>
                            <div class="form-group">
                                <label for="productPrice">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;商品金額:</label>
                                <input type="text" id="productPrice" name="productPrice" value="${product.productPrice}" />
                                <div id="error-container-price" class="error-container">
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <i class="fa-regular fa-circle-xmark error-icon"></i>
                                    <span id="error-message-price">請輸入正確數字</span>
                                </div>
                            </div>
                        </p>
                        <p>
                            <div class="form-group">
                                <label for="productPicture">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;商品照片:</label>
                                <input type="file" id="productPicture" name="productPicture" />
                            </div>
                        </p>
                        <p>
                            <div class="form-group">
                                <label for="productStock">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;商品庫存:</label>
                                <input type="text" id="productStock" name="productStock" value="${product.productStock}" />
                                <div id="error-container-stock" class="error-container">
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <i class="fa-regular fa-circle-xmark error-icon"></i>
                                    <span id="error-message-stock">請輸入正確數字</span>
                                </div>
                            </div>
                        </p>
                        <p>
                            <div class="form-group">
                                <label for="productStatus">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;商品狀態:</label>
                                <input type="text" id="productStatus" name="productStatus" value="${product.productStatus}" />
                                <div id="error-container-status" class="error-container">
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <i class="fa-regular fa-circle-xmark error-icon"></i>
                                    <span id="error-message-status">請以數字輸入正確商品狀態</span>
                                </div>
                                <p>
                                    <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    (1.已上架 2.已停售)</span>
                                </p>
                            </div>
                        </p>
                        <p>
                            <div class="form-group">
                                <label for="productDescription">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;商品描述:</label>
                                <input type="text" id="productDescription" name="productDescription" value="${product.productDescription}" />
                            </div>
                        </p>
                        <p>
                            <div class="form-group">
                                <label for="productTypeId">商品類別編號:</label>
                                <input type="text" id="productTypeId" name="productTypeId" value="${product.productType.productTypeId}" />
                                <p>
                                    <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    (1.商品 2.甜點 3.客製化蛋糕)</span>
                                </p>
                            </div>
                        </p>
                        <p>
                        <input type="hidden" name="productId" value="${product.productId}" />
                        <div class="form-group">
                            <p><input type="submit" value="確定" /></p>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <p>商品資料無法讀取。</p>
                    </c:otherwise>
                </c:choose>
            </form>
        </div>
    </div>

    <script>
        document.addEventListener("DOMContentLoaded", function() {
            showSidebar('store');
        });

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
    </script>
</body>
</html>
