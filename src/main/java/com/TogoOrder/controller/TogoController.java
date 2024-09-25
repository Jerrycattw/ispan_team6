package com.TogoOrder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.TogoOrder.bean.TogoBean;
import com.TogoOrder.service.TogoService;


@Controller
@RequestMapping("/TogoController/*")
public class TogoController {
	
	@Autowired
    private TogoService togoService;
	
	@GetMapping("getAll")
	public String getAllTogo(Model model) {
		List<TogoBean> togoList = togoService.getAllTogo();	
		model.addAttribute("togoList", togoList);
		return "togo/GetAllTogo"; //jsp
 	}
	
	@GetMapping("add")
	public String addTogo(
			@RequestParam("memberId") Integer memberId,
			@RequestParam("tgName") String tgName,
			@RequestParam("tgPhone") String tgPhone,
			@RequestParam("totalPrice") Integer totalPrice,
			@RequestParam("rentId") Integer rentId,
			@RequestParam("togoStatus") Integer togoStatus,
			@RequestParam("restaurantId") Integer restaurantId,
			@RequestParam("togoMemo") String togoMemo,
			Model model) {	
		TogoBean togo = new TogoBean(memberId, tgName, tgPhone, totalPrice, rentId, togoStatus, restaurantId, togoMemo);
		System.out.println("會員編號.................."+togo.getMemberId());
		togoService.addTogo(togo);
		model.addAttribute("togo", togo);	
		return "redirect:/TogoController/getAll";
	}
	
	@GetMapping("delete")
	public String deleteTogo(@RequestParam("togoId") Integer togoId, Model model) {	
		TogoBean togoDelete = togoService.getTogoById(togoId);
		togoService.deleteTogoById(togoDelete.getTogoId());	
		model.addAttribute("deleteSuccess", true); // 添加成功標籤
		return "redirect:/TogoController/getAll";
	}
	
	@GetMapping("getForUpdate")
	public String getTogoForUpdate(@RequestParam("togoId") Integer togoId, Model model) {
		TogoBean togo = togoService.getTogoById(togoId);
		model.addAttribute("togo", togo);	
		return "togo/UpdateTogo";
	}
	
	@GetMapping("get")
	public String getTogo(@RequestParam("tgPhone") String tgPhone, Model model) {
		List<TogoBean> togoList = togoService.getTogoByPhone(tgPhone);
	    model.addAttribute("togoList", togoList);	    
		return "togo/GetTogo"; // jsp
	}
	
	@GetMapping("update")
	public String updateTogo(
			@RequestParam("togoId") Integer togoId,
			@RequestParam("memberId") Integer memberId,
			@RequestParam("tgName") String tgName,
			@RequestParam("tgPhone") String tgPhone,
			@RequestParam("totalPrice") Integer totalPrice,
			@RequestParam("rentId") Integer rentId,
			@RequestParam("togoStatus") Integer togoStatus,
			@RequestParam("restaurantId") Integer restaurantId,
			@RequestParam("togoMemo") String togoMemo,
			Model model) {
		TogoBean togo = new TogoBean(togoId, memberId, tgName, tgPhone, totalPrice, rentId, togoStatus, restaurantId, togoMemo);	
		togoService.updateTogoById(togo);
		model.addAttribute("togo", togo);
		model.addAttribute("updateSuccess", true); // 添加成功標籤
		return "redirect:/TogoController/getAll";
	}
	
}
