package com.members.service;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.members.bean.Member;
import com.members.dao.MemberDao;

@Service
public class MemberService {

    private final MemberDao memberDao;

    @Autowired
    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Transactional(readOnly = true)
    public Member login(String account, String password) {
        Member member = memberDao.login(account, password);

        if (member == null) {
            throw new IllegalArgumentException("帳號不正確");
        }

        String storedHashedPassword = member.getPassword();
        if (!BCrypt.checkpw(password, storedHashedPassword)) {
            throw new IllegalArgumentException("密碼不正確");
        }

        String status = member.getStatus();
        if ("I".equals(status)) {
            throw new IllegalArgumentException("帳號已被停用");
        } else if ("S".equals(status)) {
            throw new IllegalArgumentException("帳號已被暫停");
        }

        return member;
    }

    @Transactional
    public Member register(String name, String account, String password, Date birthday, String email, String address,
            String phone) {

        if (memberDao.isAccountUsed(account)) {
            throw new IllegalArgumentException("帳號已被註冊");
        }

        if (memberDao.isEmailUsed(email)) {
            throw new IllegalArgumentException("電子郵件已被註冊");
        }

        validateName(name);
        validateAccount(account);
        validatePassword(password);
        validateBirthday(birthday);
        validateEmail(email);
        validateAddress(address);
        validatePhone(phone);

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        return memberDao.register(name, account, hashedPassword, birthday, email, address, phone);
    }

    private void validateName(String name) {
        String nameRegex = "^[\\u4e00-\\u9fa5]{2,}$";
        Pattern namePattern = Pattern.compile(nameRegex);
        Matcher nameMatcher = namePattern.matcher(name);
        if (!nameMatcher.matches()) {
            throw new IllegalArgumentException("姓名必須包含至少兩個中文字");
        }
    }

    private void validateAccount(String account) {
        if (account == null || account.length() < 5) {
            throw new IllegalArgumentException("帳號不能小於5個字");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("密碼長度不能小於6個字");
        }
    }

    private void validateBirthday(Date birthday) {
        if (birthday == null || birthday.after(new Date())) {
            throw new IllegalArgumentException("出生日不能是未來日期");
        }
    }

    private void validateEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern emailPattern = Pattern.compile(emailRegex);
        Matcher emailMatcher = emailPattern.matcher(email);
        if (!emailMatcher.matches()) {
            throw new IllegalArgumentException("電子郵件格式不正確");
        }
    }

    private void validateAddress(String address) {
        if (address == null || address.length() > 50) {
            throw new IllegalArgumentException("地址不得超過50個字");
        }
    }

    private void validatePhone(String phone) {
        String phoneRegex = "^(09\\d{8}|\\+8669\\d{8})$";
        Pattern phonePattern = Pattern.compile(phoneRegex);
        Matcher phoneMatcher = phonePattern.matcher(phone);
        if (!phoneMatcher.matches()) {
            throw new IllegalArgumentException("電話號碼格式不正確");
        }
    }

    @Transactional(readOnly = true)
    public Member findMemberByMemberId(int memberId) {
        return memberDao.findMemberByMemberId(memberId);
    }

    @Transactional(readOnly = true)
    public Member findMemberByAccount(String account) {
        return memberDao.findMemberByAccount(account);
    }

    @Transactional
    public boolean updatePassword(String account, String newPassword) {
        String newHashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        return memberDao.updatePassword(account, newHashedPassword);
    }
}
 