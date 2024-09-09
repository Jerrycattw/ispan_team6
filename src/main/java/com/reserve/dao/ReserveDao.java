package com.reserve.dao;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import com.reserve.bean.Reserve;
import com.reserve.bean.ReserveCheckBean;
import com.util.HibernateUtil;

public class ReserveDao {

	private Session session;

	public ReserveDao(Session session) {
		super();
		this.session = session;
	}

	// 依ID查詢單筆訂位訂單
	public Reserve selectById(String reserveId) {
		return session.get(Reserve.class, reserveId);
	}

	// 查詢所有訂位訂單
	public List<Reserve> selectAll() {
		Query<Reserve> query = session.createQuery("from Reserve", Reserve.class);
		return query.list();
	}

	// 新增單筆訂位訂單
	public Reserve insert(Reserve reserve) {
		if (reserve != null) {
			session.persist(reserve);
			return reserve;
		}
		return null;
	}

	// 更新訂位資料
	public Reserve update(Reserve reserve) {
		Reserve isExist = session.get(Reserve.class, reserve.getReserveId());
		if (isExist != null) {
			session.merge(reserve);
			return reserve;
		}
		return null;
	}

	// 刪除訂位訂單ById
	public boolean deleteById(String reserveId) {
		Reserve reserve = session.get(Reserve.class, reserveId);
		if (reserve != null) {
			session.remove(reserve);
			return true;
		} else {
			return false;
		}

	}
	
	
	public List<ReserveCheckBean> getReserveCheck(String restaurantId, String tableTypeId, LocalDate checkDate) {
	    // 原生 SQL 查詢
	    String sql = "WITH TimeSlots AS ("
	            + "    SELECT restaurant_opentime AS slot_start, "
	            + "           DATEADD(MINUTE, eattime, restaurant_opentime) AS slot_end "
	            + "    FROM restaurant "
	            + "    WHERE restaurant_id = :restaurantId "
	            + "    UNION ALL "
	            + "    SELECT DATEADD(MINUTE, 30, slot_start), "
	            + "           DATEADD(MINUTE, 30, slot_end) "
	            + "    FROM TimeSlots "
	            + "    WHERE slot_start < "
	            + "            (SELECT restaurant_closetime FROM restaurant "
	            + "             WHERE restaurant_id = :restaurantId) "
	            + ") "
	            + "SELECT ts.slot_start, ts.slot_end, rt.table_type_number, COUNT(r.reserve_id) AS reserve_table_number "
	            + "FROM TimeSlots ts "
	            + "LEFT JOIN reserve r ON "
	            + "    r.restaurant_id = :restaurantId "
	            + "    AND r.table_type_id = :tableTypeId "
	            + "    AND CONVERT(TIME, r.reserve_time) < ts.slot_end "
	            + "    AND CONVERT(TIME, r.finished_time) > ts.slot_start "
	            + "    AND CONVERT(DATE, r.reserve_time) = :checkDate "
	            + "LEFT JOIN restaurant_table rt ON "
	            + "    rt.restaurant_id = :restaurantId "
	            + "    AND rt.table_type_id = :tableTypeId "
	            + "GROUP BY ts.slot_start, ts.slot_end, rt.table_type_number "
	            + "ORDER BY ts.slot_start";

	    List<ReserveCheckBean> reservechecks = new ArrayList<>();
	    Session session = null;
	    try {
	        session = HibernateUtil.getSessionFactory().openSession();
	        NativeQuery<Object[]> query = session.createNativeQuery(sql);
	        query.setParameter("restaurantId", restaurantId);
	        query.setParameter("tableTypeId", tableTypeId);
	        query.setParameter("checkDate", checkDate);

	        List<Object[]> results = query.getResultList();

	        for (Object[] result : results) {
	            LocalTime startTime = ((java.sql.Time) result[0]).toLocalTime();
	            LocalTime endTime = ((java.sql.Time) result[1]).toLocalTime();
	            Integer totalTableNumber = ((Number) result[2]).intValue();
	            Integer reservedTableNumber = ((Number) result[3]).intValue();

	            ReserveCheckBean bean = new ReserveCheckBean(startTime, endTime, totalTableNumber, reservedTableNumber);
	            reservechecks.add(bean);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        if (session != null) {
	            session.close();
	        }
	    }

	    return reservechecks;
	}
	
	
	public List<Reserve> getReserveByCriteria(String memberName, String phone, String memberId, String restaurantId,
			String tableTypeId, String restaurantAddress, LocalDateTime reserveTimeStart,
			LocalDateTime reserveTimeEnd) {
		
	    StringBuilder hql = new StringBuilder(
	            "SELECT r FROM Reserve r JOIN r.restaurant rt JOIN r.member m WHERE 1=1");
	    
	    if (memberName != null && !memberName.isEmpty()) {
	        hql.append(" AND m.name LIKE :memberName");
	    }

	    if (phone != null && !phone.isEmpty()) {
	        hql.append(" AND m.phone LIKE :phone");
	    }

	    if (memberId != null && !memberId.isEmpty()) {
	        hql.append(" AND m.id = :memberId");
	    }

	    if (restaurantId != null && !restaurantId.isEmpty()) {
	        hql.append(" AND rt.id = :restaurantId");
	    }

	    if (restaurantAddress != null && !restaurantAddress.isEmpty()) {
	        hql.append(" AND rt.address LIKE :restaurantAddress");
	    }

	    if (tableTypeId != null && !tableTypeId.isEmpty()) {
	        hql.append(" AND r.tableTypeId = :tableTypeId");
	    }

	    if (reserveTimeStart != null) {
	        hql.append(" AND r.reserveTime >= :reserveTimeStart");
	    }

	    if (reserveTimeEnd != null) {
	        hql.append(" AND r.reserveTime <= :reserveTimeEnd");
	    }

	    Query<Reserve> query = session.createQuery(hql.toString(), Reserve.class);

	    if (memberName != null && !memberName.isEmpty()) {
	        query.setParameter("memberName", "%" + memberName + "%");
	    }

	    if (phone != null && !phone.isEmpty()) {
	        query.setParameter("phone", "%" + phone + "%");
	    }

	    if (memberId != null && !memberId.isEmpty()) {
	        query.setParameter("memberId", memberId);
	    }

	    if (restaurantId != null && !restaurantId.isEmpty()) {
	        query.setParameter("restaurantId", restaurantId);
	    }

	    if (restaurantAddress != null && !restaurantAddress.isEmpty()) {
	        query.setParameter("restaurantAddress", "%" + restaurantAddress + "%");
	    }

	    if (tableTypeId != null && !tableTypeId.isEmpty()) {
	        query.setParameter("tableTypeId", tableTypeId);
	    }

	    if (reserveTimeStart != null) {
	        query.setParameter("reserveTimeStart", Timestamp.valueOf(reserveTimeStart));
	    }

	    if (reserveTimeEnd != null) {
	        query.setParameter("reserveTimeEnd", Timestamp.valueOf(reserveTimeEnd));
	    }

	    return query.getResultList();
		
		
		
	}


}
