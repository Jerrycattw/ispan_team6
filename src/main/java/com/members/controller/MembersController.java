package com.members.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.members.bean.Member;
import com.members.bean.MemberDTO;
import com.members.service.MemberService;
import com.util.HibernateUtil;

@RestController
@RequestMapping("/member")
public class MembersController {

    private static final String SECRET_KEY = "bdZAc+vtyLQpWWUv5OUqx8fooDS0ajrvRSCfgZfzC+E=";
    private final MemberService memberService;

    @Autowired
    public MembersController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestParam("account") String account,
                                     @RequestParam("password") String password,
                                     HttpServletResponse response) {
        Map<String, Object> result = new HashMap<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Member member = memberService.login(account, password);
            if (member != null) {
                String jwtToken = Jwts.builder()
                        .setSubject(account)
                        .claim("memberId", member.getMemberId())
                        .claim("memberName", member.getMemberName())
                        .signWith(SignatureAlgorithm.HS256, new SecretKeySpec(
                                SECRET_KEY.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName()))
                        .compact();

                result.put("token", jwtToken);
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                result.put("error", "Invalid account or password");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            result.put("error", e.getMessage());
        }
        return result;
    }

    @GetMapping("/memberDetail")
    public ResponseEntity<Member> getMemberDetail(@RequestParam("memberId") int memberId) {
        Member member = memberService.findMemberByMemberId(memberId);

        if (member != null) {
            return ResponseEntity.ok(member);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/member-info")
    public ResponseEntity<?> getMemberInfo(@RequestHeader("Authorization") String authorizationHeader) {
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
                    // 將 Member 轉換為 MemberDTO
                    MemberDTO memberDTO = new MemberDTO(
                        member.getMemberId(),
                        member.getMemberName(),
                        member.getAccount(),
                        member.getPassword(),
                        member.getBirthday(),
                        member.getEmail(),
                        member.getAddress(),
                        member.getPhone(),
                        member.getRegisterDate(),
                        member.getStatus()
                    );
                    
                    return ResponseEntity.ok(memberDTO);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                         .body("{\"error\": \"Member not found\"}");
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                     .body("{\"error\": \"Invalid token\"}");
            }
        } else {
            return ResponseEntity.badRequest()
                                 .body("{\"error\": \"No token provided\"}");
        }
    }
    
    @PostMapping("/register")
    @Transactional
    public ResponseEntity<?> register(@RequestParam("memberName") String memberName,
                                       @RequestParam("account") String account,
                                       @RequestParam("password") String password,
                                       @RequestParam("birthday") String birthdayStr,
                                       @RequestParam("email") String email,
                                       @RequestParam("address") String address,
                                       @RequestParam("phone") String phone) {
        Date birthday = null;
        if (birthdayStr != null && !birthdayStr.isEmpty()) {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                birthday = new Date(simpleDateFormat.parse(birthdayStr).getTime());
            } catch (ParseException e) {
                return ResponseEntity.badRequest()
                        .body(Map.of("success", false, "error", "Invalid date format."));
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
            return ResponseEntity.ok()
                    .body(Map.of("success", true, "message", "Registration successful.", "token", jwtToken));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "error", "Registration failed due to server error."));
        }
    }

    @PostMapping("/update-password")
    public ResponseEntity<Map<String, Object>> updatePassword(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody Map<String, String> passwordMap) {
        Map<String, Object> responseMap = new HashMap<>();
        
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);

            try {
                // 解析 JWT
                Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token)
                    .getBody();
                String account = claims.getSubject();

                // 取得傳入的舊密碼與新密碼
                String oldPassword = passwordMap.get("oldPassword");
                String newPassword = passwordMap.get("newPassword");

                // 根據 account 查詢會員
                Member member = memberService.findMemberByAccount(account);

                // 驗證舊密碼，並更新新密碼
                if (member != null && BCrypt.checkpw(oldPassword, member.getPassword())) {
                    boolean success = memberService.updatePassword(account, newPassword);
                    if (success) {
                        responseMap.put("success", true);
                        return ResponseEntity.ok(responseMap);
                    } else {
                        responseMap.put("success", false);
                        responseMap.put("message", "密碼更新失敗");
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMap);
                    }
                } else {
                    responseMap.put("success", false);
                    responseMap.put("message", "舊密碼不正確");
                    return ResponseEntity.badRequest().body(responseMap);
                }
            } catch (Exception e) {
                responseMap.put("success", false);
                responseMap.put("message", "內部伺服器錯誤");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMap);
            }
        } else {
            responseMap.put("success", false);
            responseMap.put("message", "未提供 token");
            return ResponseEntity.badRequest().body(responseMap);
        }
    }
}
