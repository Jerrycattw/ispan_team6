package com.coupon.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.coupon.bean.CouponBean;
import com.coupon.bean.CouponMemberBean;
import com.coupon.dao.CouponDao;
import com.coupon.dao.CouponDao2;
import com.coupon.dto.CouponDTO;
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

@WebServlet("/Coupon/*")
public class Coupon extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	CouponService couponService;
	CouponService2 couponService2;
	

 	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
 		 Session session = (Session) request.getAttribute("hibernateSession");
 		 SessionFactory sessionFactory = (SessionFactory) request.getAttribute("hibernateSessionFactory");
         couponService = new CouponService(session);
         couponService2 = new CouponService2(session);
         
         
 		// 獲取URL中的操作名稱
 			String action = request.getPathInfo().substring(1);
 			System.out.println(action);
 			switch (action) {
 			case "Home"://優惠券首頁
 				getAllCouponWithTagsAndReceived(request, response);//查詢
 				break;
 			case "update":
 				System.out.println("switch update touch");
 				getOneCouponWithTags(request, response);
 				break;
 			case "updateExcute":
 				System.out.println("switch updateExcute touch");
 				updateCouponWithTags(request, response);
 				break;	
 			case "insert":
 				System.out.println("switch insert touch");
 				getDefaultCouponWithTags(request, response);
 				break;
 			case "insertExcute":
 				System.out.println("switch insertExcute touch");
 				insertCouponWithTags(request, response);
 				break;
 			case "deleteCoupon":
 				System.out.println("switch delete touch");
 				deleteCoupon(request, response);
 				break;
 			case "search":
 				System.out.println("switch search touch");
 				searchCoupon(request, response);
 				break;
// 			case "distribute":
// 				System.out.println("switch distribute touch");
// 				distributeCoupon(request, response);
// 				break;
// 			case "getOneCoupon":
// 				System.out.println("switch getOneCoupon touch");
// 				getOneCoupon(request, response);
// 				break;
// 			case "distributeExcute":
// 				System.out.println("switch distributeExcute touch");
// 				distributeExcute(request, response);
// 				break;	
 			default:
 				response.sendError(HttpServletResponse.SC_NOT_FOUND);
 			}
 		
	}
 	
 	//查詢所有
 	private void getAllCouponWithTagsAndReceived(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
// 		List<CouponBean> coupons = couponService.getAllCouponWithTagsAndReceived();
// 		List<CouponDTO> couponsDTO = couponService.convertCouponsToDTOs(coupons);
//
// 		
// 		// 使用自定义的Gson实例
// 	    Gson gson = new GsonBuilder()
// 	        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
// 	        .create();
// 	    
// 	    String jsonCoupons = gson.toJson(couponsDTO);
//		request.setAttribute("jsonCoupons", jsonCoupons);
//		response.setContentType("application/json");
//	    response.setCharacterEncoding("UTF-8");
//	    response.getWriter().write(jsonCoupons);
 		
// 		------------------------------------------------------------
 		List<CouponDTO> couponDTOs = couponService2.getAllCoupon();
 		
 		//使用自定义的Gson实例
 	    Gson gson = new GsonBuilder()
 	        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
 	        .create();
 	    
 	    String jsonCoupons = gson.toJson(couponDTOs);
		request.setAttribute("jsonCoupons", jsonCoupons);
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(jsonCoupons);
 		
	}
 	
 	//查詢單筆
 	private void getOneCouponWithTags(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
// 		BufferedReader reader = request.getReader();
//        String couponId = reader.readLine();
//        CouponBean coupon=couponService.getCouponWithTags(couponId);
//        CouponDTO couponDTO = couponService.convertCouponToDTO(coupon);
//        System.out.println(couponDTO);
//        Map<String, List<String>> tagOptions = couponService.getTagOptions();
//        
//     // 使用自定义的Gson实例
// 	    Gson gson = new GsonBuilder()
// 	        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
// 	        .create();
// 	    
// 	   JsonElement jsonCoupon = gson.toJsonTree(couponDTO);
//       JsonElement jsonTagOptions = gson.toJsonTree(tagOptions);
//       
//       JsonObject jsonResponse = new JsonObject();
//       jsonResponse.add("coupon", jsonCoupon);
//       jsonResponse.add("tagOptions", jsonTagOptions);
//       
// 	   response.setContentType("application/json");
// 	   response.setCharacterEncoding("UTF-8");
// 	   response.getWriter().write(jsonResponse.toString());
// 		--------------------------------------------------------------------------------
		BufferedReader reader = request.getReader();
        String couponId = reader.readLine();
        CouponDTO couponDTO = couponService2.getCouponById(couponId);
        Map<String, List<String>> tagOptions = couponService2.getTagOptions();
        
     // 使用自定义的Gson实例
 	    Gson gson = new GsonBuilder()
 	        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
 	        .create();
 	    
 	   JsonElement jsonCoupon = gson.toJsonTree(couponDTO);
       JsonElement jsonTagOptions = gson.toJsonTree(tagOptions);
       
       JsonObject jsonResponse = new JsonObject();
       jsonResponse.add("coupon", jsonCoupon);
       jsonResponse.add("tagOptions", jsonTagOptions);
       
 	   response.setContentType("application/json");
 	   response.setCharacterEncoding("UTF-8");
 	   response.getWriter().write(jsonResponse.toString());		
 	   
 	   
 	}
 	
 	//修改單筆
 	private void updateCouponWithTags(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
// 		String couponId = request.getParameter("hiddenCouponId");
// 		String couponCode = request.getParameter("couponCode");
// 		String couponDescription = request.getParameter("couponDescription");
// 		String couponStartDate = request.getParameter("couponStartDate");
// 		String couponEndDate = request.getParameter("couponEndDate");
// 		String maxCoupon = request.getParameter("maxCoupon");
// 		String perMaxCoupon = request.getParameter("perMaxCoupon");
// 		String couponStatus = request.getParameter("couponStatus");
// 		String rulesDescription = request.getParameter("rulesDescription");
// 		String discountType = request.getParameter("discountType");
// 		String discount = request.getParameter("discount");
// 		String minOrderDiscount = request.getParameter("minOrderDiscount");
// 		String maxDiscount = request.getParameter("maxDiscount");
// 		String[] productTags = request.getParameterValues("product");
// 		String[] togoTags = request.getParameterValues("togo");
// 		couponService.updateCoupon(couponId,couponCode,couponDescription,couponStartDate,couponEndDate,maxCoupon,perMaxCoupon,couponStatus,rulesDescription,discountType,discount,minOrderDiscount, maxDiscount);
// 		couponService.deleteCouponTag(couponId,productTags,togoTags);
// 		couponService.insertCouponTag(Integer.parseInt(couponId),productTags,togoTags);
// 		response.sendRedirect(request.getContextPath() + "/coupon/Home.html");
// 		--------------------------------------------------------------------------------------------
 		String couponId = request.getParameter("hiddenCouponId");
 		String couponCode = request.getParameter("couponCode");
 		String couponDescription = request.getParameter("couponDescription");
 		String couponStartDate = request.getParameter("couponStartDate");
 		String couponEndDate = request.getParameter("couponEndDate");
 		String maxCoupon = request.getParameter("maxCoupon");
 		String perMaxCoupon = request.getParameter("perMaxCoupon");
 		String couponStatus = request.getParameter("couponStatus");
 		String rulesDescription = request.getParameter("rulesDescription");
 		String discountType = request.getParameter("discountType");
 		String discount = request.getParameter("discount");
 		String minOrderDiscount = request.getParameter("minOrderDiscount");
 		String maxDiscount = request.getParameter("maxDiscount");
 		String[] productTags = request.getParameterValues("product");
 		String[] togoTags = request.getParameterValues("togo");
 		couponService2.updateCoupon(couponId,couponCode,couponDescription,couponStartDate,couponEndDate,maxCoupon,perMaxCoupon,couponStatus,rulesDescription,discountType,discount,minOrderDiscount, maxDiscount, productTags, togoTags);
 		response.sendRedirect(request.getContextPath() + "/coupon/Home.html");
 		
 		
 		
	}
 	
 	//查詢預設
 	private void getDefaultCouponWithTags(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
// 		CouponBean coupon=new CouponBean();
// 		Map<String, List<String>> tagOptions = couponService.getTagOptions();
// 		
// 	// 使用自定义的Gson实例
// 	    Gson gson = new GsonBuilder()
// 	    	.excludeFieldsWithoutExposeAnnotation()
// 	        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())//null 序列化為""
// 	        .registerTypeAdapter(String.class, new StringAdapter())//null 序列化為""
// 	        .registerTypeAdapter(Integer.class, new IntegerAdapter())//0 序列化為""
// 	        .create();
// 	    
// 	   JsonElement jsonCoupon = gson.toJsonTree(coupon);
//       JsonElement jsonTagOptions = gson.toJsonTree(tagOptions);
//       
//       JsonObject jsonResponse = new JsonObject();
//       jsonResponse.add("coupon", jsonCoupon);
//       jsonResponse.add("tagOptions", jsonTagOptions);
// 	   response.setContentType("application/json");
// 	   response.setCharacterEncoding("UTF-8");
// 	   response.getWriter().write(jsonResponse.toString());
// 	   ---------------------------------------------------------------------------------
		CouponDTO couponDTO=new CouponDTO();
		Map<String, List<String>> tagOptions = couponService2.getTagOptions();
		
	// 使用自定义的Gson实例
	    Gson gson = new GsonBuilder()
	        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())//null 序列化為""
	        .registerTypeAdapter(String.class, new StringAdapter())//null 序列化為""
	        .registerTypeAdapter(Integer.class, new IntegerAdapter())//0 序列化為""
	        .create();
	    
	   JsonElement jsonCoupon = gson.toJsonTree(couponDTO);
      JsonElement jsonTagOptions = gson.toJsonTree(tagOptions);
      
      JsonObject jsonResponse = new JsonObject();
      jsonResponse.add("coupon", jsonCoupon);
      jsonResponse.add("tagOptions", jsonTagOptions);
	   response.setContentType("application/json");
	   response.setCharacterEncoding("UTF-8");
	   response.getWriter().write(jsonResponse.toString());
 		
 	}
 	
 	//新增
 	private void insertCouponWithTags(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
 		request.setCharacterEncoding("UTF-8");
 		String couponCode = request.getParameter("couponCode");
 		String couponDescription = request.getParameter("couponDescription");
 		String couponStartDate = request.getParameter("couponStartDate");
 		String couponEndDate = request.getParameter("couponEndDate");
 		String maxCoupon = request.getParameter("maxCoupon");
 		String perMaxCoupon = request.getParameter("perMaxCoupon");
 		String couponStatus = request.getParameter("couponStatus");
 		String rulesDescription = request.getParameter("rulesDescription");
 		String discountType = request.getParameter("discountType");
 		String discount = request.getParameter("discount");
 		String minOrderDiscount = request.getParameter("minOrderDiscount");
 		String maxDiscount = request.getParameter("maxDiscount");
 		String[] productTags = request.getParameterValues("product");
 		String[] togoTags = request.getParameterValues("togo");

 		
// 		//執行insert，並回傳自動生成的ID
// 		int newCouponId=couponService.insertCoupon(couponCode,couponDescription,couponStartDate,couponEndDate,maxCoupon,perMaxCoupon,couponStatus,rulesDescription,discountType,discount,minOrderDiscount, maxDiscount);
// 		//依照自動生成的ID insert
// 		couponService.insertCouponTag(newCouponId,productTags,togoTags);
// 		response.sendRedirect(request.getContextPath() + "/coupon/Home.html");
// 		----------------------------------------------------------------------------------------------------------
 		
 		couponService2.insertCoupon(couponCode,couponDescription,couponStartDate,couponEndDate,maxCoupon,perMaxCoupon,couponStatus,rulesDescription,discountType,discount,minOrderDiscount, maxDiscount, productTags, togoTags);
 		response.sendRedirect(request.getContextPath() + "/coupon/Home.html");
 	}
 	
 	//刪除
 	private void deleteCoupon(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
 		
// 		String couponCode = request.getParameter("couponId");
// 		couponService.deleteCouponWithTags(couponCode);
// 		response.sendRedirect(request.getContextPath() + "/coupon/Home.html");
// 		----------------------------------------------------------------------------------------------
 		String couponId = request.getParameter("couponId");
 		couponService2.deleteCouponById(couponId);
 		response.sendRedirect(request.getContextPath() + "/coupon/Home.html");
 	}
 	
 	//search
 	private void searchCoupon(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
 		
 		String keyWord = request.getParameter("search");
 		System.out.println(keyWord);
 		List<CouponBean> coupons = couponService.getAllCouponWithTagsAndReceived(keyWord);
 		
 		// 使用自定义的Gson实例
 	    Gson gson = new GsonBuilder()
 	    	.excludeFieldsWithoutExposeAnnotation()
 	        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
 	        .create();
 	    
 	    String jsonCoupons = gson.toJson(coupons);
//		request.setAttribute("jsonCoupons", jsonCoupons);
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(jsonCoupons);
 	}
 	
// 	//distribute
// 	private void distributeCoupon(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
// 		BufferedReader reader = request.getReader();
//        String memberIds = reader.readLine();
// 		List<CouponDistributeBean> couponDistributes = couponService.getDistributeOption(memberIds);
// 		Gson gson = new GsonBuilder().create();
// 		String json = gson.toJson(couponDistributes);
// 		response.setContentType("application/json");
//	    response.setCharacterEncoding("UTF-8");
//	    response.getWriter().write(json);
// 	}
// 	
// 	//查詢 coupon
// 	private void getOneCoupon(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
// 		BufferedReader reader = request.getReader();
//        String memberIds = reader.readLine();
//        List<CouponBean> OneCoupon = couponService.getOneCoupon(memberIds);
//     // 使用自定义的Gson实例
// 	    Gson gson = new GsonBuilder()
// 	        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
// 	        .create();
// 	    
// 		String json = gson.toJson(OneCoupon);
// 		response.setContentType("application/json");
//	    response.setCharacterEncoding("UTF-8");
//	    response.getWriter().write(json);
// 	}
// 	
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
