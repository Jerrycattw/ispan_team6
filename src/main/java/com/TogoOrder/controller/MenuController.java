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
	
	@PostMapping("add")
	public String addMenu(
			@RequestParam("foodName") String foodName,
			@RequestParam("foodPicture") MultipartFile file,
            @RequestParam("foodPrice") Integer foodPrice,
            @RequestParam("foodKind") String foodKind,
            @RequestParam("foodStock") Integer foodStock,
            @RequestParam("foodDescription") String foodDescription,
            @RequestParam("foodStatus") Integer foodStatus,
            Model model) throws IOException {
		String uploadPath = "C:/upload/menuIMG";
        File fileSaveDir = new File(uploadPath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }
        String foodPicture = "";
        if (!file.isEmpty()) { // 檢查文件是否為空
            String fileName = file.getOriginalFilename(); // 獲取文件名
            File destinationFile = new File(uploadPath + File.separator + fileName);
            file.transferTo(destinationFile); // 保存文件到指定路徑
            // 設置 foodPicture 為圖片的相對路徑（或 URL 路徑）
            foodPicture = "/EEIT187-6/menuIMG/" + fileName;
        }
		MenuBean food = new MenuBean(foodName, foodPicture, foodPrice, foodKind, foodStock, foodDescription, foodStatus);		
		menuService.addFood(food);
		model.addAttribute("menu", food);		
		return "redirect:/MenuController/getAll";
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
	
	@GetMapping("get")
	public String getMenu(@RequestParam("foodName") String foodName, Model model) {
		List<MenuBean> foodList = menuService.getFoodByName(foodName);
		model.addAttribute("menu", foodList);		
		return "togo/GetMenu"; // jsp
	}
	
	@GetMapping("getForUpdate")
	public String getMenuForUpdate(@RequestParam("foodId") Integer foodId, Model model) {	
		MenuBean foods = menuService.getFoodById(foodId);
		model.addAttribute("menu", foods);	
		return "togo/UpdateMenu"; // jsp
	}
	
	@GetMapping("getAll")
	public String getAllMenu(Model model) {
 		List<MenuBean> foodList = menuService.getAllFoods();
 		model.addAttribute("foodList", foodList);
 		return "togo/GetAllMenu"; // jsp
 	}
	
	@PostMapping("update")
	public String updateMenu(
			@RequestParam("foodId") Integer foodId,
	        @RequestParam("foodName") String foodName,
	        @RequestParam(value = "foodPicture", required = false) MultipartFile file,
            @RequestParam("foodPrice") Integer foodPrice,
            @RequestParam("foodKind") String foodKind,
            @RequestParam("foodStock") Integer foodStock,
            @RequestParam("foodDescription") String foodDescription,
            @RequestParam("foodStatus") Integer foodStatus,
            Model model)  throws IOException {				
		// 獲取現有的食物資料
	    MenuBean existingFood = menuService.getFoodById(foodId);
	    String foodPicture = existingFood.getFoodPicture();
	    // 如果上傳了新的圖片，則更新圖片路徑
	    if (foodPicture != null && !foodPicture.isEmpty()) {
	        String uploadPath = "C:/upload/menuIMG"; // 設定圖片存儲的路徑
	        File uploadDir = new File(uploadPath);
	        if (!uploadDir.exists()) {
	            uploadDir.mkdirs(); // 如果路徑不存在，則創建
	        }
	        String fileName = file.getOriginalFilename(); // 獲取文件名
            File destinationFile = new File(uploadPath + File.separator + fileName);
            file.transferTo(destinationFile); // 保存文件到指定路徑
            // 設置 foodPicture 為圖片的相對路徑（或 URL 路徑）
            foodPicture = "/EEIT187-6/menuIMG/" + fileName;
	    }
		MenuBean food = new MenuBean(foodId, foodName, foodPicture, foodPrice, foodKind, foodStock, foodDescription, foodStatus);	
		menuService.updateFoodById(food);
		model.addAttribute("menu", food);	
		model.addAttribute("updateSuccess", true); // 添加成功標籤
		return "redirect:/MenuController/getAll";
	}

}
