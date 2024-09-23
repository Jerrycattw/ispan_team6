package com.TogoOrder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.TogoOrder.bean.MenuBean;
import com.TogoOrder.bean.TogoItemBean;
import com.TogoOrder.service.MenuService;
import com.TogoOrder.service.TogoItemService;
import com.TogoOrder.service.TogoService;
import com.TogoOrder.util.TogoCalculateUtils;


@Controller
@RequestMapping("/TogoItemController/*")
public class TogoItemController {
	
	@Autowired
	private TogoItemService togoItemService;
	
	@Autowired
	private TogoService togoService;
	
	@Autowired
	private MenuService menuService;
       
	@GetMapping("add")
	public String addTogoItem(
			@RequestParam("togoId") Integer togoId,
			@RequestParam("foodId") Integer foodId,
			@RequestParam("amount") Integer amount,
			Model model) {
		// 檢查 foodId 是否存在於菜單中
	    MenuBean food = menuService.getFoodById(foodId);   
	    if (food == null || food.getFoodPrice() == null) {	
	        // 如果 foodId 不存在，設置錯誤消息並轉發到錯誤頁面或回到訂單頁面
	        model.addAttribute("errorMessage", "無效的餐點編號：" + foodId);
	        return "redirect:/TogoItemController/get";
//	        return; // 終止方法
	    }
	    String foodName = food.getFoodName();
		Integer foodPrice = food.getFoodPrice();
		Integer togoItemPrice = foodPrice*amount;
		TogoItemBean togoItem = new TogoItemBean(togoId, foodId, foodName, foodPrice, amount, togoItemPrice);
		togoItemService.addTogoItem(togoItem);
		//更新總金額
		Integer totalPrice = TogoCalculateUtils.sumOfTotalPrice(togoId);
		togoService.updateTotalPrice(togoId, totalPrice);	
		model.addAttribute("togoItem", togoItem);
		return "redirect:/TogoItemController/get?togoId=" + togoId;
	}
	
	@GetMapping("delete")
	public String deleteTogoItem(
			@RequestParam("togoId") Integer togoId,
			@RequestParam("foodId") Integer foodId,
			Model model) {
		togoItemService.deleteTogoItemByTogoIdFoodId(togoId, foodId);
		//更新總金額
		Integer totalPrice = TogoCalculateUtils.sumOfTotalPrice(togoId);
		togoService.updateTotalPrice(togoId, totalPrice);	
		model.addAttribute("deleteSuccess", true); // 添加成功標籤
		model.addAttribute("updateSuccess", true); // 添加成功標籤
		return "redirect:/TogoItemController/get?togoId=" + togoId;
	}
	
	@GetMapping("get")
	public String getTogoItem(@RequestParam("togoId") Integer togoId, Model model) {
		List<TogoItemBean> togoItemList = togoItemService.getTogoItemByTogoId(togoId);
		// 获取可能的错误消息
		String errorMessage = (String) model.getAttribute("errorMessage"); 
	    model.addAttribute("errorMessage", errorMessage);
	    model.addAttribute("togoItemList", togoItemList);
	    return "togo/GetTogoItem"; // jsp
	}
	
	@GetMapping("getForUpdate")
	public String getTogoItemForUpdate(
			@RequestParam("togoId") Integer togoId,
			@RequestParam("foodId") Integer foodId,
			Model model) {
		TogoItemBean togoItem = togoItemService.getTogoItemByTogoIdFoodId(togoId, foodId);		
		model.addAttribute("togoItem", togoItem);
		return "togo/UpdateTogoItem"; // jsp
	}
	
	@GetMapping("update")
	public String updateTogoItem(
			@RequestParam("togoId") Integer togoId,
			@RequestParam("foodId") Integer foodId,
			@RequestParam("foodName") String foodName,
			@RequestParam("foodPrice") Integer foodPrice,
			@RequestParam("amount") Integer amount,
			Model model) {
		int togoItemPrice = foodPrice*amount;
		TogoItemBean togoItem = new TogoItemBean(togoId, foodId, foodName, foodPrice, amount, togoItemPrice);		
		togoItemService.updateTogoItemByTogoIdFoodId(togoItem);
		//更新總金額
		Integer totalPrice = TogoCalculateUtils.sumOfTotalPrice(togoId);
		togoService.updateTotalPrice(togoId, totalPrice);
		model.addAttribute("togoItem", togoItem);
		model.addAttribute("updateSuccess", true); // 添加成功標籤
		return "redirect:/TogoItemController/get?togoId=" + togoId;
	}

}
