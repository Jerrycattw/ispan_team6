<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.*,com.point.dto.PointMemberDTO"%>  
<%! @SuppressWarnings("unchecked") %>      
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>員工資料</title>
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
<link rel="stylesheet" href="../template/template.css">
<script src="../template/template.js"></script>
<style>
table {
    width: 50%;
    border-collapse: collapse;
    margin-top: 20px; /* 讓出空間給上方按鈕 */
}
th, td {
    border: 1px solid #ddd;
    padding: 8px;
    text-align: center; /* 文字置中 */
}
th {
    background-color: #f2f2f2;
}

    .top{display:flex; justify-content: space-between;}
    .in{display:flex; justify-content: flex-end;}
    .but{vertical-align: middle}
    .search{height:16px;}
    .notice{margin:0;font-size:50%;}   
</style>
</head>
<body >
<jsp:include page="../HomePage.html"></jsp:include>
<div class="content" id="content">
<div  class="container">
<div class="top">
<input type="hidden" name="memberID" >
<h1>查詢點數</h1>
</div>
<div>
	<form method="get" action="/EEIT187-6/Point/SearchPoint">
	<input type="text" class="search" name="search" id="search" placeholder="搜尋會員編號、姓名及手機等資訊" value="${keyWord}"><span id="clear"><i class="fa-solid fa-xmark"></i></span>&nbsp;&nbsp;&nbsp;&nbsp;<button type="submit" id="doSearch"><i class="fa-solid fa-magnifying-glass"></i></button>
	<p class="notice"></p>
	</form>
</div>
<br>
<div id="divIn"class="in"><button class="but" id="batchAdd" type="submit">批次新增</button></div>

<table border="1" id="table">
<tr style="background-color:#a8fefa">
<th><input type="checkbox" id="checkAll"></th><th>會員編號<th>姓名<th>電話<th>總點數<th>即將到期<th>到期日<th>動作
<% List<PointMemberDTO> pointMembers=(ArrayList<PointMemberDTO>)request.getAttribute("pointsSearch");
	for(PointMemberDTO pointMember:pointMembers) {%>
	<tr><td><input type="checkbox" name="selectedMembers" value="<%= pointMember.getMemberId() %>">
	<td><%= pointMember.getMemberId() %>
	<td><%= pointMember.getMemberName() %>
	<td><%= pointMember.getPhone() %>
	<td><%= pointMember.getTotalPointBalance() %>
	<td><%= pointMember.getExpiringPoints() %>
	<td><%= pointMember.getExpiryDate() %>
	<td><form action="/EEIT187-6/Point/GetPointsMember" method="post" style="margin: 0;">
				<input type="hidden" name="memberID" value="<%= pointMember.getMemberId() %>">
				<button type="submit">檢視</button>
			</form>
	<%}%>
</table>

<h3>共 <%=pointMembers.size() %> 筆會員點數資料</h3>
</div>
</div>
<script>
	document.addEventListener('DOMContentLoaded', (event) => {
	    let checkAll = document.getElementById("checkAll");
	    checkAll.addEventListener('change', function() {
	        let checkboxes = document.getElementsByName("selectedMembers");
	        for (let index = 0; index < checkboxes.length; index++) {
	            checkboxes[index].checked = checkAll.checked;
	        }
	    });
	});
	
	document.getElementById("clear").addEventListener('click',function(){
		window.location.href="GetAllPoints"
	})
	
	document.getElementById("doSearch").addEventListener('click',function(event){
		if(document.getElementById("search").value.trim()===""){
			event.preventDefault();
			alert("搜尋欄位不能空白");
		}
	})
	
	let w=document.getElementById("table").offsetWidth
	document.getElementById("divIn").style.width = w + "px";
	document.getElementById("search").style.width = w*0.8+"px" ;

</script>
<script>
    document.getElementById("batchAdd").addEventListener('click', function() {
        let selectedMembers = [];
        let checkboxes = document.getElementsByName("selectedMembers");

        // 收集所有打勾的複選框
        for (let index = 0; index < checkboxes.length; index++) {
            if (checkboxes[index].checked) {
                selectedMembers.push(checkboxes[index].value);
            }
        }

        if (selectedMembers.length === 0) {
            alert("請選擇至少一個會員");
            return;
        }

        // 使用 fetch API 發送 AJAX 請求
        fetch('/EEIT187-6/Point/BatchInsertPoint', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ memberIDs: selectedMembers })
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                // 你可以在這裡執行刷新頁面或其他操作
                window.location.href='../point/InsertBatchPoint.jsp';
            } else {
                alert("新增點數失敗");
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert("請求失敗");
        });
    });
    document.addEventListener("DOMContentLoaded", function() {
         showSidebar('points');
    });
</script>
</body>
</html>