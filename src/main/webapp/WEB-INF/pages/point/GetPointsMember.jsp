<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.*,com.point.dto.PointMemberDTO,com.point.bean.PointBean,com.util.DateUtils"%>  
<%! @SuppressWarnings("unchecked") %>      
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="../template/template.css">
	<script src="../template/template.js"></script>
    <style>
    	table {
    width: 100%;
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
        article{
            display: flex;
        }

		.container{
		width:800px;}

        .first{
            text-align: left;
            border-bottom: 2px solid gray;
            padding:7px;      
        }
        .second{
            text-align: left;
            border-bottom: 2px solid gray;
            padding:5px;      
        }
        
        .do{
        	display:flex;
        }
        form{
        	margin:2px;	
        }
        
    </style>
<title>員工資料</title>
</head>
<body >
<jsp:include page="../../../HomePage.jsp"></jsp:include>
<div class="content" id="content">
<% PointMemberDTO pointMember=(PointMemberDTO)request.getAttribute("pointMember");%>
	<div class="container" >
	        <article class="first">
	            <div  style="width: 700px;" >
	            <h1>會員點數</h1>
	            <h4>會員編號:<%= pointMember.getMemberId() %></h4>
	            <h4>會員姓名:<%= pointMember.getMemberName() %></h4>
	            <p>
	            <p>
	            </div>
	            <div  style="width: 200px;">
	                <form action="/EEIT187-6/Point/PreInsertPoint" method="get" style="margin: 0;">
	                    <input type="hidden" name="memberID" value="<%= pointMember.getMemberId() %>">
	                    <button type="submit">贈送點數</button>
	                </form>
	            </div>
	        </article>
	        <article class="second">
	            <div  >
	                <h4>現有點數 : <%= pointMember.getTotalPointBalance() %></h4>
	                <h4><%= pointMember.getExpiringPoints() %> 點將於 <%= pointMember.getExpiryDate() %>	到期</h4>
	                <p>
	            	<p>
	            </div>
	        </article>
	        <article class="second">
	            <div style="text-align: left;">
	                <h4>活動紀錄</h4>
	                <table border="1">
	                <tr style="background-color:#a8fefa">
	                <th>點數紀錄<th>會員<th>變動點數<th>創建日期<th>到期日<th>剩餘點數<th>交易來源<th>交易類型<th>動作
	                <% List<PointBean> pointsByID=(ArrayList<PointBean>)request.getAttribute("pointsByID");
						for(PointBean pointByID:pointsByID) {%>
						<tr><td><%= pointByID.getPointID() %>
						<td><%= pointByID.getMemberID() %>
						<td><%= pointByID.getPointChange() %>
						<td><%= DateUtils.getStringFromDate(pointByID.getCreateDate())  %>
						<td><%= DateUtils.getStringFromDate(pointByID.getExpiryDate())  %> 
						<td><%= pointByID.getPointUsage() %>
						<td><%= pointByID.getTransactionID() %>
						<td><%= pointByID.getTransactionType() %>
						<td class="do"><form action="/EEIT187-6/Point/GetPoint" method="get" style="margin: 0;">
								<input type="hidden" name="pointID" value="<%= pointByID.getPointID() %>">
								<button type="submit">編輯</button>
							</form>&nbsp;
							<form action="/EEIT187-6/Point/DeletePoint" method="get" style="margin: 0;">
								<input type="hidden" name="pointID" value="<%= pointByID.getPointID() %>">
								<button type="submit">刪除</button>
							</form>
						<%}%>
					</table>
					<br><br>
	            </div>
	        </article><br><br>
	        
	    <button onclick="window.location.href='/EEIT187-6/Point/GetAllPoints'">返回查詢點數</button>
	    </div>
	    </div>
	    <script>
	    document.addEventListener("DOMContentLoaded", function() {
        showSidebar('points');
        
    });
	    </script>
</body>
</html>
