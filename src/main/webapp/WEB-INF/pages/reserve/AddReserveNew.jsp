<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Reservation</title>
<link rel="stylesheet" href="../template/template.css">
<link rel="stylesheet" href="../template/add.css">
  <link rel="stylesheet" href="https://code.jquery.com/ui/1.14.0/themes/base/jquery-ui.css">

<script src="../template/template.js"></script>
<style>
#reserveTable {
    width: 100%;
    display: flex;
    flex-wrap: wrap; /* 允許按鈕在多行中換行 */
    justify-content: flex-start; /* 初始對齊方式為左對齊 */
    align-content: flex-start; /* 讓內容在多行中靠上對齊 */
}

#reserveTable .reserve-item {
    flex: 1 1 120; /* 調整每個按鈕的寬度，並讓它們平分空間 */
    margin: 5px;
}

#reserveTable input[type="submit"] {
    width: 100%;
    padding: 10px;
    background-color: #4CAF50;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-size: 16px;
    text-align: center;
    box-sizing: border-box;
}

#reserveTable input[type="submit"]:hover {
    background-color: #45a049;
}

#reserveTable input[type="submit"]:disabled {
    background-color: #ccc;
    cursor: not-allowed;
}
</style>
</head>
<body>
<jsp:include page="../../../HomePage.jsp"></jsp:include>

<div class="content" id="content">
	<h2>新增訂位資料</h2>
	
	<form method="post" action="/EEIT187-6/Reserve/addNew">
		會員編號 : <input type="text" name="memberId" /><p>
		會員名稱 : <input type="text" name="memberName" /><p>
		會員電話 : <input type="text" name="memberPhone" /><p>
	
		選擇訂位餐廳 :
		<select name="restaurantName" id="restaurantName">
			<c:if test="${not empty param.restaurantName}">
                <!-- 顯示 URL 中的餐廳名稱 -->
                <option value="${param.restaurantName}" selected>${param.restaurantName}</option>
            </c:if>
			<c:forEach items="${restaurantNames}" var="restaurantName">
				<option value="${restaurantName}">${restaurantName}</option>
			</c:forEach>
		</select><p>
	
		選擇用餐人數 : 
		<select name="reserveSeat" id="reserveSeat">
		    <c:forEach var="i" begin="2" end="10">
		        <option value="${i}">${i}</option>
		    </c:forEach>
		</select><p>
	
		選擇您想預訂日期 : <input type="date" name="checkDate" id="checkDate"/><p>

		選擇您想預訂時間 : <p>
	    <div id="reserveTable">
	        <!-- 這裡會插入動態生成的按鈕 -->
	    </div>
	</form>
</div>
	
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
  <script src="https://code.jquery.com/ui/1.14.0/jquery-ui.js"></script>
<script>
    document.addEventListener("DOMContentLoaded", function() {
        showSidebar('reservation');
    });
    
    $(function () {
        $('#restaurantName').on('blur', getReserveTime);
        $('#reserveSeat').on('blur', getReserveTime);
        $('#checkDate').on('blur', getReserveTime);
        
        function getReserveTime() {
            const restaurantName = $('#restaurantName').val();
            const reserveSeat = $('#reserveSeat').val();
            const checkDate = $('#checkDate').val();

            const params = $.param({
                restaurantName: restaurantName,
                reserveSeat: reserveSeat,
                checkDate: checkDate
            });

            $.ajax({
                url: '/EEIT187-6/Reserve/checkTable?' + params,
                method: 'GET',
                dataType: 'json',
                success: function(data) {
                    console.log(data);

                    // 清空表格內容
                    $('#reserveTable').empty();

                    // 檢查是否有資料
                    if (data.length > 0) {
                        // 動態生成按鈕
                        data.forEach(function(checkReserve, index) {
                            const timeFormatted = checkReserve.startTime.split(':').slice(0, 2).join(':');
                            const isAvailable = checkReserve.totalTableNumber > checkReserve.reservedTableNumber;
                            const button = $('<input type="submit">')
                                .attr('name', 'reserveTime')
                                .attr('value', timeFormatted) // 使用格式化後的時間
                                .prop('disabled', !isAvailable); // 設置是否禁用按鈕

                            const item = $('<div class="reserve-item"></div>').append(button);

                            $('#reserveTable').append(item); // 添加按鈕到 reserveTable
                        });
                    } else {
                        $('#resultCount').text('沒有找到任何訂位資料');
                    }
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    console.error('Error:', textStatus, errorThrown);
                }
            });
        }
        
        
        function setDateConstraints() {
            const today = new Date();
            const tomorrow = new Date();
            tomorrow.setDate(today.getDate() + 1);
            const nextMonth = new Date();
            nextMonth.setMonth(today.getMonth() + 1);

            const minDate = tomorrow.toISOString().split('T')[0]; // 格式化為 yyyy-mm-dd
            const maxDate = nextMonth.toISOString().split('T')[0]; // 格式化為 yyyy-mm-dd

            document.getElementById('checkDate').setAttribute('min', minDate);
            document.getElementById('checkDate').setAttribute('max', maxDate);
        }
        
        setDateConstraints();
        
    });
</script>
</body>
</html>