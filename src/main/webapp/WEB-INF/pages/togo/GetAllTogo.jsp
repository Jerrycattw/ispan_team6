<%@page import="com.TogoOrder.bean.TogoBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>查詢全部訂單</title>
<link rel="stylesheet" href="../template/template.css">
<script src="../template/template.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<link rel="stylesheet" href="../template/table.css">
</head>

<body>
<jsp:include page="../../../HomePage.jsp"></jsp:include>

	<div class="content" id="content">
	<h2>訂單總覽</h2>
	
	<form action="../Togo/AddTogo.html" method="get" style="margin-bottom: 20px;">
	    <button type="submit">新增訂單</button>
	</form>
	
	<table border = "1">
		<tr style = "background-color: #a8fefa">
			<th>訂單編號</th><th>會員編號</th><th>姓名</th><th>電話</th><th>總金額</th><th>成立時間</th><th>租借單號</th>
			<th>訂單狀態</th><th>餐廳編號</th><th>訂單備註</th><th>取消</th><th>更新</th><th>訂單明細</th><th>新增明細</th></tr>	
			<c:forEach var="togo" items="${togoList}">
            <tr>
            	<td><c:out value="${togo.togoId}" /></td>
                <td>${togo.memberId}</td>
                <td>${togo.tgName}</td>
                <td>${togo.tgPhone}</td>
                <td>${togo.totalPrice}</td>
                <td>${togo.formattedtogoCreateTime}</td>
                <td>${togo.rentId}</td>
                <td>
                	<c:choose>
                    	<c:when test="${togo.togoStatus == 1}">未取餐</c:when>
                    	<c:when test="${togo.togoStatus == 2}">已取餐</c:when>
                    	<c:otherwise>取消訂單</c:otherwise>
                	</c:choose>
                </td>
                <td>${togo.restaurantId}</td>
                <td>${togo.togoMemo}</td>
                
                <td><form action="/EEIT187-6/TogoController/delete" method="get">
                        <input type="hidden" name="togoId" value="${togo.togoId}">
                        <button type="submit">取消</button>
                    </form></td>
                <td><form action="/EEIT187-6/TogoController/getForUpdate" method="get">
                    	<input type="hidden" name="togoId" value="${togo.togoId}">
                        <input type="submit" value="更新" />
                    </form></td>
                <td><form action="/EEIT187-6/TogoItemController/get" method="get">
                        <input type="hidden" name="togoId" value="${togo.togoId}">
                        <button type="submit">查看明細</button>
                    </form></td>
                <td>
                
                <form action="../Togo/AddTogoItem.html?togoId=${togo.togoId}" method="get">                
                        <input type="hidden" name="togoId" value="${togo.togoId}">
                        <button type="submit">新增明細</button>
                    </form></td>
            </tr>
        	</c:forEach>
	</table>
	
	<h3>共<c:out value="${fn:length(togoList)}"/>筆資料</h3>
	</div>
<script>
	document.addEventListener("DOMContentLoaded", function() {
	    showSidebar('order');
	
	    const deleteSuccess = "${deleteSuccess}";
	    const updateSuccess = "${updateSuccess}";
	    
	    if (deleteSuccess === 'true') {
	        Swal.fire({
	            icon: 'success',
	            title: '成功取消!',
	            text: '訂單已經成功取消。',
	            showConfirmButton: false,
	            timer: 1500
	        }).then(function() {
	            window.location.href = '/EEIT187-6/TogoController/getAll';
	        });
	    }
	    if (updateSuccess === 'true') {
	        Swal.fire({
	            icon: 'success',
	            title: '成功更新!',
	            text: '訂單更新成功。',
	            showConfirmButton: false,
	            timer: 1500
	        }).then(function() {
	            window.location.href = '/EEIT187-6/TogoController/getAll';
	        });
	    }   
	});
</script>
<script>
    document.addEventListener("DOMContentLoaded", function() {
        showSidebar('order');
    });
</script>
</body>
</html>