//package com.TogoOrder.controller;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.MultipartConfig;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.Part;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Paths;
//import java.util.List;
//
//import org.hibernate.Session;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.TogoOrder.bean.MenuBean;
//import com.TogoOrder.service.MenuServiceImpl;
//
//@Controller
//@RequestMapping("/MenuController/*")
//public class MenuController extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//	private MenuServiceImpl menuService;
//	
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		Session session = (Session) request.getAttribute("hibernateSession");
//		menuService = new MenuServiceImpl(session);		
//		// 獲取URL中的操作名稱
//		String action = request.getPathInfo().substring(1);
//		System.out.println(action);
//
//		switch (action) {
//			case "add":
//				addMenu(request, response);
//				break;
//			case "delete":
//				deleteMenu(request, response);
//				break;
//			case "get":
//				getMenu(request, response);
//				break;
//			case "getForUpdate":
//				getMenuForUpdate(request, response);
//				break;
//			case "getAll":
//				getAllMenu(request, response);
//				break;
//			case "update":
//				updateMenu(request, response);
//				break;
//			default:
//				response.sendError(HttpServletResponse.SC_NOT_FOUND);
//		}
//	}
//	
//	protected void addMenu(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
////		menuService = new MenuServiceImpl();
//		String foodName = request.getParameter("foodName");
//		Part filePart = request.getPart("foodPicture");
//		String foodPicture = "";
//        if (filePart != null && filePart.getSize() > 0) {
//            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
//            String uploadPath = getServletContext().getRealPath("/Togo/MenuImages");
//            // 設定圖片存儲的路徑
//            File uploadDir = new File(uploadPath);
//            if (!uploadDir.exists()) {
//                uploadDir.mkdirs();
//            }
//            filePart.write(uploadPath + File.separator + fileName); // 保存文件到服務器   
//            // 設置 foodPicture 變量為相對於應用根目錄的路徑
//            foodPicture = "/EEIT187-6/Togo/MenuImages/" + fileName;
//        }  
//		int foodPrice = Integer.parseInt(request.getParameter("foodPrice"));
//		String foodKind = request.getParameter("foodKind");
//		int foodStock = Integer.parseInt(request.getParameter("foodStock"));
//		String foodDescription = request.getParameter("foodDescription");
//		int foodStatus = Integer.parseInt(request.getParameter("foodStatus"));
//		MenuBean food = new MenuBean(foodName, foodPicture, foodPrice, foodKind, foodStock, foodDescription, foodStatus);		
//		menuService.addFood(food);
//		request.setAttribute("menu", food);		
//		request.getRequestDispatcher("/MenuController/getAll").forward(request, response);
//	}
//	
//	private String getFileName(Part part) {
//		String header = part.getHeader("Content-Disposition");
//		int slashIdx = header.lastIndexOf("\\");
//		String filename;
//		if (slashIdx != -1)
//			filename = header.substring(slashIdx + 1, header.length()-1);			
//		else {
//			int idx = header.indexOf("filename");
//			filename = header.substring(idx + 10, header.length()-1);
//		}
//		return filename;
//	}
//
//	protected void deleteMenu(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
////		menuService = new MenuServiceImpl();
//		int foodId = Integer.parseInt(request.getParameter("foodId"));
//		MenuBean food = new MenuBean();
//		boolean deleteFood = menuService.deleteFoodById(foodId);
//		if (deleteFood) {
//			request.setAttribute("deleteSuccess", true); // 添加成功標籤
//		} else {
//			request.setAttribute("deleteSuccess", false);
//		}
//		request.setAttribute("menu", food);
//		request.getRequestDispatcher("/MenuController/getAll").forward(request, response);			
//		
//	}
//
//	protected void getMenu(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
////		menuService = new MenuServiceImpl();
//		String foodName = request.getParameter("foodName");		
//		List<MenuBean> foodList = menuService.getFoodByName(foodName);
//		request.setAttribute("menu", foodList);		
//		request.getRequestDispatcher("/Togo/GetMenu.jsp").forward(request, response);
//	}
//	
//	protected void getMenuForUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
////		menuService = new MenuServiceImpl();
//		int foodId = Integer.parseInt(request.getParameter("foodId"));	
//		MenuBean foods = menuService.getFoodById(foodId);
//		request.setAttribute("menu", foods);	
//		request.getRequestDispatcher("/Togo/UpdateMenu.jsp").forward(request, response);
//	}
//	
//	protected void getAllMenu(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
////		menuService = new MenuServiceImpl();
// 		List<MenuBean> foodList = menuService.getAllFoods();		
//		request.setAttribute("foods", foodList);
//		request.getRequestDispatcher("/Togo/GetAllMenu.jsp").forward(request, response);
// 	}
//	
//	protected void updateMenu(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {				
////		menuService = new MenuServiceImpl();
//		int foodId = Integer.parseInt(request.getParameter("foodId"));
//		String foodName = request.getParameter("foodName");		
//		Part filePart = request.getPart("foodPicture");
//		MenuBean existingFood = menuService.getFoodById(foodId);
//		String foodPicture = existingFood.getFoodPicture();
//        if (filePart != null && filePart.getSize() > 0) {
//            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();	
//            String uploadPath = getServletContext().getRealPath("/Togo/MenuImages");
//            // 設定圖片存儲的路徑
//            File uploadDir = new File(uploadPath);
//            if (!uploadDir.exists()) {
//                uploadDir.mkdirs();
//            }
//            filePart.write(uploadPath + File.separator + fileName); // 保存文件到服務器   
//            // 設置 foodPicture 變量為相對於應用根目錄的路徑
//            foodPicture = "/EEIT187-6/Togo/MenuImages/" + fileName;
//        }
//		int foodPrice = Integer.parseInt(request.getParameter("foodPrice"));
//		String foodKind = request.getParameter("foodKind");
//		int foodStock = Integer.parseInt(request.getParameter("foodStock"));
//		String foodDescription = request.getParameter("foodDescription");
//		int foodStatus = Integer.parseInt(request.getParameter("foodStatus"));	
//		MenuBean food = new MenuBean(foodId, foodName, foodPicture, foodPrice, foodKind, foodStock, foodDescription, foodStatus);	
//		menuService.updateFoodById(food);
//		request.setAttribute("menu", food);	
//		request.setAttribute("updateSuccess", true); // 添加成功標籤
//		request.getRequestDispatcher("/MenuController/getAll").forward(request, response);
//	}
//
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		
//		doGet(request, response);
//	}
//
//}
