package com.reserve.dao;

import java.util.List;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.reserve.bean.RestaurantTable;
import com.reserve.bean.RestaurantTableId;


@Repository
@Transactional
public class RestaurantTableDao {
	
	
//	private Session session;
	@Autowired
	private SessionFactory sessionFactory;
	
	
//	public RestaurantTableDao(Session session) {
//		this.session = session;
//	}
	
	//依ID查詢單筆餐廳桌位種類
	public RestaurantTable selectById(RestaurantTableId restaurantTableId) {
		Session session = sessionFactory.getCurrentSession();

		return session.get(RestaurantTable.class, restaurantTableId);
	}
	
	//查詢所有餐廳桌位種類by餐廳ID
//	public List<RestaurantTable> selectAll(String restaurantId) {
//		Session session = sessionFactory.getCurrentSession();
//
//		Query<RestaurantTable> query = session.createQuery("from RestaurantTable rt WHERE rt.id.restaurantId =:restaurantId ", RestaurantTable.class);
//		query.setParameter("restaurantId", restaurantId);
//		return query.list();
//	}
	
	
	//查詢所有餐廳桌位種類by餐廳ID，並使用 JOIN FETCH 加載關聯屬性
	public List<RestaurantTable> selectAll(String restaurantId) {
	    Session session = sessionFactory.getCurrentSession();

	    String hql = "SELECT rt FROM RestaurantTable rt " +
	                 "JOIN FETCH rt.tableType " +
	                 "JOIN FETCH rt.restaurant " +
	                 "WHERE rt.id.restaurantId = :restaurantId";
	    Query<RestaurantTable> query = session.createQuery(hql, RestaurantTable.class);
	    query.setParameter("restaurantId", restaurantId);
	    return query.list();
	}
	
	
	
	//新增單筆餐廳桌位種類
	public RestaurantTable insert(RestaurantTable restaurantTable) {
		Session session = sessionFactory.getCurrentSession();

		if(restaurantTable!=null) {
			session.persist(restaurantTable);
			return restaurantTable;
		}
		return null;
	}
	
	//更新餐廳桌位種類資料
	public RestaurantTable update(RestaurantTable restaurantTable) {
		Session session = sessionFactory.getCurrentSession();

		RestaurantTable isExist = session.get(RestaurantTable.class, restaurantTable.getId());
		if(isExist!=null) {
			session.merge(restaurantTable);
			return restaurantTable;
		}
		return null;
	}
	
	//刪除桌位種類ById
	public boolean deleteById(RestaurantTableId restaurantTableId) {
		Session session = sessionFactory.getCurrentSession();

		RestaurantTable restaurantTable = session.get(RestaurantTable.class, restaurantTableId);
		if(restaurantTable!=null) {
			session.remove(restaurantTable);
			return true;
		}else {
			return false;
		}
		
	}
	

}
