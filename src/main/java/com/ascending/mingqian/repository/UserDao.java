package com.ascending.mingqian.repository;

import com.ascending.mingqian.model.User;

import java.util.List;
import java.util.Set;

public interface UserDao {
    boolean save(User user);
    boolean update(User user);
    boolean delete(String userName);
    boolean delete(Long id);
    List<User> getUsers();
    User getUserById(Long id);
    User getUserByName(String userName);
}
