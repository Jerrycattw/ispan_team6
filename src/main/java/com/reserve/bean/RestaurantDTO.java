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

public class RestaurantDTO {
	
	private String restaurantId;
	private String restaurantName;
	private String restaurantAddress;
	private String restaurantPhone;
	private LocalTime restaurantOpentime;
	private LocalTime restaurantClosetime;
	private Integer eattime;
	private Integer restaurantStatus;
	private String restaurantImg;
	
	


	public RestaurantDTO() {
	}


	public RestaurantDTO(Restaurant restaurant) {
		this.restaurantId = restaurant.getRestaurantId();
		this.restaurantName = restaurant.getRestaurantName();
		this.restaurantAddress = restaurant.getRestaurantAddress();
		this.restaurantPhone = restaurant.getRestaurantPhone();
		this.restaurantOpentime = restaurant.getRestaurantOpentime();
		this.restaurantClosetime = restaurant.getRestaurantClosetime();
		this.eattime = restaurant.getEattime();
		this.restaurantStatus = restaurant.getRestaurantStatus();
		this.restaurantImg = restaurant.getRestaurantImg();
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
	
	
	
	
	

}
