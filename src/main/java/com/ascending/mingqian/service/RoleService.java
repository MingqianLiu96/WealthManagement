package com.ascending.mingqian.service;
import com.ascending.mingqian.model.Role;
import com.ascending.mingqian.repository.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class RoleService {
    @Autowired private RoleDao roleDao;
    public Role getRoleByName(String name) {
        return roleDao.getRoleByName(name);
    }
}