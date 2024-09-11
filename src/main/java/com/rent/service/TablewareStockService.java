package com.rent.service;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;
import com.rent.bean.TablewareStock;
import com.rent.dao.TablewareStockDao;

public class TablewareStockService {
	private TablewareStockDao tablewareStockDao;
	private Session session;

	public TablewareStockService(Session session) {
		this.session = session;
		this.tablewareStockDao = new TablewareStockDao(session);
	}

	public TablewareStock insert(Integer tablewareId, Integer restaurantId, Integer stock) {
		TablewareStock tablewareStock = new TablewareStock(tablewareId, restaurantId, stock);
		return tablewareStockDao.insert(tablewareStock);
	}

	public List<TablewareStock> getAll() {
		return tablewareStockDao.getAll();
	}

	public TablewareStock getById(Integer tablewareId,Integer restaurantId) {
		TablewareStock.TablewareStockId id = new TablewareStock.TablewareStockId(tablewareId, restaurantId);
		return tablewareStockDao.getById(id);
	}

	public TablewareStock update(Integer tablewareId, Integer restaurantId, Integer stock) {
		TablewareStock.TablewareStockId id = new TablewareStock.TablewareStockId(tablewareId, restaurantId);
		TablewareStock tablewareStock = tablewareStockDao.getById(id);
		if (tablewareStock != null) {
			tablewareStock.setStock(stock);
			tablewareStockDao.update(tablewareStock);
		}
		return tablewareStock;
	}

	public List<TablewareStock> search(Integer restaurantId, Integer tablewareId) {
		try {
			return tablewareStockDao.search(restaurantId, tablewareId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
