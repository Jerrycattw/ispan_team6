package com.TogoOrder.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.TogoOrder.bean.TogoBean;
import com.TogoOrder.service.TogoService;
import com.TogoOrder.service.TogoServiceImpl;


@Controller
@RequestMapping("/TogoController/*")
public class TogoController {
	
	@Autowired
    private TogoService togoService;
	
	@GetMapping("getAllTogo")
	public String getAllTogo(Model model) {
		List<TogoBean> togoList = togoService.getAllTogo();	
		model.addAttribute("togoList", togoList);
		return "togo/GetAllTogo";
 	}
	
	protected void addTogo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
//		togoService = new TogoServiceImpl();
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
//		togoService = new TogoServiceImpl();
		int togoId = Integer.parseInt(request.getParameter("togoId"));
		TogoBean togoDelete = togoService.getTogoById(togoId);
		togoService.deleteTogoById(togoDelete.getTogoId());	
		request.setAttribute("deleteSuccess", true); // 添加成功標籤
		request.getRequestDispatcher("/TogoController/getAll").forward(request, response);
	}
	
	protected void getTogoForUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		togoService = new TogoServiceImpl();		
		int togoId = Integer.parseInt(request.getParameter("togoId"));
		TogoBean togo = togoService.getTogoById(togoId);
		request.setAttribute("togo", togo);	
		request.getRequestDispatcher("/Togo/UpdateTogo.jsp").forward(request, response);
	}
	
	@GetMapping("getTogo")
	public String getTogo(@RequestParam("tgPhone") String tgPhone, Model model) {
		List<TogoBean> togoList = togoService.getTogoByPhone(tgPhone);
	    model.addAttribute("togoList", togoList);	    
		return "togo/GetTogo";
	}

	protected void updateTogo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		togoService = new TogoServiceImpl();	
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
	

}
