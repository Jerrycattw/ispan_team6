package com.point.service;

import org.hibernate.Session;

public class PointServiceFactory {
	private PointSetService pointSetService;
	private Session session;

	public PointServiceFactory(Session session) {
		this.session = session;
		this.pointSetService = new PointSetService(session);
	}


	public PointService createPointService() {
		return new PointService(session, pointSetService);
	}

	public PointSetService getPointSetService() {
		return pointSetService;
	}
}
