<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.*" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>SearchAllProducts</title>
    <link rel="stylesheet" href="../template/template.css">
    <link rel="stylesheet" href="../template/table.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <!-- 引入 DataTables 的 CSS -->
    <link rel="stylesheet" href="https://cdn.datatables.net/1.12.1/css/jquery.dataTables.min.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <!-- 引入 DataTables 的 JS -->
    <script src="https://cdn.datatables.net/1.12.1/js/jquery.dataTables.min.js"></script>
    <script src="../template/template.js"></script>
    <jsp:include page="../HomePage.jsp"></jsp:include>
    <meta charset="UTF-8">
    <style>
        .custom-button {
            background: none;
            border: none;
            cursor: pointer;
            color: #9d80f4;
        }
        .custom-button-delete {
            color: #6140c4;
        }
        /* DataTables default styling (optional) */
        .dataTables_wrapper .dataTables_paginate .paginate_button {
            padding: 0 10px;
        }
    </style>
</head>
<body>
<div class="content" id="content">
    <div align="center">
        <h2>所有商品資料</h2>
        <!-- 添加 id="productsTable" 和 class="display" -->
        <table id="productsTable" class="display" border="1">
            <thead>
                <tr style="background-color:#9D9D9D">
                    <th>商品編號</th>
                    <th>商品名稱</th>
                    <th>商品金額</th>
                    <th>商品照片</th>
                    <th>商品庫存</th>
                    <th>商品狀態</th>
                    <th>商品描述</th>
                    <th>商品類別編號</th>
                    <th>商品類別名稱</th>
                    <th>商品修改</th>
                    <th>商品刪除</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="p" items="${products}">
                    <tr>
                        <td>${p.productId}</td>
                        <td>${p.productName}</td>
                        <td>${p.productPrice}</td>
                        <td><img src="${p.productPicture}" alt="No Image" width="100" height="100" /></td>
                        <td>${p.productStock}</td>
                        <td>${p.productStatus}</td>
                        <td>${p.productDescription}</td>
                        <td>${p.productType.productId}</td>
                        <td>${p.productType.productName}</td>
                        <td>
                            <form method="post" action="/EEIT187-6/ProductController/UpdateProduct">
                                <input type="hidden" name="productId" value="${p.productId}">
                                <button type="submit" class="custom-button">
                                    <i class="fa-regular fa-pen-to-square fa-xl"></i>
                                </button>
                            </form>
                        </td>
                        <td>
                            <form method="post" action="/EEIT187-6/ProductController/DelProduct">
                                <input type="hidden" name="productId" value="${p.productId}">
                                <button type="submit" class="custom-button custom-button-delete">
                                    <i class="fa-solid fa-trash-arrow-up fa-xl"></i>
                                </button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <h3>共${fn:length(products)}筆商品資料</h3>
    </div>
</div>

<script>
$(document).ready(function() {
    $('#productsTable').DataTable({
        "paging": true,
        "searching": true,
        "ordering": true,
        "info": true
    });

    showSidebar('store');
});
</script>
<script>
document.addEventListener("DOMContentLoaded", function() {
    showSidebar('store');
});
</script>
</body>
</html>
