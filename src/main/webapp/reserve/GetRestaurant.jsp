<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Restaurant data</title>
    <link rel="stylesheet" href="../template/template.css">
    <script src="../template/template.js"></script>
    <style>
        h2 {
            text-align: center;
            color: #d4a14e;
        }

        .restaurant-info {
            position: relative;
            display: flex;
            background-color: #2c2c2c;
            padding: 20px;
            border-radius: 10px;
            color: #ffffff;
        }

		.restaurant-img {
		    flex: 1;
		    display: flex;
		    align-items: center; /* 垂直置中 */
		    justify-content: center; /* 水平置中 */
		    margin-right: 20px;
		}
		
		.restaurant-img img {
		    width: 100%;
		    border-radius: 10px;
		}

        .restaurant-details {
            flex: 1.5;
            margin-top: 50px;
            padding: 20px;
            position: relative;
        }

        .restaurant-details form {
            display: flex;
            flex-direction: column;
            margin-right: 100px;
        }

        .restaurant-details label {
            font-weight: bold;
            margin-bottom: 5px;
        }

        .restaurant-details input[type="text"] {
            background-color: #444444;
            color: #ffffff;
            border: none;
            padding: 10px;
            margin-bottom: 20px;
            border-radius: 5px;
            width: 100%;
            box-sizing: border-box;
        }

        .restaurant-details input[type="text"]:disabled {
            background-color: #444444;
            color: #d4a14e;
        }

        .restaurant-details input[type="text"]::placeholder {
            color: #d4a14e;
        }

        .view-seats-button {
            position: absolute;
            top: 20px;
            right: 30px;
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 10px 20px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            cursor: pointer;
        }

        .view-seats-button:hover {
            background-color: #45a049;
        }
        
        .view-seats-button2 {
            position: absolute;
            top: 20px;
            right: 180px;
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 10px 20px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            cursor: pointer;
        }

        .view-seats-button2:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <jsp:include page="../HomePage.jsp"></jsp:include>

    <div class="content" id="content">
        <h2>餐 廳 資 料</h2>

        <div class="restaurant-info">
        	
            <a class="view-seats-button2" href="/EEIT187-6/reserve/AddReserveNew.jsp?restaurantId=${restaurant.restaurantId}&restaurantName=${restaurant.restaurantName}">餐廳訂位</a>
            <a class="view-seats-button" href="/EEIT187-6/Table/getAll?restaurantId=${restaurant.restaurantId}&restaurantName=${restaurant.restaurantName}">查看餐廳桌位</a>

            <div class="restaurant-img">
                <img src="${restaurant.restaurantImg}" alt="Restaurant Image"/>
            </div>

            <div class="restaurant-details">
                <form method="post" action="">
                    <label>餐廳名稱 :</label>
                    <input type="text" name="rname" disabled value="${restaurant.restaurantName}"/>

                    <label>餐廳地址 :</label>
                    <input type="text" name="raddress" disabled value="${restaurant.restaurantAddress}"/>

                    <label>餐廳電話 :</label>
                    <input type="text" name="rphone" disabled value="${restaurant.restaurantPhone}"/>

                    <label>營業時間 :</label>
                    <input type="text" name="rtime" disabled value="${restaurant.restaurantOpentime} - ${restaurant.restaurantClosetime}"/>

                    <label>用餐時間限制 :</label>
                    <input type="text" name="reattime" disabled value="${restaurant.eattime}"/>
                </form>
            </div>
        </div>
    </div>

    <script>
        document.addEventListener("DOMContentLoaded", function() {
            showSidebar('reservation');
        });
    </script>
</body>
</html>