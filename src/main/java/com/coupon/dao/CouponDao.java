package com.coupon.dao;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.coupon.bean.CouponBean;
import com.coupon.bean.TagBean;
import com.coupon.bean.TagId;
import com.util.DateUtils;


public class CouponDao {
	private Session session;

	public CouponDao(Session session) {
		this.session = session;
	}

	// 查詢全部
	public List<CouponBean> getAllCoupon() {
		// "SELECT * FROM coupon"
		Query<CouponBean> query = session.createQuery("from CouponBean", CouponBean.class);
		return query.list();
	}

	// 查詢Coupon BY ID //本來回傳List<CouponBean>
	public CouponBean getOneCoupon(String couponId) {
		// "SELECT * FROM coupon WHERE coupon_id=?"
		return session.get(CouponBean.class, couponId);
	}

	// 查詢所有couponWithTags
	public List<CouponBean> getAllCouponWithTags() {
		// "SELECT *\r\n"
		// + "FROM coupon c\r\n"
		// + "LEFT JOIN coupon_tag ct ON c.coupon_id=ct.coupon_id"
		String hql = "SELECT c, t FROM CouponBean c LEFT JOIN c.tags t";
		Query<CouponBean> query = session.createQuery(hql, CouponBean.class);
		return query.list();
	}

