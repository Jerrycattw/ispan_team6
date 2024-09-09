package com.point.bean;

import java.io.Serializable;
import java.util.Date;

public class PointMemberBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private int memberID;
	private String memberName;
	private String phone;
	private int totalPoint;
	private int expiriyPoint;
	private Date expiryDate;
	
	public PointMemberBean() {
		super();
	}

	public int getMemberID() {
		return memberID;
	}

	public void setMemberID(int memberID) {
		this.memberID = memberID;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getTotalPoint() {
		return totalPoint;
	}

	public void setTotalPoint(int totalPoint) {
		this.totalPoint = totalPoint;
	}

	public int getExpiriyPoint() {
		return expiriyPoint;
	}

	public void setExpiriyPoint(int expiriyPoint) {
		this.expiriyPoint = expiriyPoint;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	
}
