<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%! @SuppressWarnings("unchecked") %>
<html>
<head>
<meta charset="UTF-8">
<title>餐廳資料</title>
<link rel="stylesheet" href="../template/template.css">
<link rel="stylesheet" href="../template/table.css">
<script src="../template/template.js"></script>
<style>
#searchForm {
    display: flex;
    align-items: center;
    gap: 10px;
    margin: 20px;
}

select, input[type="text"] {
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

	<h2>餐 廳 資 料</h2>
    <div>
        <form id="searchForm">
            <label>餐廳名稱: <input type="text" name="restaurantName" id="restaurantName" /></label>
            <label>餐廳地址: <input type="text" name="restaurantAddress" id="restaurantAddress" /></label>
            <label>餐廳狀態:
                <select name="restaurantStatus" id="restaurantStatus">
                    <option value="">---</option>
                    <option value="1">營業中</option>
                    <option value="2">已歇業</option>
                    <option value="3">籌備中</option>
                </select>
            </label>
            <button type="button" id="searchbtn">送出查詢</button>
        </form>
    </div>
    <table id="restaurantTable">
        <tr>
            <th>餐廳名稱</th>
            <th>餐廳地址</th>
            <th>餐廳電話</th>
            <th>營業時間</th>
            <th>餐廳狀態</th>
            <th>修改餐廳資料</th>
            <th>刪除餐廳資料</th>
        </tr>
        <!-- 這裡會插入動態生成的表格列 -->
    </table>
    <h3 id="resultCount"></h3>
</div>

<script>
document.addEventListener("DOMContentLoaded", function() {
    showSidebar('reservation');
});


document.getElementById('searchbtn').addEventListener('click', function() {
    const restaurantName = document.getElementById('restaurantName').value || "";
    const restaurantAddress = document.getElementById('restaurantAddress').value || "";
    const restaurantStatus = document.getElementById('restaurantStatus').value || "";

    const params = new URLSearchParams({
        restaurantName: restaurantName,
        restaurantAddress: restaurantAddress,
        restaurantStatus: restaurantStatus
    });

    fetch('/EEIT187-6/Restaurant/list?' + params.toString())
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log(data); 
            
            // 在這裡將資料插入到表格中
            const table = document.getElementById('restaurantTable');
            table.querySelectorAll('tr:not(:first-child)').forEach(row => row.remove());

            let rowCount = 0;
            data.forEach(rest => {
                const row = table.insertRow();
                row.insertCell(0).innerHTML = '<a href=\"/EEIT187-6/Restaurant/get?restaurantId='+rest.restaurantId+"\">"+rest.restaurantName+"</a>";
                row.insertCell(1).textContent = rest.restaurantAddress;
                row.insertCell(2).textContent = rest.restaurantPhone;
                row.insertCell(3).textContent = rest.restaurantOpentime +" ~ " + rest.restaurantClosetime;
                
                let statusText = '';
                switch (rest.restaurantStatus) {
                    case 1:
                        statusText = '營業中';
                        break;
                    case 2:
                        statusText = '已歇業';
                        break;
                    case 3:
                        statusText = '籌備中';
                        break;
                    default:
                        statusText = '未知狀態';
                }
                row.insertCell(4).textContent = statusText;

                row.insertCell(5).innerHTML = '<form action=\"/EEIT187-6/Restaurant/set\" method=\"get\"><input type=\"hidden\" name=\"restaurantId\" value=\"'+rest.restaurantId+'\"><input type=\"submit\" value=\"修改\"></form>';
                row.insertCell(6).innerHTML = '<form action=\"/EEIT187-6/Restaurant/del\" method=\"get\"><input type=\"hidden\" name=\"restaurantId\" value=\"'+rest.restaurantId+'\"><input type=\"submit\" value=\"刪除\"></form>';

                rowCount++;
            });

            document.getElementById('resultCount').textContent = "共"+rowCount+"筆餐廳資料";
        })

        .catch(error => console.error('Error:', error));
});

    
</script>
    
</body>
</html>