package com.reserve.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.reserve.bean.Restaurant;

public class RestaurantDao {
	
	private Session session;
	
	//建立RestaurantDao的時候傳入session，不要在dao內建立關閉session及transaction物件
	public RestaurantDao(Session session) {
		this.session = session;
	}
	
	//依ID查詢單筆餐廳
	public Restaurant selectById(String restaurantId) {
		return session.get(Restaurant.class, restaurantId);
	}
	
	//查詢所有餐廳
	public List<Restaurant> selectAll() {
		Query<Restaurant> query = session.createQuery("from Restaurant", Restaurant.class);
		return query.list();
	}
	
	//新增單筆餐廳
	public Restaurant insert(Restaurant restaurant) {
		if(restaurant!=null) {
			session.persist(restaurant);
			return restaurant;
		}
		return null;
	}
	
	//更新餐廳資料
	public Restaurant update(Restaurant restaurant) {
		Restaurant isExist = session.get(Restaurant.class, restaurant.getRestaurantId());
		if(isExist!=null) {
			session.merge(restaurant);
			return restaurant;
		}
		return null;
	}
	
	//刪除餐廳ById
	public boolean deleteById(String restaurantId) {
		Restaurant restaurant = session.get(Restaurant.class, restaurantId);
		if(restaurant!=null) {
			session.remove(restaurant);
			return true;
		}else {
			return false;
		}
		
	}
	
	// 查詢餐廳 by 可變條件 (餐廳名稱、地址、狀態)
	public List<Restaurant> selectListByCriteria(String restaurantName, String restaurantAddress, String restaurantStatus) {
	    StringBuilder hql = new StringBuilder("FROM Restaurant r WHERE 1=1");

	    if (restaurantName != null && !restaurantName.isEmpty()) {
	        hql.append(" AND r.restaurantName LIKE :restaurantName");
	    }
	    if (restaurantAddress != null && !restaurantAddress.isEmpty()) {
	        hql.append(" AND r.restaurantAddress LIKE :restaurantAddress");
	    }
	    if (restaurantStatus != null && !restaurantStatus.isEmpty()) {
	        hql.append(" AND r.restaurantStatus = :restaurantStatus");
	    }

	    Query<Restaurant> query = session.createQuery(hql.toString(), Restaurant.class);

	    if (restaurantName != null && !restaurantName.isEmpty()) {
	        query.setParameter("restaurantName", "%" + restaurantName + "%");
	    }
	    if (restaurantAddress != null && !restaurantAddress.isEmpty()) {
	        query.setParameter("restaurantAddress", "%" + restaurantAddress + "%");
	    }
	    if (restaurantStatus != null && !restaurantStatus.isEmpty()) {
	        query.setParameter("restaurantStatus", Integer.parseInt(restaurantStatus));
	    }

	    return query.list();
	}

	
	
	// 查詢餐廳編號由餐廳名稱
	public String getRestaurantId(String restaurantName) {
	    String hql = "SELECT r.restaurantId FROM Restaurant r WHERE r.restaurantName = :restaurantName";
	    Query<String> query = session.createQuery(hql, String.class);
	    query.setParameter("restaurantName", restaurantName);
	    return query.uniqueResult();
	}
	
	
	// 查詢所有餐廳名稱 (Hibernate 寫法)
	public List<String> getAllRestaurantName() {
	    List<String> restaurantNames = null;
	    try {
	        // 使用 HQL 查詢餐廳名稱
	        Query<String> query = session.createQuery("SELECT DISTINCT r.restaurantName FROM Restaurant r", String.class);
	        restaurantNames = query.list();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return restaurantNames;
	}
	
	
	
}
