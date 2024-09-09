package com.reserve.dao;

import java.util.List;


import org.hibernate.Session;
import org.hibernate.query.Query;

import com.reserve.bean.TableType;

public class TableTypeDao {
	
	
	private Session session;
	
	
	public TableTypeDao(Session session) {
		this.session = session;
	}
	
	//依ID查詢單筆桌位種類
	public TableType selectById(String tableTypeId) {
		return session.get(TableType.class, tableTypeId);
	}
	
	//查詢所有餐廳
	public List<TableType> selectAll() {
		Query<TableType> query = session.createQuery("from TableType", TableType.class);
		return query.list();
	}
	
	//新增單筆桌位種類
	public TableType insert(TableType tableType) {
		if(tableType!=null) {
			session.persist(tableType);
			return tableType;
		}
		return null;
	}
	
	//更新桌位種類資料
	public TableType update(TableType tableType) {
		TableType isExist = session.get(TableType.class, tableType.getTableTypeId());
		if(isExist!=null) {
			session.merge(tableType);
			return tableType;
		}
		return null;
	}
	
	//刪除桌位種類ById
	public boolean deleteById(String tableTypeId) {
		TableType tableType = session.get(TableType.class, tableTypeId);
		if(tableType!=null) {
			session.remove(tableType);
			return true;
		}else {
			return false;
		}
		
	}
	
	
	// 查詢桌位種類名稱由編號
	public String getTableTypeName(String tableTypeId) {
	    String hql = "SELECT t.tableTypeName FROM TableType t WHERE t.tableTypeId = :tableTypeId";
	    Query<String> query = session.createQuery(hql, String.class);
	    query.setParameter("tableTypeId", tableTypeId);
	    return query.uniqueResult();
	}
	
	

}
