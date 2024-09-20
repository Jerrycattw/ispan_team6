<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%! @SuppressWarnings("unchecked") %>
<html>
<head>
<meta charset="UTF-8">
<title>訂位資料</title>
<link rel="stylesheet" href="../template/template.css">
<link rel="stylesheet" href="../template/table.css">
<script src="../template/template.js"></script>
<style>
#searchForm {
    display: flex;
    flex-direction: column;
    gap: 10px;
    margin: 20px;
}

.searchRow {
    display: flex;
    gap: 10px;
    align-items: center;
}

input[type="datetime-local"] {
    width: 330px;
    margin-right: 25px;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
    box-sizing: border-box;
}
select{
    width: 200px;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
    box-sizing: border-box;
}
input[type="text"] {
    width: 200px;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
    box-sizing: border-box;
}

#searchbtn {
    width: 90px;
    background-color: #4CAF50;
    color: white;
    padding: 10px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-size: 16px;
}

label {
    font-weight: bold;
    color: #333;
    margin-bottom: 0;
}
</style>
</head>
<body>
<jsp:include page="../HomePage.jsp"></jsp:include>

	<div class="content" id="content">

	<h2>訂 位 資 料</h2>
    <div>
        <form id="searchForm">
            <div class="searchRow">
                <label>會員編號: <input type="text" name="memberId" id="memberId" /></label>
                <label>會員名稱: <input type="text" name="memberName" id="memberName" /></label>
                <label>會員電話: <input type="text" name="phone" id="phone" /></label>
            </div>
            <div class="searchRow">
                <label>選擇餐廳:
                <select name="restaurantId"  id="restaurantId" >
                	<option value="---" selected>---</option>
					<c:forEach items="${restaurantNames}" var="restaurantName">
						<option value="${restaurantName}">${restaurantName}</option>
					</c:forEach>
				</select>
				</label>
				
                <label>餐廳地址: <input type="text" name="restaurantAddress" id="restaurantAddress" /></label>
                <label>桌位種類:
                <select name="tableTypeId"  id="tableTypeId" >
                	<option value="---" selected>---</option>
					<c:forEach items="${tableTypes}" var="tableType">
						<option value="${tableType.tableTypeId}">${tableType.tableTypeId} (${tableType.tableTypeName} 人桌)</option>
					</c:forEach>
				</select>
				</label>
                
            </div>
            <div class="searchRow">
                <label>開始時間: <input type="datetime-local" name="reserveTime" id="reserveTime" /></label>
                <label>結束時間: <input type="datetime-local" name="finishedTime" id="finishedTime" /></label>
	            <button type="button" id="searchbtn">送出查詢</button>
            </div>
        </form>
    </div>
    <table id="reserveTable">
        <tr>
            <th>訂位編號</th>
            <th>會員姓名</th>
            <th>會員電話</th>
            <th>餐廳名稱</th>
            <th>訂位人數</th>
            <th>桌位種類</th>
            <th>開始用餐時間</th>
            <th>結束用餐時間</th>
            <th>修改訂位資料</th>
            <th>刪除訂位資料</th>
        </tr>
        <!-- 這裡會插入動態生成的表格列 -->
    </table>
    <h3 id="resultCount"></h3>
</div>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<script>
document.addEventListener("DOMContentLoaded", function() {
    showSidebar('reservation');
});


function formatDateTime(dateTimeString) {
    const date = new Date(dateTimeString);
    return date.toLocaleString('zh-TW', { year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit' }).replace(',', '');
}


$('#searchbtn').on('click', function() {
    const memberId = $('#memberId').val();
    const memberName = $('#memberName').val();
    const phone = $('#phone').val();
    const restaurantId = $('#restaurantId').val();
    const restaurantAddress = $('#restaurantAddress').val();
    const tableTypeId = $('#tableTypeId').val();
    const reserveTime = $('#reserveTime').val();
    const finishedTime = $('#finishedTime').val();

    const params = $.param({
        memberId: memberId,
        memberName: memberName,
        phone: phone,
        restaurantId: restaurantId,
        restaurantAddress: restaurantAddress,
        tableTypeId: tableTypeId,
        reserveTime: reserveTime,
        finishedTime: finishedTime
    });

    $.ajax({
        url: '/EEIT187-6/Reserve/list?' + params,
        method: 'GET',
        dataType: 'json',
        success: function(data) {
            console.log(data);
            
         	// 清空表格內容（除了標頭）
            $('#reserveTable tr:not(:first)').remove();

            // 檢查是否有資料
            if (data.length > 0) {
                // 動態生成表格列
                data.forEach(function(reserve) {
                    const row = $('<tr></tr>');
                    row.append('<td>' + reserve.reserveId + '</td>');
                    row.append('<td>' + reserve.memberName + '</td>');
                    row.append('<td>' + reserve.phone + '</td>');
                    row.append('<td>' + reserve.restaurantName + '</td>');
                    row.append('<td>' + reserve.reserveSeat + '</td>');
                    row.append('<td>' + reserve.tableTypeId + '桌 </td>');
                    row.append('<td>' + formatDateTime(reserve.reserveTime) + '</td>');
                    row.append('<td>' + formatDateTime(reserve.finishedTime) + '</td>');
                    row.append('<td><form action=\"/EEIT187-6/Reserve/set\" method=\"get\"><input type=\"hidden\" name=\"reserveId\" value=\"'+reserve.reserveId+'\"><input type=\"submit\" value=\"修改\"></form></td>');
                    row.append('<td><form action=\"/EEIT187-6/Reserve/del\" method=\"get\"><input type=\"hidden\" name=\"reserveId\" value=\"'+reserve.reserveId+'\"><input type=\"submit\" value=\"刪除\"></form></td>');
                    $('#reserveTable').append(row);
                });

                // 更新結果數量
                $('#resultCount').text('找到 ' + data.length + ' 筆訂位資料。');
            } else {
                // 無資料時顯示訊息
                $('#resultCount').text('沒有找到任何訂位資料');
            }
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.error('Error:', textStatus, errorThrown);
        }
    });
    
});

    
</script>
    
</body>
</html>