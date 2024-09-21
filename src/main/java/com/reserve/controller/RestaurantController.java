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

//@MultipartConfig
@Controller
@RequestMapping("/Restaurant/*")
public class RestaurantController{

	@Autowired
	private TableTypeService tableTypeService;
	@Autowired
	private RestaurantService restaurantService;
	@Autowired
	private ReserveService reserveService;
	@Autowired
	private RestaurantTableService restaurantTableService;
	
	
	
	@PostMapping("add")
	public String addRestaurant(HttpServletRequest request, HttpServletResponse response)
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
		
		return "redirect:/Restaurant/getAll";

	}
	
	
	@GetMapping("del")
	public String delRestaurant(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		restaurantService.delete(request.getParameter("restaurantId"));

		return "redirect:/Restaurant/getAll";
	}
	
	
	@GetMapping("get")
	public String getRestaurant(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Restaurant restaurant = restaurantService.selectById(request.getParameter("restaurantId"));
		request.setAttribute("restaurant", restaurant);
		
		return "reserve/GetRestaurant";

	}
	
	
	
	@GetMapping("getAll")
	public String getAllRestaurant(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("test");
		
		List<Restaurant> restaurants = restaurantService.selectAll();
		request.setAttribute("restaurants", restaurants);
		
		
		return "reserve/GetAllRestaurants";

	}
	
	
	
	@GetMapping("list")
	public void getRestaurantList(HttpServletRequest request, HttpServletResponse response)
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
	    Converters.registerLocalTime(gsonBuilder);
	    Gson gson = gsonBuilder.create();
        String json = gson.toJson(restaurantDTOs);
	    
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
		
	}
	
	
	
	@GetMapping("set")
	public String setRestaurant(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String restaurantId = request.getParameter("restaurantId");
		Restaurant restaurant = restaurantService.selectById(restaurantId);
		request.setAttribute("restaurant", restaurant);
		
		
		return "reserve/SetRestaurant";

	}
	
	
	
	@PostMapping("set2")
	public String setRestaurant2(HttpServletRequest request, HttpServletResponse response)
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
		
		
		
		return "redirect:/Restaurant/getAll";

	}
	
}


