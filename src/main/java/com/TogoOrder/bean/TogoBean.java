package com.TogoOrder.bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.google.gson.annotations.Expose;
import com.members.bean.Member;
import com.rent.bean.Rent;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;

@Component
@Entity @Table(name = "togo")
public class TogoBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id @Column(name = "togo_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer togoId;
	
	@Column(name = "member_id", insertable = false, updatable = false)
	private Integer memberId;
	
	@Column(name = "tg_name")
	private String tgName;
	
	@Column(name = "tg_phone")
	private String tgPhone;
	
	@Column(name = "total_price")
	private Integer totalPrice;
	
	@Column(name = "togo_create_time")
	private LocalDateTime togoCreateTime;
	
	@Column(name = "rent_id", insertable = false, updatable = false)
	private Integer rentId;
	
	@Column(name = "togo_status")
	private Integer togoStatus;
	
	@Column(name = "restaurant_id")
	private Integer restaurantId;
	
	@Column(name = "togo_memo")
	private String togoMemo;
	
//	@JoinColumn(name = "member_id")
//	private Member member;
//	
//	@OneToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "rent_id")
//	private Rent rent;
	
//	@ManyToOne
//	@JoinColumn(name = "restaurant_id")
//	private RestaurantBean restaurant;
	
	public TogoBean() {
		
	}
	
	public TogoBean(Integer memberId, String tgName, String tgPhone, Integer totalPrice, Integer rentId,
			Integer togoStatus, Integer restaurantId, String togoMemo) {
		this.memberId = memberId;
		this.tgName = tgName;
		this.tgPhone = tgPhone;
		this.totalPrice = totalPrice;
		this.rentId = rentId;
		this.togoStatus = togoStatus;
		this.restaurantId = restaurantId;
		this.togoMemo = togoMemo;
	}

	public TogoBean(Integer togoId, Integer memberId, String tgName, String tgPhone, Integer totalPrice, Integer rentId,
			Integer togoStatus, Integer restaurantId, String togoMemo) {
		this.togoId = togoId;
		this.memberId = memberId;
		this.tgName = tgName;
		this.tgPhone = tgPhone;
		this.totalPrice = totalPrice;
		this.rentId = rentId;
		this.togoStatus = togoStatus;
		this.restaurantId = restaurantId;
		this.togoMemo = togoMemo;
	}

	public Integer getTogoId() {
		return togoId;
	}

	public void setTogoId(Integer togoId) {
		this.togoId = togoId;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getTgName() {
		return tgName;
	}

	public void setTgName(String tgName) {
		this.tgName = tgName;
	}

	public String getTgPhone() {
		return tgPhone;
	}

	public void setTgPhone(String tgPhone) {
		this.tgPhone = tgPhone;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	
	public String getFormattedtogoCreateTime() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		return togoCreateTime.format(formatter);
	}
	public LocalDateTime getTogoCreateTime() {
		return togoCreateTime;
	}

	public void setTogoCreateTime(LocalDateTime togoCreateTime) {
		this.togoCreateTime = togoCreateTime;
	}

	public Integer getRentId() {
		return rentId;
	}

	public void setRentId(Integer rentId) {
		this.rentId = rentId;
	}

	public Integer getTogoStatus() {
		return togoStatus;
	}

	public void setTogoStatus(Integer togoStatus) {
		this.togoStatus = togoStatus;
	}

	public Integer getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Integer restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getTogoMemo() {
		return togoMemo;
	}

	public void setTogoMemo(String togoMemo) {
		this.togoMemo = togoMemo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

//	public Member getMember() {
//		return member;
//	}
//
//	public void setMember(Member member) {
//		this.member = member;
//	}
//
//	public Rent getRent() {
//		return rent;
//	}
//
//	public void setRent(Rent rent) {
//		this.rent = rent;
//	}

//	public RestaurantBean getRestaurant() {
//		return restaurant;
//	}
//
//	public void setRestaurant(RestaurantBean restaurant) {
//		this.restaurant = restaurant;
//	}
	
}
