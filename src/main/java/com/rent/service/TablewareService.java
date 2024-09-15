package com.rent.service;

import java.util.List;

import org.hibernate.Session;
import com.rent.bean.Tableware;
import com.rent.dao.TablewareDao;

public class TablewareService {
	private final TablewareDao tablewareDao;
	private final Session session;

	public TablewareService(Session session) {
		this.session = session;
		this.tablewareDao = new TablewareDao(session);
	}

	public Tableware insert(String tablewareName, int tablewareDeposit, String tablewareImage,
			String tablewareDescription) {
		Tableware tableware = new Tableware(tablewareName, tablewareDeposit, tablewareImage, tablewareDescription, 1);
		return tablewareDao.insert(tableware);
	}

	public Tableware getById(Integer tablewareId) {
		return tablewareDao.getById(tablewareId);
	}

	public List<Tableware> getAll() {
		return tablewareDao.getAll();
	}

	public List<Tableware> search(String keyword) {
		return tablewareDao.search(keyword);
	}

	public Tableware update(Integer tablewareId, String tablewareName, Integer tablewareDeposit, String tablewareImage,
			String tablewareDescription, int tablewareStatus) {
		Tableware tableware = tablewareDao.getById(tablewareId);
		if (tableware != null) {
			tableware.setTablewareDeposit(tablewareDeposit);
			tableware.setTablewareName(tablewareName);
			tableware.setTablewareImage(tablewareImage);
			tableware.setTablewareDescription(tablewareDescription);
			tableware.setTablewareStatus(tablewareStatus);
			tablewareDao.update(tableware);
		}
		return tableware;
	}

	public Tableware updateStatus(Integer tablewareId) {
		Tableware tableware = tablewareDao.getById(tablewareId);
		if (tableware != null) {
			int currentStatus = tableware.getTablewareStatus();
			int newStatus = (currentStatus == 1) ? 2 : 1;
			tableware.setTablewareStatus(newStatus);
			tablewareDao.update(tableware);
		}
		return tableware;
	}

	public List<Integer> getTablewareIds() {
		return tablewareDao.getTablewareId();
	}
}
