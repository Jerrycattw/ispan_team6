package com.point.service;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.point.bean.PointSetBean;
import com.point.dao.PointSetDAO;

@Service
@Transactional
public class PointSetService {
	
	@Autowired
	private PointSetDAO pointSetDao;
	
//	public PointSetService(Session session) {
//		pointSetDao = new PointSetDAO(session);
//	}
	
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
	
	
	public Boolean UpdatePoint(PointSetBean pointSet) {
		return pointSetDao.UpdatePoint(pointSet);
		
	}
}
