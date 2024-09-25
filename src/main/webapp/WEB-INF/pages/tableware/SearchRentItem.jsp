<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>租借訂單</title>
<link rel="stylesheet" href="../template/template.css">
<link rel="stylesheet" href="../template/table1.css">
<link rel="stylesheet" href="../template/rent.css">
<script src="../template/template.js"></script>

</head>
<body>
	<jsp:include page="../../../HomePage.jsp"></jsp:include>
	<div class="content" id="content">
		<h2>請輸入搜尋項目</h2>
		<form method="get" class="btn"
			action="/EEIT187-6/RentItem/getAll">
			<input type="submit" value="返回">
		</form>
		<form method="get" action="/EEIT187-6/RentItem/search" onsubmit="return validateForm()">
			<table>
				<tr>
					<td>訂單編號:
					<select id="rentId" name="rentId">
							<option value="" selected disabled>請選擇訂單編號</option>
							<c:forEach items="${rentIds}" var="rentId">
								<option value="${rentId}">${rentId}</option>
							</c:forEach>
					</select>
					</td>
				</tr>
			</table>
			<input type="submit" value="確定" />
		</form>
	</div>
		<script>
		document.addEventListener("DOMContentLoaded", function() {
			showSidebar('rental');
		});
		
		function validateForm() {
            // 获取表单字段的值
            var rentId = document.getElementById("rentId").value;
            

            // 检查是否为空
            if (rentId === "") {
                alert("必須填寫");
                return false; // 阻止表单提交
            }
            return true; // 表单验证通过，允许提交
        }
	</script>
	
</body>
</html>
