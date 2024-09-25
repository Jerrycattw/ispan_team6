<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="com.members.bean.Member"%>
 <%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>搜尋會員結果</title>
<style>
body {
    font-family: Arial, sans-serif;
    background-color: #121212; /* 深黑色背景 */
    color: #e0e0e0; /* 淺灰色文字 */
    
}

h2 {
    color: #ffffff; /* 白色文字 */
    border-bottom: 2px solid #333; /* 深灰色底邊框 */
    padding-bottom: 10px;
}

table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 20px;
    background-color: #1f1f1f; /* 深灰色背景 */
    color: #e0e0e0; /* 淺灰色文字 */
}

th, td {
    border: 1px solid #333; /* 更深的灰色邊框 */
    padding: 8px;
    text-align: left;
}

th {
    background-color: #2c2c2c; /* 更深的灰色背景 */
    color: #ffffff; /* 白色文字 */
}

tr:hover {
    background-color: #333; /* 按行懸停時變為深灰色 */
    cursor: pointer; /* 手形光標 */
}

button {
    background-color: #333; /* 按鈕背景色 */
    color: #e0e0e0; /* 按鈕文字顏色 */
    border: none;
    padding: 5px 10px;
    cursor: pointer;
    border-radius: 3px;
}

button:hover {
    background-color: #444; /* 按鈕懸停背景色 */
}
</style>
<link rel="stylesheet" href="/EEIT187-6/template/template.css">
<script src="/EEIT187-6/template/template.js"></script>
</head>
<body>
    <jsp:include page="../../../HomePage.jsp"></jsp:include>

    <!-- 把會員搜尋結果的內容放到這個div content裡面 -->
    <div class="content" id="content">
        <h2>搜尋結果</h2>
        <table border="1">
            <thead>
                <tr>
                    <th>會員ID</th>
                    <th>會員名稱</th>
                    <th>帳號</th>
                    <th>電子郵件</th>
                    <th>狀態</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <%
                List<Member> members = (List<Member>) request.getAttribute("members");
                if (members != null && !members.isEmpty()) {
                    for (Member member : members) {
                        String statusText;
                        switch (member.getStatus()) {
                            case "A" :
                                statusText = "啟用";
                                break;
                            case "S" :
                                statusText = "暫停";
                                break;
                            case "I" :
                                statusText = "停用";
                                break;
                            default :
                                statusText = "未知";
                                break;
                        }
                %>
                <tr>
                    <td onclick="showMemberDetail('<%=member.getMemberId()%>');">
                        <%=member.getMemberId()%></td>
                    <td onclick="showMemberDetail('<%=member.getMemberId()%>');">
                        <%=member.getMemberName()%></td>
                    <td onclick="showMemberDetail('<%=member.getMemberId()%>');">
                        <%=member.getAccount()%></td>
                    <td onclick="showMemberDetail('<%=member.getMemberId()%>');">
                        <%=member.getEmail()%></td>
                    <td>
                        <select onchange="updateMemberStatus('<%=member.getMemberId()%>', this.value)">
                            <option value="A" <%=member.getStatus().equals("A") ? "selected" : ""%>>啟用</option>
                            <option value="S" <%=member.getStatus().equals("S") ? "selected" : ""%>>暫停</option>
                            <option value="I" <%=member.getStatus().equals("I") ? "selected" : ""%>>停用</option>
                        </select>
                    </td>
                    <td><button onclick="editMember('<%=member.getMemberId()%>')">修改</button></td>
                </tr>
                <%
                    }
                } else {
                %>
                <tr>
                    <td colspan="6">沒有找到符合的會員。</td>
                </tr>
                <%
                }
                %>
            </tbody>
        </table>
    </div>

    <!-- 會員詳細資訊對話框 -->
    <div id="memberDetailPopup" style="display: none;">
        <h2>會員詳細資料</h2>
        <p><label>會員ID:</label> <span id="memberDetailId"></span></p>
        <p><label>會員名稱:</label> <span id="memberDetailName"></span></p>
        <p><label>帳號:</label> <span id="memberDetailAccount"></span></p>
        <p><label>電子郵件:</label> <span id="memberDetailEmail"></span></p>
        <p><label>地址:</label> <span id="memberDetailAddress"></span></p>
        <p><label>手機:</label> <span id="memberDetailPhone"></span></p>
        <p><label>狀態:</label> <span id="memberDetailStatus"></span></p>
        <button onclick="closePopup()">關閉</button>
    </div>

    <!-- 修改會員資訊對話框 -->
    <div id="editMemberPopup" style="display: none;">
        <h2>修改會員資料</h2>
        <form id="editMemberForm" action="/EEIT187-6/updateMember" method="POST" onsubmit="submitEditForm(); return false;">
            <input type="hidden" id="editMemberId" name="memberId">
            <p><label>會員名稱:</label> <input type="text" id="editMemberName" name="memberName" required></p>
            <p><label>帳號:</label> <input type="text" id="editAccount" name="account" required></p>
            <p><label>電子郵件:</label> <input type="email" id="editEmail" name="email" required></p>
            <p><label>地址:</label> <textarea id="editAddress" name="address" rows="4"></textarea></p>
            <p><label>手機:</label> <input type="text" id="editPhone" name="phone"></p>
            <p><label>狀態:</label> 
                <select id="editStatus" name="status">
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
        // JavaScript functions for popup management and AJAX requests for member details and status updates
        // 全部已經合併

        // 顯示會員詳細資料的邏輯
        function showMemberDetail(memberId) {
            fetch('/EEIT187-6/memberDetail?memberId=' + memberId)
                .then(response => response.json())
                .then(data => {
                    document.getElementById('memberDetailId').textContent = data.memberId;
                    document.getElementById('memberDetailName').textContent = data.memberName;
                    document.getElementById('memberDetailAccount').textContent = data.account;
                    document.getElementById('memberDetailEmail').textContent = data.email;
                    document.getElementById('memberDetailAddress').textContent = data.address;
                    document.getElementById('memberDetailPhone').textContent = data.phone;
                    document.getElementById('memberDetailStatus').textContent = data.status;
                    document.getElementById('memberDetailPopup').style.display = 'block';
                })
                .catch(error => {
                    console.error('Error fetching member details:', error);
                });
        }

        function closePopup() {
            document.getElementById('memberDetailPopup').style.display = 'none';
        }

        // 修改會員資料
        function editMember(memberId) {
            fetch('/EEIT187-6/memberDetail?memberId=' + memberId)
                .then(response => response.json())
                .then(data => {
                    document.getElementById('editMemberId').value = data.memberId;
                    document.getElementById('editMemberName').value = data.memberName;
                    document.getElementById('editAccount').value = data.account;
                    document.getElementById('editEmail').value = data.email;
                    document.getElementById('editAddress').value = data.address;
                    document.getElementById('editPhone').value = data.phone;
                    document.getElementById('editStatus').value = data.status;
                    document.getElementById('editMemberPopup').style.display = 'block';
                })
                .catch(error => {
                    console.error('Error fetching member details for editing:', error);
                });
        }

        function closeEditPopup() {
            document.getElementById('editMemberPopup').style.display = 'none';
        }

        function submitEditForm() {
            const form = document.getElementById('editMemberForm');
            const data = new FormData(form);
            const urlEncodedData = new URLSearchParams(data).toString();

            fetch('/EEIT187-6/Admin/AdminController/updateMember', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: urlEncodedData
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert('會員資料更新成功');
                    closeEditPopup();
                    window.location.reload();
                } else {
                    alert('會員資料更新失敗');
                }
            })
            .catch(error => {
                console.error('更新會員資料時出錯:', error);
            });
        }
    </script>

</body>
</html>
