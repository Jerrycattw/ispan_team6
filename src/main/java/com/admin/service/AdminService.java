package com.admin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.admin.bean.Admin;
import com.admin.dao.AdminDao;
import com.members.bean.Member;

@Service
public class AdminService {
	
	
	private AdminDao adminDao;
	
	@Autowired
	public AdminService(AdminDao adminDao) {
		this.adminDao = adminDao;
	}

	public boolean updateMember(Member member) {
		// 檢查會員資料是否有效
		if (member == null || member.getMemberId() <= 0) {
			return false;
		}
		// 呼叫 DAO 層的更新方法
		return adminDao.updateMember(member);
	}

	public boolean updateMemberStatus(int memberId, String newStatus) {
		return adminDao.updateMemberStatus(memberId, newStatus);
	}
	//搜尋全部管理員
	public List<Member> searchMembers(String memberName, String account, String email, String address, String phone,
			Date startDate, Date endDate, String status) {
		List<Member> members = new ArrayList<>();
		members = adminDao.searchMembers(memberName, account, email, address, phone, startDate, endDate, status);
		return members;

	}
	
	//ID搜尋管理員
	public Admin findAdminById(int adminId) {
		return adminDao.findAdminById(adminId);
	}
	//新增管理員
	public boolean addAdmin(Admin admin) {
		if (adminDao.findAdminByAccount(admin.getAccount()) == null) {
			return adminDao.addAdmin(admin);
		}
		return false;
	}
	//帳號搜尋管理員
	public Admin findAdminByAccount(String account) {
		return adminDao.findAdminByAccount(account);
	}
	//管理員一覽
	public List<Admin> findAllAdmins() {
		return adminDao.findAllAdmins();
		
	}
	//修改管理員
	public boolean updateAdmin(Admin admin) {
	    return adminDao.updateAdmin(admin);
	}
	//修改管理員狀態
	public boolean updateAdminStatus(int adminId, String newStatus) {
		return adminDao.updateAdminStatus(adminId, newStatus);
	}
	//檢查是否是超級管理員
	public boolean isSuperAdmin(int adminId) {
        Admin admin = findAdminById(adminId);
        return admin != null && admin.getRole() == 1;
    }
}
