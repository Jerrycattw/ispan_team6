package com.admin.service;

import java.util.List;

import com.admin.bean.AdminPermission;
import com.admin.dao.AdminPermissionDao;

public class AdminPermissionService {

	private AdminPermissionDao adminPermissionDao;
	
	public AdminPermissionService() {
		// TODO Auto-generated constructor stub
	}

	public AdminPermissionService(AdminPermissionDao adminPermissionDao) {
		super();
		this.adminPermissionDao = adminPermissionDao;
	}
	
	// 為管理員分配權限，返回是否成功
    public boolean assignPermissionToAdmin(int adminId, int permissionId) {
        return adminPermissionDao.insertAdminPermission(adminId, permissionId);
    }

    // 移除管理員的某個權限，返回是否成功
    public boolean removePermissionFromAdmin(int adminId, int permissionId) {
        return adminPermissionDao.deleteAdminPermission(adminId, permissionId);
    }

    // 獲取管理員擁有的所有權限
    public List<AdminPermission> getPermissionsByAdminId(int adminId) {
        return adminPermissionDao.getPermissionsByAdminId(adminId);
    }

    // 移除管理員的所有權限，返回是否成功
    public boolean removeAllPermissionsFromAdmin(int adminId) {
        return adminPermissionDao.deleteAllPermissionsByAdminId(adminId);
    }
	
}
