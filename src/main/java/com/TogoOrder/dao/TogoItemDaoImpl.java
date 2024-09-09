package com.TogoOrder.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.TogoOrder.bean.TogoItemBean;
import com.util.HibernateUtil;

public class TogoItemDaoImpl implements TogoItemDao {
	private Session session;
	
	public TogoItemDaoImpl() {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		session = factory.getCurrentSession();
	}
	
	@Override
	public TogoItemBean addTogoItem(TogoItemBean togoItem) {
		session.persist(togoItem);
		return togoItem;
	}

	@Override
	public boolean deleteTogoItemById(Integer togoId) {
		TogoItemBean togoItem = session.get(TogoItemBean.class, togoId);
		if (togoItem != null) {
			session.remove(togoItem);
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteTogoItemByTogoIdFoodId(Integer togoId, Integer foodId) {
		 String hql = "delete from TogoItemBean where togoId = :togoId and foodId = :foodId";
		 Query query = session.createQuery(hql);
		 query.setParameter("togoId", togoId);
		 query.setParameter("foodId", foodId);
		 int result = query.executeUpdate();
		return result > 0;
	}

	@Override
	public TogoItemBean updateTogoItemByTogoIdFoodId(TogoItemBean togoItem) {
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
		String hql = "from TogoItemBean where togoId = :togoId and foodId = :foodId";
		Query<TogoItemBean> query = session.createQuery(hql, TogoItemBean.class);
		query.setParameter("togoId", togoId);
		query.setParameter("foodId", foodId);
		return query.uniqueResult();
	}

	@Override
	public List<TogoItemBean> getTogoItemByTogoId(Integer togoId) {
		String hql = "from TogoItemBean where togoId = :togoId";
		Query<TogoItemBean> query = session.createQuery(hql, TogoItemBean.class);
		query.setParameter("togoId", togoId);
		return query.list();
	}

	@Override
	public List<TogoItemBean> getAllTogoItem() {
		String hql = "from TogoItemBean";
		Query<TogoItemBean> query = session.createQuery(hql, TogoItemBean.class);
		return query.list();
	}

}
