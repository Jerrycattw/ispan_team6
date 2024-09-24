<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.List"%>
<%@ page import="com.admin.bean.Admin"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-TW">
<head>
<meta charset="UTF-8">
<title>管理員搜尋結果</title>
<link rel="stylesheet" href="../views/css/findAllAdmins.css">
</head>
<body>
	<h2>搜尋結果</h2>
	<table border="1">
		<thead>
			<tr>
				<th>管理員ID</th>
				<th>管理員名稱</th>
				<th>帳號</th>
				<th>狀態</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<%
			List<Admin> admins = (List<Admin>) request.getAttribute("admins");
			if (admins != null && !admins.isEmpty()) {
				for (Admin admin : admins) {
					String statusText;
					switch (admin.getStatus()) {
				case 'A' :
					statusText = "啟用";
					break;
				case 'S' :
					statusText = "暫停";
					break;
				case 'I' :
					statusText = "停用";
					break;
				default :
					statusText = "未知";
					break;
					}
			%>
			<tr>
				<td onclick="showAdminDetail('<%=admin.getAdminId()%>')"><%=admin.getAdminId()%></td>
				<td onclick="showAdminDetail('<%=admin.getAdminId()%>')"><%=admin.getAdminName()%></td>
				<td onclick="showAdminDetail('<%=admin.getAdminId()%>')"><%=admin.getAccount()%></td>
				<td><select
					onchange="updateAdminStatus('<%=admin.getAdminId()%>', this.value)">
						<option value="A"
							<%="A".equals(String.valueOf(admin.getStatus())) ? "selected" : ""%>>啟用</option>
						<option value="S"
							<%="S".equals(String.valueOf(admin.getStatus())) ? "selected" : ""%>>暫停</option>
						<option value="I"
							<%="I".equals(String.valueOf(admin.getStatus())) ? "selected" : ""%>>停用</option>
				</select></td>
				<td>
					<button onclick="editAdmin('<%=admin.getAdminId()%>')">修改</button>
				</td>
			</tr>
			<%
			}
			} else {
			%>
			<tr>
				<td colspan="5">沒有找到符合的管理員。</td>
			</tr>
			<%
			}
			%>
		</tbody>
	</table>

	<div id="editAdminPopup"
		style="display: none; position: fixed; top: 50%; left: 50%; transform: translate(-50%, -50%); background-color: #1f1f1f; color: #e0e0e0; padding: 20px; border: 1px solid #333; z-index: 1000;">
		<h2>修改管理員資料</h2>
		<form id="editAdminForm"
			action="/EEIT187-6/Admin/AdminController/updateAdmin" method="POST"
			onsubmit="submitEditForm(); return false;">
			<input type="hidden" id="editAdminId" name="adminId">
			<p>
				<label>管理員名稱:</label> <input type="text" id="editAdminName"
					name="adminName" required>
			</p>
			<p>
				<label>帳號:</label> <input type="text" id="editAccount"
					name="account" required>
			</p>
			<p>
				<label>密碼:</label> <input type="password" id="editPassword"
					name="password" required>
			</p>
			<p>
				<label>狀態:</label> <select id="editStatus" name="status">
					<option value="A">啟用</option>
					<option value="S">暫停</option>
					<option value="I">停用</option>
				</select>
			</p>
			<button type="submit">保存更改</button>
			<button type="button" onclick="closeEditPopup()">取消</button>
		</form>
		<c:if test="${not empty message}">
			<p>${message}</p>
		</c:if>
	</div>

	<script>

        function closePopup() {
            document.getElementById('adminDetailPopup').style.display = 'none';
        }
        
        function updateAdminStatus(adminId, newStatus) {
            const url = '/EEIT187-6/Admin/AdminController/updateAdminStatus';
            const data = {
                adminId: adminId,
                status: newStatus
            };

            fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            })
            .then(response => response.json())
            .then(data => {
                if (data.status === 'success') {
                    alert('管理員狀態更新成功');
                    window.location.reload();
                } else {
                    alert('管理員狀態更新失敗');
                }
            })
            .catch(error => {
                console.error('Error updating admin status:', error);
            });
        }

        function closeEditPopup() {
            document.getElementById('editAdminPopup').style.display = 'none';
        }
        
        
        function editAdmin(adminId) {
            window.currentAdminId = adminId;

            fetch('/EEIT187-6/Admin/AdminController/adminDetail?adminId=' + adminId)
                .then(response => response.json())
                .then(data => {
                    document.getElementById('editAdminId').value = data.adminId;
                    document.getElementById('editAdminName').value = data.adminName;
                    document.getElementById('editAccount').value = data.account;
                    document.getElementById('editPassword').value = data.password;  // 假设你要编辑密码
                    document.getElementById('editStatus').value = data.status;
                    document.getElementById('editAdminPopup').style.display = 'block';
                })
                .catch(error => {
                    console.error('Error fetching admin details:', error);
                });
        }

       
        function submitEditForm() {
            const form = document.getElementById('editAdminForm');
            const data = new FormData(form);
            const urlEncodedData = new URLSearchParams(data).toString();

            fetch('/EEIT187-6/Admin/AdminController/updateAdmin', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: urlEncodedData // 使用 URL 編碼的數據
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) { // 根據後端返回的狀態處理回應
                    alert('管理員資料更新成功');
                    closeEditPopup(); // 關閉修改彈出框
                    window.location.reload(); // 重新加載頁面
                } else {
                    alert('管理員資料更新失敗');
                }
            })
            .catch(error => {
                console.error('更新管理員資料時出錯:', error);
            });
        }
    </script>
</body>
</html>
