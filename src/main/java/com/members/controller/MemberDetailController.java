package com.members.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.Session;

import com.google.gson.Gson;
import com.members.bean.Member;
import com.members.dao.MemberDao;
import com.members.service.MemberService;
import com.util.HibernateUtil;

@WebServlet("/memberDetail")
public class MemberDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private MemberService memberService;

    @Override
    public void init() throws ServletException {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            MemberDao memberDao = new MemberDao(session);
            memberService = new MemberService(memberDao);
        } catch (Exception e) {
            throw new ServletException("Unable to initialize MemberDetailController", e);
        }
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String memberIdStr = request.getParameter("memberId");
		int memberId = Integer.parseInt(memberIdStr);
        
        Member member = memberService.findMemberByMemberId(memberId);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (member != null) {
            String memberJson = new Gson().toJson(member);
            response.getWriter().write(memberJson);
        } else {
            response.getWriter().write("{}");
        }
	}

}
