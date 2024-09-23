package com.reserve.bean;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reserve.service.ReserveService;
import com.reserve.service.RestaurantService;
import com.reserve.service.RestaurantTableService;
import com.reserve.service.TableTypeService;
import com.util.HibernateUtil;


@Component
public class TableDTO {
	
	private String tableTypeId;
	private String restaurantId;
	private String tableTypeName;
	private Integer tableTypeNumber;
	private String restaurantName;
	
	@Autowired
	private TableTypeService tableTypeService;
	@Autowired
	private RestaurantService restaurantService;
	
//	@Autowired
//	private SessionFactory sessionFactory;
	
	
	public TableDTO() {
	}
	
	
	public TableDTO(RestaurantTable restaurantTable) {
		
			
			this.tableTypeId = restaurantTable.getId().getTableTypeId();
			this.restaurantId = restaurantTable.getId().getRestaurantId().toString();
//			this.tableTypeName = tableTypeService.getTableTypeName(tableTypeId);
			this.tableTypeName = restaurantTable.getTableType().getTableTypeName();
			this.tableTypeNumber = restaurantTable.getTableTypeNumber();
//			this.restaurantName = restaurantService.getRestaurantName(restaurantId);
			this.restaurantName = restaurantTable.getRestaurant().getRestaurantName();
			
	}


	public String getRestaurantName() {
		return restaurantName;
	}
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	public String getTableTypeId() {
		return tableTypeId;
	}
	public void setTableTypeId(String tableTypeId) {
		this.tableTypeId = tableTypeId;
	}
	public String getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(String restaurantId) {
		this.restaurantId = restaurantId;
	}
	public String getTableTypeName() {
		return tableTypeName;
	}
	public void setTableTypeName(String tableTypeName) {
		this.tableTypeName = tableTypeName;
	}
	public Integer getTableTypeNumber() {
		return tableTypeNumber;
	}
	public void setTableTypeNumber(Integer tableTypeNumber) {
		this.tableTypeNumber = tableTypeNumber;
	}
	
	
	@Override
	public String toString() {
		return "TableTypeBean [tableTypeId=" + tableTypeId + ", restaurantId=" + restaurantId + ", tableTypeName="
				+ tableTypeName + ", tableTypeNumber=" + tableTypeNumber + ", restaurantName=" + restaurantName + "]";
	}

	
	

}
