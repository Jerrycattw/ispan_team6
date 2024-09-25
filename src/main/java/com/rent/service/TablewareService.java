package com.rent.service;

import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rent.bean.Tableware;
import com.rent.dao.TablewareDao;

@Service
@Transactional
public class TablewareService {
	@Autowired
	private TablewareDao tablewareDao;
//	private  Session session;

//	public TablewareService(Session session) {
//		this.session = session;
//		this.tablewareDao = new TablewareDao(session);
//	}

	public Tableware insert(Tableware tableware) {
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

	public Tableware update(Tableware tableware) {
		return tablewareDao.update(tableware);
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
