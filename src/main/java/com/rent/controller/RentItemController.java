package com.rent.controller;

import java.io.IOException;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.rent.bean.RentItem;
import com.rent.dao.RentItemDao;
import com.rent.service.RentItemService;
import com.util.HibernateUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/rentItemController/*")
public class RentItemController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getPathInfo().substring(1);
		System.out.println(action);
		switch (action) {
		case "insert":
			insert(request, response);
			break;
		case "search":
			search(request, response);
			break;
		case "getAll":
			getAll(request, response);
			break;
		case "get":
			getByIds(request, response);
			break;
		case "update":
			update(request, response);
			break;
		case "restore":
			restore(request, response);
			break;
		case "delete":
			delete(request, response);
			break;
		}
	}
	
	

	protected void insert(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		RentItemService rentItemService = new RentItemService(session);
		int rentId = Integer.parseInt(request.getParameter("rent_id"));
		int tablewareId = Integer.parseInt(request.getParameter("tableware_id"));
		int rentItemQuantity = Integer.parseInt(request.getParameter("rent_item_quantity"));
		int rentItemDeposit = Integer.parseInt(request.getParameter("rent_item_deposit"));

		RentItem rentItem = rentItemService.insert(rentId, tablewareId, rentItemQuantity, rentItemDeposit);
		request.getRequestDispatcher("/rentItemController/getAll").forward(request, response);
	}

	protected void search(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		RentItemService rentItemService = new RentItemService(session);
		int rentId = Integer.parseInt(request.getParameter("rent_id"));
		List<RentItem> rentItems = rentItemService.getById(rentId);
		request.setAttribute("rentItem", rentItems);
		request.getRequestDispatcher("/rent_item/getAll.jsp").forward(request, response);
	}
	
	protected void getAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		RentItemService rentItemService = new RentItemService(session);
		List<RentItem> rentItems = rentItemService.getAll();
		request.setAttribute("rentItem", rentItems);
		request.getRequestDispatcher("/rent_item/getAll.jsp").forward(request, response);
	}
	
	protected void getByIds(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		RentItemService rentItemService = new RentItemService(session);
		int rentId = Integer.parseInt(request.getParameter("rent_id"));
		int tablewareId = Integer.parseInt(request.getParameter("tableware_id"));
		RentItem rentItem = rentItemService.getByIds(rentId, tablewareId);
		request.setAttribute("rentItem", rentItem);
		String action = request.getParameter("action");
	    if ("update".equals(action)) {
	        request.getRequestDispatcher("/rent_item/update.jsp").forward(request, response);
	    } else if ("restore".equals(action)) {
	        request.getRequestDispatcher("/rent_item/restore.jsp").forward(request, response);
	    }
	}
	
	protected void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		RentItemService rentItemService = new RentItemService(session);
		int rentId = 0;
		int tablewareId = 0;
		if(Integer.parseInt(request.getParameter("rent_id")) != 0) {
			rentId = Integer.parseInt(request.getParameter("rent_id"));  
			tablewareId = Integer.parseInt(request.getParameter("tableware_id"));
		}
		int rentItemQuantity = Integer.parseInt(request.getParameter("rent_item_quantity"));
		int rentItemDeposit = Integer.parseInt(request.getParameter("rent_item_deposit"));
		String returnMemo = request.getParameter("return_memo");
		int returnStatus = Integer.parseInt(request.getParameter("return_status"));

		Boolean isUpdated = rentItemService.update( rentId, tablewareId, rentItemQuantity, rentItemDeposit,
				returnMemo, returnStatus );
		request.getRequestDispatcher("/rentItemController/getAll").forward(request, response);
	}

	protected void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		RentItemService rentItemService = new RentItemService(session);
		int rentId = Integer.parseInt(request.getParameter("rent_id"));
		int tablewareId = Integer.parseInt(request.getParameter("tableware_id"));
		RentItem rentItem = rentItemService.getByIds(rentId, tablewareId);
		Boolean isDeleted = rentItemService.delete(rentItem);
		request.getRequestDispatcher("/rentItemController/getAll").forward(request, response);
	}
	
	protected void restore(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		RentItemService rentItemService = new RentItemService(session);
		int rentId = Integer.parseInt(request.getParameter("rent_id"));
		int tablewareId = Integer.parseInt(request.getParameter("tableware_id"));
		String returnMemo = request.getParameter("return_memo");
		int returnStatus = Integer.parseInt(request.getParameter("return_status"));

		RentItem rentItem = rentItemService.restore(rentId, tablewareId, returnMemo, returnStatus);
		request.getRequestDispatcher("/rentItemController/getAll").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
