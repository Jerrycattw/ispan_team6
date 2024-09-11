package com.admin.service;

import java.util.List;

import com.admin.bean.Permission;
import com.admin.dao.PermissionDao;

public class PermissionService {

	PermissionDao permissionDao;
	
	public PermissionService() {
		
	}

	public PermissionService(PermissionDao permissionDao) {
		super();
		this.permissionDao = permissionDao;
	}
	
	// 根據權限名稱獲取權限
    public Permission getPermissionByName(String permissionName) {
        return permissionDao.getPermissionByName(permissionName);
    }

    // 獲取所有權限
    public List<Permission> getAllPermissions() {
        return permissionDao.getAllPermissions();
    }

    // 新增權限
    public void addPermission(String permissionName) {
        Permission permission = new Permission();
        permission.setPermissionName(permissionName);
        permissionDao.insertPermission(permission);
    }

    // 刪除權限
    public void removePermission(int permissionId) {
    	permissionDao.deletePermission(permissionId);
    }
	
	
	
}
