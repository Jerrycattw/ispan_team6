package com.reserve.service;

import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reserve.bean.Restaurant;
import com.reserve.dao.RestaurantDao;


@Service
@Transactional
public class RestaurantService {
	
	@Autowired
	private RestaurantDao restaurantDao;
	
	//新增單筆餐廳
	public Restaurant insert(Restaurant restaurant) {
		return restaurantDao.insert(restaurant);
	}
	
	//查詢單筆餐廳
	public Restaurant selectById(String restaurantId) {
		return restaurantDao.selectById(restaurantId);
	}
	
	//查詢所有餐廳
	public List<Restaurant> selectAll() {
		return restaurantDao.selectAll();
	}
	
	//更新餐廳
	public Restaurant update(Restaurant restaurant) {
		return restaurantDao.update(restaurant);
	}
	
	//刪除餐廳
	public boolean delete(String restaurantId) {
		return restaurantDao.deleteById(restaurantId);
	}
	
	//查詢餐廳by可變條件
	public List<Restaurant> selectList(String restaurantName, String restaurantAddress, String restaurantStatus) {
		return restaurantDao.selectListByCriteria(restaurantName,restaurantAddress,restaurantStatus);
	}
	
	//查詢餐廳編號by餐廳名稱
	public String getRestaurantId(String restaurantName) {
		return restaurantDao.getRestaurantId(restaurantName);
	}
	
	// 查詢餐廳名稱由餐廳編號
	public String getRestaurantName(String restaurantId) {
		return restaurantDao.getRestaurantName(restaurantId);
	}
	
	//查詢所有餐廳名稱
	public List<String> getAllRestaurantName() {
		return restaurantDao.getAllRestaurantName();
	}
	
	

}
