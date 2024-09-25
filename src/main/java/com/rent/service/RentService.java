package com.rent.service;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rent.bean.Rent;
import com.rent.bean.RentItem;
import com.rent.dao.RentDao;
import com.rent.dao.RentItemDao;
@Service
@Transactional
public class RentService {
	@Autowired
	private  RentDao rentDao;
//	private  Session session;

//	public RentService(Session session) {
//		this.session = session;
//		this.rentDao = new RentDao(session);
//		this.rentItemDao = new RentItemDao(session);
//	}

	public Rent insert(Rent rent) {
		return rentDao.insert(rent);
	}

	public List<Rent> getAll() {
		return rentDao.getAll();
	}

	public Rent getById(Integer rentId) {
		return rentDao.getById(rentId);
	}

	public Rent update(Rent rent) {
		return rentDao.update(rent);
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
