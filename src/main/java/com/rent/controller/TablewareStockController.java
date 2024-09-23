package com.rent.controller;

import java.io.IOException;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rent.bean.TablewareStock;
import com.rent.service.TablewareService;
import com.rent.service.TablewareStockService;
import com.reserve.service.RestaurantService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;

@Controller
@RequestMapping("/TablewareStock/*")
public class TablewareStockController extends HttpServlet {
	
	@Autowired
	TablewareStockService tablewareStockService;
	@Autowired
	RestaurantService restaurantService;
	@Autowired
	TablewareService tablewareService;
	
	@GetMapping("getOption")
	public String getOption(@RequestParam("action") String action, Model model) {
		List<Integer> tablewareIds = tablewareService.getTablewareIds();
		List<String> restaurantNames = restaurantService.getAllRestaurantName();
		model.addAttribute("tablewareIds",tablewareIds);
		model.addAttribute("restaurantNames",restaurantNames);
		if ("insert".equals(action)) {
			return "tableware/InsertStock";
	    } else if ("search".equals(action)) {
	    	return "tableware/SearchStock";
	    }
		return null;
	}

	@GetMapping("insert")
	protected String insert(
			@RequestParam("tablewareId") Integer tablewareId,
			@RequestParam("restaurantName") String restaurantName,
			@RequestParam("stock") Integer stock,
			Model model) {
		String restaurantId = restaurantService.getRestaurantId(restaurantName);
		TablewareStock tablewareStock = tablewareStockService.insert(tablewareId, restaurantId, stock);
		model.addAttribute("tableware_stock", tablewareStock);
		return "redirect:/TablewareStock/getAll";
	}

	@GetMapping("getAll")
	protected String getAll(Model model)
			throws ServletException, IOException {
		List<TablewareStock> tablewareStocks = tablewareStockService.getAll();
		model.addAttribute("stock", tablewareStocks);
		return "tableware/GetAllStock";
	}

	@GetMapping("update")
	protected String update(
			@RequestParam("tableware_id") Integer tablewareId,
			@RequestParam("restaurant_id") String restaurantId,
			@RequestParam("stock") Integer stock,
			Model model) {
		TablewareStock tablewareStock = tablewareStockService.update(tablewareId, restaurantId, stock);
		model.addAttribute("stock", tablewareStock);
		return "redirect:/TablewareStock/getAll";
	}

	@GetMapping("search")
	protected String search(
			@RequestParam(value = "tablewareId", required = false) Integer tablewareId,
			@RequestParam(value = "restaurantName", required = false) String restaurantName,
			Model model) {
		String restaurantId = restaurantService.getRestaurantId(restaurantName);
		List<TablewareStock> tablewareStocks = tablewareStockService.search(restaurantId, tablewareId);
		model.addAttribute("stock", tablewareStocks);
		return "tableware/GetAllStock";
	}
	
	@GetMapping("getById")
	protected String getById(
			@RequestParam("tableware_id") Integer tablewareId,
			@RequestParam("restaurant_id") String restaurantId,
			Model model) {
		TablewareStock tablewareStock = tablewareStockService.getById(tablewareId, restaurantId);
		model.addAttribute("stock", tablewareStock);
		return "tableware/UpdateStock";
	}
}
