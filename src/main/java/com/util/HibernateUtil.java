package com.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
	
	//確保只會產生一個 session factory 物件
	private static final SessionFactory facotry = createSessionFactory();
	
	
	private static SessionFactory createSessionFactory() {
		StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
		SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		return sessionFactory;
	}
	
	//使用getter拿到共用的 session factory
	public static SessionFactory getSessionFactory() {
		return facotry;
	}
	
	//關閉資源
	public static void closeSessionFactory() {
		if(facotry!=null) {
			facotry.close();
		}
	}
	
	
	
	

}
