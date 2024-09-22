package com.rent.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rent.bean.RentItem;
import com.rent.bean.RentItem.RentItemId;

@Repository
public class RentItemDao {
	
	@Autowired
	private SessionFactory sessionFactory;
//	private Session session;
//
//	public RentItemDao(Session session) {
//		this.session = session;
//	}

	public RentItem insert(RentItem rentItem) {
		Session session = sessionFactory.getCurrentSession();
		if (rentItem != null) {
			session.persist(rentItem);
			return rentItem;
		}
		return null;
	}

	public List<RentItem> getAll() {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("from RentItem", RentItem.class).list();
	}

	public List<RentItem> getById(Integer rentId) {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("from RentItem where rentId = :rentId", RentItem.class)
				.setParameter("rentId", rentId).list();
	}

	public RentItem getByIds(Integer rentId, Integer tablewareId) {
		Session session = sessionFactory.getCurrentSession();
		RentItemId rentItemId = new RentItemId(rentId, tablewareId);
		return session.get(RentItem.class, rentItemId);
	}

	public RentItem update(RentItem rentItem) {
		Session session = sessionFactory.getCurrentSession();
		if (rentItem != null) {
			session.merge(rentItem);
		}
		return rentItem;
	}

	public boolean delete(RentItem rentItem) {
		Session session = sessionFactory.getCurrentSession();
		if (rentItem != null) {
			session.delete(rentItem);
			return true;
		}
		return false;
	}
	

}
