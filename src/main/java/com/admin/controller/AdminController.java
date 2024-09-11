package com.admin.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.Session;

import com.admin.bean.Admin;
import com.admin.bean.AdminPermission;
import com.admin.bean.Permission;
import com.admin.dao.AdminDao;
import com.admin.dao.AdminPermissionDao;
import com.admin.dao.PermissionDao;
import com.admin.service.AdminPermissionService;
import com.admin.service.AdminService;
import com.admin.service.PermissionService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.members.bean.Member;
import com.util.HibernateUtil;

@WebServlet("/Admin/AdminController/*")
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	AdminService adminService;
//	PermissionService permissionService;
//	AdminPermissionService adminPermissionService;

	@Override
	public void init() throws ServletException {
		try {
			AdminDao adminDao = new AdminDao();
			adminService = new AdminService(adminDao);

		} catch (Exception e) {
			throw new ServletException("Unable to initialize LoginController", e);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getPathInfo();
		System.out.println("doPost");
		if ("/login".equals(action)) {
			login(request, response);
		} else if ("/addAdmin".equals(action)) {
			addAdmin(request, response);
		} else if ("/updateMemberStatus".equals(action)) {
			updateMemberStatus(request, response);
		} else if ("/updateMember".equals(action)) {
			updateMember(request, response);
		} else if ("/updateAdmin".equals(action)) {
			updateAdmin(request, response);
		} else if ("/updateAdminStatus".equals(action)) {
			updateAdminStatus(request, response);
		}
		/*
		 * else if ("/assignPermission".equals(action)) { // Add this line
		 * assignPermission(request, response); // Add this line } else if
		 * ("/removePermission".equals(action)) { // Add this line
		 * removePermission(request, response); // Add this line }
		 */
		else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			JsonObject errorJson = new JsonObject();
			errorJson.addProperty("error", "Invalid action.");
			response.getWriter().print(errorJson.toString());
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("doget");
		String action = request.getPathInfo();
		if ("/memberSearch".equals(action)) {
			memberSearch(request, response);
		} else if ("/findAllAdmins".equals(action)) {
			findAllAdmins(request, response);
		} else if ("/adminDetail".equals(action)) {
			adminDetail(request, response);
		}
		/*else if ("/allAdminsAndPermissions".equals(action)) {
			allAdminsAndPermissions(request, response);
		} else if ("/getAdminPermissions".equals(action)) {
			getAdminPermissions(request, response);
		}*/ else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			JsonObject errorJson = new JsonObject();
			errorJson.addProperty("error", "Invalid action.");
			response.getWriter().print(errorJson.toString());
		}

	}

	// 登入
	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("登入(admincontroller)");
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();

		// 從請求中取得帳號與密碼
		String account = request.getParameter("account");
		String password = request.getParameter("password");

		// 查詢管理員
		Admin admin = null;
		try {
			admin = adminService.findAdminByAccount(account);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			JsonObject errorJson = new JsonObject();
			errorJson.addProperty("error", "Internal server error.");
			out.print(errorJson.toString());
			return;
		}

		// 驗證帳號和密碼
		if (admin != null && admin.getPassword().equals(password)) {
			if (admin.getStatus() == 'A') { // 判斷狀態是否為 'A'
				// 成功登入
				JsonObject successJson = new JsonObject();
				successJson.addProperty("message", "Login successful.");
				out.print(successJson.toString());
			} else {
				// 狀態不是 'A'，禁止登入
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				JsonObject failureJson = new JsonObject();
				failureJson.addProperty("error", "您的帳號被暫停或停權、請聯絡客服");
				out.print(failureJson.toString());
			}
		} else {
			// 帳號或密碼錯誤
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			JsonObject failureJson = new JsonObject();
			failureJson.addProperty("error", "帳號或密碼錯誤");
			out.print(failureJson.toString());
		}
	}

	// 新增管理員
	private void addAdmin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("新增管理員(admincontroller)");

		String adminName = request.getParameter("adminName");
		String account = request.getParameter("account");
		String password = request.getParameter("password");

		// 創建 Admin 實例
		Admin admin = new Admin();
		admin.setAdminName(adminName);
		admin.setAccount(account);
		admin.setPassword(password);

		// 嘗試新增管理員
		boolean success = adminService.addAdmin(admin);

		// 根據操作結果進行處理
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();

		JsonObject jsonResponse = new JsonObject();
		jsonResponse.addProperty("success", success);
		if (success) {
			jsonResponse.addProperty("message", "已新增管理員");
		} else {
			jsonResponse.addProperty("message", "帳號重複，請重試！");
		}

		out.print(jsonResponse.toString());
		out.flush();
	}

	// 查詢管理員一覽
	private void findAllAdmins(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("查詢管理員(admincontroller)");
		List<Admin> admins = adminService.findAllAdmins();
		request.setAttribute("admins", admins);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/Admin/findAllAdmins.jsp");
		dispatcher.forward(request, response);
	}

	// 搜尋會員
	private void memberSearch(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("搜尋(admincontroller)");
		// 從請求中獲取搜尋條件
		String memberName = request.getParameter("memberName");
		String account = request.getParameter("account");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		String startDateStr = request.getParameter("startDate");
		String endDateStr = request.getParameter("endDate");
		String status = request.getParameter("status");

		// 將字串日期轉換為 java.sql.Date
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;

		if (startDateStr != null && !startDateStr.isEmpty()) {
			startDate = java.sql.Date.valueOf(startDateStr);
		}

		if (endDateStr != null && !endDateStr.isEmpty()) {
			endDate = java.sql.Date.valueOf(endDateStr);
		}

		// 調用Service層進行資料搜尋
		List<Member> members = adminService.searchMembers(memberName, account, email, address, phone, startDate,
				endDate, status);
		// 將結果設置為請求屬性
		request.setAttribute("members", members);

		// 轉發到JSP頁面進行顯示
		RequestDispatcher dispatcher = request.getRequestDispatcher("/Admin/memberSearchResult.jsp");
		dispatcher.forward(request, response);
	}

	// 更新會員狀態
	private void updateMemberStatus(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("更新會員狀態(admincontroller)");
		response.setContentType("application/json"); // 設置返回內容類型為 JSON
		response.setCharacterEncoding("UTF-8");

		// 讀取請求體中的 JSON 數據
		StringBuilder stringBuilder = new StringBuilder();
		try (BufferedReader reader = request.getReader()) {
			String line;
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
			}
		}

		JsonObject jsonObject = JsonParser.parseString(stringBuilder.toString()).getAsJsonObject();
		int memberId = jsonObject.get("memberId").getAsInt();
		String newStatus = jsonObject.get("status").getAsString();
		System.out.println("------?????-----");
		System.out.println(newStatus);
		System.out.println("------?????-----");

		JsonObject responseObject = new JsonObject();

		try {
			// 更新會員狀態
			boolean isUpdated = adminService.updateMemberStatus(memberId, newStatus);

			if (isUpdated) {
				responseObject.addProperty("status", "success");
			} else {
				responseObject.addProperty("status", "failure");
			}
		} catch (Exception e) {
			// 處理例外
			responseObject.addProperty("status", "error");
		}

		// 返回 JSON 格式的響應
		try (PrintWriter out = response.getWriter()) {
			out.print(responseObject.toString());
			out.flush();
		}
	}

	// 更新會員資訊
	private void updateMember(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("更新會員全部資訊(admincontroller)");
		String memberId = request.getParameter("memberId");
		String memberName = request.getParameter("memberName");
		String account = request.getParameter("account");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		String status = request.getParameter("status");

		Member member = new Member();
		member.setMemberId(Integer.parseInt(memberId));
		member.setMemberName(memberName);
		member.setAccount(account);
		member.setEmail(email);
		member.setAddress(address);
		member.setPhone(phone);
		member.setStatus(status);

		boolean isUpdate = adminService.updateMember(member);

		// 設置返回的JSON格式的回應
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();

		JsonObject jsonResponse = new JsonObject();
		jsonResponse.addProperty("success", isUpdate);
		if (isUpdate) {
			jsonResponse.addProperty("message", "會員資訊更新成功！");
		} else {
			jsonResponse.addProperty("message", "更新失敗，請重試！");
		}

		out.print(jsonResponse.toString());
		out.flush();
	}

	// 顯示管理員細節
	private void adminDetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("adminDetail(admincontroller)");
		String adminIdStr = request.getParameter("adminId");
		int adminId = Integer.parseInt(adminIdStr);

		Admin admin = adminService.findAdminById(adminId);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		if (admin != null) {
			String adminJson = new Gson().toJson(admin);
			response.getWriter().write(adminJson);
		} else {
			response.getWriter().write("{}");
		}
	}

	// 更新管理員
	private void updateAdmin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("更新管理員(admincontroller)");
		int adminId = Integer.parseInt(request.getParameter("adminId"));
		System.out.println(request.getParameter("adminId"));
		System.out.println(adminId);
		String adminName = request.getParameter("adminName");
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		char status = request.getParameter("status").charAt(0);

		Admin admin = new Admin();
		admin.setAdminId(adminId);
		admin.setAdminName(adminName);
		admin.setAccount(account);
		admin.setPassword(password);
		admin.setStatus(status);

		boolean isUpdate = adminService.updateAdmin(admin);

		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		JsonObject jsonResponse = new JsonObject();
		jsonResponse.addProperty("success", isUpdate);
		if (isUpdate) {
			jsonResponse.addProperty("message", "會員資訊更新成功！");
		} else {
			jsonResponse.addProperty("message", "更新失敗，請重試！");
		}

		out.print(jsonResponse.toString());
		out.flush();
	}

	// 更新管理員狀態
	private void updateAdminStatus(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    System.out.println("更新管理員狀態(admincontroller)");
	    response.setContentType("application/json"); // 設置返回內容類型為 JSON
	    response.setCharacterEncoding("UTF-8");

	    // 讀取請求體中的 JSON 數據
	    StringBuilder stringBuilder = new StringBuilder();
	    try (BufferedReader reader = request.getReader()) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            stringBuilder.append(line);
	        }
	    }

	    JsonObject responseObject = new JsonObject();
	    try {
	        JsonObject jsonObject = JsonParser.parseString(stringBuilder.toString()).getAsJsonObject();
	        int adminId = jsonObject.get("adminId").getAsInt();
	        String newStatus = jsonObject.get("status").getAsString();
	        System.out.println("----------");
	        System.out.println(newStatus);
	        System.out.println("----------");

	        // 檢查 newStatus 是否為單個字符
	        if (newStatus.length() != 1) {
	            throw new IllegalArgumentException("Status must be a single character");
	        }

	        // 更新管理員狀態
	        boolean isUpdated = adminService.updateAdminStatus(adminId, newStatus);

	        if (isUpdated) {
	            responseObject.addProperty("status", "success");
	        } else {
	            responseObject.addProperty("status", "failure");
	        }
	    } catch (IllegalArgumentException e) {
	        // 處理錯誤輸入的情況
	        responseObject.addProperty("status", "error");
	        responseObject.addProperty("message", e.getMessage());
	    } catch (Exception e) {
	        // 處理其他異常
	        responseObject.addProperty("status", "error");
	        responseObject.addProperty("message", "An unexpected error occurred");
	        e.printStackTrace(); // 這裡可以選擇是否打印堆棧跟蹤
	    }

	    // 返回 JSON 格式的響應
	    try (PrintWriter out = response.getWriter()) {
	        out.print(responseObject.toString());
	        out.flush();
	    }
	}

	/*
	 * // 查詢所有管理員權限頁面 private void allAdminsAndPermissions(HttpServletRequest
	 * request, HttpServletResponse response) throws ServletException, IOException {
	 * System.out.println("查詢所有管理員權限頁面"); List<Admin> admins =
	 * adminService.findAllAdmins(); // 查詢所有管理員 List<Permission> allPermissions =
	 * permissionService.getAllPermissions(); // 查詢所有權限
	 * 
	 * // 為每個管理員查詢其擁有的權限 Map<Integer, List<AdminPermission>> adminPermissionsMap =
	 * new HashMap<>(); for (Admin admin : admins) { List<AdminPermission>
	 * adminPermissions =
	 * adminPermissionService.getPermissionsByAdminId(admin.getAdminId());
	 * adminPermissionsMap.put(admin.getAdminId(), adminPermissions); }
	 * 
	 * // 建立權限名稱映射 Map<String, String> permissionNameMap = new HashMap<>();
	 * permissionNameMap.put("member", "會員管理"); permissionNameMap.put("order",
	 * "商城管理"); permissionNameMap.put("points", "積分優惠券管理");
	 * permissionNameMap.put("rental", "租借用品管理");
	 * permissionNameMap.put("reservation", "餐廳訂位管理");
	 * permissionNameMap.put("store", "訂餐管理");
	 * 
	 * request.setAttribute("admins", admins); // 將管理員列表傳遞給 JSP
	 * request.setAttribute("allPermissions", allPermissions); // 將所有權限傳遞給 JSP
	 * request.setAttribute("adminPermissionsMap", adminPermissionsMap); //
	 * 將每個管理員的權限傳遞給 JSP request.setAttribute("permissionNameMap",
	 * permissionNameMap); // 將權限名稱映射傳遞給 JSP
	 * 
	 * RequestDispatcher dispatcher =
	 * request.getRequestDispatcher("/Admin/adminPermission.jsp"); // 引導至 JSP
	 * dispatcher.forward(request, response); }
	 * 
	 * // 查詢管理員權限 private void getAdminPermissions(HttpServletRequest request,
	 * HttpServletResponse response) throws ServletException, IOException { try {
	 * String adminIdParam = request.getParameter("adminId"); int adminId =
	 * Integer.parseInt(adminIdParam);
	 * 
	 * // Retrieve the permissions for the admin List<AdminPermission> permissions =
	 * adminPermissionService.getPermissionsByAdminId(adminId);
	 * 
	 * // Convert the list to JSON and send the response
	 * response.setContentType("application/json");
	 * response.setCharacterEncoding("UTF-8"); Gson gson = new Gson(); String json =
	 * gson.toJson(permissions); response.getWriter().write(json); } catch
	 * (NumberFormatException e) {
	 * response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid admin ID"); }
	 * catch (Exception e) {
	 * response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
	 * "An error occurred while processing the request"); e.printStackTrace(); //
	 * Log the exception } }
	 * 
	 * // 增加權限 private void assignPermission(HttpServletRequest request,
	 * HttpServletResponse response) throws ServletException, IOException {
	 * System.out.println("assignPermission(admincontroller)"); // 使用 getParameter
	 * 來讀取請求中的參數 int adminId = Integer.parseInt(request.getParameter("adminId"));
	 * int permissionId = Integer.parseInt(request.getParameter("permissionId"));
	 * 
	 * // 執行權限分配邏輯 boolean success =
	 * adminPermissionService.assignPermissionToAdmin(adminId, permissionId);
	 * 
	 * // 構建 JSON 回應 JsonObject jsonResponse = new JsonObject();
	 * jsonResponse.addProperty("success", success); if (success) {
	 * jsonResponse.addProperty("message", "權限分配成功！"); } else {
	 * jsonResponse.addProperty("message", "權限分配失敗，請重試！"); }
	 * 
	 * // 設置回應類型為 JSON 並返回結果 response.setContentType("application/json");
	 * PrintWriter out = response.getWriter(); out.print(jsonResponse.toString());
	 * out.flush(); } // 移除權限 private void removePermission(HttpServletRequest
	 * request, HttpServletResponse response) throws ServletException, IOException {
	 * System.out.println("removePermission(admincontroller)");
	 * 
	 * // 使用 getParameter 來讀取請求中的參數 int adminId =
	 * Integer.parseInt(request.getParameter("adminId"));
	 * System.out.println("????:"+request.getParameter("adminId")); int permissionId
	 * = Integer.parseInt(request.getParameter("permissionId"));
	 * 
	 * // 執行權限移除邏輯 boolean success =
	 * adminPermissionService.removePermissionFromAdmin(adminId, permissionId);
	 * 
	 * // 構建 JSON 回應 JsonObject jsonResponse = new JsonObject();
	 * jsonResponse.addProperty("success", success); if (success) {
	 * jsonResponse.addProperty("message", "權限移除成功！"); } else {
	 * jsonResponse.addProperty("message", "權限移除失敗，請重試！"); }
	 * 
	 * // 設置回應類型為 JSON 並返回結果 response.setContentType("application/json");
	 * PrintWriter out = response.getWriter(); out.print(jsonResponse.toString());
	 * out.flush(); }
	 * 
	 * 
	 * // // 解析JSON 數據 // private JsonObject parseJsonRequest(HttpServletRequest
	 * request) throws IOException { // StringBuilder stringBuilder = new
	 * StringBuilder(); // try (BufferedReader reader = request.getReader()) { //
	 * String line; // while ((line = reader.readLine()) != null) { //
	 * stringBuilder.append(line); // } // } // return
	 * JsonParser.parseString(stringBuilder.toString()).getAsJsonObject(); // }
	 * 
	 */
}
