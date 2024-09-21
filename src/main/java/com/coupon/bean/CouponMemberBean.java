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
	
	@Column(name = "total_amount")
	private int totalAmount;
	
	@Column(name = "usage_amount")
	private int usageAmount;
	
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

	
	public CouponBean getCoupon() {
		return coupon;
	}


	public void setCoupon(CouponBean coupon) {
		this.coupon = coupon;
	}

	public int getTotalAmount() {
		return totalAmount;
	}


	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}


	public int getUsageAmount() {
		return usageAmount;
	}


	public void setUsageAmount(int usageAmount) {
		this.usageAmount = usageAmount;
	}


	@Override
	public String toString() {
		return "CouponMemberBean [id=" + id + ", couponId=" + couponId + ", memberId=" + memberId + ", totalAmount="
				+ totalAmount + ", usageAmount=" + usageAmount + ", coupon=" + coupon + "]";
	}


	
	
}
