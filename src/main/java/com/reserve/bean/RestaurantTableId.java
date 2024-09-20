package com.reserve.bean;


import java.io.Serializable;
import java.util.Objects;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Component
@Embeddable
public class RestaurantTableId implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "restaurant_id")
	private Integer restaurantId;
	
	@Column(name = "table_type_id")
	private String tableTypeId;
	
	// 預設建構子
    public RestaurantTableId() {}

    public RestaurantTableId(Integer restaurantId, String tableTypeId) {
        this.restaurantId = restaurantId;
        this.tableTypeId = tableTypeId;
    }

    // Getters 和 Setters

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getTableTypeId() {
        return tableTypeId;
    }

    public void setTableTypeId(String tableTypeId) {
        this.tableTypeId = tableTypeId;
    }

    
    // hashCode 和 equals 方法（必要，用於確保複合鍵的唯一性）
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantTableId that = (RestaurantTableId) o;
        return Objects.equals(restaurantId, that.restaurantId) && 
               Objects.equals(tableTypeId, that.tableTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurantId, tableTypeId);
    }
	
	
	

}
