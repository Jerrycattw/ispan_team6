package com.point.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.point.bean.PointBean;
import com.point.bean.PointMemberBean;
import com.point.dto.PointMemberDTO;

@Repository
public class PointDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
//	private Session session;
//
//	public PointDAO(Session session) {
//		this.session = session;
//	}
	
	//查詢所有紀錄BY memberID
	public List<PointBean> getAllRecord(int memberID) {
		//"SELECT * FROM point WHERE member_id=?"
		Session session = sessionFactory.getCurrentSession();
		Query<PointBean> query = session.createQuery("from PointBean p WHERE p.memberID=:memberID ",PointBean.class);
		query.setParameter("memberID", memberID);
		return query.getResultList();
	}
	
	//查詢所有紀錄 
	public PointBean getOneRecord(int pointID) {
		//SELECT * FROM point WHERE point_id=?"
		Session session = sessionFactory.getCurrentSession();
		return session.get(PointBean.class, pointID);
		
	}
		
	//OK//查詢(會員、點數餘額、電話) all
	public  List<Object[]> getAllPointMember() {
		Session session = sessionFactory.getCurrentSession();
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

			    return results;
	}
	//OK//查詢(會員、點數餘額、電話) bymemberID (return bean)
	public  Object[] getPointMember(int memberId) {
		Session session = sessionFactory.getCurrentSession();
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
		    return results;
	}	
	
	//修改
	public void updatePoint(PointBean pointBean) {
		//"UPDATE point SET point_change=?,created_date=?,get_expiry_date=?, point_usage=?,transaction_id=?,transaction_type=? WHERE point_id=?"
		Session session = sessionFactory.getCurrentSession();
		PointBean resultBean = session.get(PointBean.class,pointBean.getPointID());
		
		if (resultBean != null) {
			resultBean.setPointChange(pointBean.getPointChange());
			resultBean.setCreateDate(pointBean.getCreateDate());
			resultBean.setExpiryDate(pointBean.getExpiryDate());
			resultBean.setPointUsage(pointBean.getPointUsage());
			resultBean.setTransactionID(pointBean.getTransactionID());
			resultBean.setTransactionType(pointBean.getTransactionType());
		}
	}
	//刪除單筆紀錄
	public void deleteOneRecord(int pointID) {
		//"DELETE FROM point WHERE point_id=?"
		Session session = sessionFactory.getCurrentSession();
		PointBean pointBean = session.get(PointBean.class,pointID);
		if (pointBean !=null) {
			session.remove(pointBean);
		}
	}
	//新增單筆紀錄
	public void insertOneRecord(PointBean pointBean) {
		//"INSERT INTO point (member_id,point_change,created_date,get_expiry_date,transaction_id,transaction_type) VALUES(?,?,?,?,?,?)"
		Session session = sessionFactory.getCurrentSession();
		session.persist(pointBean);

	}
	
	//模糊查詢
	public  List<Object[]> searchPointMember(String keyWord) {
		Session session = sessionFactory.getCurrentSession();
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
		    return results;
	} 
}
