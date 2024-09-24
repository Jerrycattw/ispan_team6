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
		<h2>新增環保餐具資料</h2>
		<form method="get" class="btn"
			action="/EEIT187-6/Tableware/getAll">
			<input type="submit" value="返回">
		</form>
		<div class="input">
			<form method="post" action="/EEIT187-6/Tableware/insert"
				enctype="multipart/form-data" id="tablewareForm"
				onsubmit="return validateForm()">
				<table>
					<tr>
						<td>用具名稱: <input type="text" name="tableware_name"
							id="tableware_name" />
					<tr>
						<td>押金金額: <input type="text" name="tableware_deposit"
							id="tableware_deposit" />
					<tr>
						<td>用具圖片: <input type="file" name="tableware_image"
							id="tableware_image" />
					<tr>
						<td>用具描述: <input type="text" name="tableware_description"
							id="tableware_description" />
				</table>
				<div id="stock-container">
					<h2>輸入餐具庫存資料</h2>
					<div class="stock-entry">
						<table>
							<tr>
								<td>餐廳名稱: 
								<select name="restaurantName0" id="restaurantName0">
										<option value="" selected disabled>請選擇餐廳名稱</option>
										<c:if test="${not empty param.restaurantName}">
											<!-- 顯示 URL 中的餐廳名稱 -->
											<option value="${param.restaurantName}" selected>${param.restaurantName}</option>
										</c:if>
										<c:forEach items="${restaurantNames}" var="restaurantName">
											<option value="${restaurantName}">${restaurantName}</option>
										</c:forEach>
								</select>
								</td>
								<td>用具庫存: <input type="text" name="stock0" /></td>
								<td><button type="button" onclick="removeStockEntry(this)">刪除</button></td>
							</tr>
						</table>
					</div>
				</div>

				<!-- 添加新餐廳庫存的按钮 -->
				<button type="button" onclick="addStockEntry()">添加庫存</button>
				<input type="submit" value="確定" />
			</form>
		</div>
	</div>
	<script>
		document.addEventListener("DOMContentLoaded", function() {
			showSidebar('rental');
		});
	</script>
	<script>
function validateForm() {
    // 获取表单字段的值
    var name = document.getElementById("tableware_name").value;
    var deposit = document.getElementById("tableware_deposit").value;
    var image = document.getElementById("tableware_image").value;
    var description = document.getElementById("tableware_description").value;

    // 检查是否有空值
    if (name === "" || deposit === "" || image === "" || description === "") {
        alert("所有字段都必须填写。");
        return false;
    }

    // 检查押金金额是否为数字
    if (isNaN(deposit)) {
        alert("押金金額必须为数字。");
        return false;
    }

    // 如果所有验证通过，返回true允许表单提交
    return true;
}
</script>
	<script>
let stockIndex = 1; // 庫存條目的索引

function addStockEntry() {
    const container = document.getElementById('stock-container');
    const newEntry = document.createElement('div');
    newEntry.classList.add('stock-entry');
    newEntry.innerHTML = `
        <table>
            <tr>
                <td>餐廳名稱: 
                	<select name="restaurantName${stockIndex}" id="restaurantName${stockIndex}">
							<option value="" selected disabled>請選擇餐廳名稱</option>
							<c:if test="${not empty param.restaurantName}">
								<!-- 顯示 URL 中的餐廳名稱 -->
								<option value="${param.restaurantName}" selected>${param.restaurantName}</option>
							</c:if>
							<c:forEach items="${restaurantNames}" var="restaurantName">
								<option value="${restaurantName}">${restaurantName}</option>
							</c:forEach>
					</select>
                </td>
                <td>用具庫存: <input type="text" name="stock${stockIndex}" /></td>
                <td><button type="button" onclick="removeStockEntry(this)">刪除</button></td>
            </tr>
        </table>
    `;
    container.appendChild(newEntry);
    stockIndex++;
}

function removeStockEntry(button) {
    const entry = button.closest('.stock-entry');
    if (entry) {
        entry.remove();
    }
}
</script>
</body>
</html></html>