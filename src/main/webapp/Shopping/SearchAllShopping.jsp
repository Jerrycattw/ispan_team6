<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.*, com.shopping.bean.ShoppingBean, com.shopping.dao.ShoppingDao" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>SearchAllShopping</title>
    <link rel="stylesheet" href="../template/template.css">
    <link rel="stylesheet" href="../template/table.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.12.1/css/jquery.dataTables.min.css">
    <script src="../template/template.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.datatables.net/1.12.1/js/jquery.dataTables.min.js"></script>
    <jsp:include page="../HomePage.jsp" />
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
        .dataTables_wrapper .dataTables_paginate .paginate_button {
            padding: 0 10px;
        }
    </style>
</head>
<body>
    <div class="content" id="content">
        <div align="center">
            <h2>所有訂單資料</h2>
            <table id="shoppingTable" class="display" border="1">
                <thead>
                    <tr style="background-color:#9D9D9D">
                        <th>訂單編號</th>
                        <th>訂單總金額</th>
                        <th>訂單日期</th>
                        <th>會員編號</th>
                        <th>會員姓名</th>
                        <th>訂單狀態</th>
                        <th>訂單備註</th>
                        <th>訂單修改</th>
                        <th>訂單刪除</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="shopping" items="${shoppings}">
                        <tr>
                            <td><a href="/EEIT187-6/ShoppingController/ShowItemDetail?shoppingId=${shopping.shoppingId}">${shopping.shoppingId}</a></td>
                            <td>${shopping.shoppingTotal}</td>
                            <td>${shopping.formattedShoppingDate}</td>
                            <td>${shopping.member.memberId}</td> 
                			<td>${shopping.member.memberName}</td>
                            <td>${shopping.shoppingStatus}</td>
                            <td>${shopping.shoppingMemo}</td>
                            <td>
                                <form method="post" action="/EEIT187-6/ShoppingController/UpdateShopping">
                                    <input type="hidden" name="shoppingId" value="${shopping.shoppingId}">
                                    <button type="submit" class="custom-button">
                                        <i class="fa-regular fa-pen-to-square fa-xl"></i>
                                    </button>
                                </form>
                            </td>
                            <td>
                                <form method="post" action="/EEIT187-6/ShoppingController/DelOrder">
                                    <input type="hidden" name="shoppingId" value="${shopping.shoppingId}">
                                    <button type="submit" class="custom-button custom-button-delete">
                                        <i class="fa-solid fa-trash-arrow-up fa-xl"></i>
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <h3>共${shoppings != null ? shoppings.size() : 0}筆訂單資料</h3>
        </div>
    </div>
    <script>
    $(document).ready(function() {
        $('#shoppingTable').DataTable({
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