package com.ascending.mingqian.controller;

import com.ascending.mingqian.model.Customer;
import com.ascending.mingqian.model.Role;
import com.ascending.mingqian.service.CustomerService;
import com.ascending.mingqian.service.RecordService;
import com.ascending.mingqian.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = {"/role"})
public class RoleController {
    @Autowired
    private Logger logger;

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/{roleName}",method = RequestMethod.GET,produces = {MediaType.APPLICATION_JSON_VALUE})
    public Role getRole(@PathVariable(name = "roleName") String r){
        return roleService.getRoleByName(r);
    }

}
