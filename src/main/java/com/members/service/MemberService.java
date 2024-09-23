package com.members.service;


import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.members.bean.Member;
import com.members.dao.MemberDao;

import jakarta.transaction.Transactional;
//@Service
//@Transactional
public class MemberService {
//	@Autowired
	private MemberDao memberDao;

	public MemberService(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	public Member login(String account, String password) {

		Member member = memberDao.login(account, password);

		if (member == null) {
			throw new IllegalArgumentException("帳號不正確");
		}

		String storedHashedPassword = member.getPassword();
		if (!BCrypt.checkpw(password, storedHashedPassword)) {
			throw new IllegalArgumentException("密碼不正確");
		}

		// 檢查用戶狀態
		String status = member.getStatus();
		if ("I".equals(status)) {
			throw new IllegalArgumentException("帳號已被停用");
		} else if ("S".equals(status)) {
			throw new IllegalArgumentException("帳號已被暫停");
		}

		return member;
	}

	public Member register(String name, String account, String password, Date birthday, String email, String address,
			String phone) {
		
		if (memberDao.isAccountUsed(account) == true) {
			throw new IllegalArgumentException("帳號已被註冊");
		}
		
		if (memberDao.isEmailUsed(email) == true) {
			throw new IllegalArgumentException("電子郵件已被註冊");
		}

		String nameRegex = "^[\\u4e00-\\u9fa5]{2,}$";
		Pattern namePattern = Pattern.compile(nameRegex);
		Matcher nameMatcher = namePattern.matcher(name);
		if (!nameMatcher.matches()) {
			throw new IllegalArgumentException("姓名必須包含至少兩個中文字");
		}

		if (account == null || account.length() < 5) {
			throw new IllegalArgumentException("帳號不能小於5個字");
		}

		if (password == null || password.length() < 6) {
			throw new IllegalArgumentException("密碼長度不能小於6個字");
		}

		if (birthday == null || birthday.after(new Date())) {
			throw new IllegalArgumentException("出生日不能是未來日期");
		}

		String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
		Pattern emailPattern = Pattern.compile(emailRegex);
		Matcher emailMatcher = emailPattern.matcher(email);
		if (!emailMatcher.matches()) {
			throw new IllegalArgumentException("電子郵件格式不正確");
		}

		if (address == null || address.length() > 50) {
			throw new IllegalArgumentException("地址不得超過50個字");
		}

		// 電話號碼的正則表達式
		String phoneRegex = "^(09\\d{8}|\\+8669\\d{8})$";
		Pattern phonePattern = Pattern.compile(phoneRegex);
		Matcher phoneMatcher = phonePattern.matcher(phone);
		if (!phoneMatcher.matches()) {
			throw new IllegalArgumentException("電話號碼格式不正確");
		}

		// 使用 bcrypt 進行密碼加密
		String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		return memberDao.register(name, account, hashedPassword, birthday, email, address, phone);
	}

	public Member findMemberByMemberId(int memberId) {
		return memberDao.findMemberByMemberId(memberId);
	}
	
	public Member findMemberByAccount(String account) {
		return memberDao.findMemberByAccount(account);
	}
	

	public boolean updatePassword(String account, String newPassword) {
		
		String newHashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
		
		return memberDao.updatePassword(account, newHashedPassword);
	}

	
	
	
	//Rent使用
//	public List<String> getAllMemberName() {
//		return memberDao.getAllMemberName();
//	}
//
//	public Integer getMemberId(String memberName) {
//		return memberDao.getMemberId(memberName);
//	}

}
