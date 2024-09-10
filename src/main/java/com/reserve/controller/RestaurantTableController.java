package com.reserve.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.reserve.bean.RestaurantTable;
import com.reserve.bean.RestaurantTableId;
import com.reserve.bean.TableDTO;
import com.reserve.bean.TableType;
import com.reserve.dao.TableTypeDao;
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

@WebServlet("/Table/*")
public class RestaurantTableController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Session session = null;
	TableTypeService tableTypeService = null;
	RestaurantService restaurantService = null;
	ReserveService reserveService = null;
	RestaurantTableService restaurantTableService = null;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		session = (Session) request.getAttribute("hibernateSession");
		tableTypeService = new TableTypeService(session);
		restaurantService = new RestaurantService(session);
		reserveService = new ReserveService(session);
		restaurantTableService = new RestaurantTableService(session);

		
		// 獲取URL中的操作名稱
		String action = request.getPathInfo().substring(1);

		System.out.println(action);

		switch (action) {
		case "add1":
			addTable(request, response);
			break;
		case "add2":
			addTable2(request, response);
			break;
		case "del":
			delTable(request, response);
			break;
		case "getAll":
			getAllTable(request, response);
			break;
		case "set1":
			setTable1(request, response);
			break;
		case "set2":
			setTable2(request, response);
			break;
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}

	}


	private void addTable(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String restaurantName = request.getParameter("restaurantName");
		request.setAttribute("restaurantName", restaurantName);
		List<TableType> tableTypes = tableTypeService.selectAll();
		request.setAttribute("allTypes", tableTypes);
		request.getRequestDispatcher("/reserve/AddTable.jsp").forward(request, response);

	}

	private void addTable2(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String restaurantId = restaurantService.getRestaurantId(request.getParameter("restaurantName"));
		String tableTypeId = request.getParameter("tableTypeId");
		TableType tableType = tableTypeService.selectById(tableTypeId);
		String tableTypeName = tableTypeService.getTableTypeName(tableTypeId);
		tableType.setTableTypeName(tableTypeName);
		
		System.out.println(tableTypeName);
		request.setAttribute("tableTypeName", tableTypeName);
		Integer tableTypeNumber = Integer.parseInt(request.getParameter("tableTypeNumber"));
		
		RestaurantTableId restaurantTableId = new RestaurantTableId(Integer.parseInt(restaurantId), tableTypeId);
		RestaurantTable restaurantTable = new RestaurantTable(restaurantTableId,tableTypeNumber);
		
		restaurantTableService.insert(restaurantTable);
		request.getRequestDispatcher("/Table/getAll").forward(request, response);

	}

	private void delTable(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String restaurantId = request.getParameter("restaurantId");
		String tableTypeId = request.getParameter("tableTypeId");
		RestaurantTableId restaurantTableId = new RestaurantTableId(Integer.parseInt(restaurantId), tableTypeId);
		restaurantTableService.delete(restaurantTableId);
		
		request.setAttribute("restaurantId", restaurantId);
		request.getRequestDispatcher("/Table/getAll").forward(request, response);

	}

	private void getAllTable(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String restaurantId = request.getParameter("restaurantId");
		String restaurantName = request.getParameter("restaurantName");
		
		if(restaurantId!=null) {
			restaurantId = request.getParameter("restaurantId");
		} else if (restaurantName!=null){
			restaurantId = restaurantService.getRestaurantId(restaurantName);
		}
		
		List<RestaurantTable> tableTypes = restaurantTableService.selectAll(restaurantId);
		List<TableDTO> tableDTOs = new ArrayList<TableDTO>();
		TableDTO tableDTO = null;
		for(RestaurantTable restaurantTable : tableTypes) {
			tableDTO = new TableDTO(restaurantTable);
			tableDTOs.add(tableDTO);
		}
		
		request.setAttribute("name", restaurantName);
		request.setAttribute("tableTypes", tableDTOs);
		request.getRequestDispatcher("/reserve/GetAllTables.jsp").forward(request, response);

	}



	private void setTable1(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String restaurantId = request.getParameter("restaurantId");
		String tableTypeId = request.getParameter("tableTypeId");
		
		RestaurantTableId restaurantTableId = new RestaurantTableId(Integer.parseInt(restaurantId), tableTypeId);
		RestaurantTable restaurantTable = restaurantTableService.selectById(restaurantTableId);
		request.setAttribute("table", restaurantTable);
		request.getRequestDispatcher("/reserve/SetTableType.jsp").forward(request, response);

	}

	private void setTable2(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String restaurantId = request.getParameter("restaurantId");
		String tableTypeId = request.getParameter("tableTypeId");
		Integer tableTypeNumber = Integer.parseInt(request.getParameter("tableTypeNumber"));
		
		RestaurantTableId restaurantTableId = new RestaurantTableId(Integer.parseInt(restaurantId), tableTypeId);
		RestaurantTable restaurantTable = restaurantTableService.selectById(restaurantTableId);
		restaurantTable.setTableTypeNumber(tableTypeNumber);
		restaurantTableService.update(restaurantTable);
		
		request.getRequestDispatcher("/Table/getAll").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
