package com.rent.service;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rent.bean.RentItem;
import com.rent.dao.RentItemDao;


@Service
@Transactional
public class RentItemService {
	
	@Autowired
	private RentItemDao rentItemDao;
//	private Session session;

//	public RentItemService(Session session) {
//		this.session = session;
//		this.rentItemDao = new RentItemDao(session);
//	}

	public RentItem insert(RentItem rentItem) {
		return rentItemDao.insert(rentItem);
	}

	public List<RentItem> getAll() {
		return rentItemDao.getAll();
	}

	public RentItem getByIds(Integer rentId, Integer tablewareId) {
		return rentItemDao.getByIds(rentId, tablewareId);
	}

	public List<RentItem> getById(Integer rentId) {
		return rentItemDao.getById(rentId);
	}

	public RentItem update(RentItem rentItem) {
		return rentItemDao.update(rentItem);
	}

	public boolean delete(RentItem rentItem) {
		if (rentItem != null) {
			return rentItemDao.delete(rentItem);
		}
		return false;
	}

	public RentItem restore(Integer rentId, Integer tablewareId, String returnMemo, int returnStatus) {
		RentItem rentItem = getByIds(rentId, tablewareId);
		if (rentItem != null) {
			rentItem.setReturnMemo(returnMemo);
			rentItem.setReturnStatus(returnStatus);
			rentItemDao.update(rentItem);
		}
		return rentItem;
	}

	
}
