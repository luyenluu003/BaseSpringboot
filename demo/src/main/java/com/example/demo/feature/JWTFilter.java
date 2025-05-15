package com.example.demo.feature;

import java.io.IOException;
import java.util.Enumeration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.demo.exception.NotAuthorizedException;
import com.example.demo.util.SecurityUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class JWTFilter extends OncePerRequestFilter {
    @Value("${demo.jwt.secret.key}")
    String jwtKey;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    HandlerExceptionResolver resolver;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        String token = null;
        if(authorization != null && authorization.startsWith("Bearer ")) {
            try{
                token = authorization.split(" ")[1].trim();

            }catch (Exception e){
                showHeader(httpServletRequest);
                resolver.resolveException(httpServletRequest,response,null,new NotAuthorizedException( "Bearer is invalid"));
                return;
            }
            UserDetails userDetails = null;
            try{
                userDetails = SecurityUtil.validateJWT(token, jwtKey);
            } catch (JWTVerificationException jwt){
                resolver.resolveException(httpServletRequest, response, null, new RuntimeException(jwt.getMessage()));
                return;
            }

            log.info("TOKEN ===> "+token);

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                    = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            filterChain.doFilter(httpServletRequest, response);
        }else{
            showHeader(httpServletRequest);
            resolver.resolveException(httpServletRequest,response,null,new NotAuthorizedException("Bearer header not found "));
        }
    }

    private void showHeader(HttpServletRequest httpServletRequest) {
        Enumeration<String> headers = httpServletRequest.getHeaderNames();
        StringBuilder builder = new StringBuilder();
        while (headers.hasMoreElements()) {
            String headerName = headers.nextElement();
            builder.append(headerName).append(": ").append(httpServletRequest.getHeader(headerName)).append(", ");
        }
        log.info("HEADERS (" + builder + ") ==> Path: " + httpServletRequest.getServletPath());
        log.info("paramMap: {} ==> Path: {}", httpServletRequest.getParameterMap(), httpServletRequest.getServletPath());
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        log.info("Checking shouldNotFilter for path: {}", path);
        if (path.startsWith("/v1/authen/") ||
                path.startsWith("/v1/")) {
            log.debug("{} shouldNotFilter: true", path);
            return true;
        }
        log.debug("{} shouldNotFilter: false", path);
        return false;
    }


}
