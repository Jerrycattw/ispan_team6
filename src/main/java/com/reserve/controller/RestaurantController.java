package com.reserve.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fatboyindustrial.gsonjavatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.reserve.bean.Restaurant;
import com.reserve.bean.RestaurantDTO;
import com.reserve.service.RestaurantService;


@Controller
@RequestMapping("/Restaurant/*")
public class RestaurantController{

	@Autowired
	private RestaurantService restaurantService;
	
	
	@PostMapping("add")
	public String addRestaurant(@ModelAttribute Restaurant restaurant,
	                             @RequestParam("ropen") String ropenStr,
	                             @RequestParam("rclose") String rcloseStr,
	                             @RequestParam("rimg") MultipartFile rimg) throws IOException {

	    // 處理開關時間
	    LocalTime ropen = LocalTime.parse(ropenStr);
	    LocalTime rclose = LocalTime.parse(rcloseStr);
	    restaurant.setRestaurantOpentime(ropen);
	    restaurant.setRestaurantClosetime(rclose);
	    restaurant.setRestaurantStatus(3); // 設定預設狀態

	    // 處理圖片上傳
	    String uploadPath = "C:/upload/restaurantIMG";
	    File fileSaveDir = new File(uploadPath);
	    if (!fileSaveDir.exists()) {
	        fileSaveDir.mkdirs();
	    }

	    if (!rimg.isEmpty()) {
	        String fileName = rimg.getOriginalFilename();
	        String extension = fileName.substring(fileName.lastIndexOf("."));
	        String newFileName = restaurant.getRestaurantName() + "_" + System.currentTimeMillis() + extension;

	        // 將檔案寫入指定路徑
	        File fileToSave = new File(uploadPath + File.separator + newFileName);
	        rimg.transferTo(fileToSave);
	        restaurant.setRestaurantImg("/EEIT187-6/restaurantIMG/" + newFileName);
	    }

	    restaurantService.insert(restaurant);
	    return "redirect:/Restaurant/getAll";
	}

	
	
	
	
	@GetMapping("del")
	public String delRestaurant(@RequestParam("restaurantId") String restaurantId, Model model) {
		restaurantService.delete(restaurantId);
		return "redirect:/Restaurant/getAll";
	}
	
	
	@GetMapping("get")
	public String getRestaurant(@RequestParam("restaurantId") String restaurantId, Model model) {
	    Restaurant restaurant = restaurantService.selectById(restaurantId);
	    model.addAttribute("restaurant", restaurant);
	    return "reserve/GetRestaurant";
	}
	
	
	
	@GetMapping("getAll")
	public String getAllRestaurant(Model model) {
	    List<Restaurant> restaurants = restaurantService.selectAll();
	    model.addAttribute("restaurants", restaurants);
	    return "reserve/GetAllRestaurants";
	}
	
	
	
	
	@GetMapping(value = "list", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getRestaurantList(
	        @RequestParam(value = "restaurantName", required = false) String restaurantName,
	        @RequestParam(value = "restaurantAddress", required = false) String restaurantAddress,
	        @RequestParam(value = "restaurantStatus", required = false) String restaurantStatus) {

	    // 查詢餐廳列表
	    List<Restaurant> restaurants = restaurantService.selectList(restaurantName, restaurantAddress, restaurantStatus);
	    List<RestaurantDTO> restaurantDTOs = new ArrayList<>();

	    // 將餐廳物件轉換為 DTO
	    for (Restaurant restaurant : restaurants) {
	        RestaurantDTO restaurantDTO = new RestaurantDTO(restaurant);
	        restaurantDTOs.add(restaurantDTO);
	    }

	    // 使用 Gson 轉換為 JSON
	    GsonBuilder gsonBuilder = new GsonBuilder();
	    Converters.registerLocalTime(gsonBuilder);  // 假設你有自定義 LocalTime 的序列化器
	    Gson gson = gsonBuilder.create();
	    
	    // 返回 JSON 字符串
	    return gson.toJson(restaurantDTOs);
	}
	
	
	
	@GetMapping("set")
	public String setRestaurant(@RequestParam("restaurantId") String restaurantId, Model model) {
		
		Restaurant restaurant = restaurantService.selectById(restaurantId);
		model.addAttribute("restaurant", restaurant);
		return "reserve/SetRestaurant";

	}
	
	
	@PostMapping("set2")
	public String setRestaurant2(@ModelAttribute Restaurant restaurant,
	                              @RequestParam("ropen") String ropenStr,
	                              @RequestParam("rclose") String rcloseStr,
	                              @RequestParam("rimg") MultipartFile rimg) throws IOException {

	    // 設置開關時間
	    LocalTime ropen = LocalTime.parse(ropenStr);
	    LocalTime rclose = LocalTime.parse(rcloseStr);
	    restaurant.setRestaurantOpentime(ropen);
	    restaurant.setRestaurantClosetime(rclose);

	    // 處理圖片上傳
	    String uploadPath = "C:/upload/restaurantIMG";
	    File fileSaveDir = new File(uploadPath);
	    if (!fileSaveDir.exists()) {
	        fileSaveDir.mkdirs();
	    }

	    if (!rimg.isEmpty()) {
	        String fileName = rimg.getOriginalFilename();
	        String extension = fileName.substring(fileName.lastIndexOf("."));
	        String newFileName = restaurant.getRestaurantName() + "_" + System.currentTimeMillis() + extension;

	        // 將檔案寫入指定路徑
	        File fileToSave = new File(uploadPath + File.separator + newFileName);
	        rimg.transferTo(fileToSave);
	        restaurant.setRestaurantImg("/EEIT187-6/restaurantIMG/" + newFileName);
	    } else {
	        // 如果沒有上傳新的圖片，保留現有圖片
	        Restaurant existingRestaurant = restaurantService.selectById(restaurant.getRestaurantId());
	        if (existingRestaurant != null) {
	            restaurant.setRestaurantImg(existingRestaurant.getRestaurantImg());
	        }
	    }

	    // 更新餐廳資料
	    restaurantService.update(restaurant);
	    return "redirect:/Restaurant/getAll";
	}

	
	
	
}


