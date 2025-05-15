package com.example.demo.api.v1;

import com.example.demo.feature.user.model.User;
import com.example.demo.feature.user.service.UserService;
import com.example.demo.util.SecurityUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Log4j2
@RestController
@RequestMapping("/v1/authen")
public class AuthenController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestParam String identifier,
            @RequestParam String password) {
        
        // Kiểm tra xem identifier là email hay số điện thoại
        boolean isEmail = identifier.contains("@");
        
        User user;
        if (isEmail) {
            user = userService.findByEmail(identifier);
        } else {
            user = userService.findByPhoneNumber(identifier);
        }
        
        // Kiểm tra user tồn tại
        if (user == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", isEmail ? "Email not found" : "Phone number not found"
            ));
        }
        
        // Kiểm tra mật khẩu
        if (!SecurityUtil.verifyPassword(password, user.getPassword())) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Invalid password"
            ));
        }
        
        // Tạo token
        String token = UUID.randomUUID().toString();
        user.setToken(token);

        // Thiết lập thời gian hết hạn token (ví dụ: 24 giờ)
        Date tokenExpired = new Date(System.currentTimeMillis() + 86400000);
        user.setTokenExpired(tokenExpired);
        user.setUpdatedAt(new Date());

        // Lưu user với token mới
        userService.saveUser(user);

        // Trả về thông tin user và token
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Login successful");
        response.put("data", user);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestParam(value = "identifier", required = true) String identifier,
            @RequestParam("password") String password,
            @RequestParam(value = "userName", required = false) String userName
    ) {
        log.info("Request registration with identifier: {}", identifier);

        // Kiểm tra xem identifier là email hay số điện thoại
        boolean isEmail = identifier.contains("@");
        String baseOn = isEmail ? "email" : "phone";
        
        // Kiểm tra xem email hoặc số điện thoại đã tồn tại chưa
        if (isEmail && userService.findByEmail(identifier) != null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Email already exists"
            ));
        }

        if (!isEmail && userService.findByPhoneNumber(identifier) != null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Phone number already exists"
            ));
        }

        // Tạo user mới
        User user = User.builder()
            .userId(UUID.randomUUID().toString())
            .email(isEmail ? identifier : null)
            .phoneNumber(!isEmail ? identifier : null)
            .baseOn(baseOn)
            .password(SecurityUtil.md5Encrypt(password))
            .userName(userName != null ? userName : "User_" + System.currentTimeMillis())
            .token(UUID.randomUUID().toString())
            .tokenExpired(new Date(System.currentTimeMillis() + 86400000))
            .createdAt(new Date())
            .updatedAt(new Date())
            .build();

        // Lưu user
        userService.saveUser(user);

        // Trả về thông tin user và token
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Registration successful");
        response.put("data", user);

        return ResponseEntity.ok(response);
    }
}
