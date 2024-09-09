package com.point.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.point.bean.PointBean;
import com.point.bean.PointMemberBean;
import com.point.dto.PointMemberDTO;

public class PointDAO {
	private Session session;

	public PointDAO(Session session) {
		this.session = session;
	}
	
	
	//查詢所有紀錄
//	public static List<PointBean> getAllRecord() {
//		Connection connection = JDBCutils.getConnection();
//		PreparedStatement stmt = null;
//		ResultSet rs = null;
//		List<PointBean> points = new ArrayList<>();
//		try {
//			stmt = connection.prepareStatement("SELECT * FROM point");
//			rs = stmt.executeQuery();
//			PointBean point = null;
//			while (rs.next()) {
//				point = new PointBean();
//				point.setPointID(rs.getInt("point_id"));
//				point.setMemberID(rs.getInt("member_id"));
//				point.setPointChange(rs.getInt("point_change"));
//				point.setCreateDate(rs.getDate("created_date"));
//				point.setExpiryDate(rs.getDate("get_expiry_date"));
//				point.setPointUsage(rs.getInt("point_usage"));
//				point.setTransactionID(rs.getInt("transaction_id"));
//				point.setTransactionType(rs.getString("transaction_type"));
//				points.add(point);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			JDBCutils.closeResource(connection, stmt, rs);
//		}
//		return points;
//	}
	
	//OK//查詢所有紀錄BY memberID
	public List<PointBean> getAllRecord(int memberID) {
		//"SELECT * FROM point WHERE member_id=?"
		Query<PointBean> query = session.createQuery("from PointBean p WHERE p.memberID=:memberID ",PointBean.class);
		query.setParameter("memberID", memberID);
		return query.getResultList();
	}
	
	//查詢所有紀錄 
	public PointBean getOneRecord(int pointID) {
		//SELECT * FROM point WHERE point_id=?"
		return session.get(PointBean.class, pointID);
		
	}
		
	//OK//查詢(會員、點數餘額、電話) all
	public  List<Object[]> getAllPointMember() {
		 String sql = """
			        WITH point_summary AS (
			            SELECT member_id, SUM(COALESCE(point_usage, 0)) AS total_point
			            FROM point
			            WHERE get_expiry_date > GETDATE()
			            GROUP BY member_id
			            HAVING SUM(COALESCE(point_usage, 0)) <> 0
			        ),
			        expiry_points AS (
			            SELECT member_id, SUM(COALESCE(point_usage, 0)) AS total_point, get_expiry_date,
			                   ROW_NUMBER() OVER (PARTITION BY member_id ORDER BY get_expiry_date) AS rank
			            FROM point
			            GROUP BY get_expiry_date, member_id
			            HAVING SUM(COALESCE(point_usage, 0)) <> 0 AND get_expiry_date > GETDATE()
			        )
			        SELECT ps.member_id, m.member_name, m.phone, ps.total_point AS total_point_balance, 
			               ep.total_point AS expiring_points, ep.get_expiry_date
			        FROM point_summary ps 
			        LEFT JOIN expiry_points ep ON ps.member_id = ep.member_id
			        LEFT JOIN members m ON ps.member_id = m.member_id
			        WHERE ep.rank = 1
			        """;

			    Query<Object[]> query = session.createNativeQuery(sql);

			    List<Object[]> results = query.getResultList();
//			    List<PointMemberDTO> PointMemberDTO = new ArrayList<>();
//
//			    for (Object[] result : results) {
//			        PointMemberDTO dto = new PointMemberDTO();
//			        dto.setMemberId((Integer) result[0]);
//			        dto.setMemberName((String) result[1]);
//			        dto.setPhone((String) result[2]);
//			        dto.setTotalPointBalance((int) result[3]); // Assuming total_point_balance is of type Long
//			        dto.setExpiringPoints((int) result[4]);    // Assuming expiring_points is of type Long
//			        dto.setExpiryDate((Date) result[5]);        // Assuming get_expiry_date is of type Date
//
//			        PointMemberDTO.add(dto);
//			    }

			    return results;
	}
	//OK//查詢(會員、點數餘額、電話) bymemberID (return bean)
	public  Object[] getPointMember(int memberId) {
		String sql = """
		        WITH point_summary AS (
		            SELECT member_id, SUM(COALESCE(point_usage, 0)) AS total_point
		            FROM point
		            WHERE get_expiry_date > GETDATE()
		            GROUP BY member_id
		            HAVING SUM(COALESCE(point_usage, 0)) <> 0
		        ),
		        expiry_points AS (
		            SELECT member_id, SUM(COALESCE(point_usage, 0)) AS total_point, get_expiry_date,
		                   ROW_NUMBER() OVER (PARTITION BY member_id ORDER BY get_expiry_date) AS rank
		            FROM point
		            GROUP BY get_expiry_date, member_id
		            HAVING SUM(COALESCE(point_usage, 0)) <> 0 AND get_expiry_date > GETDATE()
		        )
		        SELECT ps.member_id, m.member_name, m.phone, ps.total_point AS total_point_balance, 
		               ep.total_point AS expiring_points, ep.get_expiry_date
		        FROM point_summary ps 
		        LEFT JOIN expiry_points ep ON ps.member_id = ep.member_id
		        LEFT JOIN members m ON ps.member_id = m.member_id
		        WHERE ep.rank = 1 AND ps.member_id=:memberId
		        """;

		    Query<Object[]> query = session.createNativeQuery(sql);
		    query.setParameter("memberId", memberId);

		    Object[] results = query.getSingleResult();
//		    List<PointMemberDTO> pointSummaries = new ArrayList<>();
//
//		    for (Object[] result : results) {
//		    	PointMemberDTO dto = new PointMemberDTO();
//		        dto.setMemberId((Integer) result[0]);
//		        dto.setMemberName((String) result[1]);
//		        dto.setPhone((String) result[2]);
//		        dto.setTotalPointBalance((int) result[3]); 
//		        dto.setExpiringPoints((int) result[4]);    
//		        dto.setExpiryDate((Date) result[5]);       
//		        pointSummaries.add(dto);
//		    }

		    return results;
	}
	
