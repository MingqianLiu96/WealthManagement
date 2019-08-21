package com.ascending.mingqian.controller;

import com.ascending.mingqian.model.Account;
import com.ascending.mingqian.model.User;
import com.ascending.mingqian.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = {"/users"})
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;
    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @RequestMapping(value = "/{userName}",method = RequestMethod.GET,produces = {MediaType.APPLICATION_JSON_VALUE})
    public User getUser(@PathVariable(name = "userName") String u){
        return userService.getUserByName(u);
    }

    @RequestMapping(value = "/id/{userId}",method = RequestMethod.GET,produces = {MediaType.APPLICATION_JSON_VALUE})
    public User getUser(@PathVariable(name = "userId") Long uerId){
        return userService.getUserById(uerId);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public String createUser(@RequestBody User user){
        logger.debug("User: " + user.toString());
        String msg = "The user was created.";
        boolean isSuccess = userService.save(user);

        if(!isSuccess) msg = "The user was not created.";

        return msg;
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public String updateUser(@RequestBody User user){
        logger.debug("User: " + user.toString());
        String msg = "The user was updated.";
        boolean isSuccess = userService.update(user);

        if(!isSuccess) msg = "The user was not updated.";

        return msg;
    }

    @RequestMapping(value = "/{userName}", method = RequestMethod.DELETE, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public String deleteUser(@PathVariable(name = "userName") String u){
        logger.debug("User name: " + u);
        String msg = "The user was deleted.";
        boolean isSuccess = userService.delete(u);

        if(!isSuccess) msg = "The user was not deleted.";

        return msg;
    }



}
