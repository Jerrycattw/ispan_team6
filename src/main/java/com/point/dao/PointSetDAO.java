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
import com.point.bean.PointSetBean;


public class PointSetDAO {
	private Session session;

	public PointSetDAO(Session session) {
		this.session = session;
	}
	

	//查詢所有(總共也只有一筆)
	public PointSetBean getPointSet() {
		//"SELECT * FROM point_set"
		Query<PointSetBean> query = session.createQuery("from PointSetBean",PointSetBean.class);
		return query.uniqueResult();
	}
	//修改
		public Boolean UpdatePoint(int anountPerPoint,int pointsEarned,int pointRatio,String expiryMonth,String expiryday,String birthDay,String setDescription,String isExpiry) {
			//"UPDATE point_set SET amount_per_point=?,points_earned=?,point_ratio=?, expiry_month=?,expiry_day=?,birth_type=?,set_description=?,is_expiry=?"
			PointSetBean pointSetBean = session.get(PointSetBean.class,"general");
			
			if(pointSetBean!=null) {
				pointSetBean.setAmountPerPoint(anountPerPoint);
				pointSetBean.setPointsEarned(pointsEarned);
				pointSetBean.setPointRatio(pointRatio);
				pointSetBean.setExpiryMonth(expiryMonth);
				pointSetBean.setExpiryDay(expiryday);
				pointSetBean.setBirthType(birthDay);
				pointSetBean.setSetDescription(setDescription);
				pointSetBean.setIsExpiry(isExpiry);
				return true;
			}
			return false;
			
		}
	
}
