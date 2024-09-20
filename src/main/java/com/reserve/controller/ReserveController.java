package com.reserve.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fatboyindustrial.gsonjavatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.members.bean.Member;
import com.reserve.bean.Reserve;
import com.reserve.bean.ReserveCheckBean;
import com.reserve.bean.ReserveDTO;
import com.reserve.bean.Restaurant;
import com.reserve.bean.TableType;
import com.reserve.service.ReserveService;
import com.reserve.service.RestaurantService;
import com.reserve.service.RestaurantTableService;
import com.reserve.service.TableTypeService;
import com.util.HibernateUtil;

//import com.fatboyindustrial.gsonjavatime.Converters;
//import com.fatboyindustrial.gsonjavatime.LocalDateConverter;
//import com.fatboyindustrial.gsonjavatime.LocalDateTimeConverter;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.reserve.bean.ReserveBean;
//import com.reserve.bean.ReserveCheckBean;
//import com.reserve.bean.RestaurantBean;
//import com.reserve.bean.TableTypeBean;
//import com.reserve.dao.ReserveDao;
//import com.reserve.dao.RestaurantDao;
//import com.reserve.dao.TableTypeDao;
//import com.reserve.service.ReserveService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/Reserve/*")
@Transactional
public class ReserveController{
//	private static final long serialVersionUID = 1L;

//	Session session = null;
	@Autowired
	TableTypeService tableTypeService;
	@Autowired
	RestaurantService restaurantService;
	@Autowired
	ReserveService reserveService;
	@Autowired
	RestaurantTableService restaurantTableService;
	
	
	/*
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
//		session = (Session) request.getAttribute("hibernateSession");
//		tableTypeService = new TableTypeService(session);
//		restaurantService = new RestaurantService(session);
//		reserveService = new ReserveService(session);
//		restaurantTableService = new RestaurantTableService(session);
		
		// 獲取URL中的操作名稱
		String action = request.getPathInfo().substring(1);

		System.out.println(action);

		switch (action) {
		case "getAllRestaurantName":
			getAllRestaurantName(request, response);
			break;
		case "addNew":
			addReserveNew(request, response);
			break;
		case "del":
			delReserve(request, response);
			break;
		case "get":
			getReserve(request, response);
			break;
		case "getAll":
			getAllReserve(request, response);
			break;
		case "set":
			setReserve(request, response);
			break;
		case "set2":
			setReserve2(request, response);
			break;
		case "list":
			getReserveList(request, response);
			break;
		case "listName":
			getAllRestaurantNameForList(request, response);
			break;
		case "checkTable":
			checkReserveTable(request, response);
			break;
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}

	}
	*/
	
	@GetMapping("getAllRestaurantName")
	private void getAllRestaurantName(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		List<String> restaurantNames = restaurantService.getAllRestaurantName();
		request.setAttribute("restaurantNames", restaurantNames);
		request.getRequestDispatcher("/reserve/AddReserveNew.jsp").forward(request, response);

	}
	
