package com.ascending.mingqian.repository;
import com.ascending.mingqian.model.User;
public interface UserDao {
    boolean save(User user);
    User getUserByEmail(String email);
    User getUserByCredentials(String email, String password);
}