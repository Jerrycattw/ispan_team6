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

	public List<Rent> search(Integer rentId, Integer memberId, Integer restaurantId, Integer rentStatus,
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
}

//	private static final String updateAll="UPDATE rent SET rent_deposit=?,rent_date=?,restaurant_id=?,member_id=?,due_date=?,return_date=?,rent_status=?,rent_memo=?,return_restaurant_id=? WHERE rent_id=?";
//	public Rent updateRent(int rentId,int rentDeposit,Date rentDateUtil,int restaurantId,int memberId,Date dueDateUtil,Date returnDateUtil,int rentStatus,String rentMemo,int returnRestaurantId) throws SQLException {
//		Rent rent = new Rent();
//		try (Connection connection = DButil.getConnection();
//				PreparedStatement preparedStatement = connection.prepareStatement(updateAll)) {
//			
//			java.sql.Date rentDate = new java.sql.Date(rentDateUtil.getTime());
// 			java.sql.Date dueDate = new java.sql.Date(dueDateUtil.getTime());
// 			java.sql.Date returnDate = new java.sql.Date(returnDateUtil.getTime());
//			
//			preparedStatement.setInt(10, rentId);
//			preparedStatement.setInt(1, rentDeposit);
//			preparedStatement.setDate(2, rentDate);
//			preparedStatement.setInt(3, restaurantId);
//			preparedStatement.setInt(4, memberId);
//			preparedStatement.setDate(5, dueDate);
//			preparedStatement.setDate(6, returnDate);
//			preparedStatement.setInt(7, rentStatus);
//			preparedStatement.setString(8, rentMemo);
//			preparedStatement.setInt(9, returnRestaurantId);
// 			preparedStatement.executeUpdate();
// 			
// 			rent.setRentId(rentId);
//			rent.setRentDeposit(rentDeposit);
//			rent.setRentDate(rentDate);
//			rent.setRestaurantId(restaurantId);
//			rent.setMemberId(memberId);
//			rent.setDueDate(dueDate);
//			rent.setReturnDate(returnDate);
//			rent.setRentStatus(rentStatus);
//			rent.setRentMemo(rentMemo);
//			rent.setReturnRestaurantId(returnRestaurantId);
//		}
//		return rent;
//	}
//	
//	private static final String updatePart="UPDATE rent SET return_date=?,rent_status=2,rent_memo='歸還',return_restaurant_id=? WHERE rent_id=?";
//	public Rent updatePart(int rentId,Date returnDateUtil,int returnRestaurantId) throws SQLException {
//		Rent rent = new Rent();
//		try (Connection connection = DButil.getConnection();
//				PreparedStatement preparedStatement = connection.prepareStatement(updatePart)) {
//			
//			java.sql.Date returnDate = new java.sql.Date(returnDateUtil.getTime());
//			
//			preparedStatement.setInt(3, rentId);
//			preparedStatement.setDate(1, returnDate);
//			preparedStatement.setInt(2, returnRestaurantId);
//			preparedStatement.executeUpdate();
//			
//			rent.setRentId(rentId);
//			rent.setReturnDate(returnDate);
//			rent.setRentStatus(2);
//			rent.setRentMemo("歸還");
//			rent.setReturnRestaurantId(returnRestaurantId);
//		}
//		return rent;
//	}
//	
