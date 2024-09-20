package com.util;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

//@WebListener
public class SessionFactoryListener implements ServletContextListener {


	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		HibernateUtil.getSessionFactory();
		System.out.println("SessionFactory Created");
//		ServletContextListener.super.contextInitialized(sce);
	}
	
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		HibernateUtil.closeSessionFactory();
		System.out.println("SessionFactory Closed");
//		ServletContextListener.super.contextDestroyed(sce);
	}
	
	
}
