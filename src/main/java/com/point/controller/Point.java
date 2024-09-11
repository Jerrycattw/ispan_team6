package com.point.controller;

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
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.Session;

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
import com.point.service.PointServiceFactory;
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

@WebServlet("/Point/*")
public class Point extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PointServiceFactory pointServiceFactory;
	private PointSetService pointSetService;
	private PointService pointService;
	

 	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
 			
 			Session session = (Session) request.getAttribute("hibernateSession");
 			pointServiceFactory = new PointServiceFactory(session);
 			pointSetService = pointServiceFactory.getPointSetService();
 			pointService = pointServiceFactory.getPointService();
 			
 			
 	// 獲取URL中的操作名稱
 			String path = request.getPathInfo().substring(1);

 			switch (path) {
 			case "GetAllPoints"://優惠券首頁
 				GetAllPoints(request, response);
 				break;
 			//批次新增	
 			case "BatchInsertPoint":
 				BatchInsertPoint(request, response);
 				break;
//			//搜尋bar
 			case "SearchPoint":
 				SearchPoint(request, response);
 				break;
			//檢視
 			case "GetPointsMember":
 				GetPointsMember(request, response);
 				break;
 			case "DeletePoint":
 				DeletePoint(request, response);
 				break;
// 			case "DeletePointsResult":
// 				DeletePointsResult(request, response);
// 				break;
// 			case "GetAllPointsRecord":
// 				GetAllPointsRecord(request, response);
// 				break;	
 			case "GetPoint":
 				GetPoint(request, response);
 				break;	
 			case "GetPointSet":
 				GetPointSet(request, response);
 				break;	
// 			case "GetPointsMemberResult":
// 				GetPointsMemberResult(request, response);
// 				break;	
 			case "InsertBatchPoint":
 				InsertBatchPoint(request, response);
 				break;	
 			case "InsertPoint":
 				InsertPoint(request, response);
 				break;	
 			case "UpdatePoint":
 				UpdatePoint(request, response);
 				break;	
 			case "UpdatePointSet":
 				UpdatePointSet(request, response);
 				break;	
 			default:
 				response.sendError(HttpServletResponse.SC_NOT_FOUND);
 			}
 		
	}
 	
 	//ok//Home查詢
 	private void GetAllPoints(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
 		List<PointMemberDTO> pointMembers = pointService.getAllPointMember();
		request.setAttribute("pointMembers", pointMembers);
		request.getRequestDispatcher("/point/GetAllPoints.jsp").forward(request, response);
	}
 	
 	//ok//點擊檢視
 	private void GetPointsMember(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
 		ServletContext context = getServletContext();
 		
 		int memberID=request.getParameter("memberID")!=null ? Integer.parseInt(request.getParameter("memberID")):(int)(request.getAttribute("memberID"));
// 		int memberID=Integer.parseInt(request.getParameter("memberID"));
 		System.out.println(memberID);
 		PointMemberDTO pointMember = pointService.getPointMember(memberID);
 		List<PointBean> pointsByID = pointService.getAllRecord(memberID);
		request.setAttribute("pointMember", pointMember);
		request.setAttribute("pointsByID", pointsByID);
		context.setAttribute("pointMemberIndex", memberID);
		request.getRequestDispatcher("/point/GetPointsMember.jsp").forward(request, response);
	}
 	
 	//批次新增	
 	private  void BatchInsertPoint(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
 		BufferedReader reader = request.getReader();
 	    StringBuilder json = new StringBuilder();
 	    String line;
 	    while ((line = reader.readLine()) != null) {
 	        json.append(line);
 	    }
 	    
 	    
 	   JsonObject jsonObject = new Gson().fromJson(json.toString(), JsonObject.class);
 	  JsonArray memberIDArray = jsonObject.getAsJsonArray("memberIDs");

 	  List<String> memberIDs = new ArrayList<>();
 	  for (JsonElement element : memberIDArray) {
 	      memberIDs.add(element.getAsString());
 	  }

 	  System.out.println(memberIDs);

 	   boolean success = true;

 	  ServletContext context = getServletContext();
 	  context.setAttribute("memberIDs", memberIDs);
 	  String message=pointService.printMessage(memberIDs);
 	  context.setAttribute("message", message);

 	    response.getWriter().write("{\"success\": " + success + "}");
 	 
	}
 	
 	//OK
 	private  void DeletePoint(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

 		int pointID=Integer.parseInt(request.getParameter("pointID"));
 
 		pointService.deleteOneRecord(pointID);
 		
 		ServletContext context = getServletContext();
 		int pointMemberID = (int)context.getAttribute("pointMemberIndex");
 		request.setAttribute("memberID", pointMemberID);
//		request.getRequestDispatcher("DeletePointsResult").forward(request, response);
		request.getRequestDispatcher("GetPointsMember").forward(request, response);
 		
	}
 	
 	//XX
