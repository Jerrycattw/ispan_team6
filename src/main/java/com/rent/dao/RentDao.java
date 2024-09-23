package com.rent.dao;

import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rent.bean.Rent;

@Repository
public class RentDao {
	
	@Autowired
	private SessionFactory sessionFactory;
//	private Session session;
//
//	public RentDao(Session session) {
//		this.session = session;
//	}
	public Rent getById(Integer rentId) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(Rent.class, rentId);
	}

	public List<Rent> getAll() {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("from Rent", Rent.class).list();
	}
	
	public Rent insert(Rent rent) {
		Session session = sessionFactory.getCurrentSession();
		if (rent != null) {
			session.persist(rent);
			return rent;
		}
		return null;
	}

	public Rent update(Rent rent) {
		Session session = sessionFactory.getCurrentSession();
		if (rent != null) {
			session.merge(rent);
			return rent;
		}
		return null;
	}

	public List<Rent> getOver() {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("from Rent where dueDate < current_date() and returnDate is null", Rent.class).list();
	}

	public List<Rent> search(Integer rentId, Integer memberId, String restaurantId, Integer rentStatus,
			Date rentDateStart, Date rentDateEnd) {
		Session session = sessionFactory.getCurrentSession();
		StringBuilder hql = new StringBuilder("from Rent where 1=1");
		if (rentId != null)
			hql.append(" and rentId = :rentId");
		if (memberId != null)
			hql.append(" and memberId = :memberId");
		if (restaurantId != null)
			hql.append(" and restaurantId = :restaurantId");
		if (rentStatus != null)
			hql.append(" and rentStatus = :rentStatus");
		if (rentDateStart != null && rentDateEnd != null)
			hql.append(" and rentDate between :rentDateStart and :rentDateEnd");

		Query<Rent> query = session.createQuery(hql.toString(), Rent.class);

		if (rentId != null)
			query.setParameter("rentId", rentId);
		if (memberId != null)
			query.setParameter("memberId", memberId);
		if (restaurantId != null)
			query.setParameter("restaurantId", restaurantId);
		if (rentStatus != null)
			query.setParameter("rentStatus", rentStatus);
		if (rentDateStart != null && rentDateEnd != null) {
			query.setParameter("rentDateStart", rentDateStart);
			query.setParameter("rentDateEnd", rentDateEnd);
		}
		return query.getResultList();
	}

	public boolean delete(Rent rent) {
		Session session = sessionFactory.getCurrentSession();
		if (rent != null) {
			session.remove(rent);
			return true;
		}
		return false;
	}
	
	public List<Integer> getRentId(){
		Session session = sessionFactory.getCurrentSession();
		String hql = "SELECT rentId FROM Rent";
		Query<Integer> query = session.createQuery(hql, Integer.class);
		List<Integer> rentIds = query.getResultList();
		return rentIds;
	}
	
	
}

