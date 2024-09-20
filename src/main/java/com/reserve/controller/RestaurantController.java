package com.reserve.controller;

import java.io.File;
import java.io.IOException;
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
import com.reserve.bean.Restaurant;
import com.reserve.bean.RestaurantDTO;
import com.reserve.service.ReserveService;
import com.reserve.service.RestaurantService;
import com.reserve.service.RestaurantTableService;
import com.reserve.service.TableTypeService;
import com.util.HibernateUtil;

//import com.fatboyindustrial.gsonjavatime.Converters;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.reserve.bean.RestaurantBean;
//import com.reserve.dao.RestaurantDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

//@WebServlet("/Restaurant/*")
@Controller
//@MultipartConfig
@RequestMapping("/Restaurant/*")
//@Transactional
public class RestaurantController{
//	private static final long serialVersionUID = 1L;

//	Session session = null;
	@Autowired
	private TableTypeService tableTypeService;
	@Autowired
	private RestaurantService restaurantService;
	@Autowired
	private ReserveService reserveService;
	@Autowired
	private RestaurantTableService restaurantTableService;
	
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
		case "add":
			addRestaurant(request, response);
			break;
		case "del":
            delRestaurant(request, response);
			break;
		case "get":
			getRestaurant(request, response);
			break;
		case "getAll":
			getAllRestaurant(request, response);
			break;
		case "set":
			setRestaurant(request, response);
			break;
		case "set2":
			setRestaurant2(request, response);
			break;
		case "list":
			getRestaurantList(request, response);
			break;
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}

	}
	*/
	
	
	@PostMapping("add")
	private void addRestaurant(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Integer defaultStatus = 3;
		
		Restaurant restaurant = new Restaurant();
		String rname = request.getParameter("rname");
		restaurant.setRestaurantName(rname);
		restaurant.setRestaurantAddress(request.getParameter("raddress"));
		restaurant.setRestaurantPhone(request.getParameter("rphone"));
		LocalTime ropen = LocalTime.parse(request.getParameter("ropen"));
		LocalTime rclose = LocalTime.parse(request.getParameter("rclose"));
		restaurant.setRestaurantOpentime(ropen);
		restaurant.setRestaurantClosetime(rclose);
		restaurant.setEattime(Integer.parseInt(request.getParameter("reattime")));
		restaurant.setRestaurantStatus(defaultStatus);
		
//		String uploadPath = getServletContext().getRealPath("/") + "reserve" + File.separator + "restaurantIMG";
//	    File fileSaveDir = new File(uploadPath);
//	    if (!fileSaveDir.exists()) {
//	        fileSaveDir.mkdirs();
//	    }

	    Part part = request.getPart("rimg");
	    if (part != null && part.getSize() > 0) {
	        String fileName = part.getSubmittedFileName();
	        String extension = fileName.substring(fileName.lastIndexOf("."));
	        String newFileName = request.getParameter("rname") + "_" + System.currentTimeMillis() + extension;
	        
	        // 將檔案寫入指定路徑
//	        System.out.println(uploadPath + File.separator + newFileName);
//	        part.write(uploadPath + File.separator + newFileName);
	        String restaurantImg = "/EEIT187-6/reserve/restaurantIMG/" + newFileName;
	        System.out.println(restaurantImg);
	        restaurant.setRestaurantImg(restaurantImg);
	    }
		
		
		restaurantService.insert(restaurant);
		request.getRequestDispatcher("/Restaurant/getAll").forward(request, response);

	}
	
	
	@GetMapping("del")
	private void delRestaurant(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		restaurantService.delete(request.getParameter("restaurantId"));
		request.getRequestDispatcher("/Restaurant/getAll").forward(request, response);

	}
	
	
	@GetMapping("get")
	private void getRestaurant(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Restaurant restaurant = restaurantService.selectById(request.getParameter("restaurantId"));
		request.setAttribute("restaurant", restaurant);
		request.getRequestDispatcher("/reserve/GetRestaurant.jsp").forward(request, response);

	}
	
	
	
	@GetMapping("getAll")
	private void getAllRestaurant(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		List<Restaurant> restaurants = restaurantService.selectAll();
		request.setAttribute("restaurants", restaurants);
		request.getRequestDispatcher("/reserve/GetAllRestaurants.jsp").forward(request, response);

	}
	
	
	
	@GetMapping("list")
	private void getRestaurantList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	    String restaurantName = request.getParameter("restaurantName");
	    String restaurantAddress = request.getParameter("restaurantAddress");
	    String restaurantStatus = request.getParameter("restaurantStatus");

	    List<Restaurant> restaurants = restaurantService.selectList(restaurantName, restaurantAddress, restaurantStatus);
	    List<RestaurantDTO> restaurantDTOs = new ArrayList<RestaurantDTO>();
	    RestaurantDTO restaurantDTO = null;
	    for(Restaurant restaurant : restaurants) {
	    	restaurantDTO = new RestaurantDTO(restaurant);
	    	restaurantDTOs.add(restaurantDTO);
	    }
	    
	    
		GsonBuilder gsonBuilder = new GsonBuilder();