	@GetMapping("listName")
	private void getAllRestaurantNameForList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		List<String> restaurantNames = restaurantService.getAllRestaurantName();
		List<TableType> allTableType = tableTypeService.selectAll();
		request.setAttribute("tableTypes", allTableType);
		request.setAttribute("restaurantNames", restaurantNames);
		request.getRequestDispatcher("/reserve/GetListReserve.jsp").forward(request, response);
		
	}
	
	@GetMapping("list")
	private void getReserveList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String memberId = request.getParameter("memberId");
	    String memberName = request.getParameter("memberName");
	    String phone = request.getParameter("phone");
	    
	    String restaurantId = null;
	    if(request.getParameter("restaurantId").equals("---")) {
	    	restaurantId=null;
	    } else {
	    	restaurantId=restaurantService.getRestaurantId(request.getParameter("restaurantId"));
	    }
	    
	    String restaurantAddress = request.getParameter("restaurantAddress");
	    
	    String tableTypeId = null;
	    if(request.getParameter("tableTypeId").equals("---")) {
	    	tableTypeId=null;
	    } else {
	    	tableTypeId=request.getParameter("tableTypeId");
	    }
	    
	    
	    LocalDateTime reserveTime = null;
	    LocalDateTime finishedTime = null;
	    
	    if(request.getParameter("reserveTime")!=null && !request.getParameter("reserveTime").isEmpty()) {
	    	reserveTime = LocalDateTime.parse(request.getParameter("reserveTime"));
	    }
	    if(request.getParameter("finishedTime")!=null && !request.getParameter("finishedTime").isEmpty()) {
	    	finishedTime = LocalDateTime.parse(request.getParameter("finishedTime"));
	    }
	    
	    List<Reserve> reserves = reserveService.getReserveByCriteria(memberName, phone, memberId, restaurantId, tableTypeId, restaurantAddress, reserveTime, finishedTime);
	    List<ReserveDTO> reserveDTOs = new ArrayList<ReserveDTO>();
	    ReserveDTO reserveDTO = null;
	    
	    for(Reserve reserve : reserves) {
	    	reserveDTO =new ReserveDTO(reserve);
	    	reserveDTOs.add(reserveDTO);
	    }
	    
		GsonBuilder gsonBuilder = new GsonBuilder();
	    Converters.registerLocalDateTime(gsonBuilder);
	    Gson gson = gsonBuilder.create();
	    String json = gson.toJson(reserveDTOs);

	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(json);
		
		
	}
	
	
	@GetMapping("checkTable")
	private void checkReserveTable(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String restaurantId = restaurantService.getRestaurantId(request.getParameter("restaurantName"));
		String reserveSeat = request.getParameter("reserveSeat");
		String tableTypeId = reserveService.getTableTypeId(Integer.parseInt(reserveSeat));
		
		LocalDate checkDate = null;
		if(request.getParameter("checkDate")!=null && !request.getParameter("checkDate").isEmpty()) {
			checkDate = LocalDate.parse( request.getParameter("checkDate"));
		}
		
		System.out.println(restaurantId);
		System.out.println(reserveSeat);
		System.out.println(checkDate);
		
		List<ReserveCheckBean> reserveChecks = reserveService.getReserveCheck(restaurantId, tableTypeId, checkDate);
		
		System.out.println(reserveChecks);
		
		GsonBuilder gsonBuilder = new GsonBuilder();
	    Converters.registerLocalTime(gsonBuilder);
	    Gson gson = gsonBuilder.create();
		String json = gson.toJson(reserveChecks);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
		
	}
	
	@PostMapping("addNew")
	private void addReserveNew(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		// 餐廳
		String restaurantId = restaurantService.getRestaurantId(request.getParameter("restaurantName"));
		// 座位數
		Integer reserveSeat = Integer.parseInt(request.getParameter("reserveSeat"));
		// 依座位數預定對應桌子種類ID
		String tableTypeId = reserveService.getTableTypeId(reserveSeat);
		
		// 用餐時間範圍
		// DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDate checkDate = LocalDate.parse(request.getParameter("checkDate"));
		LocalTime reserveTime = LocalTime.parse(request.getParameter("reserveTime"));
		// 使用 atTime 方法將 LocalDate 和 LocalTime 合併成 LocalDateTime
		LocalDateTime reserveDateTime = checkDate.atTime(reserveTime);
		LocalDateTime finishedTime = reserveDateTime.plusMinutes(restaurantService.selectById(restaurantId).getEattime());
		
		// 會員ID
		String memberId = request.getParameter("memberId");
		
		Reserve reserve = new Reserve(memberId, restaurantId, reserveSeat, tableTypeId, reserveDateTime, finishedTime);
		reserveService.insert(reserve);
		request.getRequestDispatcher("/Reserve/listName").forward(request, response);
		
	}
	
	@GetMapping("del")
	private void delReserve(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		reserveService.delete(request.getParameter("reserveId"));
		request.getRequestDispatcher("/Reserve/listName").forward(request, response);

	}
	
	
	@GetMapping("get")
	private void getReserve(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Reserve reserve = reserveService.selectById(request.getParameter("reserveId"));
		request.setAttribute("reserve", reserve);
		request.getRequestDispatcher("/reserve/GetReserve.jsp").forward(request, response);

	}

	
	@GetMapping("getAll")
	private void getAllReserve(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		List<Reserve> reserves = reserveService.selectAll();
		request.setAttribute("reserves", reserves);
		request.getRequestDispatcher("/reserve/GetAllReserve.jsp").forward(request, response);

	}
	
	
	@GetMapping("set")
	private void setReserve(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Reserve reserve = reserveService.selectById(request.getParameter("reserveId"));
		request.setAttribute("reserve", reserve);
		request.getRequestDispatcher("/reserve/SetReserveNew.jsp").forward(request, response);

	}
	
	
	@PostMapping("set2")
	private void setReserve2(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Reserve reserve = reserveService.selectById(request.getParameter("reserveId"));
		
		// 餐廳
		String restaurantId = restaurantService.getRestaurantId(request.getParameter("restaurantName"));
		// 座位數
		Integer reserveSeat = Integer.parseInt(request.getParameter("reserveSeat"));
		// 依座位數預定對應桌子種類ID
		String tableTypeId = reserveService.getTableTypeId(reserveSeat);
		
		// 用餐時間範圍
		LocalDate checkDate = LocalDate.parse(request.getParameter("checkDate"));
		LocalTime reserveTime = LocalTime.parse(request.getParameter("reserveTime"));
		// 使用 atTime 方法將 LocalDate 和 LocalTime 合併成 LocalDateTime
		LocalDateTime reserveDateTime = checkDate.atTime(reserveTime);
		LocalDateTime finishedTime = reserveDateTime.plusMinutes(restaurantService.selectById(restaurantId).getEattime());
		
		// 會員ID
		String memberId = request.getParameter("memberId");
		
		reserve.setRestaurantId(restaurantId);
		reserve.setReserveSeat(reserveSeat);
		reserve.setTableTypeId(tableTypeId);
		reserve.setReserveTime(reserveDateTime);
		reserve.setFinishedTime(finishedTime);
		reserve.setMemberId(memberId);
		
		reserveService.update(reserve);
		
		request.getRequestDispatcher("/Reserve/listName").forward(request, response);

	}
	
	
	
}
