package com.reserve.service;

import java.util.List;

import org.hibernate.Session;

import com.reserve.bean.RestaurantTable;
import com.reserve.bean.RestaurantTableId;
import com.reserve.dao.RestaurantTableDao;

public class RestaurantTableService {

	private RestaurantTableDao restaurantTableDao;
	
	public RestaurantTableService(Session session) {
		restaurantTableDao = new RestaurantTableDao(session);
	}
	
	//新增單筆餐廳桌位種類
	public RestaurantTable insert(RestaurantTable restaurantTable) {
		return restaurantTableDao.insert(restaurantTable);
	}
	
	//查詢單筆餐廳桌位種類
	public RestaurantTable selectById(RestaurantTableId restaurantTableId) {
		return restaurantTableDao.selectById(restaurantTableId);
	}
	
	//查詢所有餐廳桌位種類by餐廳Id
	public List<RestaurantTable> selectAll(String restaurantId) {
		return restaurantTableDao.selectAll(restaurantId);
	}
	
	//更新餐廳桌位種類
	public RestaurantTable update(RestaurantTable restaurantTable) {
		return restaurantTableDao.update(restaurantTable);
	}
	
	//刪除餐廳桌位種類
	public boolean delete(RestaurantTableId restaurantTableId) {
		return restaurantTableDao.deleteById(restaurantTableId);
	}
	
	
	
	

}