	// 查詢couponWithTags BY couponID
	public CouponBean getCouponWithTags(int couponId) {
		// "SELECT *\r\n"
		// + "FROM coupon c\r\n"
		// + "LEFT JOIN coupon_tag ct ON c.coupon_id=ct.coupon_id\r\n"
		// + "WHERE c.coupon_id=?"
		String hql = "SELECT c, t FROM CouponBean c LEFT JOIN c.tags t WHERE c.couponId=:couponId";
		Query<Object[]> query = session.createQuery(hql, Object[].class);
	    query.setParameter("couponId", couponId);

	    List<Object[]> results = query.getResultList();

	    // Initialize a CouponBean object and a map for tags
	    CouponBean coupon = null;
	    Map<Integer, TagBean> tagMap = new HashMap<>();

	    // Process results to populate CouponBean and its tags
	    for (Object[] result : results) {
	        if (coupon == null) {
	            coupon = (CouponBean) result[0];
	            coupon.setTags(new ArrayList<>()); // Initialize tags list
	        }
	        
	        TagBean tag = (TagBean) result[1];
	        if (tag != null) {
	            tagMap.put(tag.getTagId().getTagName().hashCode(), tag); // Use a unique identifier
	        }
	    }

	    if (coupon != null) {
	        coupon.setTags(new ArrayList<>(tagMap.values())); // Set the tags list
	    }

	    return coupon;
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

	// 新增Tags dao
	public void insertCouponTags(TagId tagId, String tagType) {
		// "INSERT INTO coupon_tag(coupon_id,tag_name, tag_type) VALUES(?,?,?)"
		TagBean tag = new TagBean(tagId, tagType);
		System.out.println("==="+tag);
		session.save(tag);

	}

	// 新增coupon(並返回自動生成couponID)
	public int insertCoupon(String couponCode, String couponDescription, LocalDate couponStartDate,
			LocalDate couponEndDate, int maxCoupon, int perMaxCoupon, String couponStatus, String rulesDescription,
			String discountType, int discount, int minOrderDiscount, int maxDiscount) {
		// "INSERT INTO coupon
		// (coupon_code,coupon_description,coupon_start_date,coupon_end_date,max_coupon,per_max_coupon,coupon_status,rules_description,"
		// + "discount_type,discount,min_order_amount,max_discount)
		// VALUES(?,?,?,?,?,?,?,?,?,?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS

		CouponBean couponBean = new CouponBean(couponCode, couponDescription, couponStartDate, couponEndDate, maxCoupon,
				perMaxCoupon, couponStatus, rulesDescription, discountType, discount, minOrderDiscount, maxDiscount);
		int generatedId = (int) session.save(couponBean);// 執行並返回自動增長ID
		return generatedId;
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

	// 刪除Tags
	public void deleteTags(int couponId) {
		// "DELETE FROM coupon_tag WHERE coupon_id=?"
		String hql = "DELETE FROM TagBean t WHERE t.tagId.couponId = :couponId";
		Query<TagBean> query = session.createQuery(hql);
		query.setParameter("couponId", couponId);
		query.executeUpdate();

	}

	// 修改Coupon
	public void updateCoupon(int couponId, String couponCode, String couponDescription, LocalDate couponStartDate,
			LocalDate couponEndDate, int maxCoupon, int perMaxCoupon, String couponStatus, String rulesDescription,
			String discountType, int discount, int minOrderDiscount, int maxDiscount) {
//		"UPDATE coupon SET coupon_code=?,coupon_description=?,coupon_start_date=?,coupon_end_date=?,max_coupon=?,"
//				+ "per_max_coupon=?,coupon_status=?,rules_description=?,discount_type=?,discount=?,min_order_amount=?,max_discount=? WHERE coupon_id=?"
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
		}

	}

	// 查詢所有couponWithTagsAndReceived ------------------ok-----------------
	public List<CouponBean> getAllCouponWithTagsAndReceived() {
		List<CouponBean> coupons = new ArrayList<>();
		// "WITH coupon_summary AS (\r\n"
		// + "SELECT coupon_id,count(member_id) AS received_amount\r\n"
		// + "FROM member_coupon\r\n"
		// + "GROUP BY coupon_id\r\n"
		// + ")\r\n"
		// + "SELECT *\r\n"
		// + "FROM coupon c\r\n"
		// + "LEFT JOIN coupon_summary cs ON c.coupon_id=cs.coupon_id\r\n"
		// + "LEFT JOIN coupon_tag ct ON c.coupon_id=ct.coupon_id";
		 String jpql = "SELECT c, cs.receivedAmount, ct " +
		                 "FROM CouponBean c " +
		                 "LEFT JOIN (SELECT couponId AS couponId, COUNT(memberId) AS receivedAmount " +
		                            "FROM CouponMemberBean " +
		                            "GROUP BY couponId) cs ON c.couponId = cs.couponId " +
		                 "LEFT JOIN TagBean ct ON c.couponId = ct.tagId.couponId";
		// 創建查詢
		Query<Object[]> query = session.createQuery(jpql, Object[].class);
		List<Object[]> results = query.getResultList();

		// 處理結果
		Map<Integer, CouponBean> mapCoupon = new HashMap<>();
		for (Object[] result : results) {
			CouponBean coupon = (CouponBean) result[0];
			Long receivedAmount = (Long) result[1];
			TagBean tag = (TagBean) result[2];

			// 如果 CouponBean 不在 Map 中，則添加它
			if (!mapCoupon.containsKey(coupon.getCouponId())) {
				coupon.setReceivedAmount(receivedAmount != null ? receivedAmount.intValue() : 0);
				coupon.setTags(new ArrayList<>());
				coupons.add(coupon);
				mapCoupon.put(coupon.getCouponId(), coupon);
			}

			// 添加 TagBean 到 CouponBean 的 tags 列表中
			if (tag != null) {
				mapCoupon.get(coupon.getCouponId()).getTags().add(tag);
			}
		}
		return coupons;
	}

	// 查詢所有couponWithTagsAndReceived 模糊比對
	public List<CouponBean> getAllCouponWithTagsAndReceived(String KeyWord){
		//"WITH coupon_summary AS (\r\n"
		//		+ "SELECT coupon_id,count(member_id) AS received_amount\r\n"
		//		+ "FROM member_coupon\r\n"
		//		+ "GROUP BY coupon_id\r\n"
		//		+ ")\r\n"
		//		+ "SELECT *\r\n"
		//		+ "FROM coupon c\r\n"
		//		+ "LEFT JOIN coupon_summary cs ON c.coupon_id=cs.coupon_id\r\n"
		//		+ "LEFT JOIN coupon_tag ct ON c.coupon_id=ct.coupon_id\r\n"
		//		+ "WHERE c.coupon_id LIKE ? OR c.coupon_code  LIKE ? OR c.coupon_description LIKE ? "
		//		+ "OR c.coupon_status LIKE ? OR c.rules_description  LIKE ? OR c.discount_type  LIKE ?"
		//		+ "OR c.coupon_start_date LIKE ? OR c.coupon_end_date LIKE ?"
		String jpql = "SELECT c, cs.receivedAmount, ct " +
                "FROM CouponBean c " +
                "LEFT JOIN (SELECT couponId AS couponId, COUNT(memberId) AS receivedAmount " +
                           "FROM CouponMemberBean cm " +
                           "GROUP BY couponId) cs ON c.couponId = cs.couponId " +
                "LEFT JOIN TagBean ct ON c.couponId = ct.tagId.couponId " +
                "WHERE c.couponId = :couponId OR c.couponCode LIKE :couponCode OR c.couponDescription LIKE :couponDescription OR c.couponStatus LIKE :couponStatus OR c.rulesDescription LIKE :rulesDescription  " +
                "OR c.discountType LIKE :discountType OR c.couponStartDate = :couponStartDate OR c.couponEndDate = :couponEndDate";
		// 創建查詢
		Query<Object[]> query = session.createQuery(jpql, Object[].class);
//		query.setParameter("couponId","%"+KeyWord+"%");
		Integer intKeyWord;
		try {
			intKeyWord=Integer.parseInt(KeyWord);			
		} catch (Exception e) {
			intKeyWord=null;
		}
		query.setParameter("couponId",intKeyWord);
		query.setParameter("couponCode","%"+KeyWord+"%");
		query.setParameter("couponDescription","%"+KeyWord+"%");
		query.setParameter("couponStatus","%"+KeyWord+"%");
		query.setParameter("rulesDescription","%"+KeyWord+"%");
		query.setParameter("discountType","%"+KeyWord+"%");
//		query.setParameter("couponStartDate","%"+KeyWord+"%");
//		query.setParameter("couponEndDate",KeyWord);
		LocalDate LocalDateKeyword = DateUtils.convertLocalDate(KeyWord);
		query.setParameter("couponStartDate",LocalDateKeyword);
		query.setParameter("couponEndDate",LocalDateKeyword);
		
		System.out.println(LocalDateKeyword);
		
		List<Object[]> results = query.getResultList();
		
		// 處理結果
		List<CouponBean> coupons = new ArrayList<>();
		Map<Integer, CouponBean> mapCoupon = new HashMap<>();
		for (Object[] result : results) {
			CouponBean coupon = (CouponBean) result[0];
			Long receivedAmount = (Long) result[1];
			TagBean tag = (TagBean) result[2];
		
			// 如果 CouponBean 不在 Map 中，則添加它
			if (!mapCoupon.containsKey(coupon.getCouponId())) {
				coupon.setReceivedAmount(receivedAmount != null ? receivedAmount.intValue() : 0);
				coupon.setTags(new ArrayList<>());
				coupons.add(coupon);
				mapCoupon.put(coupon.getCouponId(), coupon);
			}
		
			// 添加 TagBean 到 CouponBean 的 tags 列表中
			if (tag != null) {
				mapCoupon.get(coupon.getCouponId()).getTags().add(tag);
			}
		}
		return coupons;
		
	}
	
//	//查詢可發送優惠券
//	public List<CouponDistributeBean> getDistributeOption() {
//		Connection connection = JDBCutils.getConnection();
//		PreparedStatement stmt = null;
//		ResultSet rs = null;
//		List<CouponDistributeBean> couponDistributes = new ArrayList<>();
//		try {
//			stmt = connection.prepareStatement("WITH coupon_summary AS (\r\n"
//					+ "SELECT coupon_id,count(member_id) AS received_amount\r\n"
//					+ "FROM member_coupon\r\n"
//					+ "GROUP BY coupon_id\r\n"
//					+ ")\r\n"
//					+ "SELECT *\r\n"
//					+ "FROM coupon c\r\n"
//					+ "LEFT JOIN coupon_summary cs ON c.coupon_id=cs.coupon_id\r\n"
//					+ "");
//			rs = stmt.executeQuery();
//			CouponDistributeBean couponDistribute = null;
//			while (rs.next()) {
//				couponDistribute = new CouponDistributeBean();
//				couponDistribute.setCouponId(rs.getInt("coupon_id"));	
//				
//				String selectOption=rs.getString("coupon_code")+", "+rs.getString("coupon_description");
//				couponDistribute.setSelectOption(selectOption);
//				couponDistribute.setReceivedAmount(rs.getInt("received_amount"));
//				couponDistribute.setMaxCoupon(rs.getInt("max_coupon"));
////				coupon = new CouponBean();
////				coupon.setCouponId(rs.getInt("coupon_id"));
////				coupon.setCouponCode(rs.getString("coupon_code"));
////				coupon.setCouponDescription(rs.getString("coupon_description"));
////				coupon.setCouponStartDate(rs.getDate("coupon_start_date").toLocalDate());
////				coupon.setCouponEndDate(rs.getDate("coupon_end_date").toLocalDate());
////				coupon.setMaxCoupon(rs.getInt("max_coupon"));
////				coupon.setPerMaxCoupon(rs.getInt("per_max_coupon"));
////				coupon.setCouponStatus(rs.getString("coupon_status"));
////				coupon.setRulesDescription(rs.getString("rules_description"));
////				coupon.setDiscountType(rs.getString("discount_type"));
////				coupon.setDiscount(rs.getInt("discount"));
////				coupon.setMinOrderDiscount(rs.getInt("min_order_amount"));
////				coupon.setMaxDiscount(rs.getInt("max_discount"));
//				couponDistributes.add(couponDistribute);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			JDBCutils.closeResource(connection, stmt, rs);
//		}
//		return couponDistributes;
//	}
//	
//	//查詢 CouponMember BY coupon member ID
//	public CouponMemberBean  sumMemberCoupon(String memberId,String couponId) {
//		Connection connection = JDBCutils.getConnection();
//		PreparedStatement stmt = null;
//		ResultSet rs = null;
//		CouponMemberBean CouponMember = null;
//		try {
//			stmt = connection.prepareStatement("SELECT coupon_id,member_id,count(status) AS received_amount \r\n"
//					+ "FROM member_coupon\r\n"
//					+ "GROUP BY coupon_id,member_id\r\n"
//					+ "HAVING member_id=? AND coupon_id=?");
//			stmt.setString(1, memberId);
//			stmt.setString(2, couponId);
//			rs = stmt.executeQuery();
//
//			while (rs.next()) {
//				CouponMember = new CouponMemberBean();
//				CouponMember.setCouponId(rs.getInt("coupon_id"));	
//				CouponMember.setMemberId(rs.getInt("member_id"));	
//				CouponMember.setReceivedAmount(rs.getInt("received_amount"));
//
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			JDBCutils.closeResource(connection, stmt, rs);
//		}
//		return CouponMember;
//	}
//	
//	//發放優惠券
//	public CouponMemberBean  insertMemberCoupon(String memberId,String couponId, int status) {
//		Connection connection = JDBCutils.getConnection();
//		PreparedStatement stmt = null;
//		ResultSet rs = null;
//		CouponMemberBean CouponMember = null;
//		try {
//			stmt = connection.prepareStatement("Insert INTO member_coupon(member_id,coupon_id,status) VALUES(?,?,?)");
//			stmt.setString(1, memberId);
//			stmt.setString(2, couponId);
//			stmt.setInt(3, status);
//			stmt.execute();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			JDBCutils.closeResource(connection, stmt, rs);
//		}
//		return CouponMember;
//	}
}
