package com.example.demo.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.servlet.http.HttpServletRequest;

public class SecurityUtil {
    
    public static String md5Encrypt(String input) {
        return DigestUtils.md5Hex(input).toLowerCase();
    }
    
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        return md5Encrypt(plainPassword).equals(hashedPassword);
    }

    public static UserDetails validateJWT(String token, String jwtKey) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateJWT'");
    }

    public static String getUserNameFromHttpRequest(HttpServletRequest httpServletRequest, String jwtKey) {
        String token = getTokenFromHttpRequest(httpServletRequest, jwtKey);
        if (token != null) {
            UserDetails userDetails = validateJWT(token, jwtKey);
            return userDetails != null ? userDetails.getUsername() : null;
        }
        return null;
    }

    public static String getTokenFromHttpRequest(HttpServletRequest httpServletRequest, String jwtKey) {
        String authorization = httpServletRequest.getHeader(org.springframework.http.HttpHeaders.AUTHORIZATION);
        if (authorization != null && authorization.startsWith("Bearer ")) {
            return authorization.split(" ")[1].trim();
        }
        return null;
    }
}
