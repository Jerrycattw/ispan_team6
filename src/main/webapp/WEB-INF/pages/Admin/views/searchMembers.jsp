<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.List"%>
<%@ page import="com.members.bean.Member"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-TW">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>會員搜尋</title>
<link rel="stylesheet" href="views/css/searchMembers.css">
</head>

<body>

	<header>
		<h1>會員搜尋</h1>
	</header>

	<main>
		        <form class="search-form" id="searchForm" action="EEIT187-6/admin/memberSearch" method="GET">
			<div class="form-group">
				<label for="memberName">會員名稱:</label> <input type="text"
					id="memberName" name="memberName" placeholder="輸入會員名稱">
			</div>
			<div class="form-group">
				<label for="account">帳號:</label> <input type="text" id="account"
					name="account" placeholder="輸入帳號">
			</div>
			<div class="form-group">
				<label for="email">電子郵件:</label> <input type="email" id="email"
					name="email" placeholder="輸入電子郵件">
			</div>
			<div class="form-group">
				<label for="address">地址:</label> <input type="text" id="address"
					name="address" placeholder="輸入地址">
			</div>
			<div class="form-group">
				<label for="phone">手機:</label> <input type="text" id="phone"
					name="phone" placeholder="輸入手機號碼">
			</div>
			<div class="form-group">
				<label for="startDate">註冊開始日期:</label> <input type="date"
					id="startDate" name="startDate">
			</div>
			<div class="form-group">
				<label for="endDate">註冊結束日期:</label> <input type="date" id="endDate"
					name="endDate">
			</div>
			<div class="form-group">
				<label for="status">狀態:</label>
				<div class="checkbox-group">
					<label><input type="checkbox" name="status" value="A">
						啟用</label> <label><input type="checkbox" name="status" value="I">
						停用</label> <label><input type="checkbox" name="status" value="S">
						暫停</label>
				</div>
			</div>
			<button type="submit">搜尋</button>
		</form>
	</main>

</body>

</html>
