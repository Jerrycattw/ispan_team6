package com.rent.controller;

import java.io.IOException;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rent.bean.RentItem;
import com.rent.dao.RentItemDao;
import com.rent.service.RentItemService;
import com.util.HibernateUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/RentItem/*")
public class RentItemController{
	
	@Autowired
	RentItemService rentItemService;
	
	@GetMapping("insert")
	protected String insert(
			@RequestParam("rent_id") Integer rentId, 
			@RequestParam("tableware_id") Integer tablewareId,
			@RequestParam("rent_item_quantity") Integer rentItemQuantity,
			@RequestParam("rent_item_deposit") Integer rentItemDeposit, 
			Model model) {
		RentItem rentItem = rentItemService.insert(rentId, tablewareId, rentItemQuantity, rentItemDeposit);
		return "redirect:/RentItem/getAll";
	}

	@GetMapping("search")
	protected String search(@RequestParam("rent_id") Integer rentId, Model model) {
		List<RentItem> rentItems = rentItemService.getById(rentId);
		model.addAttribute("rentItem", rentItems);
		return "tableware/GetAllRentItem";
	}
	
	@GetMapping("getAll")
	protected String getAll(Model model) {
		List<RentItem> rentItems = rentItemService.getAll();
		model.addAttribute("rentItem", rentItems);
		return "tableware/GetAllRentItem";
	}
	
	@GetMapping("get")
	protected String getByIds(
			@RequestParam("rent_id") Integer rentId,
			@RequestParam("tableware_id") Integer tablewareId, 
			@RequestParam("action") String action, 
			Model model) {
		RentItem rentItem = rentItemService.getByIds(rentId, tablewareId);
		model.addAttribute("rentItem", rentItem);
		if ("update".equals(action)) {
			return "tableware/UpdateRentItem";
		} else if ("restore".equals(action)) {
			return "tableware/ReturnRentItem";
		}
		return null;
	}
	
	@GetMapping("update")
	protected String update(
			@RequestParam("rent_id") Integer rentId, 
			@RequestParam("tableware_id") Integer tablewareId,
			@RequestParam("rent_item_quantity") Integer rentItemQuantity,
			@RequestParam("rent_item_deposit") Integer rentItemDeposit, 
			@RequestParam("return_memo") String returnMemo,
			@RequestParam("return_status") Integer returnStatus,
			Model model) {
		rentItemService.update(rentId, tablewareId, rentItemQuantity, rentItemDeposit, returnMemo, returnStatus);
		return "redirect:/RentItem/getAll";
	}

	@GetMapping("delete")
	protected String delete(
			@RequestParam("rent_id") Integer rentId, 
			@RequestParam("tableware_id") Integer tablewareId,
			Model model) {
		RentItem rentItem = rentItemService.getByIds(rentId, tablewareId);
		rentItemService.delete(rentItem);
		return "redirect:/RentItem/getAll";
	}
	
	@GetMapping("restore")
	protected String restore(
			@RequestParam("rent_id") Integer rentId, 
			@RequestParam("tableware_id") Integer tablewareId,
			@RequestParam("return_memo") String returnMemo,
			@RequestParam("return_status") Integer returnStatus,
			Model model) {
		RentItem rentItem = rentItemService.restore(rentId, tablewareId, returnMemo, returnStatus);
		return "redirect:/RentItem/getAll";
	}
}
