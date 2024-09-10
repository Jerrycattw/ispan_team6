package com.coupon.bean;

import com.google.gson.annotations.Expose;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity @Table(name = "member_coupon")
public class CouponMemberBean {
	

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // 虚拟主键
	
	@Column(name = "coupon_id")
	private int couponId;
	
	@Column(name = "member_id")
	private int memberId;
	
	@Column(name = "status")
	private int status;
	
	@Transient
	private int receivedAmount;
	
	@Transient
	private String distributeStatus;//for發放
	
	@Transient
	private String distributeFailReason;//for發放
	
	@Expose(serialize = false)
	@ManyToOne
	@JoinColumn(name = "coupon_id", insertable = false, updatable = false)
	private CouponBean coupon;
	
	public CouponMemberBean() {

	}
	
	
	public int getCouponId() {
		return couponId;
	}
	public void setCouponId(int couponId) {
		this.couponId = couponId;
	}
	public int getMemberId() {
		return memberId;
	}
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getReceivedAmount() {
		return receivedAmount;
	}
	public void setReceivedAmount(int receivedAmount) {
		this.receivedAmount = receivedAmount;
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

	
	public CouponBean getCoupon() {
		return coupon;
	}


	public void setCoupon(CouponBean coupon) {
		this.coupon = coupon;
	}


	@Override
	public String toString() {
		return "CouponMemberBean [couponId=" + couponId + ", memberId=" + memberId + ", status=" + status
				+ ", receivedAmount=" + receivedAmount + ", distributeStatus=" + distributeStatus
				+ ", distributeFailReason=" + distributeFailReason + "]";
	}
	
	
}
