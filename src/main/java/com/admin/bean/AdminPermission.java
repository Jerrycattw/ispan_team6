package com.admin.bean;

public class AdminPermission {
	private int adminId;
    private int permissionId;
    
    public AdminPermission() {
		// TODO Auto-generated constructor stub
	}

	public AdminPermission(int adminId, int permissionId) {
		super();
		this.adminId = adminId;
		this.permissionId = permissionId;
	}

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public int getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(int permissionId) {
		this.permissionId = permissionId;
	}
    
	
}
