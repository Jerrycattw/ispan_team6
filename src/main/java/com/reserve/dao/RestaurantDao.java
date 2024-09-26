package com.reserve.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.reserve.bean.Restaurant;


@Repository
@Transactional
public class RestaurantDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	//依ID查詢單筆餐廳
	public Restaurant selectById(String restaurantId) {
		Session session = sessionFactory.getCurrentSession();

		return session.get(Restaurant.class, restaurantId);
	}
	
	//查詢所有餐廳
	public List<Restaurant> selectAll() {
		Session session = sessionFactory.getCurrentSession();

		Query<Restaurant> query = session.createQuery("from Restaurant", Restaurant.class);
		return query.list();
	}
	
	//新增單筆餐廳
	public Restaurant insert(Restaurant restaurant) {
		Session session = sessionFactory.getCurrentSession();

		if(restaurant!=null) {
			session.persist(restaurant);
			return restaurant;
		}
		return null;
	}
	
	//更新餐廳資料
	public Restaurant update(Restaurant restaurant) {
		Session session = sessionFactory.getCurrentSession();

		Restaurant isExist = session.get(Restaurant.class, restaurant.getRestaurantId());
		if(isExist!=null) {
			session.merge(restaurant);
			return restaurant;
		}
		return null;
	}
	
	//刪除餐廳ById
	public boolean deleteById(String restaurantId) {
		Session session = sessionFactory.getCurrentSession();

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
	    
		Session session = sessionFactory.getCurrentSession();

		
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
		Session session = sessionFactory.getCurrentSession();

	    String hql = "SELECT r.restaurantId FROM Restaurant r WHERE r.restaurantName = :restaurantName";
	    Query<String> query = session.createQuery(hql, String.class);
	    query.setParameter("restaurantName", restaurantName);
	    return query.uniqueResult();
	}
	
	
	// 查詢餐廳名稱由餐廳編號
	public String getRestaurantName(String restaurantId) {
		Session session = sessionFactory.getCurrentSession();

		String hql = "SELECT r.restaurantName FROM Restaurant r WHERE r.restaurantId = :restaurantId";
		Query<String> query = session.createQuery(hql, String.class);
		query.setParameter("restaurantId", restaurantId);
		return query.uniqueResult();
	}
	
	
	// 查詢所有餐廳名稱 (Hibernate 寫法)
	public List<String> getAllRestaurantName() {
		Session session = sessionFactory.getCurrentSession();

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
