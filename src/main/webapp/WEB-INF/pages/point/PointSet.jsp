<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.*,com.point.bean.PointSetBean"%> 
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>點數設定</title>
    <link rel="stylesheet" href="../template/template.css">
	
    <style>

        .container article{
            border-bottom: 2px solid gray;
            padding:5px 5px 20px 5px;
        }

        .container{
            width: 800px;
        }
        .container select{
            width: 50px;
        }
        .container header{
            color: rgb(27, 114, 114);
            padding:0 0 5px;
        }
        .container a{
            border:2px solid rgb(27, 114, 114);
            padding: 2px;     
        }
        .container nav{
            padding-bottom: 5px;
        }
        .right{text-align:right;width:100px;}
    </style>
    
    <script src="../template/template.js"></script>
</head>
<body>
<jsp:include page="../../../HomePage.jsp"></jsp:include>
<div class="content" id="content">
<form action="/EEIT187-6/Point/UpdatePointSet" method="post">
<% PointSetBean pointSet=(PointSetBean)request.getAttribute("pointSet");%>
    <div class="container">
        <h1>點數設定</h1>
        <div>
            <article>
                <header>消費點數</header>
                <span>顧客在每次購物後，該筆訂單的狀態轉為「已完成」時獲得的點數</span>

            </article>
            <article>
                <header>集點換算</header>
                每NT$<input type="text" name="amountPerPoint" class="right number" value="<%=pointSet.getAmountPerPoint() %>">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                累積<input type="text" name="pointsEarned" class="right number" value="<%=pointSet.getPointsEarned() %>">點<span class="remindPoint"></span><br> 
                1點紅利點數可折抵NT$ 1
            </article>
            <article>
                <header>會員生日點數</header>
                於會員生日<select name="birthType">
                    <option <%= pointSet.getBirthType().equals("當月") ? "selected" : "" %>>當月</option>
                    <option <%= pointSet.getBirthType().equals("當日") ? "selected" : "" %>>當日</option>
                </select>，紅利點數累積<input type="text" name="pointRatio" class="right number" value="<%=pointSet.getPointRatio() %>">倍。<span class="remindRatio"></span>
            </article>
            <article>
                <header>點數到期日</header>
                <% String radioOption = pointSet.getIsExpiry(); %>
                <% System.out.println("");%>
                <label ><input type="radio" name="isExpiry" value="isExpiry" <%= "isExpiry".equals(radioOption) ? "checked" : "" %>>指定到期日</label>
                <label ><input type="radio" name="isExpiry" value="noExpiry" <%= "noExpiry".equals(radioOption) ? "checked" : "" %>>永不到期</label>
                <br><br>
                在一年內發送的點數會統一在明年的指定日到期。例如：若設定到期日為6/30，則當年度1/1至12/31發送的點數，會統一在明年的6/30到期。<br>
                <select id="month" name="expiryMonth" value="<%=pointSet.getExpiryMonth() %>">
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                    <option value="6">6</option>
                    <option value="7">7</option>
                    <option value="8">8</option>
                    <option value="9">9</option>
                    <option value="10">10</option>
                    <option value="11">11</option>
                    <option value="12">12</option>
                </select>月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <select id="day" name="expiryDay" value="<%=pointSet.getExpiryDay() %>"></select>日
            </article>
            <article>
                <header>點數規則說明</header>
                寫下你的點數使用說明、集點說明集條款，將於會員中心展示。<br>
                <textarea name="setDescription" id="comment" rows="10" cols="80" ><%=pointSet.getSetDescription() %></textarea>
            </article>
            <article>
                <input type="submit"  value="修改" id="submit">
            </article>   
        </div>
    </div>
    </div>
</form>
    <script>
        let monthIndex=<%= pointSet.getExpiryMonth() %>;
        let dayIndex=<%= pointSet.getExpiryDay() %>
        let dayTotal;
        
        //點擊month選單
        const selectMonth=document.getElementById("month")
        selectMonth.addEventListener('change',function(event){
            monthIndex=parseInt(event.target.selectedOptions[0].value);
            selectMonth.value=monthIndex;
            updateDayOption();
        })
        //點擊day選單
        const selectDay=document.getElementById("day")
        selectDay.addEventListener('change',function(event){
            dayIndex=parseInt(event.target.selectedOptions[0].value);
	
        })

        function updateDayOption(){
            switch(monthIndex){
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    dayTotal=31;
                    break
                case 4: 
                case 6:
                case 9:
                case 11:
                    dayTotal=30;
                    break;
                case 2:
                    dayTotal=28;    
            }
            
            selectDay.innerHTML="";
            for(let i =1;i<=dayTotal;i++){
                let dayOption=document.createElement('option');
                dayOption.textContent=i;
                selectDay.appendChild(dayOption);
            }

            if(dayIndex>dayTotal){
                dayIndex=dayTotal
            }
            selectDay.value = dayIndex; 
        }
		
        //預設
        selectMonth.value=monthIndex;
        updateDayOption();
        
    	document.getElementById('submit').addEventListener('click',function(){
            let numbers=document.querySelectorAll('.number')
	        let check=true;
            for (let i = 0; i < numbers.length; i++) {
                if (!/^\d+$/.test(numbers[i].value)) {
                    check = false;
                    break; // 中途退出循环
                }
            }
            if(check)alert("修改成功!")
            else{
            	alert("格式有誤，請重新輸入!");
            	event.preventDefault();
            }
        })
document.addEventListener("DOMContentLoaded", function() {
     showSidebar('points');
});
    </script>    
</body>
</html>
