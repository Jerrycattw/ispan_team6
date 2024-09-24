package com.rent.dao;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rent.bean.Tableware;

@Repository
public class TablewareDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
//	private Session session;
//
//	public TablewareDao(Session session) {
//		this.session = session;
//	}
	
	public Tableware insert(Tableware tableware) {
		Session session = sessionFactory.getCurrentSession();
		if (tableware != null) {
			session.persist(tableware);
			return tableware;
		}
		return null;
	}
	
	public List<Tableware> getAll() {
		Session session = sessionFactory.getCurrentSession();
		List<Tableware> tablewares = session.createQuery("from Tableware", Tableware.class).list();
		return tablewares;
	}
	
	public Tableware getById(Integer tablewareId) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(Tableware.class, tablewareId);
	}
	
	public Tableware update(Tableware tableware) {
		Session session = sessionFactory.getCurrentSession();
		if(tableware!=null) {
			session.merge(tableware);
		}
		return tableware;
	}
	
	public List<Tableware> search(String keyword) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM Tableware WHERE tablewareName LIKE :keyword OR tablewareDescription LIKE :keyword";
		Query<Tableware> query = session.createQuery(hql, Tableware.class);
		query.setParameter("keyword", "%" + keyword + "%");
		List<Tableware> tablewares = query.getResultList();
		return tablewares;
	}
	
	public List<Integer> getTablewareId(){
		Session session = sessionFactory.getCurrentSession();
		String hql = "SELECT tablewareId FROM Tableware";
		Query<Integer> query = session.createQuery(hql, Integer.class);
		List<Integer> tablewareIds = query.getResultList();
		return tablewareIds;
	}
	

}
