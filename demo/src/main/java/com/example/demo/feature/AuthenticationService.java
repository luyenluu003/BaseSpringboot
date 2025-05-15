package com.example.demo.feature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.config.DemoConfiguration;
import com.example.demo.util.SecurityUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class AuthenticationService implements UserDetailsService {

    @Autowired
    DemoConfiguration demoConfiguration;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public String getUserNameFromHttpRequest(HttpServletRequest httpServletRequest) {
        return SecurityUtil.getUserNameFromHttpRequest(httpServletRequest, demoConfiguration.getJwtSecretKey());
    }

    public String getTokenFromHttpRequest(HttpServletRequest httpServletRequest) {
        return SecurityUtil.getTokenFromHttpRequest(httpServletRequest, demoConfiguration.getJwtSecretKey());
    }

}
