package com.ascending.mingqian.repository;

import com.ascending.mingqian.model.User;

import java.util.List;

public interface UserDao {
    boolean save(User user);
    boolean update(User user);
    boolean delete(String userName);
    List<User> getUsers();
    User getUserByName(String userName);
}