	//查詢(會員、點數餘額、電話) byName
//	public static List<PointMemberBean> getPointMemberByName(String memberName) {
//		Connection connection = JDBCutils.getConnection();
//		PreparedStatement stmt = null;
//		ResultSet rs = null;
//		List<PointMemberBean> points = new ArrayList<>();
//		PointMemberBean point =null;
//		try {
//			stmt = connection.prepareStatement("WITH point_summary AS (\r\n"
//					+ "    SELECT member_id,SUM(COALESCE(point_usage, 0)) AS total_point\r\n"
//					+ "    FROM point\r\n"
//					+ "    WHERE get_expiry_date > GETDATE()\r\n"
//					+ "    GROUP BY member_id\r\n"
//					+ "    HAVING SUM(COALESCE(point_usage, 0)) <> 0\r\n"
//					+ "),\r\n"
//					+ "expiry_points AS (\r\n"
//					+ "    SELECT member_id,SUM(COALESCE(point_usage, 0)) AS total_point,get_expiry_date,ROW_NUMBER() OVER (PARTITION BY member_id ORDER BY get_expiry_date) AS rank\r\n"
//					+ "    FROM point\r\n"
//					+ "    GROUP BY get_expiry_date, member_id\r\n"
//					+ "    HAVING  SUM(COALESCE(point_usage, 0)) <> 0 AND get_expiry_date > GETDATE()\r\n"
//					+ ")\r\n"
//					+ "SELECT ps.member_id,m.member_name,m.phone,ps.total_point AS total_point_balance,ep.total_point AS expiring_points, ep.get_expiry_date\r\n"
//					+ "FROM point_summary ps LEFT JOIN expiry_points ep ON ps.member_id = ep.member_id\r\n"
//					+ "		left join members m on ps.member_id=m.member_id		\r\n"
//					+ "WHERE ep.rank = 1 AND m.member_name LIKE ?;");
//			stmt.setString(1, "%"+memberName+"%");
//			rs = stmt.executeQuery();
//			while (rs.next()) {
//				point = new PointMemberBean();
//				point.setMemberID(rs.getInt("member_id"));
//				point.setMemberName(rs.getString("member_name"));
//				point.setPhone(rs.getString("phone"));
//				point.setTotalPoint(rs.getInt("total_point_balance"));
//				point.setExpiriyPoint(rs.getInt("expiring_points"));
//				point.setExpiryDate(rs.getDate("get_expiry_date"));
//				points.add(point);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			JDBCutils.closeResource(connection, stmt, rs);
//		}
//		return points;
//	}
	//查詢(會員、點數餘額、電話) byPhone、MemberID
//	public static List<PointMemberBean> getPointMemberByNumber(String number) {
//		Connection connection = JDBCutils.getConnection();
//		PreparedStatement stmt = null;
//		ResultSet rs = null;
//		List<PointMemberBean> points = new ArrayList<>();
//		PointMemberBean point =null;
//		try {
//			stmt = connection.prepareStatement("WITH point_summary AS (\r\n"
//					+ "    SELECT member_id,SUM(COALESCE(point_usage, 0)) AS total_point\r\n"
//					+ "    FROM point\r\n"
//					+ "    WHERE get_expiry_date > GETDATE()\r\n"
//					+ "    GROUP BY member_id\r\n"
//					+ "    HAVING SUM(COALESCE(point_usage, 0)) <> 0\r\n"
//					+ "),\r\n"
//					+ "expiry_points AS (\r\n"
//					+ "    SELECT member_id,SUM(COALESCE(point_usage, 0)) AS total_point,get_expiry_date,ROW_NUMBER() OVER (PARTITION BY member_id ORDER BY get_expiry_date) AS rank\r\n"
//					+ "    FROM point\r\n"
//					+ "    GROUP BY get_expiry_date, member_id\r\n"
//					+ "    HAVING  SUM(COALESCE(point_usage, 0)) <> 0 AND get_expiry_date > GETDATE()\r\n"
//					+ ")\r\n"
//					+ "SELECT ps.member_id,m.member_name,m.phone,ps.total_point AS total_point_balance,ep.total_point AS expiring_points, ep.get_expiry_date\r\n"
//					+ "FROM point_summary ps LEFT JOIN expiry_points ep ON ps.member_id = ep.member_id\r\n"
//					+ "		left join members m on ps.member_id=m.member_id		\r\n"
//					+ "WHERE ep.rank = 1 AND (m.phone LIKE ? OR ps.member_id LIKE ?);");
//			stmt.setString(1, "%"+number+"%");
//			stmt.setString(2, "%"+number+"%");
//			rs = stmt.executeQuery();
//			while (rs.next()) {
//				point = new PointMemberBean();
//				point.setMemberID(rs.getInt("member_id"));
//				point.setMemberName(rs.getString("member_name"));
//				point.setPhone(rs.getString("phone"));
//				point.setTotalPoint(rs.getInt("total_point_balance"));
//				point.setExpiriyPoint(rs.getInt("expiring_points"));
//				point.setExpiryDate(rs.getDate("get_expiry_date"));
//				points.add(point);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			JDBCutils.closeResource(connection, stmt, rs);
//		}
//		return points;
//	}
	
