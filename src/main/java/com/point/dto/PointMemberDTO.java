package com.point.dto;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class PointMemberDTO {
	private Integer memberId;
	private String memberName;
	private String phone;
	private int totalPointBalance;
	private int expiringPoints;
	private Date expiryDate;
	
	public PointMemberDTO() {
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getTotalPointBalance() {
		return totalPointBalance;
	}

	public void setTotalPointBalance(int totalPointBalance) {
		this.totalPointBalance = totalPointBalance;
	}

	public int getExpiringPoints() {
		return expiringPoints;
	}

	public void setExpiringPoints(int expiringPoints) {
		this.expiringPoints = expiringPoints;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	
	
}
