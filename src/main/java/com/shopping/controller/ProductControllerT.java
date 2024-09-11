package com.shopping.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.shopping.bean.ProductBean;
import com.util.HibernateUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ProductControllerT/*")
public class ProductControllerT extends HttpServlet {
	private static final long serialVersionUID = 1L;
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.getCurrentSession();
		
		
		ProductBean productBean = session.get(ProductBean.class, 3);
		System.out.println(productBean.getProductName());
		
//			Query<ProductBean> query =session.createQuery("from product", ProductBean.class);
//			List<ProductBean> resultsBeans=query.list();
//			
//			 for (ProductBean queryBean : resultsBeans) {
//	                System.out.println("Product ID: " + queryBean.getProductId());
//	                System.out.println("Product ID: " + queryBean.getProductName());
//	            }
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
