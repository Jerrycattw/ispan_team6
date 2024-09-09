package com.members.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.reserve.bean.Reserve;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity @Table(name = "members")
public class Member {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private int memberId;
	@Expose
	@Column(name = "member_name", nullable = false, length = 30)
	private String memberName;
	
	@Column(name = "account", nullable = false, unique = true, length = 30) 
	private String account;
	@Expose
	@Column(name = "password", nullable = false, length = 60)
	private String password;
	
	@Column(name = "birthday")
	@Temporal(TemporalType.DATE)
	private Date birthday;
	
	@Column(name = "email", nullable = false, unique = true, length = 50)
	private String email;
	
	@Column(name = "address", length = 255)
	private String address;
	
	@Column(name = "phone", length = 30)
	private String phone;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "register_date" , columnDefinition = "DATE DEFAULT GETDATE()")
	private Date registerDate;
	
	@Column(name = "status", nullable = false, columnDefinition = "CHAR(1) DEFAULT 'A'")
	private String status;
	
	@Expose
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "member", cascade = CascadeType.ALL)
	private List<Reserve> reserves = new ArrayList<Reserve>();
	
	
	
	

	public Member() {
	}
	
	public Member(int memberId, String memberName, String account, String password, Date birthday, String email,
			String address, String phone) {
		super();
		this.memberId = memberId;
		this.memberName = memberName;
		this.account = account;
		this.password = password;
		this.birthday = birthday;
		this.email = email;
		this.address = address;
		this.phone = phone;
	}
	
	public Member(int memberId, String memberName, String account, Date birthday, String email, String address,
			String phone, Date registerDate, String status) {
		super();
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

	public Member(int memberId, String memberName, String account, String password, Date birthday, String email,
			String address, String phone, Date registerDate, String status) {
		super();
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
