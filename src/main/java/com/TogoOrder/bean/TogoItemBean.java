package com.TogoOrder.bean;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Component
@Entity @Table(name = "togo_item")
public class TogoItemBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id @Column(name = "togo_id")
	private Integer togoId;
	
	@Id @Column(name = "food_id")
	private Integer foodId;
	
	@Column(name = "food_name")
	private String foodName;
	
	@Column(name = "food_price")
	private Integer foodPrice;
	
	@Column(name = "amount")
	private Integer amount;
	
	@Column(name = "togo_item_price")
	private Integer togoItemPrice;
	
	public TogoItemBean() {
		
	}

	public TogoItemBean(Integer togoId, Integer foodId, String foodName, Integer foodPrice, Integer amount,
			Integer togoItemPrice) {
		
		this.togoId = togoId;
		this.foodId = foodId;
		this.foodName = foodName;
		this.foodPrice = foodPrice;
		this.amount = amount;
		this.togoItemPrice = togoItemPrice;
	}



	public Integer getTogoId() {
		return togoId;
	}

	public void setTogoId(Integer togoId) {
		this.togoId = togoId;
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

	public Integer getFoodPrice() {
		return foodPrice;
	}

	public void setFoodPrice(Integer foodPrice) {
		this.foodPrice = foodPrice;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getTogoItemPrice() {
		return togoItemPrice;
	}

	public void setTogoItemPrice(Integer togoItemPrice) {
		this.togoItemPrice = togoItemPrice;
	}

}
