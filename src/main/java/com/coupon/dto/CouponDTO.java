package com.coupon.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;



@Component
public class CouponDTO {

	private Integer couponId;
	private String couponCode;
	private String couponDescription;
	private LocalDate couponStartDate;
	private LocalDate couponEndDate;
	private int maxCoupon;
	private int perMaxCoupon;
	private String couponStatus;
	private String rulesDescription;
	private String discountType;
	private int discount;
	private int minOrderDiscount;
	private int maxDiscount;
	private List<TagDTO> tags;
	private int receivedAmount;
	
	public CouponDTO() {
		
	}
	
	
	public CouponDTO(Integer couponId, String couponCode, String couponDescription, LocalDate couponStartDate,
			LocalDate couponEndDate, int maxCoupon, int perMaxCoupon, String couponStatus, String rulesDescription,
			String discountType, int discount, int minOrderDiscount, int maxDiscount, List<TagDTO> tags,
			int receivedAmount) {
		this.couponId = couponId;
		this.couponCode = couponCode;
		this.couponDescription = couponDescription;
		this.couponStartDate = couponStartDate;
		this.couponEndDate = couponEndDate;
		this.maxCoupon = maxCoupon;
		this.perMaxCoupon = perMaxCoupon;
		this.couponStatus = couponStatus;
		this.rulesDescription = rulesDescription;
		this.discountType = discountType;
		this.discount = discount;
		this.minOrderDiscount = minOrderDiscount;
		this.maxDiscount = maxDiscount;
		this.tags = tags;
		this.receivedAmount = receivedAmount;
	}


	public Integer getCouponId() {
		return couponId;
	}
	public void setCouponId(Integer couponId) {
		this.couponId = couponId;
	}
	public String getCouponCode() {
		return couponCode;
	}
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
	public String getCouponDescription() {
		return couponDescription;
	}
	public void setCouponDescription(String couponDescription) {
		this.couponDescription = couponDescription;
	}
	public LocalDate getCouponStartDate() {
		return couponStartDate;
	}
	public void setCouponStartDate(LocalDate couponStartDate) {
		this.couponStartDate = couponStartDate;
	}
	public LocalDate getCouponEndDate() {
		return couponEndDate;
	}
	public void setCouponEndDate(LocalDate couponEndDate) {
		this.couponEndDate = couponEndDate;
	}
	public int getMaxCoupon() {
		return maxCoupon;
	}
	public void setMaxCoupon(int maxCoupon) {
		this.maxCoupon = maxCoupon;
	}
	public int getPerMaxCoupon() {
		return perMaxCoupon;
	}
	public void setPerMaxCoupon(int perMaxCoupon) {
		this.perMaxCoupon = perMaxCoupon;
	}
	public String getCouponStatus() {
		return couponStatus;
	}
	public void setCouponStatus(String couponStatus) {
		this.couponStatus = couponStatus;
	}
	public String getRulesDescription() {
		return rulesDescription;
	}
	public void setRulesDescription(String rulesDescription) {
		this.rulesDescription = rulesDescription;
	}
	public String getDiscountType() {
		return discountType;
	}
	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}
	public int getDiscount() {
		return discount;
	}
	public void setDiscount(int discount) {
		this.discount = discount;
	}
	public int getMinOrderDiscount() {
		return minOrderDiscount;
	}
	public void setMinOrderDiscount(int minOrderDiscount) {
		this.minOrderDiscount = minOrderDiscount;
	}
	public int getMaxDiscount() {
		return maxDiscount;
	}
	public void setMaxDiscount(int maxDiscount) {
		this.maxDiscount = maxDiscount;
	}
	public List<TagDTO> getTags() {
		return tags;
	}
	public void setTags(List<TagDTO> tags) {
		this.tags = tags;
	}

	
	public int getReceivedAmount() {
		return receivedAmount;
	}
	public void setReceivedAmount(int receivedAmount) {
		this.receivedAmount = receivedAmount;
	}
	    
}
