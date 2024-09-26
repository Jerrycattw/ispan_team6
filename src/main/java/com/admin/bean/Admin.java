package com.admin.bean;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "admin")
public class Admin {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "admin_id")
	private int adminId;
	
	@Column(name = "admin_name", nullable = false, length = 30)
	private String adminName;
	
	@Column(name = "account", nullable = false, length = 30, unique = true)
	private String account;
	
	@Column(name = "password", nullable = false, length = 60)
	private String password;
	
	@Column(name = "status", nullable = false, length = 1)
	private char status = 'A';
	
	@Column(name = "role", nullable = false)
	private int role;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "admin_permissions",
	        joinColumns = @JoinColumn(name = "admin_id"),
	        inverseJoinColumns = @JoinColumn(name = "permission_id")
	)
	private Set<Permission> permissions = new HashSet<>();

	public Admin() {
		// TODO Auto-generated constructor stub
	}

	public Admin(int adminId, String adminName, String account, String password, char status, int role) {
		super();
		this.adminId = adminId;
		this.adminName = adminName;
		this.account = account;
		this.password = password;
		this.status = status;
		this.role = role;
	}

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}
	
	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

}
