package com.rent.controller;




import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.members.service.MemberService;
import com.rent.bean.Rent;
import com.rent.bean.RentItem;
import com.rent.service.RentItemService;
import com.rent.service.RentService;
import com.rent.service.TablewareService;
import com.reserve.service.RestaurantService;


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
//	@Autowired
//	MemberService memberService;
	
	@GetMapping("getAll")
	protected String getAll(Model model) {
		List<Rent> rents = rentService.getAll();
		model.addAttribute("rent", rents);
		return "tableware/GetAllRent";
	}
	
	@GetMapping("getOption")
	protected String getOption(Model model) {
		List<String> restaurantNames = restaurantService.getAllRestaurantName();
//		List<String> memberNames = memberService.getAllMemberName();
		List<Integer> tablewareIds = tablewareService.getTablewareIds();
		model.addAttribute("restaurantNames", restaurantNames);
//		model.addAttribute("memberNames", memberNames);
		model.addAttribute("tablewareIds", tablewareIds);
		return "tableware/InsertRent";
	}
	
	@SuppressWarnings("unused")
	@GetMapping("insert")
	protected String insert(
			@RequestParam("rent_deposit") Integer rentDeposit,
			@RequestParam("restaurantName") String restaurantName, 
//			@RequestParam("memberName") String memberName,
			@RequestParam("member_id") Integer memberId,
			@RequestParam Map<String, String> allParams,
			Model model) {
		java.util.Date rentDate = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(rentDate);
		calendar.add(Calendar.DAY_OF_YEAR, 7);
		java.util.Date dueDate = calendar.getTime();
		String restaurantId = restaurantService.getRestaurantId(restaurantName);
//		Integer memberId = memberService.getMemberId(memberName);
		
		Rent rent = new Rent();
		rent.setRentDeposit(rentDeposit);
		rent.setRentDate(rentDate);
		rent.setRestaurantId(restaurantId);
		rent.setMemberId(memberId);
		rent.setDueDate(dueDate);
		rent.setRentStatus(1);
		rent.setRentMemo("未歸還");
		rentService.insert(rent);

		int rentId = rent.getRentId();
		
		List<String> tablewareIds = new ArrayList<>();
	    List<String> rentItemQuantities = new ArrayList<>();
	    List<String> rentItemDeposits = new ArrayList<>();

	    for (Map.Entry<String, String> entry : allParams.entrySet()) {
	        if (entry.getKey().startsWith("tablewareId")) {
	            tablewareIds.add(entry.getValue());
	        } else if (entry.getKey().startsWith("rentItemQuantity")) {
	            rentItemQuantities.add(entry.getValue());
	        } else if (entry.getKey().startsWith("rentItemDeposit")) {
	            rentItemDeposits.add(entry.getValue());
	        }
	    }
		
		List<RentItem> rentItems = new ArrayList<>();
		for (int i = 0; i < tablewareIds.size(); i++) {
	        Integer tablewareId = Integer.parseInt(tablewareIds.get(i));
	        Integer rentItemQuantity = Integer.parseInt(rentItemQuantities.get(i));
	        Integer rentItemDeposit = Integer.parseInt(rentItemDeposits.get(i));

	        RentItem rentItem = new RentItem(rentId, tablewareId, rentItemQuantity, rentItemDeposit, "未歸還", 1);
	        rentItems.add(rentItem);
	    }
		for (RentItem rentItem : rentItems) {
	        rentItemService.insert(rentItem);
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
			
			Rent rent = new Rent();
			rent.setRentId(rentId);
			rent.setRentDeposit(rentDeposit);
			rent.setRentDate(rentDate);
			rent.setRestaurantId(restaurantId);
			rent.setMemberId(memberId);
			rent.setDueDate(dueDate);
			rent.setReturnDate(returnDate);
			rent.setRentStatus(rentStatus);
			rent.setRentMemo(rentMemo);
			rent.setReturnRestaurantId(returnRestaurantId);
			rentService.update(rent);
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
			@RequestParam(value = "rent_id" , required = false) Integer rentId,
			@RequestParam(value = "member_id" , required = false) Integer memberId,
			@RequestParam(value = "restaurantName" , required = false) String restaurantName, 
			@RequestParam(value = "rent_status", required = false) Integer rentStatus,
			@RequestParam(value = "rent_date_start", required = false) String rentDateStartStr,
			@RequestParam(value = "rent_date_end", required = false) String rentDateEndStr,
			Model model) {
		try {
			rentId = (rentId != null) ? rentId : null;
			memberId = (memberId != null) ? memberId : null;
			String restaurantId = restaurantService.getRestaurantId(restaurantName);
	        restaurantId = (restaurantId != null && !restaurantId.isEmpty()) ? restaurantId : null;
	        rentStatus = (rentStatus != null) ? rentStatus : null;
	        Date rentDateStart = (rentDateStartStr != null && !rentDateStartStr.isEmpty()) ? new SimpleDateFormat("yyyy-MM-dd").parse(rentDateStartStr) : null;
	        Date rentDateEnd = (rentDateEndStr != null && !rentDateEndStr.isEmpty()) ? new SimpleDateFormat("yyyy-MM-dd").parse(rentDateEndStr) : null;
	        
			List<Rent> rents = rentService.getByMany(rentId, memberId, restaurantId, rentStatus, rentDateStart,rentDateEnd);
			model.addAttribute("rent", rents);
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

