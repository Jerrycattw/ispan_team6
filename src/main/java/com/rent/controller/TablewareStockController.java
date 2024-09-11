package com.rent.controller;

import java.io.IOException;
import java.util.List;

import javax.xml.transform.Source;

import org.hibernate.Session;

import com.rent.bean.TablewareStock;
import com.rent.service.TablewareStockService;
import com.util.HibernateUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/tablewareStockController/*")
@SuppressWarnings("unchecked")
public class TablewareStockController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getPathInfo().substring(1);
		System.out.println(action);
		switch (action) {
		case "insert":
			insert(request, response);
			break;
		case "getAll":
			getAll(request, response);
			break;
		case "search":
			search(request, response);
			break;
		case "get":
			getById(request, response);
			break;
		case "update":
			update(request, response);
			break;
		}
	}

	protected void insert(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		TablewareStockService tablewareStockService = new TablewareStockService(session);

		int tablewareId = Integer.parseInt(request.getParameter("tableware_id"));
		int restaurantId = Integer.parseInt(request.getParameter("restaurant_id"));
		int stock = Integer.parseInt(request.getParameter("stock"));
		try {
			TablewareStock tablewareStock = tablewareStockService.insert(tablewareId, restaurantId, stock);
			request.setAttribute("tableware_stock", tablewareStock);
			request.getRequestDispatcher("/tablewareStockController/getAll").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void getAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		TablewareStockService tablewareStockService = new TablewareStockService(session);
		try {
			List<TablewareStock> tablewareStocks = tablewareStockService.getAll();
			request.setAttribute("stock", tablewareStocks);
			request.getRequestDispatcher("/tableware_stock/getAll.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		TablewareStockService tablewareStockService = new TablewareStockService(session);

		int tablewareId = Integer.parseInt(request.getParameter("tableware_id"));
		int restaurantId = Integer.parseInt(request.getParameter("restaurant_id"));
		Integer stock = Integer.parseInt(request.getParameter("stock"));
		
		try {
			TablewareStock tablewareStock = tablewareStockService.update(tablewareId,restaurantId,stock);
			request.setAttribute("stock", tablewareStock);
			request.getRequestDispatcher("/tablewareStockController/getAll").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void search(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		TablewareStockService tablewareStockService = new TablewareStockService(session);

		Integer restaurantId = request.getParameter("restaurant_id") != null
				? Integer.parseInt(request.getParameter("restaurant_id"))
				: null;
		Integer tablewareId = request.getParameter("tableware_id") != null
				? Integer.parseInt(request.getParameter("tableware_id"))
				: null;

		List<TablewareStock> tablewareStocks = tablewareStockService.search(restaurantId, tablewareId);
		request.setAttribute("stock", tablewareStocks);
		request.getRequestDispatcher("/tableware_stock/getAll.jsp").forward(request, response);
	}
	
	protected void getById(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		TablewareStockService tablewareStockService = new TablewareStockService(session);
		try {
			int tablewareId = Integer.parseInt(request.getParameter("tableware_id"));
			int restaurantId = Integer.parseInt(request.getParameter("restaurant_id"));
			TablewareStock tablewareStock = tablewareStockService.getById(tablewareId, restaurantId);
			request.setAttribute("stock", tablewareStock);
			request.getRequestDispatcher("/tableware_stock/update.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
