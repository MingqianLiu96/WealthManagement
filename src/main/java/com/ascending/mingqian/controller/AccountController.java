package com.ascending.mingqian.controller;

import com.ascending.mingqian.model.Account;

import com.ascending.mingqian.model.Customer;
import com.ascending.mingqian.model.View;
import com.ascending.mingqian.service.AccountService;

import com.ascending.mingqian.service.CustomerService;
import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/accounts","/acc"})
public class AccountController {
    @Autowired
    private Logger logger;

    @Autowired
    private AccountService accountService;
    @Autowired
    private CustomerService customerService;

    @JsonView(View.Account.class)
    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public List<Account> getAccounts(){
        return accountService.getAccounts();
    }

    @RequestMapping(value = "/customer/{customerName}",method = RequestMethod.GET,produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Account> getAccounts(@PathVariable(name = "customerName") String u){
        Customer customer = customerService.getCustomerByName(u);
        List<Account> accounts = accountService.getAccountByCustomerId(customer.getId());
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
    public String deleteCustomer(@PathVariable(name = "accountId") Long id){
        logger.debug("Account Id: " + id);
        String msg = "The account was deleted.";

        boolean isSuccess = accountService.delete(id);

        if(!isSuccess) msg = "The account was not deleted.";

        return msg;
    }

}
