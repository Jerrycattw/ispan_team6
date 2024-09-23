package com.admin.controller;

import com.admin.bean.Admin;
import com.admin.service.AdminService;
import com.members.bean.Member;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@GetMapping("/addAdmin")
	public String showAddAdminPage(Model model) {
	    List<Admin> admins = adminService.findAllAdmins();
	    model.addAttribute("admins", admins);
		return "Admin/addAdmin";
	}
	
	

//    @GetMapping("/findAllAdminsResult")
//    public String showFindAllAdminsResultPage(Model model) {
//        return "Admin/findAllAdmins"; 
//    }

	@GetMapping("/searchMembers")
	public String showSearchMembersPage(Model model) {
		return "Admin/searchMembers"; // 對應到 /WEB-INF/pages/admin/searchMembers.jsp
	}

	// 登入
	@PostMapping("/login")
	public String loginPost(@RequestParam String account, @RequestParam String password, Model model,
			HttpServletResponse response) throws IOException {
		Admin admin = adminService.findAdminByAccount(account);

		if (admin != null && admin.getPassword().equals(password)) {
			if (admin.getStatus() == 'A') {
				model.addAttribute("message", "Login successful.");
				return "adminDashboard"; // 登入成功後的頁面
			} else {
				model.addAttribute("error", "您的帳號被暫停或停權、請聯絡客服");
				return "loginPage"; // 返回登入頁面
			}
		} else {
			model.addAttribute("error", "帳號或密碼錯誤");
			return "loginPage"; // 返回登入頁面
		}
	}

	// 新增管理員
	@PostMapping("/add")
	public String addAdmin(@RequestParam("adminName") String adminName, 
	                       @RequestParam("account") String account,
	                       @RequestParam("password") String password, 
	                       RedirectAttributes redirectAttributes) {
	    Admin admin = new Admin();
	    admin.setAdminName(adminName);
	    admin.setAccount(account);
	    admin.setPassword(password);

	    boolean success = adminService.addAdmin(admin);

	    if (success) {
	        redirectAttributes.addFlashAttribute("message", "已成功新增管理員");
	        return "redirect:/admin/findAllAdmins"; // 重定向到管理員列表頁面
	    } else {
	        redirectAttributes.addFlashAttribute("error", "帳號重複，請重試！");
	        return "redirect:/admin/addAdmin"; // 返回到新增管理員的頁面並顯示錯誤訊息
	    }
	}


	// 查詢所有管理員
	@GetMapping("/findAllAdmins")
	public String findAllAdmins(Model model) {
		List<Admin> admins = adminService.findAllAdmins();
		model.addAttribute("admins", admins);
		return "Admin/findAllAdmins"; // 管理員列表頁面
	}

	// 搜尋會員
	@GetMapping("/memberSearch")
	public String memberSearch(
			@RequestParam(value = "memberName", required = false) String memberName,
	        @RequestParam(value = "account", required = false) String account, 
	        @RequestParam(value = "email", required = false) String email,
	        @RequestParam(value = "address", required = false) String address, 
	        @RequestParam(value = "phone", required = false) String phone,
	        @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, 
	        @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
	        @RequestParam(value = "status", required = false) String status, 
	        Model model) {
		List<Member> members = adminService.searchMembers(memberName, account, email, address, phone, startDate,
				endDate, status);
		model.addAttribute("members", members);
		return "Admin/memberSearchResult"; // 返回會員搜尋結果頁面
	}

	// 更新會員狀態
	@PostMapping("/updateMemberStatus")
	public String updateMemberStatus(@RequestParam int memberId, @RequestParam String status, Model model) {
		boolean isUpdated = adminService.updateMemberStatus(memberId, status);

		if (isUpdated) {
			model.addAttribute("message", "會員狀態更新成功！");
		} else {
			model.addAttribute("message", "更新失敗，請重試！");
		}

		return "memberDetail"; // 更新後返回會員詳細頁面
	}

	// 顯示管理員詳細資訊
	@GetMapping("/adminDetail/{adminId}")
	public String adminDetail(@PathVariable int adminId, Model model) {
		Admin admin = adminService.findAdminById(adminId);
		model.addAttribute("admin", admin);
		return "adminDetail"; // 返回管理員詳細頁面
	}

	// 更新管理員
	@PostMapping("/updateAdmin")
	public String updateAdmin(@RequestParam int adminId, @RequestParam String adminName, @RequestParam String account,
			@RequestParam String password, @RequestParam char status, Model model) {
		Admin admin = new Admin();
		admin.setAdminId(adminId);
		admin.setAdminName(adminName);
		admin.setAccount(account);
		admin.setPassword(password);
		admin.setStatus(status);

		boolean isUpdate = adminService.updateAdmin(admin);

		if (isUpdate) {
			model.addAttribute("message", "管理員資訊更新成功！");
		} else {
			model.addAttribute("message", "更新失敗，請重試！");
		}

		return "adminDetail"; // 更新後返回管理員詳細頁面
	}
}
