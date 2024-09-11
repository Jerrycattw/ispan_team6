package com.rent.service;


import java.util.List;

import org.hibernate.Session;
import com.rent.bean.RentItem;
import com.rent.dao.RentItemDao;

public class RentItemService {
	private final RentItemDao rentItemDao;
	private final Session session;

	public RentItemService(Session session) {
		this.session = session;
		this.rentItemDao = new RentItemDao(session);
	}

	public RentItem insert(Integer rentId, Integer tablewareId, Integer rentItemQuantity, Integer rentItemDeposit) {
		RentItem rentItem = new RentItem(rentId, tablewareId, rentItemQuantity, rentItemDeposit, "未歸還", 1);
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

	public boolean update(int rentId, int tablewareId, int rentItemQuantity, int rentItemDeposit, String returnMemo,
			int returnStatus) {
		RentItem rentItem = rentItemDao.getByIds(rentId, tablewareId);
		if (rentItem != null) {
			rentItem.setRentItemQuantity(rentItemQuantity);
			rentItem.setRentItemDeposit(rentItemDeposit);
			rentItem.setReturnMemo(returnMemo);
			rentItem.setReturnStatus(returnStatus);
			session.update(rentItem);
			return true;
		}
		return false;
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
