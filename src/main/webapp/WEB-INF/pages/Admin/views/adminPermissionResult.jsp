<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.List"%>
<%@ page import="com.admin.bean.Admin"%>
<%@ page import="com.admin.bean.Permission"%>
<%@ page import="com.admin.bean.AdminPermission"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-TW">
<head>
    <meta charset="UTF-8">
    <title>管理員權限管理</title>
    <link rel="stylesheet" href="../views/css/adminPermission.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
</head>
<body>
    <h2>管理員權限管理</h2>
    <table>
        <thead>
            <tr>
                <th>管理員ID</th>
                <th>管理員名稱</th>
                <th>帳號</th>
                <th>當前權限</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="admin" items="${admins}">
                <tr>
                    <td>${admin.adminId}</td>
                    <td>${admin.adminName}</td>
                    <td>${admin.account}</td>
                    <td style="font-size: 15px;">
                        <c:forEach var="permission" items="${allPermissions}">
                            <c:set var="hasPermission" value="false" />
                            <c:forEach var="adminPermission" items="${adminPermissionsMap[admin.adminId]}">
                                <c:if test="${adminPermission.permissionId == permission.permissionId}">
                                    <c:set var="hasPermission" value="true" />
                                </c:if>
                            </c:forEach>
                            <c:if test="${hasPermission}">
                                <span class="permission-tag">${permissionNameMap[permission.permissionName]}</span>
                            </c:if>
                        </c:forEach>
                    </td>
                    <td><button onclick="openPopup('${admin.adminId}')">修改權限</button></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <c:forEach var="admin" items="${admins}">
        <div id="popup-${admin.adminId}" class="popup">
            <div class="popup-content">
                <span class="close" onclick="closePopup('${admin.adminId}')">&times;</span>
                <h3>修改 ${admin.adminName} 的權限</h3>
                <form id="permissionForm-${admin.adminId}">
                    <c:forEach var="permission" items="${allPermissions}">
                        <c:set var="isChecked" value="false" />
                        <c:forEach var="adminPermission" items="${adminPermissionsMap[admin.adminId]}">
                            <c:if test="${adminPermission.permissionId == permission.permissionId}">
                                <c:set var="isChecked" value="true" />
                            </c:if>
                        </c:forEach>
                        <label>
                            <input type="checkbox" name="permissions" value="${permission.permissionId}"
                                ${isChecked ? 'checked' : ''}
                                onchange="updatePermission('${admin.adminId}', '${permission.permissionId}', this.checked)">
                            ${permissionNameMap[permission.permissionName]}
                        </label><br>
                    </c:forEach>
                </form>
            </div>
        </div>
    </c:forEach>

        <script>
    function openPopup(adminId) {
        document.getElementById('popup-' + adminId).style.display = 'flex';
    }

    function closePopup(adminId) {
        document.getElementById('popup-' + adminId).style.display = 'none';
    }

    function updatePermission(adminId, permissionId, isChecked) {
        var url = isChecked ? '/EEIT187-6/Admin/AdminController/assignPermission' 
                            : '/EEIT187-6/Admin/AdminController/removePermission';
        
        $.ajax({
            url: url,
            method: 'POST',
            data: {
                adminId: adminId,
                permissionId: permissionId
            },
            success: function(response) {
                if (response.success) {
                    alert(response.message);
                    updatePermissionUI(adminId);
                } else {
                    alert('操作失敗: ' + response.message);
                    // 如果操作失敗，將checkbox恢復到原來的狀態
                    document.querySelector(`#permissionForm-${adminId} input[value="${permissionId}"]`).checked = !isChecked;
                }
            },
            error: function() {
                alert('發生錯誤，請稍後再試');
                // 如果發生錯誤，將checkbox恢復到原來的狀態
                document.querySelector(`#permissionForm-${adminId} input[value="${permissionId}"]`).checked = !isChecked;
            }
        });
    }

    function updatePermissionUI(adminId) {
        var permissionTagsContainer = document.querySelector(`tr:has(td:first-child:contains('${adminId}')) td:nth-child(4)`);
        var checkboxes = document.querySelectorAll(`#permissionForm-${adminId} input[type="checkbox"]`);
        
        // 清空現有的權限標籤
        permissionTagsContainer.innerHTML = '';
        
        // 根據checkbox的狀態重新添加權限標籤
        checkboxes.forEach(function(checkbox) {
            if (checkbox.checked) {
                var permissionName = checkbox.nextElementSibling.textContent.trim();
                var newTag = document.createElement('span');
                newTag.className = 'permission-tag';
                newTag.textContent = permissionName;
                permissionTagsContainer.appendChild(newTag);
            }
        });
    }
    </script>
</body>
</html>