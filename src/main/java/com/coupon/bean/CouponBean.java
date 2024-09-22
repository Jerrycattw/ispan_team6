package com.coupon.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.google.gson.annotations.Expose;
import com.members.bean.Member;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Component
@Entity @Table(name = "coupon")
public class CouponBean implements Serializable{
	private static final long serialVersionUID = 1L;
	

	@Id @Column(name = "coupon_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer couponId;
	

	@Column(name = "coupon_code")
	private String couponCode;
	
	
	@Column(name = "coupon_description")
	private String couponDescription;
	
	
	@Column(name = "coupon_start_date")
	private LocalDate couponStartDate;
	
	
	@Column(name = "coupon_end_date")
	private LocalDate couponEndDate;
	
	
	@Column(name = "max_coupon")
	private int maxCoupon;
	
	
	@Column(name = "per_max_coupon")
	private int perMaxCoupon;
	
	
	@Column(name = "coupon_status")
	private String couponStatus;
	
	
	@Column(name = "rules_description")
	private String rulesDescription;
	
	
	@Column(name = "discount_type")
	private String discountType;
	
	
	@Column(name = "discount")
	private int discount;
	
	
	@Column(name = "min_order_amount")
	private int minOrderDiscount;
	
	
	@Column(name = "max_discount")
	private int maxDiscount;
	
	
	@Transient
	private int receivedAmount;
	
	
	
	@OneToMany(fetch = FetchType.LAZY,cascade=CascadeType.ALL,mappedBy = "coupon",orphanRemoval=true)
	private List<TagBean> tags=new ArrayList<TagBean>();
	
	@ManyToMany
	@JoinTable(name = "member_coupon",
	joinColumns = @JoinColumn(name="coupon_id"),
	inverseJoinColumns = @JoinColumn(name="member_id"))
	private List<Member> members; // 关联的 MemberBean 实体集合
	
//	@Expose(serialize = false)//Gson
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "coupon")
//	private Set<CouponMemberBean> couponMember = new HashSet<CouponMemberBean>();
	
	
	public CouponBean() {
	}
	
	
	public CouponBean(String couponCode, String couponDescription, LocalDate couponStartDate,
			LocalDate couponEndDate, int maxCoupon, int perMaxCoupon, String couponStatus, String rulesDescription,
			String discountType, int discount, int minOrderDiscount, int maxDiscount) {
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
	}
	
	public CouponBean(Integer couponId, String couponCode, String couponDescription, LocalDate couponStartDate,
			LocalDate couponEndDate, int maxCoupon, int perMaxCoupon, String couponStatus, String rulesDescription,
			String discountType, int discount, int minOrderDiscount, int maxDiscount) {
		this.couponId=couponId;
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
	public List<TagBean> getTags() {
		return tags;
	}
	public void setTags(List<TagBean> tags) {
		this.tags = tags;
	}
	
	public int getReceivedAmount() {
		return receivedAmount;
	}
	
	public void setReceivedAmount(int receivedAmount) {
		this.receivedAmount = receivedAmount;
	}
	
	
	
	@Override
	public String toString() {
		return "CouponBean [couponId=" + couponId + ", couponCode=" + couponCode + ", couponDescription="
				+ couponDescription + ", couponStartDate=" + couponStartDate + ", couponEndDate=" + couponEndDate
				+ ", maxCoupon=" + maxCoupon + ", perMaxCoupon=" + perMaxCoupon + ", couponStatus=" + couponStatus
				+ ", rulesDescription=" + rulesDescription + ", discountType=" + discountType + ", discount=" + discount
				+ ", minOrderDiscount=" + minOrderDiscount + ", maxDiscount=" + maxDiscount + ", tags=" + tags +", receivedAmount="+receivedAmount+ "]\r\n";
	}


	public List<Member> getMembers() {
		return members;
	}


	public void Members(List<Member> members) {
		this.members = members;
	}

}
