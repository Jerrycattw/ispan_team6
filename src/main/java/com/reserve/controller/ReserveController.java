package com.reserve.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fatboyindustrial.gsonjavatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.reserve.bean.Reserve;
import com.reserve.bean.ReserveCheckBean;
import com.reserve.bean.ReserveDTO;
import com.reserve.bean.TableType;
import com.reserve.service.ReserveService;
import com.reserve.service.RestaurantService;
import com.reserve.service.RestaurantTableService;
import com.reserve.service.TableTypeService;



@Controller
@RequestMapping("/Reserve/*")
public class ReserveController{
	
	@Autowired
	TableTypeService tableTypeService;
	@Autowired
	RestaurantService restaurantService;
	@Autowired
	ReserveService reserveService;
	@Autowired
	RestaurantTableService restaurantTableService;
	
	
	@GetMapping("getAllRestaurantName")
	public String getAllRestaurantName(Model model) {
	    List<String> restaurantNames = restaurantService.getAllRestaurantName();
	    model.addAttribute("restaurantNames", restaurantNames);
	    return "reserve/AddReserveNew";
	}

	@GetMapping("listName")
	public String getAllRestaurantNameForList(Model model) {
	    List<String> restaurantNames = restaurantService.getAllRestaurantName();
	    List<TableType> allTableTypes = tableTypeService.selectAll();
	    
	    model.addAttribute("tableTypes", allTableTypes);
	    model.addAttribute("restaurantNames", restaurantNames);
	    
	    return "reserve/GetListReserve";
	}
	
	
	
	
	
