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
import com.point.bean.PointSetBean;

@Repository
public class PointSetDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
//	private Session session;
//
//	public PointSetDAO(Session session) {
//		this.session = session;
//	}
	

	//查詢所有(總共也只有一筆)
	public PointSetBean getPointSet() {
		//"SELECT * FROM point_set"
		Session session = sessionFactory.getCurrentSession();
		Query<PointSetBean> query = session.createQuery("from PointSetBean",PointSetBean.class);
		return query.uniqueResult();
	}
	//修改
		public Boolean UpdatePoint(PointSetBean pointSetBean) {
			//"UPDATE point_set SET amount_per_point=?,points_earned=?,point_ratio=?, expiry_month=?,expiry_day=?,birth_type=?,set_description=?,is_expiry=?"
			Session session = sessionFactory.getCurrentSession();
			PointSetBean resultBean = session.get(PointSetBean.class,pointSetBean.getPointSetName());
			
			if(resultBean!=null) {
				resultBean.setAmountPerPoint(pointSetBean.getAmountPerPoint());
				resultBean.setPointsEarned(pointSetBean.getPointsEarned());
				resultBean.setPointRatio(pointSetBean.getPointRatio());
				resultBean.setExpiryMonth(pointSetBean.getExpiryMonth());
				resultBean.setExpiryDay(pointSetBean.getExpiryDay());
				resultBean.setBirthType(pointSetBean.getBirthType());
				resultBean.setSetDescription(pointSetBean.getSetDescription());
				resultBean.setIsExpiry(pointSetBean.getIsExpiry());
				return true;
			}
			return false;
			
		}
	
}
