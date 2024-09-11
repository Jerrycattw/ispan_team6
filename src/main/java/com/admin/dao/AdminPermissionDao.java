package com.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.admin.bean.AdminPermission;

public class AdminPermissionDao {
	
	private DataSource dataSource;
	
	public AdminPermissionDao() {
		// TODO Auto-generated constructor stub
	}

	public AdminPermissionDao(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}
	
	// 插入新的管理員權限記錄，返回是否成功
    public boolean insertAdminPermission(int adminId, int permissionId) {
        String sql = "INSERT INTO admin_permissions (admin_id, permission_id) VALUES (?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setInt(1, adminId);
            preparedStatement.setInt(2, permissionId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // 如果插入行數大於0，表示成功

        } catch (SQLException e) {
            e.printStackTrace(); // 使用適當的錯誤處理機制，如日誌
            return false; // 發生錯誤時返回 false
        }
    }

    // 刪除管理員的某個權限，返回是否成功
    public boolean deleteAdminPermission(int adminId, int permissionId) {
        String sql = "DELETE FROM admin_permissions WHERE admin_id = ? AND permission_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setInt(1, adminId);
            preparedStatement.setInt(2, permissionId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace(); // 使用適當的錯誤處理機制，如日誌
            return false;
        }
    }

    // 查詢某個管理員擁有的所有權限
    public List<AdminPermission> getPermissionsByAdminId(int adminId) {
        List<AdminPermission> permissions = new ArrayList<>();
        String sql = "SELECT * FROM admin_permissions WHERE admin_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setInt(1, adminId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                AdminPermission adminPermission = new AdminPermission();
                adminPermission.setAdminId(resultSet.getInt("admin_id"));
                adminPermission.setPermissionId(resultSet.getInt("permission_id"));
                permissions.add(adminPermission);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // 使用適當的錯誤處理機制，如日誌
        }

        return permissions;
    }

    // 刪除某個管理員的所有權限，返回是否成功
    public boolean deleteAllPermissionsByAdminId(int adminId) {
        String sql = "DELETE FROM admin_permissions WHERE admin_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setInt(1, adminId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace(); // 使用適當的錯誤處理機制，如日誌
            return false;
        }
    }
	
}