	//查詢(會員、點數餘額、電話) byMemberID (return list)
//	public static List<PointMemberBean> getPointMemberByID(String memberID) {
//		Connection connection = JDBCutils.getConnection();
//		PreparedStatement stmt = null;
//		ResultSet rs = null;
//		List<PointMemberBean> points = new ArrayList<>();
//		PointMemberBean point =null;
//		try {
//			stmt = connection.prepareStatement("WITH point_summary AS (\r\n"
//					+ "    SELECT member_id,SUM(COALESCE(point_usage, 0)) AS total_point\r\n"
//					+ "    FROM point\r\n"
//					+ "    WHERE get_expiry_date > GETDATE()\r\n"
//					+ "    GROUP BY member_id\r\n"
//					+ "    HAVING SUM(COALESCE(point_usage, 0)) <> 0\r\n"
//					+ "),\r\n"
//					+ "expiry_points AS (\r\n"
//					+ "    SELECT member_id,SUM(COALESCE(point_usage, 0)) AS total_point,get_expiry_date,ROW_NUMBER() OVER (PARTITION BY member_id ORDER BY get_expiry_date) AS rank\r\n"
//					+ "    FROM point\r\n"
//					+ "    GROUP BY get_expiry_date, member_id\r\n"
//					+ "    HAVING  SUM(COALESCE(point_usage, 0)) <> 0 AND get_expiry_date > GETDATE()\r\n"
//					+ ")\r\n"
//					+ "SELECT ps.member_id,m.member_name,m.phone,ps.total_point AS total_point_balance,ep.total_point AS expiring_points, ep.get_expiry_date\r\n"
//					+ "FROM point_summary ps LEFT JOIN expiry_points ep ON ps.member_id = ep.member_id\r\n"
//					+ "		left join members m on ps.member_id=m.member_id		\r\n"
//					+ "WHERE ep.rank = 1 AND m.member_id=?;");
//			stmt.setString(1, memberID);
//			rs = stmt.executeQuery();
//			while (rs.next()) {
//				point = new PointMemberBean();
//				point.setMemberID(rs.getInt("member_id"));
//				point.setMemberName(rs.getString("member_name"));
//				point.setPhone(rs.getString("phone"));
//				point.setTotalPoint(rs.getInt("total_point_balance"));
//				point.setExpiriyPoint(rs.getInt("expiring_points"));
//				point.setExpiryDate(rs.getDate("get_expiry_date"));
//				points.add(point);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			JDBCutils.closeResource(connection, stmt, rs);
//		}
//		return points;
//	}
	
	
	//修改
	public void updatePoint(int pointChange,Date createDate,Date expiryDate,int pointUsage,int transactionID,String transactionType,int pointID) {
		//"UPDATE point SET point_change=?,created_date=?,get_expiry_date=?, point_usage=?,transaction_id=?,transaction_type=? WHERE point_id=?"
		PointBean pointBean = session.get(PointBean.class,pointID);
		
		if (pointBean != null) {
			pointBean.setPointChange(pointChange);
			pointBean.setCreateDate(createDate);
			pointBean.setExpiryDate(expiryDate);
			pointBean.setPointUsage(pointUsage);
			pointBean.setTransactionID(transactionID);
			pointBean.setTransactionType(transactionType);
		}
	}
	//刪除單筆紀錄
	public void deleteOneRecord(int pointID) {
		//"DELETE FROM point WHERE point_id=?"
		PointBean pointBean = session.get(PointBean.class,pointID);
		if (pointBean !=null) {
			session.remove(pointBean);
		}
	}
	//新增單筆紀錄
	public void insertOneRecord(PointBean pointBean) {
		//"INSERT INTO point (member_id,point_change,created_date,get_expiry_date,transaction_id,transaction_type) VALUES(?,?,?,?,?,?)"
		session.persist(pointBean);

	}
	
