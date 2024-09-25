package com.TogoOrder.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.TogoOrder.bean.TogoItemBean;

@Repository
@Transactional
public class TogoItemDaoImpl implements TogoItemDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
//	public TogoItemDaoImpl() {
//		SessionFactory factory = HibernateUtil.getSessionFactory();
//		session = factory.getCurrentSession();
//	}

	@Override
	public TogoItemBean addTogoItem(TogoItemBean togoItem) {
		Session session = sessionFactory.getCurrentSession();
		session.persist(togoItem);
		return togoItem;
	}

	@Override
	public boolean deleteTogoItemById(Integer togoId) {
		Session session = sessionFactory.getCurrentSession();
		TogoItemBean togoItem = session.get(TogoItemBean.class, togoId);
		if (togoItem != null) {
			session.remove(togoItem);
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteTogoItemByTogoIdFoodId(Integer togoId, Integer foodId) {
		Session session = sessionFactory.getCurrentSession();
		 String hql = "delete from TogoItemBean where togoId = :togoId and foodId = :foodId";
		 Query<TogoItemBean> query = session.createQuery(hql);
		 query.setParameter("togoId", togoId);
		 query.setParameter("foodId", foodId);
		 int result = query.executeUpdate();
		return result > 0;
	}

	@Override
	public TogoItemBean updateTogoItemByTogoIdFoodId(TogoItemBean togoItem) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "from TogoItemBean where togoId = :togoId and foodId = :foodId";
		Query<TogoItemBean> query = session.createQuery(hql, TogoItemBean.class);
		query.setParameter("togoId", togoItem.getTogoId());
		query.setParameter("foodId", togoItem.getFoodId());
		TogoItemBean updateTogoItem = query.uniqueResult();
		if (updateTogoItem != null) {
			updateTogoItem.setTogoId(togoItem.getTogoId());
			updateTogoItem.setFoodId(togoItem.getFoodId());
			updateTogoItem.setFoodName(togoItem.getFoodName());
			updateTogoItem.setFoodPrice(togoItem.getFoodPrice());
			updateTogoItem.setAmount(togoItem.getAmount());
			updateTogoItem.setTogoItemPrice(togoItem.getTogoItemPrice());
			return updateTogoItem;
		}
		return null;
	}

	@Override
	public TogoItemBean getTogoItemByTogoIdFoodId(Integer togoId, Integer foodId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "from TogoItemBean where togoId = :togoId and foodId = :foodId";
		Query<TogoItemBean> query = session.createQuery(hql, TogoItemBean.class);
		query.setParameter("togoId", togoId);
		query.setParameter("foodId", foodId);
		return query.uniqueResult();
	}

	@Override
	public List<TogoItemBean> getTogoItemByTogoId(Integer togoId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "from TogoItemBean where togoId = :togoId";
		Query<TogoItemBean> query = session.createQuery(hql, TogoItemBean.class);
		query.setParameter("togoId", togoId);
		return query.list();
	}

	@Override
	public List<TogoItemBean> getAllTogoItem() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "from TogoItemBean";
		Query<TogoItemBean> query = session.createQuery(hql, TogoItemBean.class);
		return query.list();
	}

}
