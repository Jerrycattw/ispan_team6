package com.coupon.bean;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.google.gson.annotations.Expose;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Component
@Entity @Table(name = "coupon_tag")
public class TagBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private TagId tagId;
	
	
	@Column(name = "tag_type")
	private String tagType;
	
	@Expose(serialize = false)//gson
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "coupon_id",insertable=false, updatable=false)
	private CouponBean coupon;
	
	public TagBean() {
	}
	
	public TagBean(TagId tagId, String tagType) {
		this.tagId = tagId;
		this.tagType = tagType;
	}



	public String getTagType() {
		return tagType;
	}

	public void setTagType(String tagType) {
		this.tagType = tagType;
	}
	
	

	public TagId getTagId() {
		return tagId;
	}

	public void setTagId(TagId tagId) {
		this.tagId = tagId;
	}
	

	public CouponBean getCoupon() {
		return coupon;
	}
	

	public void setCoupon(CouponBean coupon) {
		this.coupon = coupon;
	}

	@Override
	public String toString() {
//		return "TagBean [tagId=" + tagId + ", tagType=" + tagType + "]";
		return "TagBean [couponId=" + tagId.getCouponId() + ", tagName=" + tagId.getTagName() + ", tagType=" + tagType + "]";
	}
	
	
	
}