	//模糊查詢
	public  List<Object[]> searchPointMember(String keyWord) {
		String sql = """
		        WITH point_summary AS (
		            SELECT member_id, SUM(COALESCE(point_usage, 0)) AS total_point
		            FROM point
		            WHERE get_expiry_date > GETDATE()
		            GROUP BY member_id
		            HAVING SUM(COALESCE(point_usage, 0)) <> 0
		        ),
		        expiry_points AS (
		            SELECT member_id, SUM(COALESCE(point_usage, 0)) AS total_point, get_expiry_date,
		                   ROW_NUMBER() OVER (PARTITION BY member_id ORDER BY get_expiry_date) AS rank
		            FROM point
		            GROUP BY get_expiry_date, member_id
		            HAVING SUM(COALESCE(point_usage, 0)) <> 0 AND get_expiry_date > GETDATE()
		        )
		        SELECT ps.member_id, m.member_name, m.phone, ps.total_point AS total_point_balance, 
		               ep.total_point AS expiring_points, ep.get_expiry_date
		        FROM point_summary ps 
		        LEFT JOIN expiry_points ep ON ps.member_id = ep.member_id
		        LEFT JOIN members m ON ps.member_id = m.member_id
		        WHERE ep.rank = 1 AND (m.phone LIKE :phone OR m.member_id LIKE :memberId OR m.member_name LIKE :name)
		        """;

		    Query<Object[]> query = session.createNativeQuery(sql);
		    query.setParameter("memberId", "%"+keyWord+"%");
		    query.setParameter("name", "%"+keyWord+"%");
		    query.setParameter("phone", "%"+keyWord+"%");

		    List<Object[]> results = query.getResultList();
//		    List<PointMemberDTO> pointSummaries = new ArrayList<>();
//
//		    for (Object[] result : results) {
//		    	PointMemberDTO dto = new PointMemberDTO();
//		        dto.setMemberId((Integer) result[0]);
//		        dto.setMemberName((String) result[1]);
//		        dto.setPhone((String) result[2]);
//		        dto.setTotalPointBalance((int) result[3]); 
//		        dto.setExpiringPoints((int) result[4]);    
//		        dto.setExpiryDate((Date) result[5]);       
//		        pointSummaries.add(dto);
//		    }

		    return results;
	} 
}
