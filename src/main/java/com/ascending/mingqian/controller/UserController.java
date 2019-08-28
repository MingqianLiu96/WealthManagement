package com.ascending.mingqian.controller;

import com.ascending.mingqian.model.User;
import com.ascending.mingqian.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = {"/users"})
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public String createUser(@RequestBody User user){
        logger.debug("User: " + user.toString());
        String msg = "The user was created.";
        boolean isSuccess = userService.save(user);

        if(!isSuccess) msg = "The user was not created.";

        return msg;
    }

    @RequestMapping(value = "/{email}",method = RequestMethod.GET,produces = {MediaType.APPLICATION_JSON_VALUE})
    public User getUserByEmail(@PathVariable(name = "email") String e){
        return userService.getUserByEmail(e);
    }

    @RequestMapping(value = "/{email_password}",method = RequestMethod.GET,produces = {MediaType.APPLICATION_JSON_VALUE})
    public User getUserByCredentials(@PathVariable(name = "email_password") String email, String password){
        return userService.getUserByCredentials(email, password);
    }
}
