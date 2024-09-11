package com.members.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.crypto.spec.SecretKeySpec;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.Session;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import com.members.bean.Member;
import com.members.dao.MemberDao;
import com.members.service.MemberService;
import com.util.HibernateUtil;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SECRET_KEY = "bdZAc+vtyLQpWWUv5OUqx8fooDS0ajrvRSCfgZfzC+E=";

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        String account = request.getParameter("account");
        String password = request.getParameter("password");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            MemberDao memberDao = new MemberDao(session);
            MemberService memberService = new MemberService(memberDao);

            try {
                Member member = memberService.login(account, password);
                try (PrintWriter out = response.getWriter()) {
                    if (member != null) {
                        String jwtToken = Jwts.builder()
                                .setSubject(account)
                                .claim("memberId", member.getMemberId())
                                .claim("memberName", member.getMemberName())
                                .signWith(SignatureAlgorithm.HS256, new SecretKeySpec(
                                        SECRET_KEY.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName()))
                                .compact();

                        response.setStatus(HttpServletResponse.SC_OK);
                        out.print("{\"token\": \"" + jwtToken + "\"}");
                    } else {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        out.print("{\"error\": \"Invalid account or password\"}");
                    }
                }
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                try (PrintWriter out = response.getWriter()) {
                    out.print("{\"error\": \"" + e.getMessage() + "\"}");
                }
            }
        }
    }

}
