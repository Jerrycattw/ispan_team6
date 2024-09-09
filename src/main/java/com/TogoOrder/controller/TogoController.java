package com.TogoOrder.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.TogoOrder.bean.TogoBean;
import com.TogoOrder.service.TogoServiceImpl;



@WebServlet("/TogoController/*")
public class TogoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private TogoServiceImpl togoService;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 獲取URL中的操作名稱
		String action = request.getPathInfo().substring(1);
		System.out.println(action);

		switch (action) {
			case "add":
				addTogo(request, response);
				break;
			case "delete":
	            deleteTogo(request, response);
				break;
			case "get":
				getTogo(request, response);
				break;
			case "getForUpdate":
				getTogoForUpdate(request, response);
				break;
			case "getAll":
				getAllTogo(request, response);
				break;
			case "update":
				updateTogo(request, response);
				break;
			default:
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}
	

	protected void getAllTogo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		togoService = new TogoServiceImpl();
		List<TogoBean> togoList = togoService.getAllTogo();	
		request.setAttribute("togoList", togoList);
		request.getRequestDispatcher("/Togo/GetAllTogo.jsp").forward(request, response);
 	}
	
	protected void addTogo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		togoService = new TogoServiceImpl();
		int memberId = Integer.parseInt(request.getParameter("memberId"));
		String tgName = request.getParameter("tgName");
		String tgPhone = request.getParameter("tgPhone");
		int totalPrice = Integer.parseInt(request.getParameter("totalPrice"));
		int rentId = Integer.parseInt(request.getParameter("rentId"));
		int togoStatus = Integer.parseInt(request.getParameter("togoStatus"));
		int restaurantId = Integer.parseInt(request.getParameter("restaurantId"));
		String togoMemo = request.getParameter("togoMemo");
		TogoBean togo = new TogoBean(memberId, tgName, tgPhone, totalPrice, rentId, togoStatus, restaurantId, togoMemo);
		togoService.addTogo(togo);
		request.setAttribute("togo", togo);	
		request.getRequestDispatcher("/TogoController/getAll").forward(request, response);
	}
	
	protected void deleteTogo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		togoService = new TogoServiceImpl();
		int togoId = Integer.parseInt(request.getParameter("togoId"));
		TogoBean togoDelete = togoService.getTogoById(togoId);
		togoService.deleteTogoById(togoDelete.getTogoId());	
		request.setAttribute("deleteSuccess", true); // 添加成功標籤
		request.getRequestDispatcher("/TogoController/getAll").forward(request, response);
	}
	
	protected void getTogoForUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		togoService = new TogoServiceImpl();		
		int togoId = Integer.parseInt(request.getParameter("togoId"));
		TogoBean togo = togoService.getTogoById(togoId);
		request.setAttribute("togo", togo);	
		request.getRequestDispatcher("/Togo/UpdateTogo.jsp").forward(request, response);
	}
	
	protected void getTogo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		togoService = new TogoServiceImpl();		
		String tgPhone =request.getParameter("tgPhone");		
		List<TogoBean> togoList = togoService.getTogoByPhone(tgPhone);
	    request.setAttribute("togoList", togoList);	    
		request.getRequestDispatcher("/Togo/GetTogo.jsp").forward(request, response);
	}

	protected void updateTogo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		togoService = new TogoServiceImpl();	
		int togoId = Integer.parseInt(request.getParameter("togoId"));
		int memberId = Integer.parseInt(request.getParameter("memberId"));
		String tgName = request.getParameter("tgName");
		String tgPhone = request.getParameter("tgPhone");
		int totalPrice = Integer.parseInt(request.getParameter("totalPrice"));
		int rentId = Integer.parseInt(request.getParameter("rentId"));
		int togoStatus = Integer.parseInt(request.getParameter("togoStatus"));
		int restaurantId = Integer.parseInt(request.getParameter("restaurantId"));
		String togoMemo = request.getParameter("togoMemo");
		TogoBean togo = new TogoBean(togoId, memberId, tgName, tgPhone, totalPrice, rentId, togoStatus, restaurantId, togoMemo);	
		togoService.updateTogoById(togo);
		request.setAttribute("togo", togo);
		request.setAttribute("updateSuccess", true); // 添加成功標籤
		request.getRequestDispatcher("/TogoController/getAll").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
