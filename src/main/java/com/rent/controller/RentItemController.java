package com.rent.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rent.bean.RentItem;
import com.rent.service.RentItemService;
import com.rent.service.RentService;

@Controller
@RequestMapping("/RentItem/*")
public class RentItemController{
	
	@Autowired
	RentItemService rentItemService;
	@Autowired
	RentService rentService;
	
//	@GetMapping("insert")
//	protected String insert(
//			@RequestParam("rent_id") Integer rentId, 
//			@RequestParam("tableware_id") Integer tablewareId,
//			@RequestParam("rent_item_quantity") Integer rentItemQuantity,
//			@RequestParam("rent_item_deposit") Integer rentItemDeposit, 
//			Model model) {
//		RentItem rentItem = new RentItem();
//		rentItem.setRentId(rentId);
//		rentItem.setTablewareId(tablewareId);
//		rentItem.setRentItemQuantity(rentItemQuantity);
//		rentItem.setRentItemDeposit(rentItemDeposit);
//		rentItemService.insert(rentItem);
//		return "redirect:/RentItem/getAll";
//	}
	
	@GetMapping("getOption")
	public String getOption(Model model) {
		List<Integer> rentIds = rentService.getRentId();
		model.addAttribute("rentIds",rentIds);
		return "tableware/SearchRentItem";
	}

	@GetMapping("search")
	protected String search(@RequestParam("rentId") Integer rentId, Model model) {
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
		RentItem rentItem = new RentItem();
		rentItem.setRentId(rentId);
		rentItem.setTablewareId(tablewareId);
		rentItem.setRentItemQuantity(rentItemQuantity);
		rentItem.setRentItemDeposit(rentItemDeposit);
		rentItem.setReturnMemo(returnMemo);
		rentItem.setReturnStatus(returnStatus);
		rentItemService.update(rentItem);
		return "redirect:/RentItem/getAll";
	}

//	@GetMapping("delete")
//	protected String delete(
//			@RequestParam("rent_id") Integer rentId, 
//			@RequestParam("tableware_id") Integer tablewareId,
//			Model model) {
//		RentItem rentItem = rentItemService.getByIds(rentId, tablewareId);
//		rentItemService.delete(rentItem);
//		return "redirect:/RentItem/getAll";
//	}
	
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
