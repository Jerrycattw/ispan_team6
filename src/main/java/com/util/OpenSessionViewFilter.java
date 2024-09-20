package com.util;

import java.io.IOException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;

//@WebFilter(urlPatterns = {"/*"})
public class OpenSessionViewFilter implements Filter {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse respones, FilterChain chain)
			throws IOException, ServletException {
		
		
//		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		request.setAttribute("hibernateSession", session);
		chain.doFilter(request, respones);
			
			
		
//		try {
//			
//			session.beginTransaction();
//			System.out.println("Transaction Begin");
//			
//			request.setAttribute("hibernateSession", session);
//			
//			chain.doFilter(request, respones);
//			
//			session.getTransaction().commit();
//			System.out.println("Tansaction Commit");
//			
//			
//		} catch (Exception e) {
//			session.getTransaction().rollback();
//			System.out.println("Tansaction Rollback");
//			e.printStackTrace();
//		} finally {
//			
//			System.out.println("Session Closed");
//			
//		}
		
		

	}

}
