package com.shopping.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.members.bean.Member;
import com.shopping.bean.ShoppingBean;
import com.shopping.bean.ShoppingBean;

@Repository
@Transactional
public class ShoppingDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	// 新增
	public ShoppingBean addOrder(ShoppingBean bean) {
		Session session = sessionFactory.getCurrentSession();
		if (bean != null) {
			session.persist(bean);
			return bean;
		}
		return null;
	}

	// 新增總金額
	public void addTotal(Integer shoppingItemQuantity, Integer productId, Integer shoppingId) {
		Session session = sessionFactory.getCurrentSession();

		String selectPriceHql = "SELECT p.productPrice FROM ProductBean p WHERE p.productId = :productId";
		Query<Integer> selectPriceQuery = session.createQuery(selectPriceHql, Integer.class);
		selectPriceQuery.setParameter("productId", productId);
		Integer productPrice = selectPriceQuery.uniqueResult();

		int itemPrice = shoppingItemQuantity * productPrice;

		String updateItemHql = "UPDATE ItemBean si SET si.shoppingItemPrice = :itemPrice, si.shoppingItemQuantity = :quantity WHERE si.product.productId = :productId AND si.shoppingBean.shoppingId = :shoppingId";
		Query updateItemQuery = session.createQuery(updateItemHql);
		updateItemQuery.setParameter("itemPrice", itemPrice);
		updateItemQuery.setParameter("quantity", shoppingItemQuantity);
		updateItemQuery.setParameter("productId", productId);
		updateItemQuery.setParameter("shoppingId", shoppingId);
		updateItemQuery.executeUpdate();

		String updateOrderHql = "UPDATE ShoppingBean so SET so.shoppingTotal = (SELECT COALESCE(SUM(si.shoppingItemPrice), 0) FROM ItemBean si WHERE si.shoppingBean.shoppingId = :shoppingId) WHERE so.shoppingId = :shoppingId";
		Query updateOrderQuery = session.createQuery(updateOrderHql);
		updateOrderQuery.setParameter("shoppingId", shoppingId);
		updateOrderQuery.executeUpdate();

	}

	// 刪除
	public boolean deleteShopping(Integer shoppingId) {
		Session session = sessionFactory.getCurrentSession();
		ShoppingBean resultBean = session.get(ShoppingBean.class, shoppingId);
		if (resultBean != null) {
			session.remove(resultBean);
			return true;
		}
		return false;
	}

	// 删除訂單項目並更新訂單總金額
	public void deleteItemTotal(Integer shoppingItemQuantity, Integer productId, Integer shoppingId) {

		Session session = sessionFactory.getCurrentSession();
		String selectPriceHql = "SELECT p.productPrice FROM ProductBean p WHERE p.productId = :productId";
		Query<Integer> selectPriceQuery = session.createQuery(selectPriceHql, Integer.class);
		selectPriceQuery.setParameter("productId", productId);
		Integer productPrice = selectPriceQuery.uniqueResult();

		Integer itemPrice = shoppingItemQuantity * productPrice;

		String deleteItemHql = "DELETE FROM ItemBean i WHERE i.product.productId = :productId AND i.shopping.shoppingId = :shoppingId";
		Query<?> deleteItemQuery = session.createQuery(deleteItemHql);
		deleteItemQuery.setParameter("productId", productId);
		deleteItemQuery.setParameter("shoppingId", shoppingId);
		deleteItemQuery.executeUpdate();
		String updateOrderHql = "UPDATE ShoppingBean s SET s.shoppingTotal = (SELECT COALESCE(SUM(i.shoppingItemPrice * i.shoppingItemQuantity), 0) FROM ItemBean i WHERE i.shopping.shoppingId = :shoppingId) WHERE s.shoppingId = :shoppingId";
		Query<?> updateOrderQuery = session.createQuery(updateOrderHql);
		updateOrderQuery.setParameter("shoppingId", shoppingId);
		updateOrderQuery.executeUpdate();

	}

	// 更新
	public ShoppingBean updateShopping(ShoppingBean shoppingBean) {
		Session session = sessionFactory.getCurrentSession();
		ShoppingBean isExist = session.get(ShoppingBean.class, shoppingBean.getShoppingId());
		if (isExist != null) {
			session.merge(shoppingBean);
			return shoppingBean;
		}
		return null;
	}

	// 更新訂單項目並更新訂單總金額
	public void updateTotal(Integer shoppingItemQuantity, Integer productId, Integer shoppingId) {

		Session session = sessionFactory.getCurrentSession();
		String selectPriceHql = "SELECT p.productPrice FROM ProductBean p WHERE p.productId = :productId";
		Query<Integer> selectPriceQuery = session.createQuery(selectPriceHql, Integer.class);
		selectPriceQuery.setParameter("productId", productId);
		Integer productPrice = selectPriceQuery.uniqueResult();

		int itemPrice = shoppingItemQuantity * productPrice;

		String updateItemHql = "UPDATE ShoppingItem si SET si.quantity = :quantity, si.price = :price WHERE si.product.productId = :productId AND si.shopping.shoppingId = :shoppingId";
		Query updateItemQuery = session.createQuery(updateItemHql);
		updateItemQuery.setParameter("quantity", shoppingItemQuantity);
		updateItemQuery.setParameter("price", itemPrice);
		updateItemQuery.setParameter("productId", productId);
		updateItemQuery.setParameter("shoppingId", shoppingId);
		updateItemQuery.executeUpdate();

		String updateOrderHql = "UPDATE ShoppingOrder so SET so.total = (SELECT COALESCE(SUM(si.price), 0) FROM ShoppingItem si WHERE si.shopping.shoppingId = :shoppingId) WHERE so.shoppingId = :shoppingId";
		Query updateOrderQuery = session.createQuery(updateOrderHql);
		updateOrderQuery.setParameter("shoppingId", shoppingId);
		updateOrderQuery.executeUpdate();
	}

	public ShoppingBean updateDataShopping(ShoppingBean shoppingBean) {
		Session session = sessionFactory.getCurrentSession();
		ShoppingBean isExist = session.get(ShoppingBean.class, shoppingBean.getShoppingId());
		if (isExist != null) {
			session.merge(shoppingBean);
			return shoppingBean;
		}
		return null;
	}

	// 查詢全部
	public List<ShoppingBean> searchAllShopping() {
		Session session = sessionFactory.getCurrentSession();
		Query<ShoppingBean> query = session.createQuery("SELECT s FROM ShoppingBean s JOIN FETCH s.member",
				ShoppingBean.class);
		return query.list();
	}

	// 依ID查詢
	public ShoppingBean searchByShoppingId(Integer ShoppingId) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(ShoppingBean.class, ShoppingId);
	}

	// 依會員ID查詢
	public Member searchMemberById(Integer memberId) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(Member.class, memberId);
	}

	// 查詢所有會員
	public List<Member> searchAllMembers() {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("from Member", Member.class).list();
	}

}
