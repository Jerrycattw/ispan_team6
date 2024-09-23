package com.point.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.coupon.bean.CouponBean;
import com.coupon.dao.CouponDao;
import com.coupon.service.CouponService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.point.bean.PointBean;
import com.point.bean.PointMemberBean;
import com.point.bean.PointSetBean;
import com.point.dao.PointDAO;
import com.point.dao.PointSetDAO;
import com.point.dto.PointMemberDTO;
import com.point.service.PointService;
import com.point.service.PointSetService;
import com.util.DateUtils;
import com.util.IntegerAdapter;
import com.util.LocalDateAdapter;
import com.util.StringAdapter;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/Point")
@SessionAttributes(names = { "pointMemberIndex", "memberIds", "message" })
public class Point {

	@Autowired
	private PointSetService pointSetService;

	@Autowired
	private PointService pointService;

	// Home查詢-------------------------
	@GetMapping("/GetAllPoints")
	public String getAllPoints(Model model) {
		List<PointMemberDTO> pointMembers = pointService.getAllPointMember();
		model.addAttribute("pointMembers", pointMembers);
		return "point/GetAllPoints";
	}

	// 點擊檢視-------------------------
	@GetMapping("/SetPointMemberIndex")
	public String setPointMemberIndex(@RequestParam("memberID") int memberID, Model model) {
		model.addAttribute("pointMemberIndex", memberID); // 將其放入模型中
		return "redirect:GetPointsMember"; // 重定向到獲取點數會員的請求
	}

	@GetMapping("/GetPointsMember")
	public String getPointsMember(@SessionAttribute("pointMemberIndex") int memberID, Model model) {
		System.out.println("touch");
		PointMemberDTO pointMember = pointService.getPointMember(memberID);
		List<PointBean> pointsByID = pointService.getAllRecord(memberID);
		model.addAttribute("pointMember", pointMember);
		model.addAttribute("pointsByID", pointsByID);
		return "point/GetPointsMember";
	}

	// 批次新增-------------------------
	@PostMapping("/BatchInsertPoint")
	@ResponseBody
	public String batchInsertPoint(@RequestBody List<String> memberIds, Model model) {
		System.out.println(memberIds);
		String message = pointService.printMessage(memberIds);

		model.addAttribute("memberIds", memberIds);
		model.addAttribute("message", message);

		return "success";
	}

//	@GetMapping("/PreInsertBatchPoint")
//	public String preBatchInsertExcute() {
//		return "point/InsertBatchPoint";
//	}

	@PostMapping("/BatchInsertExcute")
	public String batchInsertExcute(@SessionAttribute("memberIds") List<String> memberIds, PointBean pointBean) {
		pointService.insertBatchRecord(memberIds, pointBean);
		return "redirect:GetAllPoints";
	}

	// 刪除-------------------------
	@GetMapping("/DeletePoint")
	public String deletePoint(@RequestParam("pointID") int pointId) {
		pointService.deleteOneRecord(pointId);
		return "redirect:GetPointsMember";
	}

	// 修改-------------------------
	@GetMapping("/GetPoint")
	public String getPoint(@RequestParam("pointID") int pointId, Model model) {
		PointBean point = pointService.getOneRecord(pointId);
		model.addAttribute("point", point);
		return "point/Update";
	}

	@PostMapping("/UpdatePoint")
	public String updatePoint(PointBean pointBean) {
		pointService.updatePoint(pointBean);
		return "redirect:GetPointsMember";
	}

	// 點數設定-------------------------
	@GetMapping("/GetPointSet")
	public String getPointSet(Model model) {
		PointSetBean pointSet = pointSetService.getPointSet();
		model.addAttribute("pointSet", pointSet);
		return "point/PointSet";
	}

	@PostMapping("/UpdatePointSet")
	public String updatePointSet(PointSetBean pointSet) {
		pointSet.setPointSetName("general");
		pointSetService.UpdatePoint(pointSet);
		return "redirect:GetPointSet";
	}

	// 新增-------------------------
	
//	@GetMapping("/PreInsertPoint") // jsp>controller>jsp
//	public String preInsertPoint() {
//		return "point/InsertPoint";
//	}

	@PostMapping("/InsertPoint")
	public String insertPoint(PointBean pointBean, @SessionAttribute("pointMemberIndex") int memberID) {
		pointBean.setMemberID(memberID);
		pointService.insertOneRecord(pointBean);
		return "redirect:GetPointsMember";

	}

	// search-------------------------
	@GetMapping("/SearchPoint")
	public String searchPoint(@RequestParam("search") String keyWord, Model model) {
		List<PointMemberDTO> points = pointService.searchPointMember(keyWord);
		model.addAttribute("pointMembers", points);
		model.addAttribute("keyWord", keyWord);
		return "point/GetAllPoints";
	}
	
	//映射格式-------------------------
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, "createDate",
				new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
		binder.registerCustomEditor(Date.class, "expiryDate",
				new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}

}
