package com.reserve.controller;

import java.io.IOException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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


@Controller
@RequestMapping("/TableType/*")
public class TableTypeController {
	
	@Autowired
	TableTypeService tableTypeService;
	@Autowired
	RestaurantService restaurantService;
	@Autowired
	ReserveService reserveService;
	@Autowired
	RestaurantTableService restaurantTableService;
	
	
	@PostMapping("add")
	public String addTableType(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String tableTypeId = request.getParameter("tableTypeId");
		String tableTypeName = request.getParameter("tableTypeName");
		TableType tableType = new TableType(tableTypeId, tableTypeName);
		tableTypeService.insert(tableType);
		
		
		return "redirect:/TableType/getAllType";


	}

	@GetMapping("del")
	public String delTableType(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String tableTypeId = request.getParameter("tableTypeId");
		tableTypeService.delete(tableTypeId);
		
		return "redirect:/TableType/getAllType";


	}

	
	@GetMapping("getAllType")
	public String getAllTableType(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		List<TableType> tableTypes = tableTypeService.selectAll();
		request.setAttribute("tableTypes", tableTypes);
		
		return "reserve/GetAllTableTypes";


	}

	
	
}
