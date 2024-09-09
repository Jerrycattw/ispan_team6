package com.TogoOrder.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.TogoOrder.bean.TogoBean;
import com.util.HibernateUtil;

public class TogoDaoImpl implements TogoDao {
	private Session session;
	
	public TogoDaoImpl() {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		session = factory.getCurrentSession();
	}
	
	@Override
	public TogoBean addTogo(TogoBean togo) {
		togo.setTogoCreateTime(LocalDateTime.now());
		session.persist(togo);
		return togo;
	}

	@Override
	public boolean deleteTogoById(Integer togoId) {
		TogoBean togo = session.get(TogoBean.class, togoId);
		if (togo != null) {
			togo.setTogoStatus(3);
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteTogoByStatus(Integer togoStatus) {
		String hql = "delete from TogoBean where togo_status = :togoStatus";
		Query query = session.createQuery(hql);
		query.setParameter("togoStatus", togoStatus);
		// 執行刪除操作，返回受影響的資料筆數
	    int result = query.executeUpdate();
	    // 根據刪除筆數返回結果
		return result>0;
	}

	@Override
	public TogoBean updateTogoById(TogoBean togo) {
		TogoBean updateTogo = session.get(TogoBean.class, togo.getTogoId());
		if (updateTogo != null) {
			updateTogo.setTogoId(togo.getTogoId());
			updateTogo.setMemberId(togo.getMemberId());
			updateTogo.setTgName(togo.getTgName());
			updateTogo.setTgPhone(togo.getTgPhone());
			updateTogo.setTotalPrice(togo.getTotalPrice());
			updateTogo.setRentId(togo.getRentId());
			updateTogo.setTogoStatus(togo.getTogoStatus());
			updateTogo.setRestaurantId(togo.getRestaurantId());
			updateTogo.setTogoMemo(togo.getTogoMemo());
			return updateTogo;
		}
		return null;
	}

	@Override
	public TogoBean updateTogoStatusById(Integer togoId, Integer togoStatus) {
		TogoBean togo = session.get(TogoBean.class, togoId);
		if (togo != null) {
			togo.setTogoStatus(togoStatus);
		}
		return togo;
	}

	@Override
	public TogoBean updateTotalPrice(Integer togoId, Integer totalPrice) {
		TogoBean togo = session.get(TogoBean.class, togoId);
		if (togo != null) {
			togo.setTotalPrice(totalPrice);
		}
		return togo;
	}

	@Override
	public TogoBean getTogoById(Integer togoId) {
		TogoBean togo = session.get(TogoBean.class, togoId);
		return togo;
	}

	@Override
	public List<TogoBean> getAllTogo() {
		String hql = "from TogoBean";
		Query<TogoBean> query = session.createQuery(hql, TogoBean.class);
		return query.list();
	}

	@Override
	public List<TogoBean> getTogoByPhone(String tgPhone) {
		String hql = "from TogoBean where tgPhone like :tgPhone";
		Query<TogoBean> query = session.createQuery(hql, TogoBean.class);
		query.setParameter("tgPhone", "%"+tgPhone+"%");
		return query.list();
	}

}
