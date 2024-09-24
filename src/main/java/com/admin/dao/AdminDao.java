package com.admin.dao;

import java.util.List;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.admin.bean.Admin;
import com.members.bean.Member;

@Repository
public class AdminDao {

    @Autowired
    private SessionFactory sessionFactory; // 使用 Spring 管理的 SessionFactory

    // 獲取當前的 Hibernate Session
    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession(); // 使用 Spring 管理的事務上下文
    }

    // 更改會員資料
    @Transactional
    public boolean updateMember(Member member) {
        getCurrentSession().update(member); // Hibernate 的 update 方法
        return true; // 成功返回 true，事務自動提交
    }

    // 更改會員狀態
    @Transactional
    public boolean updateMemberStatus(int memberId, String newStatus) {
        String hql = "UPDATE Member SET status = :newStatus WHERE memberId = :memberId";
        Query<?> query = getCurrentSession().createQuery(hql);
        query.setParameter("newStatus", newStatus);
        query.setParameter("memberId", memberId);
        int affectedRows = query.executeUpdate();
        return affectedRows > 0; // 根據影響行數決定是否成功
    }

    // 查詢會員
    @Transactional(readOnly = true)
    public List<Member> searchMembers(String memberName, String account, String email, String address, String phone,
                                      Date startDate, Date endDate, String status) {
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

        Query<Member> query = getCurrentSession().createQuery(hql.toString(), Member.class);

        // 設置參數
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
            query.setParameter("address", "%" + address + "%");
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

        return query.list(); // 返回查詢結果
    }

    // 新增管理員
    @Transactional
    public boolean addAdmin(Admin admin) {
        getCurrentSession().save(admin); // Hibernate 的 save 方法
        return true; // 成功返回 true
    }

    // ID 查詢管理員
    @Transactional(readOnly = true)
    public Admin findAdminById(int adminId) {
        String hql = "FROM Admin WHERE adminId = :adminId";
        Query<Admin> query = getCurrentSession().createQuery(hql, Admin.class);
        query.setParameter("adminId", adminId);
        return query.uniqueResult(); // 返回唯一的結果
    }

    // 帳號查詢管理員
    @Transactional(readOnly = true)
    public Admin findAdminByAccount(String account) {
        String hql = "FROM Admin WHERE account = :account";
        Query<Admin> query = getCurrentSession().createQuery(hql, Admin.class);
        query.setParameter("account", account);
        return query.uniqueResult(); // 返回唯一的結果
    }

    // 查詢管理員一覽
    @Transactional(readOnly = true)
    public List<Admin> findAllAdmins() {
        String hql = "FROM Admin";
        Query<Admin> query = getCurrentSession().createQuery(hql, Admin.class);
        return query.list(); // 返回管理員列表
    }

    // 更改管理員
    @Transactional
    public boolean updateAdmin(Admin admin) {
        getCurrentSession().update(admin); // Hibernate 的 update 方法
        return true; // 成功返回 true
    }

    // 更改管理員狀態
    @Transactional
    public boolean updateAdminStatus(int adminId, String newStatus) {
        String hql = "UPDATE Admin SET status = :newStatus WHERE adminId = :adminId";
        Query<?> query = getCurrentSession().createQuery(hql);
        query.setParameter("newStatus", newStatus.charAt(0));
        query.setParameter("adminId", adminId);
        int affectedRows = query.executeUpdate();
        return affectedRows > 0; // 根據影響行數決定是否成功
    }
}
