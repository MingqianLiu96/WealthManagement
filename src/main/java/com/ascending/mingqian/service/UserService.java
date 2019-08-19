package com.ascending.mingqian.service;

import com.ascending.mingqian.model.User;
import com.ascending.mingqian.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public boolean save(User user){
        return userDao.save(user);
    }
    public boolean update(User user){
        return userDao.update(user);
    }
    public boolean delete(String userName){
        return userDao.delete(userName);
    }
    public boolean delete(Long id){
        return userDao.delete(id);
    }
    public List<User> getUsers(){
        return userDao.getUsers();
    }
    public User getUserById(Long id){
        return userDao.getUserById(id);
    }
    public User getUserByName(String userName){
        return userDao.getUserByName(userName);
    }
}
