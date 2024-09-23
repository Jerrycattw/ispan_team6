<%@page import="com.TogoOrder.bean.TogoItemBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>查詢訂單項目</title>
<link rel="stylesheet" href="../template/template.css">
<script src="../template/template.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/validate.js/0.13.1/validate.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<link rel="stylesheet" href="../template/table.css">
</head>

<body>
<jsp:include page="../../../HomePage.jsp"></jsp:include>
	<div class="content" id="content">
	<h2>訂單項目清單</h2>
	
	<form action="/EEIT187-6/TogoController/getAll" method="get">
        <button type="submit">返回全部訂單</button>
    </form><p>
	<form class="addForm" action="../togo/AddTogoItem.html?togoId=${togoItem.togoId}" method="get">
    <c:forEach var="togoItem" items="${togoItemList}">
    	<c:set var="firstTogoId" value="${togoItemList[0].togoId}" />
        <input type="hidden" name="togoId" value="${togoItem.togoId}">
    </c:forEach>
        <button type="submit">新增訂單明細</button>
    </form>
    <p>
	
	<table border = "1">
		<tr style = "background-color: #a8fefa">
			<th>訂單編號</th><th>餐點編號</th><th>餐點名稱</th><th>訂購數量</th><th>訂單單項金額</th><th>刪除訂單項目</th><th>更新項目資訊</th></tr>	
			<c:forEach var="togoItem" items="${togoItemList}">
            <tr>
            	<td>${togoItem.togoId}</td>
                <td>${togoItem.foodId}</td>
                <td>${togoItem.foodName}</td>
                <td>${togoItem.amount}</td>
                <td>${togoItem.togoItemPrice}</td>
                
                <td><form class="deleteForm" action="/EEIT187-6/TogoItemController/delete" method="get">
                        <input type="hidden" name="togoId" value="${togoItem.togoId}">
                        <input type="hidden" name="foodId" value="${togoItem.foodId}">
                        <button type="submit" style="display: block; margin: auto;">刪除</button>
                    </form></td>
                <td><form action="/EEIT187-6/TogoItemController/getForUpdate" method="get">
                    	<input type="hidden" name="togoId" value="${togoItem.togoId}">
                        <input type="hidden" name="foodId" value="${togoItem.foodId}">
                        <button type="submit" style="display: block; margin: auto;">更新資訊</button>
                    </form></td>
            </tr>
        	</c:forEach>
	</table>
	
	<h3>共<c:out value="${fn:length(togoItemList)}"/>筆資料</h3>
	</div>
<script>
    document.addEventListener("DOMContentLoaded", function() {
        showSidebar('order');
        // 顯示錯誤提示框
        const errorMessage = '${errorMessage}';
        const deleteSuccess = "${deleteSuccess}";
        const updateSuccess = "${updateSuccess}";
	    console.log(errorMessage)
	    console.log(updateSuccess)
        // 錯誤foodId
        if (errorMessage) {
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: errorMessage
            });
        }
        //更新成功
        if (updateSuccess) {
        	Swal.fire({
                icon: 'success',
                title: '成功更新!',
                text: '訂單明細已經成功更新。',
                showConfirmButton: false,
                timer: 1500
        	});
        }
        
        
		// 刪除表單
        document.querySelectorAll('.deleteForm').forEach(form => {
            form.addEventListener('submit', function(event) {
                event.preventDefault(); // 阻止表單默認提交

                const formData = new FormData(this);
                const params = new URLSearchParams(new FormData(this)).toString();
               
                fetch(this.action + '?' + params, {
                    method: 'GET'
                })
                .then(response => {
                    if (!response.ok) throw new Error('Network response was not ok');
                    return response.text();
                })
                .then(() => {
                    Swal.fire({
                        icon: 'success',
                        title: '成功刪除!',
                        text: '訂單明細已經成功刪除。',
                        showConfirmButton: false,
                        timer: 1500
                    }).then(() => {
                        window.location.href = '/EEIT187-6/TogoItemController/get?' + params;
                    });
                })
                .catch(error => {
                    console.error('Error:', error);
                    Swal.fire({
                        icon: 'error',
                        title: '提交失敗',
                        text: error.message
                    });
                });
            });
        });
    
    });
</script>
</body>
</html>