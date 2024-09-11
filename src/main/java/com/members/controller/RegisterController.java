package com.members.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.Session;
import org.json.JSONObject;

import com.members.bean.Member;
import com.members.dao.MemberDao;
import com.members.service.MemberService;
import com.util.HibernateUtil;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@WebServlet("/register")
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String SECRET_KEY = "bdZAc+vtyLQpWWUv5OUqx8fooDS0ajrvRSCfgZfzC+E=";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("login.html");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
		MemberDao memberDao = new MemberDao(session);
		MemberService memberService = new MemberService(memberDao);

		String memberName = request.getParameter("memberName");
		System.out.println(memberName);
		String account = request.getParameter("account");
		System.out.println(account);
		String password = request.getParameter("password");
		System.out.println(password);
		String birthdayStr = request.getParameter("birthday");
		System.out.println(birthdayStr);
		String email = request.getParameter("email");
		System.out.println(email);
		String address = request.getParameter("address");
		System.out.println(address);
		String phone = request.getParameter("phone");
		System.out.println(phone);
		Date birthday = null;
		if (birthdayStr != null && !birthdayStr.isEmpty()) {
			try {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				birthday = new Date(simpleDateFormat.parse(birthdayStr).getTime());
			} catch (ParseException e) {
			    response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                PrintWriter out = response.getWriter();
                JSONObject jsonResponse = new JSONObject();
                jsonResponse.put("success", false);
                jsonResponse.put("error", "Invalid date format.");
                out.print(jsonResponse.toString());
                out.flush();
                return;
			}
		}

        try {
            Member member = memberService.register(memberName, account, password, birthday, email, address, phone);
            String jwtToken = Jwts.builder()
                    .setSubject(account)
                    .claim("memberId", member.getMemberId())
                    .claim("memberName", member.getMemberName())
                    .signWith(SignatureAlgorithm.HS256, new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName()))
                    .compact();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("success", true);
            jsonResponse.put("message", "Registration successful.");
            jsonResponse.put("token", jwtToken);//將jwtToken放入要回傳的資訊中
            out.print(jsonResponse.toString());
            out.flush();
        } catch (IllegalArgumentException e) {
        	 response.setContentType("application/json");
             response.setCharacterEncoding("UTF-8");
             PrintWriter out = response.getWriter();
             JSONObject jsonResponse = new JSONObject();
             jsonResponse.put("success", false);
             jsonResponse.put("error", e.getMessage());
             out.print(jsonResponse.toString());
             out.flush();
        } catch (Exception e) {
        	e.printStackTrace();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("success", false);
            jsonResponse.put("error", "Registration failed due to server error.");
            out.print(jsonResponse.toString());
            out.flush();
        }
	}

}
