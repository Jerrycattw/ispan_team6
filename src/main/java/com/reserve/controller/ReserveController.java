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
public class ReserveController{
	
	@Autowired
	TableTypeService tableTypeService;
	@Autowired
	RestaurantService restaurantService;
	@Autowired
	ReserveService reserveService;
	@Autowired
	RestaurantTableService restaurantTableService;
	
	
	
	@GetMapping("getAllRestaurantName")
	public String getAllRestaurantName(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		List<String> restaurantNames = restaurantService.getAllRestaurantName();
		request.setAttribute("restaurantNames", restaurantNames);
		
		return "reserve/AddReserveNew";

	}
	
	
	
	
	@GetMapping("listName")
	public String getAllRestaurantNameForList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		List<String> restaurantNames = restaurantService.getAllRestaurantName();
		List<TableType> allTableType = tableTypeService.selectAll();
		request.setAttribute("tableTypes", allTableType);
		request.setAttribute("restaurantNames", restaurantNames);
		
		
		return "reserve/GetListReserve";
	}
	
	
	
	@GetMapping("list")
	public void getReserveList(HttpServletRequest request, HttpServletResponse response)
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
	public void checkReserveTable(HttpServletRequest request, HttpServletResponse response)
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
	public String addReserveNew(HttpServletRequest request, HttpServletResponse response)
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
		
		return "redirect:/Reserve/listName";
		
	}
	
	@GetMapping("del")
	public String delReserve(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		reserveService.delete(request.getParameter("reserveId"));

		
		return "redirect:/Reserve/listName";

	}
	
	
	@GetMapping("get")
	public String getReserve(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Reserve reserve = reserveService.selectById(request.getParameter("reserveId"));
		request.setAttribute("reserve", reserve);
		
		return "reserve/GetReserve";

	}

	
	@GetMapping("getAll")
	public String getAllReserve(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		List<Reserve> reserves = reserveService.selectAll();
		request.setAttribute("reserves", reserves);

		
		return "reserve/GetAllReserve";

	}
	
	
	@GetMapping("set")
	public String setReserve(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Reserve reserve = reserveService.selectById(request.getParameter("reserveId"));
		request.setAttribute("reserve", reserve);
		
		
		return "reserve/SetReserveNew";
		

	}
	
	
	@PostMapping("set2")
	public String setReserve2(HttpServletRequest request, HttpServletResponse response)
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
		
		return "redirect:/Reserve/listName";

	}
	
	
	
}
