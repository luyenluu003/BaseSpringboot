package com.example.demo.feature.user.dao;

import com.example.demo.feature.user.model.User;

public interface UserDao {
    User findById(String userId);
    User findByUserName(String userName);
    User findByEmail(String email);
    User findByPhoneNumber(String phoneNumber);
    User findByToken(String token);
    User save(User user);
}
