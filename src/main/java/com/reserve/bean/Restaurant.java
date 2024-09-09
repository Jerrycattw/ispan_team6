package com.reserve.bean;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONPropertyIgnore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity @Table(name = "restaurant")
public class Restaurant {
	
	@Expose
	@Id @Column(name = "restaurant_id") @GeneratedValue(strategy = GenerationType.IDENTITY)
	private String restaurantId;
	@Expose
	@Column(name = "restaurant_name")
	private String restaurantName;
	@Expose
	@Column(name = "restaurant_address")
	private String restaurantAddress;
	@Expose
	@Column(name = "restaurant_phone")
	private String restaurantPhone;
	@Expose
	@Column(name = "restaurant_opentime")
	private LocalTime restaurantOpentime;
	@Expose
	@Column(name = "restaurant_closetime")
	private LocalTime restaurantClosetime;
	@Expose
	@Column(name = "eattime")
	private Integer eattime;
	@Expose
	@Column(name = "restaurant_status")
	private Integer restaurantStatus;
	@Expose
	@Column(name = "restaurant_img")
	private String restaurantImg;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant", cascade = CascadeType.ALL)
	private List<RestaurantTable> restaurantTables = new ArrayList<RestaurantTable>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant", cascade = CascadeType.ALL)
	private List<Reserve> reserves = new ArrayList<Reserve>();
	
	


	public Restaurant() {
	}


	public Restaurant(String restaurantName, String restaurantAddress, String restaurantPhone,
			LocalTime restaurantOpentime, LocalTime restaurantClosetime, Integer eattime, Integer restaurantStatus,
			String restaurantImg) {
		this.restaurantName = restaurantName;
		this.restaurantAddress = restaurantAddress;
		this.restaurantPhone = restaurantPhone;
		this.restaurantOpentime = restaurantOpentime;
		this.restaurantClosetime = restaurantClosetime;
		this.eattime = eattime;
		this.restaurantStatus = restaurantStatus;
		this.restaurantImg = restaurantImg;
	}


	public String getRestaurantId() {
		return restaurantId;
	}


	public void setRestaurantId(String restaurantId) {
		this.restaurantId = restaurantId;
	}


	public String getRestaurantName() {
		return restaurantName;
	}


	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}


	public String getRestaurantAddress() {
		return restaurantAddress;
	}


	public void setRestaurantAddress(String restaurantAddress) {
		this.restaurantAddress = restaurantAddress;
	}


	public String getRestaurantPhone() {
		return restaurantPhone;
	}


	public void setRestaurantPhone(String restaurantPhone) {
		this.restaurantPhone = restaurantPhone;
	}


	public LocalTime getRestaurantOpentime() {
		return restaurantOpentime;
	}


	public void setRestaurantOpentime(LocalTime restaurantOpentime) {
		this.restaurantOpentime = restaurantOpentime;
	}


	public LocalTime getRestaurantClosetime() {
		return restaurantClosetime;
	}


	public void setRestaurantClosetime(LocalTime restaurantClosetime) {
		this.restaurantClosetime = restaurantClosetime;
	}


	public Integer getEattime() {
		return eattime;
	}


	public void setEattime(Integer eattime) {
		this.eattime = eattime;
	}


	public Integer getRestaurantStatus() {
		return restaurantStatus;
	}


	public void setRestaurantStatus(Integer restaurantStatus) {
		this.restaurantStatus = restaurantStatus;
	}


	public String getRestaurantImg() {
		return restaurantImg;
	}


	public void setRestaurantImg(String restaurantImg) {
		this.restaurantImg = restaurantImg;
	}
	
	
	public List<RestaurantTable> getRestaurantTables() {
		return restaurantTables;
	}
	
	
	public void setRestaurantTables(List<RestaurantTable> restaurantTables) {
		this.restaurantTables = restaurantTables;
	}
	
	
	public List<Reserve> getReserves() {
		return reserves;
	}
	
	
	public void setReserves(List<Reserve> reserves) {
		this.reserves = reserves;
	}
	
	
	

}
