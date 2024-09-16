package com.rent.service;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import com.rent.bean.Rent;
import com.rent.bean.RentItem;
import com.rent.dao.RentDao;
import com.rent.dao.RentItemDao;

public class RentService {
	private final RentDao rentDao;
	private RentItemDao rentItemDao;
	private final Session session;

	public RentService(Session session) {
		this.session = session;
		this.rentDao = new RentDao(session);
		this.rentItemDao = new RentItemDao(session);
	}

	public Rent insert(int rentDeposit, Date rentDate, String restaurantId, int memberId, Date dueDate) {
		Rent rent = new Rent(rentDeposit, rentDate, restaurantId, memberId, dueDate, null, 1, "未歸還");
		return rentDao.insert(rent);
	}

	public List<Rent> getAll() {
		return rentDao.getAll();
	}

	public Rent getById(Integer rentId) {
		return rentDao.getById(rentId);
	}

	public Rent update(Integer rentId, Integer rentDeposit, Date rentDate, String restaurantId, Integer memberId,
			Date dueDate, Date returnDate, Integer rentStatus, String rentMemo, String returnRestaurantId) {
		Rent rent = rentDao.getById(rentId);
		if (rent != null) {
			rent.setRentDeposit(rentDeposit);
			rent.setRentDate(rentDate);
			rent.setRestaurantId(restaurantId);
			rent.setMemberId(memberId);
			rent.setDueDate(dueDate);
			rent.setReturnDate(returnDate);
			rent.setRentStatus(rentStatus);
			rent.setRentMemo(rentMemo);
			rent.setReturnRestaurantId(returnRestaurantId);
			rentDao.update(rent);
		}
		return rent;
	}

	public boolean delete(Integer rentId) {
		boolean isDeleted = false;
		Rent rent = rentDao.getById(rentId);
		if (rent != null) {
			isDeleted = rentDao.delete(rent);
		}
		return isDeleted;
	}

	public List<Rent> getOver() {
		return rentDao.getOver();
	}

	public List<Rent> getByMany(Integer rentId, Integer memberId, String restaurantId, Integer rentStatus,
			Date rentDateStart, Date rentDateEnd) {
		return rentDao.search(rentId, memberId, restaurantId, rentStatus, rentDateStart, rentDateEnd);
	}

	public Rent restore(Integer rentId, Date returnDate, String returnRestaurantId) {
		Rent rent = rentDao.getById(rentId);
		if (rent != null) {
			rent.setReturnDate(returnDate);
			rent.setRentStatus(2);
			rent.setRentMemo("歸還");
			rent.setReturnRestaurantId(returnRestaurantId);
			rentDao.update(rent);
		}
		return rent;
	}

	public List<Integer> getRentId(){
		return rentDao.getRentId();
	}
	
}
