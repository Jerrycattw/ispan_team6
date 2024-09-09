package com.reserve.bean;


import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity @Table(name = "table_type")
public class TableType {
	@Expose
	@Id @Column(name = "table_type_id")
	private String tableTypeId;
	@Expose
	@Column(name = "table_type_name")
	private String tableTypeName;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tableType", cascade = CascadeType.ALL)
	private List<RestaurantTable> restaurantTables = new ArrayList<RestaurantTable>();
	
	


	public TableType() {
	}


	public TableType(String tableTypeId, String tableTypeName) {
		this.tableTypeId = tableTypeId;
		this.tableTypeName = tableTypeName;
	}


	public String getTableTypeId() {
		return tableTypeId;
	}


	public void setTableTypeId(String tableTypeId) {
		this.tableTypeId = tableTypeId;
	}


	public String getTableTypeName() {
		return tableTypeName;
	}


	public void setTableTypeName(String tableTypeName) {
		this.tableTypeName = tableTypeName;
	}
	
	
	public List<RestaurantTable> getRestaurantTables() {
		return restaurantTables;
	}
	
	
	public void setRestaurantTables(List<RestaurantTable> restaurantTables) {
		this.restaurantTables = restaurantTables;
	}


	@Override
	public String toString() {
		return "TableType [tableTypeId=" + tableTypeId + ", tableTypeName=" + tableTypeName + "]";
	}
	
	

}
