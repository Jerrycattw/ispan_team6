package com.coupon.dao;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.coupon.bean.CouponBean;


public class CouponDao2 {
	private Session session;

	public CouponDao2(Session session) {
		this.session = session;
	}

	// 查詢全部
	public List<CouponBean> getAllCoupon() {
		Query<CouponBean> query = session.createQuery("from CouponBean", CouponBean.class);
		return query.list();
	}

	// 查詢Coupon BY ID
	public CouponBean getCouponById(String couponId) {
		return session.get(CouponBean.class, couponId);
	}

	// 取得productType選項
	public List<String> getProductTypeTagsOption() {
		String hql = "SELECT p.productTypeName FROM ProductType p";
		Query<String> query = session.createQuery(hql, String.class);
		return query.list();
	}

	// 取得togoType選項
	public List<String> getTogoTypeTagsOption() {
		String hql = "SELECT DISTINCT m.foodKind FROM MenuBean m";
		Query<String> query = session.createQuery(hql, String.class);
		return query.list();
	}

	// 新增coupon
	public void insertCoupon(CouponBean couponBean) {
		session.persist(couponBean);
	}

	// 刪除Coupon
	public boolean deleteCoupon(int couponId) {
		CouponBean couponBean = session.get(CouponBean.class, couponId);
		if (couponBean != null) {
			session.remove(couponBean);
			return true;
		}
		return false;
	}

	// 修改Coupon
	public void updateCoupon(CouponBean couponBean) {
		CouponBean updateBean = session.get(CouponBean.class, couponBean.getCouponId());
		if (updateBean != null) {
			session.merge(couponBean);
		}	
	}
	
}
