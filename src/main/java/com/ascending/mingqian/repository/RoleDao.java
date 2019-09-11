package com.ascending.mingqian.repository;

import com.ascending.mingqian.model.Role;

public interface RoleDao {
    Role getRoleByName(String name);
}
