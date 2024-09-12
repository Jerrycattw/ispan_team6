package com.members.bean;

import java.util.Date;

public class MemberDTO {

	private int memberId;

	private String memberName;

	private String account;

	private String password;

	private Date birthday;

	private String email;

	private String address;

	private String phone;

	private Date registerDate;

	private String status;

	public MemberDTO() {
	}

	public MemberDTO(int memberId, String memberName, String account, String password, Date birthday, String email,
			String address, String phone) {
		this.memberId = memberId;
		this.memberName = memberName;
		this.account = account;
		this.password = password;
		this.birthday = birthday;
		this.email = email;
		this.address = address;
		this.phone = phone;
	}

	public MemberDTO(int memberId, String memberName, String account, Date birthday, String email, String address,
			String phone, Date registerDate, String status) {
		this.memberId = memberId;
		this.memberName = memberName;
		this.account = account;
		this.birthday = birthday;
		this.email = email;
		this.address = address;
		this.phone = phone;
		this.registerDate = registerDate;
		this.status = status;
	}

	public MemberDTO(int memberId, String memberName, String account, String password, Date birthday, String email,
			String address, String phone, Date registerDate, String status) {
		this.memberId = memberId;
		this.memberName = memberName;
		this.account = account;
		this.password = password;
		this.birthday = birthday;
		this.email = email;
		this.address = address;
		this.phone = phone;
		this.registerDate = registerDate;
		this.status = status;
	}

	// Getter 和 Setter
	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
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

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
