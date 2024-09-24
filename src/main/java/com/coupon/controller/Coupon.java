package com.coupon.controller;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coupon.bean.CouponBean;
import com.coupon.bean.CouponMemberBean;
import com.coupon.dao.CouponDao;
import com.coupon.dao.CouponDao2;
import com.coupon.dto.CouponDTO;
import com.coupon.dto.CouponDistributeDTO;
import com.coupon.service.CouponService;
import com.coupon.service.CouponService2;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.util.HibernateUtil;
import com.util.IntegerAdapter;
import com.util.LocalDateAdapter;
import com.util.OpenSessionViewFilter;
import com.util.StringAdapter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/Coupon")
public class Coupon  {

	
	@Autowired
	CouponService2 couponService2;
	
 	//查詢所有
 	@GetMapping("/Home")
 	@ResponseBody
 	public List<CouponDTO> getAllCouponWithTagsAndReceived() {
 		List<CouponDTO> couponDTOs = couponService2.getAllCoupon();
 		return couponDTOs;
 	}
 	
 	//查詢單筆	
 	@PostMapping("/update")
 	@ResponseBody
 	public Map<String, Object> getOneCouponWithTagss(@RequestBody String couponId) {
 		CouponDTO couponDTO = couponService2.getCouponById(couponId);
 		Map<String, List<String>> tagOptions = couponService2.getTagOptions();
 		
 		Map<String, Object> data = new HashMap<String, Object>();
 		data.put("coupon", couponDTO);
 		data.put("tagOptions", tagOptions);
 		return data;
 	}
 	
 	//修改單筆
 	@PostMapping("/updateExcute")
 	private String updateCouponWithTags(CouponBean couponBean,
 										@RequestParam("hiddenCouponId") int couponId, 
 										@RequestParam(value = "product",required = false) String[] productTags,
										@RequestParam(value = "togo",required = false) String[] togoTags) {
 		couponBean.setCouponId(couponId);
 		couponService2.updateCoupon(couponBean,productTags,togoTags);
 		return "redirect:/coupon/Home.html";
 	}
 	

 	//查詢預設
 	@GetMapping("/insert")
 	@ResponseBody
 	public Map<String, Object> getDefaultCouponWithTags(){
 		CouponDTO couponDTO=new CouponDTO();
 		Map<String, List<String>> tagOptions = couponService2.getTagOptions();
 		
 		Map<String, Object> data = new HashMap<String, Object>();
 		data.put("coupon", couponDTO);
 		data.put("tagOptions", tagOptions);
 		return data;
 		
 	}
 	
 	//新增
 	@PostMapping("/insertExcute")
 	public String insertCouponWithTags(CouponBean couponBean, 
 										@RequestParam(value = "product",required = false) String[] productTags,
 										@RequestParam(value = "togo",required = false) String[] togoTags) {
 		couponService2.insertCoupon(couponBean,productTags,togoTags);
 		return "redirect:/coupon/Home.html";
 	}
 	
 	//刪除
 	@GetMapping("delete")
 	public String deleteCoupon(@RequestParam("couponId") String couponId) {
 		couponService2.deleteCouponById(couponId);
 		return "redirect:/coupon/Home.html";
 	}
 	
 	//search
 	@GetMapping("/search")
 	@ResponseBody
 	public List<CouponDTO> searchCoupon(@RequestParam("search") String keyWord){
 		System.out.println("touch");
 		List<CouponDTO> coupons = couponService2.searchCoupons(keyWord);
 		System.out.println(coupons);
 		return coupons;
 	}
 	
 	//distribute
 	private void distributeCoupon(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
 		BufferedReader reader = request.getReader();
        String memberIds = reader.readLine();
 		List<CouponDistributeDTO> couponDistributes = couponService2.getDistributeOption(memberIds);
 		Gson gson = new GsonBuilder().create();
 		String json = gson.toJson(couponDistributes);
 		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(json);
 	}
 	
 	//查詢 coupon
 	private void getOneCoupon(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
 		BufferedReader reader = request.getReader();
        String memberIds = reader.readLine();
        CouponDTO couponDTO = couponService2.getCouponById(memberIds);
     // 使用自定义的Gson实例
 	    Gson gson = new GsonBuilder()
 	        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
 	        .create();
 	    
 		String json = gson.toJson(couponDTO);
 		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(json);
 	}
 	
// 	//
// 	private void distributeExcute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
// 		 // 读取请求体中的 JSON 数据
// 	// 读取请求体中的 JSON 数据
//        StringBuilder stringBuilder = new StringBuilder();
//        try (BufferedReader reader = request.getReader()) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                stringBuilder.append(line);
//            }
//        }
//        String requestBody = stringBuilder.toString();
//
//        // 使用 Gson 解析 JSON 数据
//        JsonObject jsonObject = JsonParser.parseString(requestBody).getAsJsonObject();
//        String memberIds = jsonObject.get("memberIds").getAsString();
//        String couponId = jsonObject.get("couponId").getAsString();
//        String perMaxCoupon = jsonObject.get("perMaxCoupon").getAsString();
//     
//        List<CouponMemberBean> couponMembers = couponService.distributeExcute(memberIds,couponId,perMaxCoupon);
//        Gson gson = new GsonBuilder().create();
// 		String json = gson.toJson(couponMembers);
// 		response.setContentType("application/json");
//	    response.setCharacterEncoding("UTF-8");
//	    response.getWriter().write(json);
//
// 	}
// 	
//	protected void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		doGet(request, response);
//	}
 	

}
