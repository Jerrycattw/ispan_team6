package com.TogoOrder.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.TogoOrder.bean.MenuBean;
import com.TogoOrder.service.MenuService;

@Controller
@RequestMapping("/MenuController/*")
public class MenuController {
	
	@Autowired
	private MenuService menuService;
	@Autowired
	private MenuBean food;
	
	@PostMapping("addMenu")
	public String addMenu(
			@RequestParam("foodName") String foodName,
			@RequestParam("foodPicture") MultipartFile foodPicture,
            @RequestParam("foodPrice") Integer foodPrice,
            @RequestParam("foodKind") String foodKind,
            @RequestParam("foodStock") Integer foodStock,
            @RequestParam("foodDescription") String foodDescription,
            @RequestParam("foodStatus") Integer foodStatus,
            Model model) throws IOException {
		String uploadPath = "C:/upload/foodIMG";
        File fileSaveDir = new File(uploadPath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }
        String fileName = "";
        if (!foodPicture.isEmpty()) {
        	fileName = Paths.get(foodPicture.getOriginalFilename()).getFileName().toString();
        	// 將文件保存到指定路徑
			foodPicture.transferTo(new File(uploadPath + File.separator + fileName));
        }
        String foodPicturePath = "/EEIT187-6/togo/MenuImages/" + fileName;
		MenuBean food = new MenuBean(foodName, foodPicturePath, foodPrice, foodKind, foodStock, foodDescription, foodStatus);		
		menuService.addFood(food);
		model.addAttribute("menu", food);		
		return "redirect:/MenuController/getAllMenu";
	}
	
	@GetMapping("delete")
	public String deleteMenu(@RequestParam("foodId") Integer foodId, Model model) {
		boolean deleteFood = menuService.deleteFoodById(foodId);
		if (deleteFood) {
			model.addAttribute("deleteSuccess", true); // 添加成功標籤
		} else {
			model.addAttribute("deleteSuccess", false);
		}
		List<MenuBean> foodList = menuService.getAllFoods();
 		model.addAttribute("foodList", foodList);
 		return "togo/GetAllMenu"; // jsp
		
	}
	
	@GetMapping("getMenu")
	public String getMenu(@RequestParam("foodName") String foodName, Model model) {
		List<MenuBean> foodList = menuService.getFoodByName(foodName);
		model.addAttribute("menu", foodList);		
		return "togo/GetMenu";
	}
	
	@GetMapping("getMenuForUpdate")
	public String getMenuForUpdate(@RequestParam("foodId") Integer foodId, Model model) {	
		MenuBean foods = menuService.getFoodById(foodId);
		model.addAttribute("menu", foods);	
		return "togo/UpdateMenu";
	}
	
	@GetMapping("getAllMenu")
	public String getAllMenu(Model model) {
 		List<MenuBean> foodList = menuService.getAllFoods();
 		model.addAttribute("foodList", foodList);
 		return "togo/GetAllMenu"; // jsp
 	}
	
	@PostMapping("updateMenu")
	public String updateMenu(
			@RequestParam("foodId") Integer foodId,
	        @RequestParam("foodName") String foodName,
	        @RequestParam(value = "foodPicture", required = false) MultipartFile foodPicture,
            @RequestParam("foodPrice") Integer foodPrice,
            @RequestParam("foodKind") String foodKind,
            @RequestParam("foodStock") Integer foodStock,
            @RequestParam("foodDescription") String foodDescription,
            @RequestParam("foodStatus") Integer foodStatus,
            Model model)  throws IOException {				
		// 獲取現有的食物資料
	    MenuBean existingFood = menuService.getFoodById(foodId);
	    String foodPicturePath = existingFood.getFoodPicture();
	    // 如果上傳了新的圖片，則更新圖片路徑
	    if (foodPicture != null && !foodPicture.isEmpty()) {
	        String fileName = Paths.get(foodPicture.getOriginalFilename()).getFileName().toString();
	        String uploadPath = "C:/upload/foodIMG"; // 設定圖片存儲的路徑
	        File uploadDir = new File(uploadPath);
	        if (!uploadDir.exists()) {
	            uploadDir.mkdirs(); // 如果路徑不存在，則創建
	        }
	        // 將文件保存到指定路徑
	        foodPicture.transferTo(new File(uploadPath + File.separator + fileName));
	        foodPicturePath = "/EEIT187-6/togo/MenuImages/" + fileName; // 更新路徑
	    }
		MenuBean food = new MenuBean(foodId, foodName, foodPicturePath, foodPrice, foodKind, foodStock, foodDescription, foodStatus);	
		menuService.updateFoodById(food);
		model.addAttribute("menu", food);	
		model.addAttribute("updateSuccess", true); // 添加成功標籤
		return "redirect:/MenuController/getAllMenu";
	}

}
