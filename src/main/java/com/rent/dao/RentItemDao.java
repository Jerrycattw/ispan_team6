package com.rent.dao;

import java.util.List;

import org.hibernate.Session;

import com.rent.bean.RentItem;
import com.rent.bean.RentItem.RentItemId;

public class RentItemDao {
	private Session session;

	public RentItemDao(Session session) {
		this.session = session;
	}

	public RentItem insert(RentItem rentItem) {
		if (rentItem != null) {
			session.persist(rentItem);
			return rentItem;
		}
		return null;
	}

	public List<RentItem> getAll() {
		return session.createQuery("from RentItem", RentItem.class).list();
	}

	public List<RentItem> getById(Integer rentId) {
		return session.createQuery("from RentItem where rentId = :rentId", RentItem.class)
				.setParameter("rentId", rentId).list();
	}

	public RentItem getByIds(Integer rentId, Integer tablewareId) {
		RentItemId rentItemId = new RentItemId(rentId, tablewareId);
		return session.get(RentItem.class, rentItemId);
	}

	public RentItem update(RentItem rentItem) {
		if (rentItem != null) {
			session.merge(rentItem);
		}
		return rentItem;
	}

	public boolean delete(RentItem rentItem) {
		if (rentItem != null) {
			session.delete(rentItem);
			return true;
		}
		return false;
	}
	

}
