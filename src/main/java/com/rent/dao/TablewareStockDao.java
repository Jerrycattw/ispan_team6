package com.rent.dao;



import java.sql.SQLException;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
import com.rent.bean.TablewareStock;

public class TablewareStockDao {
	private Session session;

	public TablewareStockDao(Session session) {
		this.session = session;
	}

	public TablewareStock insert(TablewareStock tablewareStock) {
		if (tablewareStock != null) {
			session.persist(tablewareStock);
			return tablewareStock;
		}
		return null;
	}

	public List<TablewareStock> getAll() {
		List<TablewareStock> tablewareStocks = session.createQuery("from TablewareStock", TablewareStock.class).list();
		return tablewareStocks;
	}
	
	public TablewareStock getById(TablewareStock.TablewareStockId id) {
		return session.find(TablewareStock.class, id);
	}
	
	public TablewareStock update(TablewareStock tablewareStock) {
		if(tablewareStock!=null) {
			session.update(tablewareStock);
		}
		return tablewareStock;
	}
	
	public List<TablewareStock> search(Integer restaurantId,Integer tablewareId) throws SQLException {
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
