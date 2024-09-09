package com.reserve.dao;

import java.util.List;


import org.hibernate.Session;
import org.hibernate.query.Query;

import com.reserve.bean.RestaurantTable;
import com.reserve.bean.RestaurantTableId;

public class RestaurantTableDao {
	
	
	private Session session;
	
	
	public RestaurantTableDao(Session session) {
		this.session = session;
	}
	
	//依ID查詢單筆餐廳桌位種類
	public RestaurantTable selectById(RestaurantTableId restaurantTableId) {
		return session.get(RestaurantTable.class, restaurantTableId);
	}
	
	//查詢所有餐廳桌位種類by餐廳ID
	public List<RestaurantTable> selectAll(String restaurantId) {
		Query<RestaurantTable> query = session.createQuery("from RestaurantTable rt WHERE rt.id.restaurantId =:restaurantId", RestaurantTable.class);
		query.setParameter("restaurantId", restaurantId);
		return query.list();
	}
	
	//新增單筆餐廳桌位種類
	public RestaurantTable insert(RestaurantTable restaurantTable) {
		if(restaurantTable!=null) {
			session.persist(restaurantTable);
			return restaurantTable;
		}
		return null;
	}
	
	//更新餐廳桌位種類資料
	public RestaurantTable update(RestaurantTable restaurantTable) {
		RestaurantTable isExist = session.get(RestaurantTable.class, restaurantTable.getId());
		if(isExist!=null) {
			session.merge(restaurantTable);
			return restaurantTable;
		}
		return null;
	}
	
	//刪除桌位種類ById
	public boolean deleteById(RestaurantTableId restaurantTableId) {
		RestaurantTable restaurantTable = session.get(RestaurantTable.class, restaurantTableId);
		if(restaurantTable!=null) {
			session.remove(restaurantTable);
			return true;
		}else {
			return false;
		}
		
	}
	

}
