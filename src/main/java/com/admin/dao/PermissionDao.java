package com.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.admin.bean.Permission;

public class PermissionDao {
	
	private DataSource dataSource;
	
	public PermissionDao() {
		// TODO Auto-generated constructor stub
	}
	
	public PermissionDao(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	// 根據權限名稱查詢權限
    public Permission getPermissionByName(String permissionName) {
        Permission permission = null;
        String sql = "SELECT * FROM permissions WHERE permission_name = ?";

    	try (Connection conn = dataSource.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

    		preparedStatement.setString(1, permissionName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                permission = new Permission();
                permission.setPermissionId(resultSet.getInt("permissionId"));
                permission.setPermissionName(resultSet.getString("permissionName"));
            }

        } catch (SQLException e) {
            e.printStackTrace();  // 您可以改為使用更好的錯誤處理機制
        }

        return permission;
    }
    
    // 獲取所有權限
    public List<Permission> getAllPermissions() {
        List<Permission> permissions = new ArrayList<>();
        String sql = "SELECT * FROM permissions";

        try (Connection conn = dataSource.getConnection(); 
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Permission permission = new Permission();
                permission.setPermissionId(resultSet.getInt("permission_id"));
                permission.setPermissionName(resultSet.getString("permission_name"));
                permissions.add(permission);
            }

        } catch (SQLException e) {
            // 可以使用更好的錯誤處理機制，例如記錄日誌
            e.printStackTrace();
        }

        return permissions;
    }

    // 插入新的權限
    public void insertPermission(Permission permission) {
        String sql = "INSERT INTO permissions (permission_name) VALUES (?)";

        try (Connection conn = dataSource.getConnection(); 
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, permission.getPermissionName());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // 可以使用更好的錯誤處理機制，例如記錄日誌
            e.printStackTrace();
        }
    }

    // 刪除權限
    public void deletePermission(int permissionId) {
        String sql = "DELETE FROM permissions WHERE permission_id = ?";

        try (Connection conn = dataSource.getConnection(); 
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setInt(1, permissionId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // 可以使用更好的錯誤處理機制，例如記錄日誌
            e.printStackTrace();
        }
    }
}

