package com.ascending.mingqian.controller;

import com.ascending.mingqian.model.Account;

import com.ascending.mingqian.model.User;
import com.ascending.mingqian.service.AccountService;

import com.ascending.mingqian.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = {"/accounts","/acc"})
public class AccountController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public List<Account> getAccounts(){
        return accountService.getAccounts();
    }

    @RequestMapping(value = "/user/{userName}",method = RequestMethod.GET,produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Account> getAccounts(@PathVariable(name = "userName") String u){
        User user = userService.getUserByName(u);
        List<Account> accounts = accountService.getAccountByUserId(user.getId());
        return accounts;
    }

    @RequestMapping(value = "/{accountId}",method = RequestMethod.GET,produces = {MediaType.APPLICATION_JSON_VALUE})
    public Account getAccount(@PathVariable(name = "accountId") Long id){
        Account account = accountService.getAccountById(id);
        return account;
    }

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public String createAccount(@RequestBody Account account){
        logger.debug("Account: " + account.toString());
        String msg = "The account was created.";
        boolean isSuccess = accountService.save(account);

        if(!isSuccess) msg = "The account was not created.";

        return msg;
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public String updateAccount(@RequestBody Account account){
        logger.debug("Account: " + account.toString());
        String msg = "The account was updated.";
        boolean isSuccess = accountService.update(account);

        if(!isSuccess) msg = "The account was not updated.";

        return msg;
    }

    @RequestMapping(value = "/{accountId}", method = RequestMethod.DELETE, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public String deleteUser(@PathVariable(name = "accountId") Long id){
        logger.debug("Account Id: " + id);
        String msg = "The account was deleted.";

        boolean isSuccess = accountService.delete(id);

        if(!isSuccess) msg = "The account was not deleted.";

        return msg;
    }

}
