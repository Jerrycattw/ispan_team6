package com.rent.controller;

import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import com.rent.bean.Rent;
import com.rent.bean.RentItem;
import com.rent.bean.Tableware;
import com.rent.service.RentItemService;
import com.rent.service.RentService;
import com.rent.service.TablewareService;
import com.reserve.service.RestaurantService;
import com.util.HibernateUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/rentController/*")
public class RentController extends HttpServlet {

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
		case "getOver":
			getOver(request, response);
			break;
		case "getById":
			getById(request, response);
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
		case "getOption":
			getOption(request,response);
			break;
		case "searchOption":
			searchOption(request,response);
			break;
		}
	}
	
	protected void getOption(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    try {
	        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	        TablewareService tablewareService = new TablewareService(session);
	        RestaurantService restaurantService = new RestaurantService(session);
	        List<String> restaurantNames = restaurantService.getAllRestaurantName();
	        List<Integer> tablewareIds = tablewareService.getTablewareIds();
	        request.setAttribute("restaurantNames", restaurantNames);
	        request.setAttribute("tablewareIds", tablewareIds);
	        request.getRequestDispatcher("/rent/insert.jsp").forward(request, response);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	protected void insert(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		RentService rentService = new RentService(session);
		RentItemService rentItemService = new RentItemService(session);
		RestaurantService restaurantService = new RestaurantService(session);
		
		java.util.Date rentDate = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(rentDate);
		calendar.add(Calendar.DAY_OF_YEAR, 7);
		java.util.Date dueDate = calendar.getTime();
		
		int rentDeposit = Integer.parseInt(request.getParameter("rent_deposit"));
		String restaurantId = restaurantService.getRestaurantId(request.getParameter("restaurantName"));
		int memberId = Integer.parseInt(request.getParameter("member_id"));
		
		Rent rent = rentService.insert(rentDeposit, rentDate, restaurantId, memberId, dueDate);
		session.flush();
		int rentId = rent.getRentId();
		
		List<RentItem> rentItems = new ArrayList<>();
		int index = 0;
		while (true) {
            String tablewareIdParam = request.getParameter("tablewareId" + index);
            String rentItemQuantityParam = request.getParameter("rentItemQuantity" + index);
            String rentItemDepositParam = request.getParameter("rentItemDeposit" + index);

            if (tablewareIdParam == null) {
                break; // 如果没有更多的tablewareIdParam，退出循环
            }
            System.out.println("Rent Item " + index + ":");
            System.out.println("Tableware ID: " + tablewareIdParam);
            System.out.println("Quantity: " + rentItemQuantityParam);
            System.out.println("Deposit: " + rentItemDepositParam);

            Integer tablewareId = Integer.parseInt(tablewareIdParam);
            Integer rentItemQuantity = Integer.parseInt(rentItemQuantityParam);
            Integer rentItemDeposit = Integer.parseInt(rentItemDepositParam);

            RentItem rentItem = new RentItem(rentId,tablewareId,rentItemQuantity,rentItemDeposit,"未歸還",1);
            rentItems.add(rentItem);
            
            index++; // 处理下一个RentItem
        }
		for (RentItem rentItem : rentItems) {
	        rentItemService.insert(rentItem.getRentId(), rentItem.getTablewareId(), rentItem.getRentItemQuantity(), rentItem.getRentItemDeposit());
	    }
		response.sendRedirect(request.getContextPath() +"/rentController/getAll");
	}

	protected void getById(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		RentService rentService = new RentService(session);
		int rentId = Integer.parseInt(request.getParameter("rent_id"));
		Rent rent = rentService.getById(rentId);
		RestaurantService restaurantService = new RestaurantService(session);
		List<String> restaurantNames = restaurantService.getAllRestaurantName();
		request.setAttribute("restaurantNames", restaurantNames);
		request.setAttribute("rent", rent);
		String action = request.getParameter("action");
		if ("update".equals(action)) {
	        request.getRequestDispatcher("/rent/update.jsp").forward(request, response);
	    } else if ("restore".equals(action)) {
	        request.getRequestDispatcher("/rent/restore.jsp").forward(request, response);
	    }
	}

	protected void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		RentService rentService = new RentService(session);
		RestaurantService restaurantService = new RestaurantService(session);
		try {
			Integer rentId = Integer.parseInt(request.getParameter("rent_id"));
			Integer rentDeposit = Integer.parseInt(request.getParameter("rent_deposit"));
			Date rentDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("rent_date"));
			String restaurantId = restaurantService.getRestaurantId(request.getParameter("restaurantName"));
			Integer memberId = Integer.parseInt(request.getParameter("member_id"));
			Date dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("due_date"));
			Date returnDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("return_date"));
			Integer rentStatus = Integer.parseInt(request.getParameter("rent_status"));
			String rentMemo = request.getParameter("rent_memo");
			String returnRestaurantId = restaurantService.getRestaurantId(request.getParameter("restaurantName"));

			Rent rent = rentService.update(rentId, rentDeposit, rentDate, restaurantId, memberId, dueDate, returnDate,
					rentStatus, rentMemo, returnRestaurantId);
			response.sendRedirect(request.getContextPath() +"/rentController/getAll");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		RentService rentService = new RentService(session);
		RentItemService rentItemService = new RentItemService(session);
		Integer rentId = Integer.parseInt(request.getParameter("rent_id"));
		
		List<RentItem> rentItems = rentItemService.getById(rentId);
		for(RentItem rentItem: rentItems) {
			rentItemService.delete(rentItem);
		}
		rentService.delete(rentId);
		response.sendRedirect(request.getContextPath() + "/rentController/getAll");
	}

	protected void restore(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		RentService rentService = new RentService(session);
		RestaurantService restaurantService = new RestaurantService(session);
		try {
			Integer rentId = Integer.parseInt(request.getParameter("rent_id"));
			Date returnDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("return_date"));
			String returnRestaurantId = restaurantService.getRestaurantId(request.getParameter("restaurantName"));
			Rent rent = rentService.restore(rentId, returnDate, returnRestaurantId);
			response.sendRedirect(request.getContextPath() +"/rentController/getAll");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void getAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		RentService rentService = new RentService(session);
		List<Rent> rents = rentService.getAll();
		request.setAttribute("rent", rents);
		request.getRequestDispatcher("/rent/getAll.jsp").forward(request, response);
	}
	
	protected void searchOption(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		RentService rentService = new RentService(session);
		RestaurantService restaurantService = new RestaurantService(session);
		List<Integer> rentIds = rentService.getRentId();
		List<String> restaurantNames = restaurantService.getAllRestaurantName();
		request.setAttribute("rentIds", rentIds);
		request.setAttribute("restaurantNames", restaurantNames);
		request.getRequestDispatcher("/rent/search.jsp").forward(request, response);
	}

	protected void search(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		RentService rentService = new RentService(session);
		RestaurantService restaurantService = new RestaurantService(session);
		try {
			Integer rentId = request.getParameter("rentId") != null && !request.getParameter("rentId").isEmpty() 
	                ? Integer.parseInt(request.getParameter("rentId")) : null;
	        Integer memberId = request.getParameter("member_id") != null && !request.getParameter("member_id").isEmpty() 
	                ? Integer.parseInt(request.getParameter("member_id")) : null;
	        String restaurantId = restaurantService.getRestaurantId(request.getParameter("restaurantName")) != null && !restaurantService.getRestaurantId(request.getParameter("restaurantName")).isEmpty() 
	                ?restaurantService.getRestaurantId(request.getParameter("restaurantName")) : null;
	        Integer rentStatus = request.getParameter("rent_status") != null && !request.getParameter("rent_status").isEmpty() 
	                ? Integer.parseInt(request.getParameter("rent_status")) : null;
	        Date rentDateStart = request.getParameter("rent_date_start") != null && !request.getParameter("rent_date_start").isEmpty() 
	                ? new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("rent_date_start")) : null;
	        Date rentDateEnd = request.getParameter("rent_date_end") != null && !request.getParameter("rent_date_end").isEmpty() 
	                ? new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("rent_date_end")) : null;

			List<Rent> rents = rentService.getByMany(rentId, memberId, restaurantId, rentStatus, rentDateStart,
					rentDateEnd);
			request.setAttribute("rent", rents);
			request.getRequestDispatcher("/rent/getAll.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void getOver(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		RentService rentService = new RentService(session);

		List<Rent> rents = rentService.getOver();
		request.setAttribute("rent", rents);
		request.getRequestDispatcher("/rent/getAll.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
