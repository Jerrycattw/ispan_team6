package com.members.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import javax.crypto.spec.SecretKeySpec;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.Session;

import com.members.bean.Member;
import com.members.dao.MemberDao;
import com.members.service.MemberService;
import com.util.HibernateUtil;
import com.google.gson.Gson;

@WebServlet("/member-info")
public class MemberInfoController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String SECRET_KEY = "bdZAc+vtyLQpWWUv5OUqx8fooDS0ajrvRSCfgZfzC+E=";
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	System.out.println("我有跑到這裡來嗎");
        String authorizationHeader = request.getHeader("Authorization");
        System.out.println("Authorization Header: " + authorizationHeader);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);

            try {
                Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token)
                    .getBody();
                String account = claims.getSubject();
                Member member = memberService.findMemberByAccount(account);
                

                if (member != null) {
                    Gson gson = new Gson();
                    String memberJson = gson.toJson(member);
                    out.print(memberJson);
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print("{\"error\": \"Member not found\"}");
                }
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                out.print("{\"error\": \"Invalid token\"}");
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\": \"No token provided\"}");
        }

        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
