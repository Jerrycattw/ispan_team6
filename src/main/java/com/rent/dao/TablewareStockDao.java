package com.rent.dao;



import java.sql.SQLException;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rent.bean.TablewareStock;

@Repository
public class TablewareStockDao {
	
	@Autowired
	private SessionFactory sessionFactory;
//	private Session session;
//
//	public TablewareStockDao(Session session) {
//		this.session = session;
//	}

	public TablewareStock insert(TablewareStock tablewareStock) {
		Session session = sessionFactory.getCurrentSession();
		if (tablewareStock != null) {
			session.persist(tablewareStock);
			return tablewareStock;
		}
		return null;
	}

	public List<TablewareStock> getAll() {
		Session session = sessionFactory.getCurrentSession();
		List<TablewareStock> tablewareStocks = session.createQuery("from TablewareStock", TablewareStock.class).list();
		return tablewareStocks;
	}
	
	public TablewareStock getById(TablewareStock.TablewareStockId id) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(TablewareStock.class, id);
	}
	
	public TablewareStock update(TablewareStock tablewareStock) {
		Session session = sessionFactory.getCurrentSession();
		if(tablewareStock!=null) {
			session.merge(tablewareStock);
		}
		return tablewareStock;
	}
	
	public List<TablewareStock> search(Integer restaurantId, Integer tablewareId) throws SQLException {
		Session session = sessionFactory.getCurrentSession();
		StringBuilder hql = new StringBuilder("FROM TablewareStock WHERE 1=1");
        if (restaurantId != null) {
            hql.append(" AND restaurantId = :restaurantId");
        }
        if (tablewareId != null) {
            hql.append(" AND tablewareId = :tablewareId");
        }
        Query<TablewareStock> query = session.createQuery(hql.toString(), TablewareStock.class);    
        if (restaurantId != null) {
            query.setParameter("restaurantId", restaurantId);
        }
        if (tablewareId != null) {
            query.setParameter("tablewareId", tablewareId);
        }
		return query.getResultList();
	}



}
