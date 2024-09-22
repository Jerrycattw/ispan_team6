package com.TogoOrder.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.TogoOrder.bean.MenuBean;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public class MenuDaoImpl implements MenuDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
//	public MenuDaoImpl(Session session) {
//		this.session = session;
//	}

	@Override
	public MenuBean addFood(MenuBean food) {
		Session session = sessionFactory.getCurrentSession();
		session.persist(food);		
		return food;
	}

	@Override
	public boolean deleteFoodById(Integer foodId) {
		Session session = sessionFactory.getCurrentSession();
		//foodId找到要刪除的物件，成功刪除回傳true
		MenuBean food = session.get(MenuBean.class, foodId);
		if (food != null) {
			session.remove(food);
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteFoodByName(String foodName) {
		Session session = sessionFactory.getCurrentSession();
		MenuBean food = session.get(MenuBean.class, foodName);
		if (food != null) {
			session.remove(food);
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteFoodByStatus(Integer foodStatus) {
		Session session = sessionFactory.getCurrentSession();
		// 可能有多筆資料要刪除
		String hql = "delete from MenuBean where food_status = :foodStatus";
		Query<MenuBean> query = session.createQuery(hql);
		query.setParameter("foodStatus", foodStatus);
		// 執行刪除操作，返回受影響的資料筆數
	    int result = query.executeUpdate();
	    // 根據刪除筆數返回結果
		return result>0;
	}

	@Override
	public MenuBean updateFoodStatusById(Integer foodId, Integer foodStatus) {
		Session session = sessionFactory.getCurrentSession();
		MenuBean food = session.get(MenuBean.class, foodId);
		if (food != null) {
			food.setFoodStatus(foodStatus);
		}
		return food;
	}

	@Override
	public MenuBean updateFoodStatusByName(String foodName, Integer foodStatus) {
		Session session = sessionFactory.getCurrentSession();
		MenuBean food = session.get(MenuBean.class, foodName);
		if (food != null) {
			food.setFoodStatus(foodStatus);
		}
		return food;
	}

	@Override
	public MenuBean updateFoodById(MenuBean food) {
		Session session = sessionFactory.getCurrentSession();
		MenuBean updateFood = session.get(MenuBean.class, food.getFoodId());
		if (updateFood != null) {
			updateFood.setFoodId(food.getFoodId());
			updateFood.setFoodName(food.getFoodName());
			updateFood.setFoodPicture(food.getFoodPicture());
			updateFood.setFoodPrice(food.getFoodPrice());
			updateFood.setFoodKind(food.getFoodKind());
			updateFood.setFoodStock(food.getFoodStock());
			updateFood.setFoodDescription(food.getFoodDescription());
			updateFood.setFoodStatus(food.getFoodStatus());
			return updateFood;
		}
		return null;
	}

	@Override
	public MenuBean updateFoodByName(MenuBean food) {
		Session session = sessionFactory.getCurrentSession();
		MenuBean updateFood = session.get(MenuBean.class, food.getFoodName());
		if (updateFood != null) {
			updateFood.setFoodId(food.getFoodId());
			updateFood.setFoodName(food.getFoodName());
			updateFood.setFoodPicture(food.getFoodPicture());
			updateFood.setFoodPrice(food.getFoodPrice());
			updateFood.setFoodKind(food.getFoodKind());
			updateFood.setFoodStock(food.getFoodStock());
			updateFood.setFoodDescription(food.getFoodDescription());
			updateFood.setFoodStatus(food.getFoodStatus());
			return updateFood;
		}
		return null;
	}

	@Override
	public MenuBean getFoodById(Integer foodId) {
		Session session = sessionFactory.getCurrentSession();
		MenuBean food = session.get(MenuBean.class, foodId);
		return food;
	}

	@Override
	public List<MenuBean> getAllFoods() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "from MenuBean";
		Query<MenuBean> query = session.createQuery(hql, MenuBean.class);
		return query.list();
	}

	@Override
	public List<MenuBean> getFoodByName(String foodName) {
		Session session = sessionFactory.getCurrentSession();
	    String hql = "from MenuBean where foodName like :foodName";
	    Query<MenuBean> query = session.createQuery(hql, MenuBean.class);
	    query.setParameter("foodName", "%"+foodName+"%");
	    // 執行查詢並返回結果列表
	    return query.list();
	}
	
	public boolean checkFoodReferences(Integer foodId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "select count(*) from TogoItemBean where foodId = :foodId";
	    Query<Long> query = session.createQuery(hql, Long.class);
	    query.setParameter("foodId", foodId);
	    Long count = query.getSingleResult();
	    return count > 0;
	}

}
