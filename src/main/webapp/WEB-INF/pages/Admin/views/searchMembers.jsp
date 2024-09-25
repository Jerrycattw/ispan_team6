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
<style>
body {
    font-family: Arial, sans-serif;
    background-color: #121212; /* 深黑色背景 */
    color: #e0e0e0; /* 淺灰色文字 */
    margin: 0;
}

header {
    background-color: #1f1f1f; /* 深灰色背景 */
    padding: 20px;
    text-align: center;
    border-bottom: 2px solid #333; /* 深灰色底邊框 */
}

h1 {
    margin: 0;
    color: #ffffff; /* 白色文字 */
}

main {
    padding: 20px;
    display: flex;
    flex-direction: column;
    align-items: center;
}

.search-form {
    background-color: #1f1f1f; /* 深灰色背景 */
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(255, 255, 255, 0.2); /* 白色陰影 */
    width: 100%;
    max-width: 600px;
    display: flex;
    flex-direction: column;
    gap: 15px; /* 元素之間的間距 */
}

.form-group {
    display: flex;
    align-items: center;
    gap: 10px; /* 標籤和選項之間的間距 */
    margin-bottom: 15px;
}

label {
    color: #b0b0b0; /* 較淺的灰色文字 */
    margin: 0;
    width: 120px; /* 限制標籤寬度 */
    text-align: right; /* 右對齊 */
}

input[type="text"], input[type="email"], input[type="date"] {
    width: 100%;
    max-width:450px; /* 限制輸入欄位最大寬度 */
    padding: 10px;
    border: 1px solid #333; /* 更深的灰色邊框 */
    border-radius: 4px;
    background-color: #2c2c2c; /* 較深的背景色 */
    color: #e0e0e0; /* 淺灰色文字 */
    box-sizing: border-box; /* 包含邊框和內邊距在總寬度內 */
}

input::placeholder {
    color: #777; /* 較淺的灰色佔位文字 */
}

.checkbox-group {
    display: flex;
    gap: 15px; /* 選項之間的間距 */
}

.checkbox-group label {
    display: flex;
    align-items: center;
    color: #e0e0e0; /* 淺灰色文字 */
}

button {
    width: 100%;
    padding: 15px;
    background-color: #333; /* 深灰色按鈕背景 */
    border: none;
    border-radius: 4px;
    color: #ffffff; /* 白色文字 */
    font-size: 18px;
    cursor: pointer;
    box-sizing: border-box; /* 包含邊框和內邊距在總寬度內 */
}

button:hover {
    background-color: #444; /* 按鈕懸停時變為較淺的灰色 */
}

#results {
    margin-top: 20px;
    width: 100%;
    max-width: 600px;
    background-color: #1f1f1f; /* 深灰色背景 */
    padding: 10px;
    border-radius: 4px;
    color: #e0e0e0; /* 淺灰色文字 */
}
</style>
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
