package com.members.dao;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.members.bean.Member;

@Repository
public class MemberDao {

    @Autowired
    private SessionFactory sessionFactory;

    // 獲取當前的 Session
    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional
    public Member register(String name, String account, String hashedPassword, Date birthday, String email,
            String address, String phone) {

        Member member = new Member();
        try {
            // 建立新會員
            member.setMemberName(name);
            member.setAccount(account);
            member.setPassword(hashedPassword);
            member.setBirthday(birthday);
            member.setEmail(email);
            member.setAddress(address);
            member.setPhone(phone);
            member.setRegisterDate(new Date()); // 設置當前日期
            member.setStatus("A"); // 設置預設狀態

            // 儲存會員
            getCurrentSession().save(member);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return member;
    }

    @Transactional(readOnly = true)
    public Member login(String account, String password) {
        Member member = null;
        try {
            // 使用 HQL 查詢
            String hql = "FROM Member WHERE account = :account";
            Query<Member> query = getCurrentSession().createQuery(hql, Member.class);
            query.setParameter("account", account);
            member = query.uniqueResult(); // 獲取唯一結果
        } catch (Exception e) {
            e.printStackTrace();
        }
        return member;
    }

    @Transactional(readOnly = true)
    public Member findMemberByMemberId(int memberId) {
        Member member = null;
        try {
            String hql = "FROM Member WHERE memberId = :memberId";
            Query<Member> query = getCurrentSession().createQuery(hql, Member.class);
            query.setParameter("memberId", memberId);

            // 執行查詢並獲取結果
            member = query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return member;
    }

    @Transactional(readOnly = true)
    public Member findMemberByAccount(String account) {
        Member member = null;
        try {
            // 使用 HQL 查詢
            String hql = "FROM Member WHERE account = :account";
            Query<Member> query = getCurrentSession().createQuery(hql, Member.class);
            query.setParameter("account", account);

            // 執行查詢並獲取結果
            member = query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return member;
    }

    @Transactional(readOnly = true)
    public Member findMemberByEmail(String email) {
        Member member = null;
        try {
            // 使用 HQL 查詢
            String hql = "FROM Member WHERE email = :email";
            Query<Member> query = getCurrentSession().createQuery(hql, Member.class);
            query.setParameter("email", email);

            // 執行查詢並獲取結果
            member = query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return member;
    }

    @Transactional
    public boolean updatePassword(String account, String newHashedPassword) {
        boolean isUpdated = false;
        try {
            String hql = "UPDATE Member SET password = :newPassword WHERE account = :account";
            Query<?> query = getCurrentSession().createQuery(hql);
            query.setParameter("newPassword", newHashedPassword);
            query.setParameter("account", account);

            int affectedRows = query.executeUpdate();
            if (affectedRows > 0) {
                isUpdated = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isUpdated;
    }

    @Transactional(readOnly = true)
    public boolean isEmailUsed(String email) {
        boolean isUsed = false;
        if (email == null || email.isEmpty()) {
            System.out.println("Email parameter is null or empty");
            return false;
        }

        try {
            // 使用 HQL 查詢
            String hql = "SELECT COUNT(*) FROM Member WHERE email = :email";
            Query<Long> query = getCurrentSession().createQuery(hql, Long.class);
            query.setParameter("email", email);

            // 執行查詢並檢查結果
            Long count = query.uniqueResult();
            isUsed = (count != null && count > 0);
        } catch (Exception e) {
            e.printStackTrace();
            // 提供更詳細的錯誤資訊
            System.out.println("Error while checking if email is used: " + e.getMessage());
        }
        return isUsed;
    }

    @Transactional(readOnly = true)
    public boolean isAccountUsed(String account) {
        boolean isUsed = false;
        if (account == null || account.isEmpty()) {
            System.out.println("Account parameter is null or empty");
            return false;
        }

        try {
            // 使用 HQL 查詢
            String hql = "SELECT COUNT(*) FROM Member WHERE account = :account";
            Query<Long> query = getCurrentSession().createQuery(hql, Long.class);
            query.setParameter("account", account);

            // 執行查詢並檢查結果
            Long count = query.uniqueResult();
            isUsed = (count != null && count > 0);
        } catch (Exception e) {
            e.printStackTrace();
            // 提供更詳細的錯誤資訊
            System.out.println("Error while checking if account is used: " + e.getMessage());
        }
        return isUsed;
    }

}
