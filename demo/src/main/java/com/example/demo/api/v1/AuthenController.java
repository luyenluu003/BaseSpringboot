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
            @RequestParam String userName,
            @RequestParam String password) {
        
        
        User user = userService.getUserByUserName(userName);
        
        
        if (user == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Username not found"
            ));
        }
        
        
        if (!SecurityUtil.verifyPassword(password, user.getPassword())) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Invalid password"
            ));
        }
        
        
        String token = UUID.randomUUID().toString();
        user.setToken(token);

        
        Date tokenExpired = new Date(System.currentTimeMillis() + 86400000);
        user.setTokenExpired(tokenExpired);
        user.setUpdatedAt(new Date());

        
        userService.saveUser(user);

        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Login successful");
        response.put("data", user);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestParam(value = "userName", required = true) String userName,
            @RequestParam(value = "email", required = true) String email,
            @RequestParam(value = "password", required = true) String password,
            @RequestParam(value = "confirmPassword", required = true) String confirmPassword
    ) {
        log.info("Request registration with userName: {}, email: {}", userName, email);

        
        if (userService.getUserByUserName(userName) != null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Username already exists"
            ));
        }

        
        if (userService.findByEmail(email) != null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Email already exists"
            ));
        }

        
        if (!password.equals(confirmPassword)) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Passwords do not match"
            ));
        }

        
        User user = User.builder()
            .userId(UUID.randomUUID().toString())
            .userName(userName)
            .email(email)
            .password(SecurityUtil.md5Encrypt(password))
            .token(UUID.randomUUID().toString())
            .tokenExpired(new Date(System.currentTimeMillis() + 86400000))
            .createdAt(new Date())
            .updatedAt(new Date())
            .build();

        
        userService.saveUser(user);

        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Registration successful");
        response.put("data", user);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @RequestParam String userName,
            @RequestParam String emailOrPhone,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword) {
        
        log.info("Request password reset for userName: {}", userName);
        
        
        User user = userService.getUserByUserName(userName);
        
        
        if (user == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Username not found"
            ));
        }
        
        
        boolean emailMatches = user.getEmail() != null && user.getEmail().equals(emailOrPhone);
        boolean phoneMatches = user.getPhoneNumber() != null && user.getPhoneNumber().equals(emailOrPhone);
        
        if (!emailMatches && !phoneMatches) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Email or phone number does not match"
            ));
        }
        
        
        if (!newPassword.equals(confirmPassword)) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Passwords do not match"
            ));
        }
        
        
        if (!SecurityUtil.isStrongPassword(newPassword)) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Password must be at least 8 characters and include uppercase, lowercase, numbers, and special characters"
            ));
        }
        
        
        user.setPassword(SecurityUtil.md5Encrypt(newPassword));
        
        
        String token = UUID.randomUUID().toString();
        user.setToken(token);
        
        
        Date tokenExpired = new Date(System.currentTimeMillis() + 86400000);
        user.setTokenExpired(tokenExpired);
        user.setUpdatedAt(new Date());
        
        
        userService.saveUser(user);
        
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Password reset successful");
        
        return ResponseEntity.ok(response);
    }
}
