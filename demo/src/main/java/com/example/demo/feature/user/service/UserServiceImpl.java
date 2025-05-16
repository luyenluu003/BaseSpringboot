package com.example.demo.feature.user.service;

import com.example.demo.feature.user.dao.UserDao;
import com.example.demo.feature.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(String userId) {
        return userDao.findById(userId);
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public User findByPhoneNumber(String phoneNumber) {
        return userDao.findByPhoneNumber(phoneNumber);
    }

    @Override
    public User findByToken(String token) {
        return userDao.findByToken(token);
    }

    @Override
    public User saveUser(User user) {
        return userDao.save(user);
    }

    @Override
    public User getUserByUserName(String userName) {
        return userDao.findByUserName(userName);
    }

    @Override
    public User findByEmailOrPhone(String emailOrPhone) {
        User user = findByEmail(emailOrPhone);
        if (user == null) {
            user = findByPhoneNumber(emailOrPhone);
        }
        return user;
    }
}
