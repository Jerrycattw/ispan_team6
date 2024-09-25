package com.rent.service;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rent.bean.TablewareStock;
import com.rent.dao.TablewareStockDao;

@Service
@Transactional
public class TablewareStockService {
	@Autowired
	private TablewareStockDao tablewareStockDao;
//	private Session session;

//	public TablewareStockService(Session session) {
//		this.session = session;
//		this.tablewareStockDao = new TablewareStockDao(session);
//	}

	public TablewareStock insert(Integer tablewareId, String restaurantId, Integer stock) {
		TablewareStock tablewareStock = new TablewareStock(tablewareId, restaurantId, stock);
		return tablewareStockDao.insert(tablewareStock);
	}

	public List<TablewareStock> getAll() {
		return tablewareStockDao.getAll();
	}

	public TablewareStock getById(Integer tablewareId,String restaurantId) {
		TablewareStock.TablewareStockId id = new TablewareStock.TablewareStockId(tablewareId, restaurantId);
		return tablewareStockDao.getById(id);
	}

	public TablewareStock update(Integer tablewareId, String restaurantId, Integer stock) {
		TablewareStock.TablewareStockId id = new TablewareStock.TablewareStockId(tablewareId, restaurantId);
		TablewareStock tablewareStock = tablewareStockDao.getById(id);
		if (tablewareStock != null) {
			tablewareStock.setStock(stock);
			tablewareStockDao.update(tablewareStock);
		}
		return tablewareStock;
	}

	public List<TablewareStock> search(String restaurantId, Integer tablewareId) {
		try {
			return tablewareStockDao.search(restaurantId, tablewareId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