//		gsonBuilder.excludeFieldsWithoutExposeAnnotation();
	    Converters.registerLocalTime(gsonBuilder);
	    Gson gson = gsonBuilder.create();
        String json = gson.toJson(restaurantDTOs);
	    
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
		
	}
	
	
	
	@GetMapping("set")
	private void setRestaurant(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String restaurantId = request.getParameter("restaurantId");
		Restaurant restaurant = restaurantService.selectById(restaurantId);
		request.setAttribute("restaurant", restaurant);
		request.getRequestDispatcher("/reserve/SetRestaurant.jsp").forward(request, response);

	}
	
	
	
	@PostMapping("set2")
	private void setRestaurant2(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Restaurant restaurant = new Restaurant();
		String restaurantId = request.getParameter("restaurantId");
		
		restaurant.setRestaurantId(restaurantId);
		restaurant.setRestaurantName(request.getParameter("rname"));
		restaurant.setRestaurantAddress(request.getParameter("raddress"));
		restaurant.setRestaurantPhone(request.getParameter("rphone"));
		LocalTime ropen = LocalTime.parse(request.getParameter("ropen"));
		LocalTime rclose = LocalTime.parse(request.getParameter("rclose"));
		restaurant.setRestaurantOpentime(ropen);
		restaurant.setRestaurantClosetime(rclose);
		restaurant.setEattime(Integer.parseInt(request.getParameter("reattime")));
		restaurant.setRestaurantStatus(Integer.parseInt(request.getParameter("restaurantStatus")));
		
	    
//	    String uploadPath = getServletContext().getRealPath("/") + "reserve" + File.separator + "restaurantIMG";
//	    File fileSaveDir = new File(uploadPath);
//	    if (!fileSaveDir.exists()) {
//	        fileSaveDir.mkdirs();
//	    }

	    Part part = request.getPart("rimg");
	    if (part != null && part.getSize() > 0) {
	        String fileName = part.getSubmittedFileName();
	        String extension = fileName.substring(fileName.lastIndexOf("."));
	        String newFileName = request.getParameter("rname") + "_" + System.currentTimeMillis() + extension;
	        
	        // 將檔案寫入指定路徑
//	        System.out.println(uploadPath + File.separator + newFileName);
//	        part.write(uploadPath + File.separator + newFileName);
	        String restaurantImg = "/EEIT187-6/reserve/restaurantIMG/" + newFileName;
	        System.out.println(restaurantImg);
	        restaurant.setRestaurantImg(restaurantImg);
	    } else {
	        if (restaurantId != null) {
	            // 保留現有照片
	            Restaurant existingRestaurant = restaurantService.selectById(restaurantId);
	            restaurant.setRestaurantImg(existingRestaurant.getRestaurantImg());
	        }
	    }
		
		restaurantService.update(restaurant);
		request.getRequestDispatcher("/Restaurant/getAll").forward(request, response);

	}
	
}


