package com.point.service;

import org.hibernate.Session;

public class PointServiceFactory {
    private PointService pointService;
    private PointSetService pointSetService;
    private Session session;

    // Constructor
    public PointServiceFactory(Session session) {
        this.session = session;
        this.pointSetService = new PointSetService(session);
        this.pointService = new PointService(session, pointSetService); // 在构造函数中直接初始化
    }

    // Getter for PointService
    public PointService getPointService() {
        return pointService;
    }

    // Setter for PointService (如果需要，可以定义 setter)
    public void setPointService(PointService pointService) {
        this.pointService = pointService;
    }

    // Getter for PointSetService
    public PointSetService getPointSetService() {
        return pointSetService;
    }

    // Setter for PointSetService (如果需要)
    public void setPointSetService(PointSetService pointSetService) {
        this.pointSetService = pointSetService;
    }
}
