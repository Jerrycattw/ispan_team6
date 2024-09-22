/*package com.rent.controller;

import java.io.IOException;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rent.bean.Rent;
import com.rent.bean.RentItem;
import com.rent.service.RentItemService;
import com.rent.service.RentService;
import com.rent.service.TablewareService;
import com.reserve.service.RestaurantService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/Rent/*")
public class RentController{

	@Autowired
	RentService rentService;
	@Autowired
	RestaurantService restaurantService;
	@Autowired
	TablewareService tablewareService;
	@Autowired
	RentItemService rentItemService;
	
	@GetMapping("getAll")
	protected String getAll(Model model) {
		List<Rent> rents = rentService.getAll();
		model.addAttribute("rent", rents);
		return "tableware/GetAllRent";
	}
	
	@GetMapping("getOption")
	protected String getOption(Model model) {
		List<String> restaurantNames = restaurantService.getAllRestaurantName();
		List<Integer> tablewareIds = tablewareService.getTablewareIds();
		model.addAttribute("restaurantNames", restaurantNames);
		model.addAttribute("tablewareIds", tablewareIds);
		return "tableware/InsertRent";
	}
	
	@SuppressWarnings("unused")
	@GetMapping("insert")
	protected String insert(
			@RequestParam("rent_deposit") Integer rentDeposit,
			@RequestParam("restaurantName") String restaurantName, 
			@RequestParam("member_id") Integer memberId,
			@RequestParam("tablewareId") String tablewareIdParam,
			@RequestParam("rentItemQuantity") String rentItemQuantityParam,
			@RequestParam("rentItemDeposit") String rentItemDepositParam,
			Model model) {
		java.util.Date rentDate = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(rentDate);
		calendar.add(Calendar.DAY_OF_YEAR, 7);
		java.util.Date dueDate = calendar.getTime();
		
		String restaurantId = restaurantService.getRestaurantId(restaurantName);
		Rent rent = rentService.insert(rentDeposit, rentDate, restaurantId, memberId, dueDate);

		int rentId = rent.getRentId();
		List<RentItem> rentItems = new ArrayList<>();
		int index = 0;
		while (true) {
            tablewareIdParam += index;
            rentItemQuantityParam += index;
            rentItemDepositParam += index;

            if (tablewareIdParam == null) {
                break; // 如果没有更多的tablewareIdParam，退出循环
            }

            Integer tablewareId = Integer.parseInt(tablewareIdParam);
            Integer rentItemQuantity = Integer.parseInt(rentItemQuantityParam);
            Integer rentItemDeposit = Integer.parseInt(rentItemDepositParam);

            RentItem rentItem = new RentItem(rentId,tablewareId,rentItemQuantity,rentItemDeposit,"未歸還",1);
            rentItems.add(rentItem);
            index++; // 处理下一个RentItem
        }
		for (RentItem rentItem : rentItems) {
	        rentItemService.insert(rentItem.getRentId(), rentItem.getTablewareId(), rentItem.getRentItemQuantity(), rentItem.getRentItemDeposit());
	    }
		return "redirect:/Rent/getAll";
	}
	
	@GetMapping("getById")
	protected String getById(
			@RequestParam("rent_id") Integer rentId, 
			@RequestParam("action") String action, 
			Model model){
		Rent rent = rentService.getById(rentId);
		List<String> restaurantNames = restaurantService.getAllRestaurantName();
		model.addAttribute("restaurantNames", restaurantNames);
		model.addAttribute("rent", rent);
		if ("update".equals(action)) {
			return "tableware/UpdateRent";
	    } else if ("restore".equals(action)) {
	        return "tableware/ReturnRent";
	    }
		return null;
	}

	@GetMapping("update")
	protected String update(
			@RequestParam("rent_id") Integer rentId,
			@RequestParam("rent_deposit") Integer rentDeposit,
			@RequestParam("rent_date") String rentDateUtil,
			@RequestParam("restaurantName") String restaurantName, 
			@RequestParam("member_id") Integer memberId,
			@RequestParam("due_date") String dueDateUtil,
			@RequestParam("return_date") String returnDateUtil,
			@RequestParam("rent_status") Integer rentStatus,
			@RequestParam("rent_memo") String rentMemo,
			@RequestParam("returnRestaurantName") String returnRestaurantName, 
			Model model) {
		try {
			Date rentDate = new SimpleDateFormat("yyyy-MM-dd").parse(rentDateUtil);
			String restaurantId = restaurantService.getRestaurantId(restaurantName);
			Date dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(dueDateUtil);
			Date returnDate = new SimpleDateFormat("yyyy-MM-dd").parse(returnDateUtil);
			String returnRestaurantId = restaurantService.getRestaurantId(returnRestaurantName);

			Rent rent = rentService.update(rentId, rentDeposit, rentDate, restaurantId, memberId, dueDate, returnDate,
					rentStatus, rentMemo, returnRestaurantId);
			return "redirect:/Rent/getAll";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping("delete")
	protected String delete(
			@RequestParam("rent_id") Integer rentId,
			Model model) {
		List<RentItem> rentItems = rentItemService.getById(rentId);
		for(RentItem rentItem: rentItems) {
			rentItemService.delete(rentItem);
		}
		rentService.delete(rentId);
		return "redirect:/Rent/getAll";
	}

	@GetMapping("restore")
	protected String restore(
			@RequestParam("rent_id") Integer rentId,
			@RequestParam("return_date") String returnDateUtil,
			@RequestParam("restaurantName") String restaurantName, 
			Model model) {
		try {
			Date returnDate = new SimpleDateFormat("yyyy-MM-dd").parse(returnDateUtil);
			String returnRestaurantId = restaurantService.getRestaurantId(restaurantName);
			Rent rent = rentService.restore(rentId, returnDate, returnRestaurantId);
			return "redirect:/Rent/getAll";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping("searchOption")
	protected String searchOption(Model model) {
		List<Integer> rentIds = rentService.getRentId();
		List<String> restaurantNames = restaurantService.getAllRestaurantName();
		model.addAttribute("rentIds", rentIds);
		model.addAttribute("restaurantNames", restaurantNames);
		return "tableware/SearchRent";
	}

	@GetMapping("search")
	protected String search(
			@RequestParam("rent_id") Integer rentId,
			@RequestParam("member_id") Integer memberId,
			@RequestParam("restaurantName") String restaurantName, 
			@RequestParam("rent_status") Integer rentStatus,
			@RequestParam("rent_date_start") String rentDateStart,
			@RequestParam("rent_date_end") String rentDateEnd,
			Model model) {
		try {
//			rentId = rentId != null && !rentId.isEmpty() ? rentId : null;
//	        memberId = memberId != null && !memberId.isEmpty() ? memberId : null;
//	        String restaurantId = restaurantService.getRestaurantId(restaurantName) != null && !restaurantService.getRestaurantId(restaurantName).isEmpty() ?restaurantService.getRestaurantId(restaurantName) : null;
//	        rentStatus = rentStatus != null && !rentStatus.isEmpty() ? rentStatus : null;
//	        Date rentDateStart = rentDateStart != null && !rentDateStart.isEmpty() ? new SimpleDateFormat("yyyy-MM-dd").parse(rentDateStart) : null;
//	        Date rentDateEnd = rentDateEnd != null && !rentDateEnd.isEmpty() ? new SimpleDateFormat("yyyy-MM-dd").parse(rentDateEnd) : null;

//			List<Rent> rents = rentService.getByMany(rentId, memberId, restaurantId, rentStatus, rentDateStart,rentDateEnd);
//			model.addAttribute("rent", rents);
			return "tableware/GetAllRent";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping("getOver")
	protected String getOver(Model model) {
		List<Rent> rents = rentService.getOver();
		model.addAttribute("rent", rents);
		return "tableware/GetAllRent";
	}
}
*/