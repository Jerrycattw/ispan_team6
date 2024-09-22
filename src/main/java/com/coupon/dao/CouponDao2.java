package com.coupon.dao;


import java.time.LocalDate;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.coupon.bean.CouponBean;
import com.util.DateUtils;

@Repository
public class CouponDao2 {
	
	@Autowired
	private SessionFactory sessionFactory;

	// 查詢全部
	public List<CouponBean> getAllCoupon() {
		Session session = sessionFactory.getCurrentSession();
		Query<CouponBean> query = session.createQuery("from CouponBean", CouponBean.class);
		return query.list();
	}

	// 查詢Coupon BY ID
	public CouponBean getCouponById(String couponId) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(CouponBean.class, couponId);
	}

	// 取得productType選項
	public List<String> getProductTypeTagsOption() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "SELECT p.productTypeName FROM ProductType p";
		Query<String> query = session.createQuery(hql, String.class);
		return query.list();
	}

	// 取得togoType選項
	public List<String> getTogoTypeTagsOption() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "SELECT DISTINCT m.foodKind FROM MenuBean m";
		Query<String> query = session.createQuery(hql, String.class);
		return query.list();
	}

	// 新增coupon
	public void insertCoupon(CouponBean couponBean) {
		Session session = sessionFactory.getCurrentSession();
		session.persist(couponBean);
	}

	// 刪除Coupon
	public boolean deleteCoupon(int couponId) {
		Session session = sessionFactory.getCurrentSession();
		CouponBean couponBean = session.get(CouponBean.class, couponId);
		if (couponBean != null) {
			session.remove(couponBean);
			return true;
		}
		return false;
	}

	// 修改Coupon
	public void updateCoupon(CouponBean couponBean) {
		Session session = sessionFactory.getCurrentSession();
		CouponBean updateBean = session.get(CouponBean.class, couponBean.getCouponId());
		if (updateBean != null) {
			session.merge(couponBean);
		}	
	}
	
	//search模糊查詢
	public List<CouponBean> searchCoupons(String keyWord){
		Session session = sessionFactory.getCurrentSession();
		String hql = "SELECT c from CouponBean c WHERE c.couponId = :couponId "
				+ "OR c.couponCode LIKE :couponCode OR c.couponDescription LIKE :couponDescription "
				+ "OR c.couponStatus LIKE :couponStatus OR c.rulesDescription LIKE :rulesDescription "
				+ "OR c.discountType LIKE :discountType OR c.couponStartDate = :couponStartDate OR c.couponEndDate = :couponEndDate ";
		Query<CouponBean> query = session.createQuery(hql,CouponBean.class);
		Integer intKeyWord;
		try {
			intKeyWord=Integer.parseInt(keyWord);			
		} catch (Exception e) {
			intKeyWord=null;
		}
		query.setParameter("couponId",intKeyWord);
		query.setParameter("couponCode","%"+keyWord+"%");
		query.setParameter("couponDescription","%"+keyWord+"%");
		query.setParameter("couponStatus","%"+keyWord+"%");
		query.setParameter("rulesDescription","%"+keyWord+"%");
		query.setParameter("discountType","%"+keyWord+"%");
		LocalDate LocalDateKeyword = DateUtils.convertLocalDate(keyWord);
		query.setParameter("couponStartDate",LocalDateKeyword);
		query.setParameter("couponEndDate",LocalDateKeyword);
		
		return query.list();
		
	}
}
