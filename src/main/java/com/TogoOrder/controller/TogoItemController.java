//package com.TogoOrder.controller;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.List;
//
//import javax.xml.transform.Source;
//
//import org.hibernate.Session;
//
//import com.TogoOrder.bean.MenuBean;
//import com.TogoOrder.bean.TogoItemBean;
//import com.TogoOrder.dao.MenuDaoImpl;
//import com.TogoOrder.dao.TogoDaoImpl;
//import com.TogoOrder.dao.TogoItemDaoImpl;
//import com.TogoOrder.service.MenuServiceImpl;
//import com.TogoOrder.service.TogoItemServiceImpl;
//import com.TogoOrder.service.TogoServiceImpl;
//import com.TogoOrder.util.TogoCalculateUtils;
//
//
//
//@WebServlet("/TogoItemController/*")
//public class TogoItemController extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//	private TogoItemServiceImpl togoItemService;
//	private TogoServiceImpl togoService;
//	private MenuServiceImpl menuService;
//       
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		Session session = (Session) request.getAttribute("hibernateSession");
//		togoItemService = new TogoItemServiceImpl(session);
//		togoService = new TogoServiceImpl(session);
//		menuService = new MenuServiceImpl(session);
//		
//		// 獲取URL中的操作名稱
//		String action = request.getPathInfo().substring(1);
//		System.out.println(action);
//
//		switch (action) {
//			case "add":
//				addTogoItem(request, response);
//				break;
//			case "delete":
//	            deleteTogoItem(request, response);
//				break;
//			case "get":
//				getTogoItem(request, response);
//				break;
//			case "getForUpdate":
//				getTogoItemForUpdate(request, response);
//				break;
//			case "update":
//				updateTogoItem(request, response);
//				break;
//			default:
//				response.sendError(HttpServletResponse.SC_NOT_FOUND);
//		}
//	}
//	
//	protected void addTogoItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
////		togoItemService = new TogoItemServiceImpl();
////		MenuDaoImpl menuDao = new MenuDaoImpl();
//		int togoId = Integer.parseInt(request.getParameter("togoId"));
//		int foodId = Integer.parseInt(request.getParameter("foodId"));
//		// 檢查 foodId 是否存在於菜單中
//	    MenuBean food = menuService.getFoodById(foodId);   
//	    if (food == null || food.getFoodPrice() == null) {	
//	        // 如果 foodId 不存在，設置錯誤消息並轉發到錯誤頁面或回到訂單頁面
//	        request.setAttribute("errorMessage", "無效的餐點編號：" + foodId);
//	        request.getRequestDispatcher("/TogoItemController/get").forward(request, response);
////	        return; // 終止方法
//	    }
//		String foodName = food.getFoodName();
//		int foodPrice = food.getFoodPrice();
//		int amount = Integer.parseInt(request.getParameter("amount"));
//		int togoItemPrice = foodPrice*amount;
//		TogoItemBean togoItem = new TogoItemBean(togoId, foodId, foodName, foodPrice, amount, togoItemPrice);
//		togoItemService.addTogoItem(togoItem);
//		//更新總金額
////		togoService = new TogoServiceImpl();
//		int totalPrice = TogoCalculateUtils.sumOfTotalPrice(togoId);
//		togoService.updateTotalPrice(togoId, totalPrice);	
//		request.setAttribute("togoItem", togoItem);
//		request.getRequestDispatcher("/TogoItemController/get").forward(request, response);
//	}
//
//	protected void deleteTogoItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
////		togoItemService = new TogoItemServiceImpl();
//		int togoId = Integer.parseInt(request.getParameter("togoId"));
//		int foodId = Integer.parseInt(request.getParameter("foodId"));
//		togoItemService.deleteTogoItemByTogoIdFoodId(togoId, foodId);
//		//更新總金額
////		togoService = new TogoServiceImpl();
//		int totalPrice = TogoCalculateUtils.sumOfTotalPrice(togoId);
//		togoService.updateTotalPrice(togoId, totalPrice);	
//		request.setAttribute("deleteSuccess", true); // 添加成功標籤
//		request.setAttribute("updateSuccess", true); // 添加成功標籤
//		request.getRequestDispatcher("/TogoItemController/get").forward(request, response);
//	}
//	
//	protected void getTogoItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
////		togoItemService = new TogoItemServiceImpl();
//		int togoId = Integer.parseInt(request.getParameter("togoId"));
//		List<TogoItemBean> togoItemList = togoItemService.getTogoItemByTogoId(togoId);
//		// 获取可能的错误消息
//		String errorMessage = (String) request.getAttribute("errorMessage"); 
//	    request.setAttribute("errorMessage", errorMessage);
//		request.setAttribute("togoItemList", togoItemList);
//		request.getRequestDispatcher("/Togo/GetTogoItem.jsp").forward(request, response);
//	}
//
//	protected void getTogoItemForUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
////		TogoItemDaoImpl togoItemDao = new TogoItemDaoImpl();
//		int togoId = Integer.parseInt(request.getParameter("togoId"));
//		int foodId = Integer.parseInt(request.getParameter("foodId"));		
//		TogoItemBean togoItem = togoItemService.getTogoItemByTogoIdFoodId(togoId, foodId);		
//		request.setAttribute("togoItem", togoItem);
//		request.getRequestDispatcher("/Togo/UpdateTogoItem.jsp").forward(request, response);
//	}
//	
//	protected void updateTogoItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
////		togoItemService = new TogoItemServiceImpl();
//		int togoId = Integer.parseInt(request.getParameter("togoId"));
//		int foodId = Integer.parseInt(request.getParameter("foodId"));
//		String foodName = request.getParameter("foodName");
//		int foodPrice = Integer.parseInt(request.getParameter("foodPrice"));
//		int amount = Integer.parseInt(request.getParameter("amount"));
//		int togoItemPrice = foodPrice*amount;
//		TogoItemBean togoItem = new TogoItemBean(togoId, foodId, foodName, foodPrice, amount, togoItemPrice);		
//		togoItemService.updateTogoItemByTogoIdFoodId(togoItem);
//		//更新總金額
////		togoService = new TogoServiceImpl();
//		int totalPrice = TogoCalculateUtils.sumOfTotalPrice(togoId);
//		togoService.updateTotalPrice(togoId, totalPrice);	
//		request.setAttribute("togoItem", togoItem);
//		request.setAttribute("updateSuccess", true); // 添加成功標籤
//		request.getRequestDispatcher("/TogoItemController/get").forward(request, response);
//	}
//
//	
//	
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		
//		doGet(request, response);
//	}
//
//}
