package com.reserve.bean;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.reserve.service.RestaurantService;
import com.reserve.service.TableTypeService;
import com.util.HibernateUtil;

public class TableDTO {
	
	private String tableTypeId;
	private String restaurantId;
	private String tableTypeName;
	private Integer tableTypeNumber;
	private String restaurantName;
	
	
	
	public TableDTO() {
	}
	
	
	public TableDTO(RestaurantTable restaurantTable) {
		
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		
		try (Session session = sessionFactory.openSession()) {
			TableTypeService tableTypeService = new TableTypeService(session);
			RestaurantService restaurantService = new RestaurantService(session);
			
			this.tableTypeId = restaurantTable.getId().getTableTypeId();
			this.restaurantId = restaurantTable.getId().getRestaurantId().toString();
			this.tableTypeName = tableTypeService.getTableTypeName(tableTypeId);
			this.tableTypeNumber = restaurantTable.getTableTypeNumber();
			this.restaurantName = restaurantService.getRestaurantName(restaurantId);
			
		} catch (HibernateException e) {
			e.printStackTrace();
		}
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
