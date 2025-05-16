package com.example.demo.feature.user.service;

import com.example.demo.feature.user.model.User;

public interface UserService {
    User getUserById(String userId);
    User getUserByUserName(String userName);
    User findByEmail(String email);
    User findByPhoneNumber(String phoneNumber);
    User findByToken(String token);
    User saveUser(User user);
    User findByEmailOrPhone(String emailOrPhone);
}
