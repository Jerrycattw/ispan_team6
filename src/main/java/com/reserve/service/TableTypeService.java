package com.reserve.service;

import java.util.List;

import org.hibernate.Session;

import com.reserve.bean.TableType;
import com.reserve.dao.TableTypeDao;

public class TableTypeService {

	private TableTypeDao tableTypeDao;
	
	public TableTypeService(Session session) {
		tableTypeDao = new TableTypeDao(session);
	}
	
	//新增單筆桌位種類
	public TableType insert(TableType tableType) {
		return tableTypeDao.insert(tableType);
	}
	
	//查詢單筆桌位種類
	public TableType selectById(String tableTypeId) {
		return tableTypeDao.selectById(tableTypeId);
	}
	
	//查詢所有桌位種類
	public List<TableType> selectAll() {
		return tableTypeDao.selectAll();
	}
	
	//更新桌位種類
	public TableType update(TableType tableType) {
		return tableTypeDao.update(tableType);
	}
	
	//刪除桌位種類
	public boolean delete(String tableTypeId) {
		return tableTypeDao.deleteById(tableTypeId);
	}
	
	// 查詢桌位種類名稱由編號
	public String getTableTypeName(String tableTypeId) {
		return tableTypeDao.getTableTypeName(tableTypeId);
	}
	
	

}
