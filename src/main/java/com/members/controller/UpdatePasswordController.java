package com.members.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.crypto.spec.SecretKeySpec;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.Session;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import com.members.bean.Member;
import com.members.dao.MemberDao;
import com.members.service.MemberService;
import com.util.HibernateUtil;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;

@WebServlet("/update-password")
public class UpdatePasswordController extends HttpServlet {
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
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
//                System.out.println(account);

                StringBuilder jsonString = new StringBuilder();
                try (BufferedReader reader = request.getReader()) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        jsonString.append(line);
                    }
                }

                JSONObject jsonObject = new JSONObject(jsonString.toString());
                String oldPassword = jsonObject.getString("oldPassword");
                String newPassword = jsonObject.getString("newPassword");

                Member member = memberService.findMemberByAccount(account);

                if (member != null && BCrypt.checkpw(oldPassword, member.getPassword())) {
                    boolean success = memberService.updatePassword(account, newPassword);
                    if (success) {
                        response.setStatus(HttpServletResponse.SC_OK);
                        out.write("{\"success\": true}");
                    } else {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        out.write("{\"success\": false, \"message\": \"密碼更新失敗\"}");
                    }
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.write("{\"success\": false, \"message\": \"舊密碼不正確\"}");
                }
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.write("{\"success\": false, \"message\": \"內部伺服器錯誤\"}");
                e.printStackTrace();
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.write("{\"success\": false, \"message\": \"未提供 token\"}");
        }

        out.flush();
    }
}
