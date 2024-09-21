package com.coupon.dto;

import jakarta.persistence.Transient;

public class CouponDistributeDTO {
	private int couponId;
	private String selectOption;
	private int memberId;
	private String excuteStatus;
	private int maxCoupon;
	private int receivedAmount;
	private String distributeStatus;//for發放
	private String distributeFailReason;//for發放
	
	public CouponDistributeDTO() {
	}

	public CouponDistributeDTO(int couponId, String selectOption, int maxCoupon, int receivedAmount) {
		super();
		this.couponId = couponId;
		this.selectOption = selectOption;
		this.maxCoupon = maxCoupon;
		this.receivedAmount = receivedAmount;
	}


	public int getCouponId() {
		return couponId;
	}

	public void setCouponId(int couponId) {
		this.couponId = couponId;
	}

	public String getSelectOption() {
		return selectOption;
	}

	public void setSelectOption(String selectOption) {
		this.selectOption = selectOption;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}


	public String getExcuteStatus() {
		return excuteStatus;
	}

	public void setExcuteStatus(String excuteStatus) {
		this.excuteStatus = excuteStatus;
	}

	public int getReceivedAmount() {
		return receivedAmount;
	}

	public void setReceivedAmount(int receivedAmount) {
		this.receivedAmount = receivedAmount;
	}

	public int getMaxCoupon() {
		return maxCoupon;
	}

	public void setMaxCoupon(int maxCoupon) {
		this.maxCoupon = maxCoupon;
	}

	public String getDistributeStatus() {
		return distributeStatus;
	}

	public void setDistributeStatus(String distributeStatus) {
		this.distributeStatus = distributeStatus;
	}

	public String getDistributeFailReason() {
		return distributeFailReason;
	}

	public void setDistributeFailReason(String distributeFailReason) {
		this.distributeFailReason = distributeFailReason;
	}
	
	
	
	
	
}
