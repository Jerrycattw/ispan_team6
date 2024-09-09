package com.reserve.controller;

import java.io.IOException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.reserve.bean.TableType;
import com.reserve.service.ReserveService;
import com.reserve.service.RestaurantService;
import com.reserve.service.RestaurantTableService;
import com.reserve.service.TableTypeService;
import com.util.HibernateUtil;

//import com.reserve.bean.RestaurantBean;
//import com.reserve.bean.TableTypeBean;
//import com.reserve.dao.RestaurantDao;
//import com.reserve.dao.TableTypeDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/TableType/*")
public class TableTypeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

//	SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	Session session = null;
	TableTypeService tableTypeService = null;
	RestaurantService restaurantService = null;
	ReserveService reserveService = null;
	RestaurantTableService restaurantTableService = null;
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		session = (Session) request.getAttribute("hibernateSession");
		tableTypeService = new TableTypeService(session);
		restaurantService = new RestaurantService(session);
		reserveService = new ReserveService(session);
		restaurantTableService = new RestaurantTableService(session);

		
		// 獲取URL中的操作名稱
		String action = request.getPathInfo().substring(1);

		System.out.println(action);

		switch (action) {
		case "add":
			addTableType(request, response);
			break;
		case "del":
			delTableType(request, response);
			break;
		case "getAllType":
			getAllTableType(request, response);
			break;
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}

	}

	private void addTableType(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
//		Session session = sessionFactory.getCurrentSession();
//		TableTypeService tableTypeService = new TableTypeService(session);
		
		String tableTypeId = request.getParameter("tableTypeId");
		String tableTypeName = request.getParameter("tableTypeName");
		TableType tableType = new TableType(tableTypeId, tableTypeName);
		tableTypeService.insert(tableType);
		request.getRequestDispatcher("/TableType/getAllType").forward(request, response);

	}


	private void delTableType(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

//		Session session = sessionFactory.getCurrentSession();
//		TableTypeService tableTypeService = new TableTypeService(session);
		String tableTypeId = request.getParameter("tableTypeId");
		tableTypeService.delete(tableTypeId);
		request.getRequestDispatcher("/TableType/getAllType").forward(request, response);

	}


	private void getAllTableType(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
//		Session session = sessionFactory.getCurrentSession();
//		TableTypeService tableTypeService = new TableTypeService(session);
		List<TableType> tableTypes = tableTypeService.selectAll();
		
		request.setAttribute("tableTypes", tableTypes);
		request.getRequestDispatcher("/reserve/GetAllTableTypes.jsp").forward(request, response);

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
