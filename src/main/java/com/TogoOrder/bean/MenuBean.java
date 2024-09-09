package com.TogoOrder.bean;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity @Table(name = "menu")
public class MenuBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Expose
	@Id @Column(name = "food_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer foodId;
	
	@Expose
	@Column(name = "food_name")
	private String foodName;
	
	@Expose
	@Column(name = "food_picture")
	private String foodPicture;
	
	@Expose
	@Column(name = "food_price")
	private Integer foodPrice;
	
	@Expose
	@Column(name = "food_kind")
	private String foodKind;
	
	@Expose
	@Column(name = "food_stock")
	private Integer foodStock;
	
	@Expose
	@Column(name = "food_description")
	private String foodDescription;
	
	@Expose
	@Column(name = "food_status")
	private Integer foodStatus;
	
	public MenuBean() {
		
	}

	public MenuBean(Integer foodId, String foodName, String foodPicture, Integer foodPrice, String foodKind,
			Integer foodStock, String foodDescription, Integer foodStatus) {
		
		this.foodId = foodId;
		this.foodName = foodName;
		this.foodPicture = foodPicture;
		this.foodPrice = foodPrice;
		this.foodKind = foodKind;
		this.foodStock = foodStock;
		this.foodDescription = foodDescription;
		this.foodStatus = foodStatus;
	}





	public MenuBean(String foodName, String foodPicture, Integer foodPrice, String foodKind, Integer foodStock,
			String foodDescription, Integer foodStatus) {
		super();
		this.foodName = foodName;
		this.foodPicture = foodPicture;
		this.foodPrice = foodPrice;
		this.foodKind = foodKind;
		this.foodStock = foodStock;
		this.foodDescription = foodDescription;
		this.foodStatus = foodStatus;
	}





	public Integer getFoodId() {
		return foodId;
	}

	public void setFoodId(Integer foodId) {
		this.foodId = foodId;
	}

	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	public String getFoodPicture() {
		return foodPicture;
	}

	public void setFoodPicture(String foodPicture) {
		this.foodPicture = foodPicture;
	}

	public Integer getFoodPrice() {
		return foodPrice;
	}

	public void setFoodPrice(Integer foodPrice) {
		this.foodPrice = foodPrice;
	}

	public String getFoodKind() {
		return foodKind;
	}

	public void setFoodKind(String foodKind) {
		this.foodKind = foodKind;
	}

	public Integer getFoodStock() {
		return foodStock;
	}

	public void setFoodStock(Integer foodStock) {
		this.foodStock = foodStock;
	}

	public String getFoodDescription() {
		return foodDescription;
	}

	public void setFoodDescription(String foodDescription) {
		this.foodDescription = foodDescription;
	}

	public Integer getFoodStatus() {
		return foodStatus;
	}

	public void setFoodStatus(Integer foodStatus) {
		this.foodStatus = foodStatus;
	}
	
	
	
}