	@GetMapping(value = "list", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getReserveList(
		        @RequestParam(value = "memberId", required = false) String memberId,
		        @RequestParam(value = "memberName", required = false) String memberName,
		        @RequestParam(value = "phone", required = false) String phone,
		        @RequestParam(value = "restaurantId", required = false) String restaurantId,
		        @RequestParam(value = "restaurantAddress", required = false) String restaurantAddress,
		        @RequestParam(value = "tableTypeId", required = false) String tableTypeId,
		        @RequestParam(value = "reserveTime", required = false) LocalDateTime reserveTime,
		        @RequestParam(value = "finishedTime", required = false) LocalDateTime finishedTime) {

	    if (restaurantId != null && restaurantId.equals("---")) {
	        restaurantId = null;
	    } else if (restaurantId != null) {
	        restaurantId = restaurantService.getRestaurantId(restaurantId);
	    }

	    if (tableTypeId != null && tableTypeId.equals("---")) {
	        tableTypeId = null;
	    }

	    List<Reserve> reserves = reserveService.getReserveByCriteria(
	            memberName, phone, memberId, restaurantId, tableTypeId, restaurantAddress, reserveTime, finishedTime);

	    
	    List<ReserveDTO> reserveDTOs = new ArrayList<ReserveDTO>();
	    ReserveDTO reserveDTO = null;
	    
	    for(Reserve reserve : reserves) {
	    	reserveDTO =new ReserveDTO(reserve);
	    	reserveDTOs.add(reserveDTO);
	    }
	    
		GsonBuilder gsonBuilder = new GsonBuilder();
	    Converters.registerLocalDateTime(gsonBuilder);
	    Gson gson = gsonBuilder.create();

	    return gson.toJson(reserveDTOs);
	    
	    
	}
	
	
	
	
	
	@GetMapping("checkTable")
	@ResponseBody
	public String checkReserveTable(
	        @RequestParam("restaurantName") String restaurantName,
	        @RequestParam("reserveSeat") String reserveSeat,
	        @RequestParam(value = "checkDate", required = false) String checkDateStr) {

	    String restaurantId = restaurantService.getRestaurantId(restaurantName);
	    String tableTypeId = reserveService.getTableTypeId(Integer.parseInt(reserveSeat));

	    LocalDate checkDate = null;
	    if (checkDateStr != null && !checkDateStr.isEmpty()) {
	        checkDate = LocalDate.parse(checkDateStr);
	    }

	    System.out.println(restaurantId);
	    System.out.println(reserveSeat);
	    System.out.println(checkDate);

	    List<ReserveCheckBean> reserveChecks = reserveService.getReserveCheck(restaurantId, tableTypeId, checkDate);

	    System.out.println(reserveChecks);

	    GsonBuilder gsonBuilder = new GsonBuilder();
	    Converters.registerLocalTime(gsonBuilder);
	    Gson gson = gsonBuilder.create();

	    return gson.toJson(reserveChecks);

	}
	
	
	
	@PostMapping("addNew")
	public String addReserveNew(
	        @RequestParam("restaurantName") String restaurantName,
	        @RequestParam("reserveSeat") Integer reserveSeat,
	        @RequestParam("checkDate") String checkDateStr,
	        @RequestParam("reserveTime") String reserveTimeStr,
	        @RequestParam("memberId") String memberId) throws IOException {

	    // 餐廳
	    String restaurantId = restaurantService.getRestaurantId(restaurantName);
	    
	    // 依座位數預定對應桌子種類ID
	    String tableTypeId = reserveService.getTableTypeId(reserveSeat);
	    
	    // 用餐時間範圍
	    LocalDate checkDate = LocalDate.parse(checkDateStr);
	    LocalTime reserveTime = LocalTime.parse(reserveTimeStr);
	    
	    // 使用 atTime 方法將 LocalDate 和 LocalTime 合併成 LocalDateTime
	    LocalDateTime reserveDateTime = checkDate.atTime(reserveTime);
	    LocalDateTime finishedTime = reserveDateTime.plusMinutes(restaurantService.selectById(restaurantId).getEattime());
	    
	    // 創建 Reserve 對象並插入資料
	    Reserve reserve = new Reserve(memberId, restaurantId, reserveSeat, tableTypeId, reserveDateTime, finishedTime);
	    reserveService.insert(reserve);
	    
	    // 重定向到訂位列表頁面
	    return "redirect:/Reserve/listName";
	}
	
	
	
	@GetMapping("del")
	public String delReserve(@RequestParam("reserveId") String reserveId) {
	    reserveService.delete(reserveId);
	    return "redirect:/Reserve/listName";
	}

	
	
	@GetMapping("get")
	public String getReserve(@RequestParam("reserveId") String reserveId, Model model) {
	    Reserve reserve = reserveService.selectById(reserveId);
	    model.addAttribute("reserve", reserve);
	    return "reserve/GetReserve";
	}

	
	
	@GetMapping("getAll")
	public String getAllReserve(Model model) {
	    List<Reserve> reserves = reserveService.selectAll();
	    model.addAttribute("reserves", reserves);
	    return "reserve/GetAllReserve";
	}

	
	
	@GetMapping("set")
	public String setReserve(@RequestParam("reserveId") String reserveId, Model model) {
	    Reserve reserve = reserveService.selectById(reserveId);
	    ReserveDTO reserveDTO = new ReserveDTO(reserve);
	    model.addAttribute("reserve", reserveDTO);
	    return "reserve/SetReserveNew";
	}
	
	
	
	
	@PostMapping("set2")
	public String setReserve2(
	        @RequestParam("reserveId") String reserveId,
	        @RequestParam("restaurantName") String restaurantName,
	        @RequestParam("reserveSeat") Integer reserveSeat,
	        @RequestParam("checkDate") String checkDateStr,
	        @RequestParam("reserveTime") String reserveTimeStr,
	        @RequestParam("memberId") String memberId) {

	    // 獲取預約信息
	    Reserve reserve = reserveService.selectById(reserveId);
	    
	    // 餐廳
	    String restaurantId = restaurantService.getRestaurantId(restaurantName);
	    
	    // 依座位數預定對應桌子種類ID
	    String tableTypeId = reserveService.getTableTypeId(reserveSeat);
	    
	    // 用餐時間範圍
	    LocalDate checkDate = LocalDate.parse(checkDateStr);
	    LocalTime reserveTime = LocalTime.parse(reserveTimeStr);
	    
	    // 合併成 LocalDateTime
	    LocalDateTime reserveDateTime = checkDate.atTime(reserveTime);
	    LocalDateTime finishedTime = reserveDateTime.plusMinutes(restaurantService.selectById(restaurantId).getEattime());
	    
	    // 更新預約信息
	    reserve.setRestaurantId(restaurantId);
	    reserve.setReserveSeat(reserveSeat);
	    reserve.setTableTypeId(tableTypeId);
	    reserve.setReserveTime(reserveDateTime);
	    reserve.setFinishedTime(finishedTime);
	    reserve.setMemberId(memberId);
	    
	    reserveService.update(reserve);
	    
	    return "redirect:/Reserve/listName";
	}
	
	
	
}
