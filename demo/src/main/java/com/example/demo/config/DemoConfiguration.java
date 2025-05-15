package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
public class DemoConfiguration {

    @Value("${demo.jwt.secret.key}")
    private String jwtSecretKey;

}
