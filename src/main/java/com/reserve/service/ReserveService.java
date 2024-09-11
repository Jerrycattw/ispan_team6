package com.reserve.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.Session;

import com.reserve.bean.Reserve;
import com.reserve.bean.ReserveCheckBean;
import com.reserve.dao.ReserveDao;

public class ReserveService {

	private ReserveDao reserveDao;
	
	public ReserveService(Session session) {
		reserveDao = new ReserveDao(session);
	}
	
	//新增單筆訂位
	public Reserve insert(Reserve reserve) {
		return reserveDao.insert(reserve);
	}
	
	//查詢單筆訂位
	public Reserve selectById(String reserveId) {
		return reserveDao.selectById(reserveId);
	}
	
	//查詢所有訂位
	public List<Reserve> selectAll() {
		return reserveDao.selectAll();
	}
	
	//更新訂位
	public Reserve update(Reserve reserve) {
		return reserveDao.update(reserve);
	}
	
	//刪除訂位
	public boolean delete(String reserveId) {
		return reserveDao.deleteById(reserveId);
	}
	
	//查詢訂位by可變條件
	public List<Reserve> getReserveByCriteria(String memberName, String phone, String memberId,
			String restaurantId, String tableTypeId, String restaurantAddress, LocalDateTime reserveTimeStart,
			LocalDateTime reserveTimeEnd) {
		
		return reserveDao.getReserveByCriteria(memberName, phone, memberId, restaurantId, tableTypeId, restaurantAddress, reserveTimeStart, reserveTimeEnd);
		
	}
	
	//查詢可預訂時間
	public List<ReserveCheckBean> getReserveCheck(String restaurantId, String tableTypeId, LocalDate checkDate) {
		return reserveDao.getReserveCheck(restaurantId, tableTypeId, checkDate);
	}
	
	
	
	public String getTableTypeId(int seats) {
		if (seats == 1 || seats == 2) return "A";
	    if (seats == 3 || seats == 4) return "B";
	    if (seats == 5 || seats == 6) return "C";
	    if (seats == 7 || seats == 8) return "D";
	    if (seats == 9 || seats == 10) return "E";
	    else return null;
	}
	
	
	

}
