package com.admin.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.admin.bean.Admin;
import com.members.bean.Member;
import com.util.HibernateUtil;

public class AdminDao {

	private Session session;

	public AdminDao() {
		this.session = HibernateUtil.getSessionFactory().getCurrentSession();
	}

	// 更改會員資料
	public boolean updateMember(Member member) {
		Session session = null;
		try {
	    	session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.update(member); // Hibernate 的 update 方法
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// 更改會員狀態
	public boolean updateMemberStatus(int memberId, String newStatus) {
		Session session = null;
	    try {
	    	session = HibernateUtil.getSessionFactory().getCurrentSession();        
	        String hql = "UPDATE Member SET status = :newStatus WHERE memberId = :memberId";
	        Query<Member> query = session.createQuery(hql);
	        query.setParameter("newStatus", newStatus);
	        query.setParameter("memberId", memberId);
	        
	        int affectedRows = query.executeUpdate();
	        
	        return affectedRows > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    } 
	}


	// 查詢會員
	public List<Member> searchMembers(String memberName, String account, String email, String address, String phone,
			Date startDate, Date endDate, String status) {
		Session session = null;
		List<Member> members = new ArrayList<>();
		StringBuilder hql = new StringBuilder("FROM Member WHERE 1=1");

		if (memberName != null && !memberName.isEmpty()) {
			hql.append(" AND memberName LIKE :memberName");
		}
		if (account != null && !account.isEmpty()) {
			hql.append(" AND LOWER(account) LIKE :account");
		}
		if (email != null && !email.isEmpty()) {
			hql.append(" AND LOWER(email) LIKE :email");
		}
		if (address != null && !address.isEmpty()) {
			hql.append(" AND address LIKE :address");
		}
		if (phone != null && !phone.isEmpty()) {
			hql.append(" AND phone LIKE :phone");
		}
		if (startDate != null) {
			hql.append(" AND registerDate >= :startDate");
		}
		if (endDate != null) {
			hql.append(" AND registerDate <= :endDate");
		}
		if (status != null && !status.isEmpty()) {
			hql.append(" AND status = :status");
		}
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		Query<Member> query = session.createQuery(hql.toString(), Member.class);

		if (memberName != null && !memberName.isEmpty()) {
			query.setParameter("memberName", "%" + memberName + "%");
		}
		if (account != null && !account.isEmpty()) {
			query.setParameter("account", "%" + account.toLowerCase() + "%");
		}
		if (email != null && !email.isEmpty()) {
			query.setParameter("email", "%" + email.toLowerCase() + "%");
		}
		if (address != null && !address.isEmpty()) {
			query.setParameter("address", "%" + address.toLowerCase() + "%");
		}
		if (phone != null && !phone.isEmpty()) {
			query.setParameter("phone", "%" + phone + "%");
		}
		if (startDate != null) {
			query.setParameter("startDate", startDate);
		}
		if (endDate != null) {
			query.setParameter("endDate", endDate);
		}
		if (status != null && !status.isEmpty()) {
			query.setParameter("status", status);
		}

		try {

			members = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return members;
	}

	// 新增管理員
	public boolean addAdmin(Admin admin) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.save(admin); // Hibernate 的 save 方法
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// ID 查詢管理員
	public Admin findAdminById(int adminId) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			String hql = "FROM Admin WHERE adminId = :adminId";
			Query<Admin> query = session.createQuery(hql, Admin.class);
			query.setParameter("adminId", adminId);
			return query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 帳號查詢管理員
	public Admin findAdminByAccount(String account) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			String hql = "FROM Admin WHERE account = :account";
			Query<Admin> query = session.createQuery(hql, Admin.class);
			query.setParameter("account", account);
			return query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 查詢管理員一覽
	public List<Admin> findAllAdmins() {
		Session session = null;
		List<Admin> admins = new ArrayList<>();
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			String hql = "FROM Admin";
			Query<Admin> query = session.createQuery(hql, Admin.class);
			admins = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return admins;
	}

	// 更改管理員
	public boolean updateAdmin(Admin admin) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.update(admin); // Hibernate 的 update 方法
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// 更改管理員狀態
	public boolean updateAdminStatus(int adminId, String newStatus) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			String hql = "UPDATE Admin SET status = :newStatus WHERE adminId = :adminId";
			Query<?> query = session.createQuery(hql);
			query.setParameter("newStatus", newStatus.charAt(0));
			query.setParameter("adminId", adminId);
			int affectedRows = query.executeUpdate();
			return affectedRows > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
