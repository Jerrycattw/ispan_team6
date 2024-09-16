package com.rent.dao;

import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
import com.rent.bean.Rent;

public class RentDao {
	private Session session;

	public RentDao(Session session) {
		this.session = session;
	}

	public Rent insert(Rent rent) {
		if (rent != null) {
			session.persist(rent);
			return rent;
		}
		return null;
	}

	public List<Rent> getAll() {
		return session.createQuery("from Rent", Rent.class).list();
	}

	public Rent getById(Integer rentId) {
		return session.get(Rent.class, rentId);
	}

	public List<Rent> getOver() {
		return session.createQuery("from Rent where dueDate < current_date() and returnDate is null", Rent.class)
				.list();
	}

	public List<Rent> search(Integer rentId, Integer memberId, String restaurantId, Integer rentStatus,
			Date rentDateStart, Date rentDateEnd) {
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

	public Rent update(Rent rent) {
		if (rent != null) {
			session.update(rent);
			return rent;
		}
		return null;
	}

	public boolean delete(Rent rent) {
		if (rent != null) {
			session.delete(rent);
			return true;
		}
		return false;
	}
	
	public List<Integer> getRentId(){
		String hql = "SELECT rentId FROM Rent";
		Query<Integer> query = session.createQuery(hql, Integer.class);
		List<Integer> rentIds = query.getResultList();
		return rentIds;
	}
	
}