// 	private  void DeletePointsResult(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
// 		ServletContext context = getServletContext();
// 		
// 		int memberID=(int)request.getAttribute("pointMemberID");
// 		PointMemberBean pointMember = PointDAO.getPointMember(memberID);
// 		List<PointBean> pointsByID = PointDAO.getAllRecord(memberID);
//		request.setAttribute("pointMember", pointMember);
//		request.setAttribute("pointsByID", pointsByID);
//		request.getRequestDispatcher("/point/GetPointsMember.jsp").forward(request, response);
//	}
 	
 	//
 	private void GetAllPointsRecord(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//// 		String empno=request.getParameter("empno");
// 		List<PointBean> points = PointDAO.getAllRecord();
//		request.setAttribute("points", points);
//		request.getRequestDispatcher("/point/GetAllPointsRecord.jsp").forward(request, response);
	}
 	
 	//OK
 	private void GetPoint(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
 		int pointID=Integer.parseInt(request.getParameter("pointID"));
 		PointBean point = pointService.getOneRecord(pointID);
		request.setAttribute("point", point);
		request.getRequestDispatcher("/point/Update.jsp").forward(request, response);
	}
 	
 	//OK
 	private void GetPointSet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
 		PointSetBean pointSet = pointSetService.getPointSet();
 		System.out.println(pointSet);
		request.setAttribute("pointSet", pointSet);
		request.getRequestDispatcher("/point/PointSet.jsp").forward(request, response);
	}
 	
 	//
// 	private void GetPointsMemberResult(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
// 		int memberID=(int)(request.getAttribute("memberID"));
// 		System.out.println(memberID);
// 		PointMemberBean pointMember = PointDAO.getPointMember(memberID);
// 		List<PointBean> pointsByID = PointDAO.getAllRecord(memberID);
//		request.setAttribute("pointMember", pointMember);
//		request.setAttribute("pointsByID", pointsByID);
//		request.getRequestDispatcher("/point/GetPointsMember.jsp").forward(request, response);
//	}
 	
 	//
 	private void InsertBatchPoint(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
 		ServletContext context = getServletContext();
 		List<String> membersID = (List<String>)context.getAttribute("memberIDs");
 		System.out.println(membersID);
 		int pointChange=Integer.parseInt(request.getParameter("pointChange"));
 		String createDateString=request.getParameter("createDate");
 		int transactionID=Integer.parseInt(request.getParameter("transactionID"));
 		String transactionType=request.getParameter("transactionType");
 		

 		pointService.insertBatchRecord(membersID, pointChange, createDateString, transactionID, transactionType);	

		request.getRequestDispatcher("GetAllPoints").forward(request, response);
	}
 	
 	//
 	private void InsertPoint(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
 		ServletContext context = getServletContext();
 		int pointMemberID = (int)context.getAttribute("pointMemberIndex");		
 		int pointChange=Integer.parseInt(request.getParameter("pointChange"));
 		String createDateString=request.getParameter("createDate");
 		int transactionID=Integer.parseInt(request.getParameter("transactionID"));
 		String transactionType=request.getParameter("transactionType");
 		
 		pointService.insertOneRecord(pointMemberID, pointChange, createDateString, transactionID, transactionType);
 		
 		request.setAttribute("memberID", pointMemberID);
		request.getRequestDispatcher("GetPointsMember").forward(request, response);
	}
 	
 	//搜尋bar
 	private void SearchPoint(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
 		String keyWord=request.getParameter("search");
 		List<PointMemberDTO> points =pointService.searchPointMember(keyWord);
		request.setAttribute("pointsSearch", points);
		request.setAttribute("keyWord", keyWord);
		request.getRequestDispatcher("/point/GetPointsSearch.jsp").forward(request, response);
	}
 	
 	//ok
 	private void UpdatePoint(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
 		int pointID=Integer.parseInt(request.getParameter("pointID"));
 		int memberID=Integer.parseInt(request.getParameter("memberID"));
 		int pointChange=Integer.parseInt(request.getParameter("pointChange"));
 		Date createDate=DateUtils.GetDateFromString(request.getParameter("createDate"));
 		Date expiryDate=DateUtils.GetDateFromString(request.getParameter("expiryDate"));
 		int pointUsage=Integer.parseInt(request.getParameter("pointUsage"));
 		int transactionID=Integer.parseInt(request.getParameter("transactionID"));
 		String transactionType=request.getParameter("transactionType");

 		pointService.updatePoint(pointChange, createDate, expiryDate, pointUsage, transactionID, transactionType,pointID);
		request.setAttribute("memberID", memberID);
//		request.getRequestDispatcher("GetPointsMemberResult").forward(request, response);
		request.getRequestDispatcher("GetPointsMember").forward(request, response);
	}
 	
 	//OK
 	private void UpdatePointSet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {	
 		int AnountPerPoint=Integer.parseInt(request.getParameter("perAmount"));
 		int pointsEarned=Integer.parseInt(request.getParameter("pointEarn"));
 		String birthType=request.getParameter("birthType");
 		String isExpiry=request.getParameter("expiration");
 		String expiryMonth=request.getParameter("month");
 		String expiryDay=request.getParameter("day");
 		String setDescription=request.getParameter("comment");
 		pointSetService.UpdatePoint(AnountPerPoint, pointsEarned, pointsEarned, expiryMonth, expiryDay, birthType, setDescription, isExpiry);
 		
		request.getRequestDispatcher("GetPointSet").forward(request, response);
	}

 	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
