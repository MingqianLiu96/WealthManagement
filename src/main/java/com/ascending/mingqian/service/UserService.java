package com.ascending.mingqian.service;

import com.ascending.mingqian.model.User;
import com.ascending.mingqian.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class UserService {
    @Autowired private UserDao userDao;
    public boolean save(User user) {
        return userDao.save(user);
    }
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }
    public User getUserByCredentials(String email, String password) {
        return userDao.getUserByCredentials(email, password);
    }
}