package com.reserve.bean;


import com.google.gson.annotations.Expose;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity @Table(name = "restaurant_table")
public class RestaurantTable {
	@Expose
    @EmbeddedId
    private RestaurantTableId id; // 嵌入的複合主鍵

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", insertable = false, updatable = false)
    private Restaurant restaurant; // 與 Restaurant 的關聯
 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_type_id", insertable = false, updatable = false)
    private TableType tableType; // 與 TableType 的關聯
    
    @Expose
    @Column(name = "table_type_number")
    private Integer tableTypeNumber; // 桌子數量
	
	
	public RestaurantTable() {
	}


	public RestaurantTable(Integer tableTypeNumber) {
		this.tableTypeNumber = tableTypeNumber;
	}
	
	
	

	public RestaurantTable(RestaurantTableId id, Integer tableTypeNumber) {
		this.id = id;
		this.tableTypeNumber = tableTypeNumber;
	}


	public RestaurantTableId getId() {
		return id;
	}


	public void setId(RestaurantTableId id) {
		this.id = id;
	}


	public Restaurant getRestaurant() {
		return restaurant;
	}


	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}


	public TableType getTableType() {
		return tableType;
	}


	public void setTableType(TableType tableType) {
		this.tableType = tableType;
	}


	public Integer getTableTypeNumber() {
		return tableTypeNumber;
	}


	public void setTableTypeNumber(Integer tableTypeNumber) {
		this.tableTypeNumber = tableTypeNumber;
	}

	
	

	
	
	
	
	
	

}
