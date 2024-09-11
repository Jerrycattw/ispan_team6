package com.coupon.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.coupon.bean.CouponBean;
import com.coupon.bean.TagBean;
import com.coupon.bean.TagId;

public class CouponDao2 {
	private Session session;

	public CouponDao2(Session session) {
		this.session = session;
	}

	// 查詢全部
	public List<CouponBean> getAllCoupon() {
		// "SELECT * FROM coupon"
		Query<CouponBean> query = session.createQuery("from CouponBean", CouponBean.class);
		return query.list();
	}

	// 查詢Coupon BY ID
	public CouponBean getCouponById(String couponId) {
		// "SELECT * FROM coupon WHERE coupon_id=?"
		return session.get(CouponBean.class, couponId);
	}

	// 取得productType選項
	public List<String> getProductTypeTagsOption() {
		// "SELECT product_type_name FROM product_type"
		String hql = "SELECT p.product_type_name FROM ProductTypeBean p";
		Query<String> query = session.createQuery(hql, String.class);
		return query.list();
	}

	// 取得togoType選項
	public List<String> getTogoTypeTagsOption() {
		// "SELECT DISTINCT food_kind FROM menu"
		String hql = "SELECT DISTINCT m.foodKind FROM MenuBean m";
		Query<String> query = session.createQuery(hql, String.class);
		return query.list();
	}

	// 新增coupon
	public void insertCoupon(String couponCode, String couponDescription, LocalDate couponStartDate,
			LocalDate couponEndDate, int maxCoupon, int perMaxCoupon, String couponStatus, String rulesDescription,
			String discountType, int discount, int minOrderDiscount, int maxDiscount, String[] productTags,
			String[] togoTags) {

		CouponBean couponBean = new CouponBean(couponCode, couponDescription, couponStartDate, couponEndDate, maxCoupon,
				perMaxCoupon, couponStatus, rulesDescription, discountType, discount, minOrderDiscount, maxDiscount);

		int couponId = (int) session.save(couponBean);

		if (togoTags != null) {
			for (String togoTag : togoTags) {
				TagBean tagBean = new TagBean();
				tagBean.setTagType("togo");
				tagBean.setTagId(new TagId(couponId, togoTag));
				couponBean.getTags().add(tagBean);
			}
		}

		if (productTags != null) {
			for (String productTag : productTags) {
				TagBean tagBean = new TagBean();
				tagBean.setTagType("product");
				tagBean.setTagId(new TagId(couponId, productTag));
				couponBean.getTags().add(tagBean);
			}
		}

//		// 由于持久化操作会更新 CouponBean 的 ID
//		int couponId = couponBean.getCouponId();
//		
//		for (TagBean tagBean : couponBean.getTags()) {
//	        tagBean.setTagId(new TagId(couponId, tagBean.getTagId().getTagName()));  // 使用 couponId 更新 TagId
//	        // 将 TagBean 持久化到数据库
//	        session.persist(tagBean);
//		}
	}

	// 刪除Coupon
	public boolean deleteCoupon(int couponId) {
		// "DELETE FROM coupon WHERE coupon_id=?"
		CouponBean couponBean = session.get(CouponBean.class, couponId);
		if (couponBean != null) {
			session.remove(couponBean);
			return true;
		}
		return false;
	}

	// 修改Coupon
	public void updateCoupon(int couponId, String couponCode, String couponDescription, LocalDate couponStartDate,
			LocalDate couponEndDate, int maxCoupon, int perMaxCoupon, String couponStatus, String rulesDescription,
			String discountType, int discount, int minOrderDiscount, int maxDiscount, String[] productTags,
			String[] togoTags) {
//			"UPDATE coupon SET coupon_code=?,coupon_description=?,coupon_start_date=?,coupon_end_date=?,max_coupon=?,"
//					+ "per_max_coupon=?,coupon_status=?,rules_description=?,discount_type=?,discount=?,min_order_amount=?,max_discount=? WHERE coupon_id=?"
		CouponBean couponBean = session.get(CouponBean.class, couponId);
		if (couponBean != null) {
			couponBean.setCouponCode(couponCode);
			couponBean.setCouponDescription(couponDescription);
			couponBean.setCouponStartDate(couponStartDate);
			couponBean.setCouponEndDate(couponEndDate);
			couponBean.setMaxCoupon(perMaxCoupon);
			couponBean.setPerMaxCoupon(perMaxCoupon);
			couponBean.setCouponStatus(couponStatus);
			couponBean.setRulesDescription(rulesDescription);
			couponBean.setDiscountType(discountType);
			couponBean.setDiscount(maxDiscount);
			couponBean.setMinOrderDiscount(minOrderDiscount);
			couponBean.setMaxDiscount(maxDiscount);

			couponBean.getTags().clear();

			if (togoTags != null) {
				for (String togoTag : togoTags) {
					TagBean tagBean = new TagBean();
					tagBean.setTagType("togo");
					tagBean.setTagId(new TagId(couponId, togoTag));
					tagBean.setCoupon(couponBean);
					couponBean.getTags().add(tagBean);
				}
			}
			if (productTags != null) {
				for (String productTag : productTags) {
					TagBean tagBean = new TagBean();
					tagBean.setTagType("product");
					tagBean.setTagId(new TagId(couponId, productTag));
					tagBean.setCoupon(couponBean);
					couponBean.getTags().add(tagBean);
				}
			}

			session.merge(couponBean);

		}

	}
}
