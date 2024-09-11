package com.rent.dao;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.rent.bean.Tableware;

public class TablewareDao {
	private Session session;

	public TablewareDao(Session session) {
		this.session = session;
	}
	
	public Tableware insert(Tableware tableware) {
		if (tableware != null) {
			session.persist(tableware);
			return tableware;
		}
		return null;
	}
	
	public List<Tableware> getAll() {
		List<Tableware> tablewares = session.createQuery("from Tableware", Tableware.class).list();
		return tablewares;
	}
	
	public Tableware getById(Integer tablewareId) {
		return session.get(Tableware.class, tablewareId);
	}
	
	public Tableware update(Tableware tableware) {
		if(tableware!=null) {
			session.merge(tableware);
		}
		return tableware;
	}
	
	public List<Tableware> search(String keyword) {
		String hql = "FROM Tableware WHERE tablewareName LIKE :keyword OR tablewareDescription LIKE :keyword";
		Query<Tableware> query = session.createQuery(hql, Tableware.class);
		query.setParameter("keyword", "%" + keyword + "%");
		List<Tableware> tablewares = query.getResultList();
		return tablewares;
	}
	
//	private static final String insert="INSERT INTO tableware(tableware_name,tableware_deposit,tableware_image,tableware_description,tableware_status) VALUES (?,?,?,?,1)";
//	public TablewareBean insertTableware(String tablewareName,int tablewareDeposit,String tablewareImage,String tablewareDescription) throws SQLException {
//		TablewareBean tableware = new TablewareBean();
//		try (Connection connection = DButil.getConnection();
//				PreparedStatement preparedStatement = connection.prepareStatement(insert)) {
//			preparedStatement.setString(1, tablewareName);
//			preparedStatement.setInt(2, tablewareDeposit);
//			preparedStatement.setString(3, tablewareImage);
//			preparedStatement.setString(4, tablewareDescription);
//			preparedStatement.executeUpdate();
//			tableware.setTablewareName(tablewareName);
// 			tableware.setTablewareDeposit(tablewareDeposit);
// 			tableware.setTablewareImage(tablewareImage);
// 			tableware.setTablewareDescription(tablewareDescription);
// 			tableware.setTablewareStatus(1);
//		}
//		return tableware;
//	}
//	
//	//tableware_name tableware_description 
//	private static final String get="SELECT * FROM tableware WHERE tableware_name LIKE ? OR tableware_description LIKE ?";
//	public ArrayList<TablewareBean> getTableware(String keyword) throws SQLException {
//		ArrayList<TablewareBean> tablewares = new ArrayList<>();
//		try (Connection connection = DButil.getConnection();
//				PreparedStatement preparedStatement = connection.prepareStatement(get)) {
//			String searchKeyword = "%" + keyword + "%";
//	        preparedStatement.setString(1, searchKeyword);
//	        preparedStatement.setString(2, searchKeyword);
//			try (ResultSet resultSet = preparedStatement.executeQuery()) {
//				TablewareBean tableware =null;
//				while(resultSet.next()) {
//					tableware = new TablewareBean();
//					tableware.setTablewareId(resultSet.getInt("tableware_id"));
//					tableware.setTablewareName(resultSet.getString("tableware_name"));
//					tableware.setTablewareDeposit(resultSet.getInt("tableware_deposit"));
//					tableware.setTablewareImage(resultSet.getString("tableware_image"));
//					tableware.setTablewareDescription(resultSet.getString("tableware_description"));
//					tableware.setTablewareStatus(resultSet.getInt("tableware_status"));
//					tablewares.add(tableware);
//				}
//			}
//		}
//		return tablewares;
//	}
//	
//	private static final String getAll="SELECT * FROM tableware";
//	public ArrayList<TablewareBean> getAllTableware() throws SQLException {
//		ArrayList<TablewareBean> tablewares = new ArrayList<>();
//		try (Connection connection = DButil.getConnection();
//				PreparedStatement preparedStatement = connection.prepareStatement(getAll);
//				ResultSet resultSet = preparedStatement.executeQuery()) {
//			TablewareBean tableware=null;
//			while(resultSet.next()) {
//				tableware=new TablewareBean();
//				tableware.setTablewareId(resultSet.getInt("tableware_id"));
//				tableware.setTablewareName(resultSet.getString("tableware_name"));
//				tableware.setTablewareDeposit(resultSet.getInt("tableware_deposit"));
//				tableware.setTablewareImage(resultSet.getString("tableware_image"));
//				tableware.setTablewareDescription(resultSet.getString("tableware_description"));
//				tableware.setTablewareStatus(resultSet.getInt("tableware_status"));
//				tablewares.add(tableware);
//			}
//		}
//		return tablewares;
//	}
//	
//	private static final String getUpdate="SELECT * FROM tableware WHERE tableware_id=?";
//	public TablewareBean getUpdateTableware(int tablewareId) throws SQLException {
//		TablewareBean tableware = new TablewareBean();
//		try (Connection connection = DButil.getConnection();
//				PreparedStatement preparedStatement = connection.prepareStatement(getUpdate)) {
//			preparedStatement.setInt(1, tablewareId);
//			try (ResultSet resultSet = preparedStatement.executeQuery()) {
//				if (resultSet.next()) {
//					tableware.setTablewareId(tablewareId);
//					tableware.setTablewareName(resultSet.getString("tableware_name"));
//					tableware.setTablewareDeposit(resultSet.getInt("tableware_deposit"));
//					tableware.setTablewareImage(resultSet.getString("tableware_image"));
//					tableware.setTablewareDescription(resultSet.getString("tableware_description"));
//					tableware.setTablewareStatus(resultSet.getInt("tableware_status"));
//				}
//			}
//		}
//		return tableware;
//	}
//	
//	private static final String updateAll="UPDATE tableware SET tableware_name=?,tableware_deposit=?,tableware_image=?,tableware_description=?,tableware_status=? WHERE tableware_id=?";
//	public TablewareBean updateTablewareAll(int tablewareId,String TablewareName,int tablewareDeposit,String tablewareImage,String tablewareDescription,int tablewareStatus) throws SQLException {
//		TablewareBean tableware = new TablewareBean();
//		try (Connection connection = DButil.getConnection();
//				PreparedStatement preparedStatement = connection.prepareStatement(updateAll)) {
//			preparedStatement.setInt(6, tablewareId);
//			preparedStatement.setString(1, TablewareName);
//			preparedStatement.setInt(2, tablewareDeposit);
//			preparedStatement.setString(3, tablewareImage);
//			preparedStatement.setString(4, tablewareDescription);
//			preparedStatement.setInt(5, tablewareStatus);
// 			preparedStatement.executeUpdate();
// 			
// 			tableware.setTablewareId(tablewareId);
// 			tableware.setTablewareName(TablewareName);
// 			tableware.setTablewareDeposit(tablewareDeposit);
// 			tableware.setTablewareImage(tablewareImage);
// 			tableware.setTablewareDescription(tablewareDescription);
// 			tableware.setTablewareStatus(tablewareStatus);
//		}
//		return tableware;
//	}
//	
//	private static final String getStatus= "SELECT tableware_status FROM tableware WHERE tableware_id=?";
//	private static final String updateStatus="UPDATE tableware SET tableware_status=? WHERE tableware_id=?";
//	public TablewareBean updateTablewareStatus(int tablewareId) throws SQLException {
//		TablewareBean tableware = new TablewareBean();
//		try (Connection connection = DButil.getConnection()) {
//			int currentStatus = 0;
//			try (PreparedStatement preparedStatement = connection.prepareStatement(getStatus)) {
//				preparedStatement.setInt(1, tablewareId);
//				try (ResultSet resultSet = preparedStatement.executeQuery()) {
//					if (resultSet.next()) {
//	                    currentStatus = resultSet.getInt("tableware_status");
//	                }
//				}
//			} 
//			int newStatus = (currentStatus == 1) ? 2 : 1;
//			try (PreparedStatement updateStatement = connection.prepareStatement(updateStatus)) {
//	            updateStatement.setInt(1, newStatus);
//	            updateStatement.setInt(2, tablewareId);
//	            updateStatement.executeUpdate();
//	        }
//		}
//		return tableware;
//	}
//	
//	private static final String tid = "SELECT tableware_id FROM tableware";
//	public ArrayList<TablewareBean> getAllTablewareIds() throws SQLException {
//	        ArrayList<TablewareBean> ids = new ArrayList<>();
//	        try (Connection connection = DButil.getConnection();
//	             PreparedStatement preparedStatement = connection.prepareStatement(tid);
//	             ResultSet resultSet = preparedStatement.executeQuery()) {
//	        	TablewareBean tableware = null;
//	            while (resultSet.next()) {
//	            	tableware = new TablewareBean();
//	            	tableware.setTablewareId(resultSet.getInt("tableware_id"));
//	                ids.add(tableware);
//	            }
//	        }
//	        return ids;
//	}
}
