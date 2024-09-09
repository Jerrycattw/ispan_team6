package com.point.service;

import org.hibernate.Session;

import com.point.bean.PointSetBean;
import com.point.dao.PointSetDAO;

public class PointSetService {
	private PointSetDAO pointSetDao;
	
	public PointSetService(Session session) {
		pointSetDao = new PointSetDAO(session);
	}
	
	public PointSetBean getPointSet() {
		PointSetBean pointSet = pointSetDao.getPointSet();
		
		if (pointSet==null) {
			pointSet=new PointSetBean();
			pointSet.setPointSetName("general");
			pointSet.setAmountPerPoint(100);
			pointSet.setPointsEarned(1);
			pointSet.setPointRatio(2);
			pointSet.setExpiryMonth("1");
			pointSet.setExpiryDay("1");
			pointSet.setBirthType("當月");
			pointSet.setSetDescription("test");
			pointSet.setIsExpiry("isExpiry");
		}
		return pointSet;
	}
	
	
	public Boolean UpdatePoint(int anountPerPoint,int pointsEarned,int pointRatio,String expiryMonth,String expiryday,String birthDay,String setDescription,String isExpiry) {
		return pointSetDao.UpdatePoint(anountPerPoint, pointsEarned, pointRatio, expiryMonth, expiryday, birthDay, setDescription, isExpiry);
		
	}
}
