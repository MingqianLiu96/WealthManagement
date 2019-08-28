package com.ascending.mingqian.controller;

import com.ascending.mingqian.model.Customer;
import com.ascending.mingqian.model.View;
import com.ascending.mingqian.service.CustomerService;
import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/customers"})
public class CustomerController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CustomerService customerService;

    @JsonView(View.Customer.class)
    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public List<Customer> getCustomers(){
        return customerService.getCustomers();
    }

    @RequestMapping(value = "/{customerName}",method = RequestMethod.GET,produces = {MediaType.APPLICATION_JSON_VALUE})
    public Customer getCustomer(@PathVariable(name = "customerName") String u){
        return customerService.getCustomerByName(u);
    }

    @RequestMapping(value = "/id/{customerId}",method = RequestMethod.GET,produces = {MediaType.APPLICATION_JSON_VALUE})
    public Customer getCustomer(@PathVariable(name = "customerId") Long uerId){
        return customerService.getCustomerById(uerId);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public String createCustomer(@RequestBody Customer customer){
        logger.debug("Customer: " + customer.toString());
        String msg = "The customer was created.";
        boolean isSuccess = customerService.save(customer);

        if(!isSuccess) msg = "The customer was not created.";

        return msg;
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public String updateCustomer(@RequestBody Customer customer){
        logger.debug("Customer: " + customer.toString());
        String msg = "The customer was updated.";
        boolean isSuccess = customerService.update(customer);

        if(!isSuccess) msg = "The customer was not updated.";

        return msg;
    }

    @RequestMapping(value = "/{customerName}", method = RequestMethod.DELETE, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public String deleteCustomer(@PathVariable(name = "customerName") String u){
        logger.debug("Customer name: " + u);
        String msg = "The customer was deleted.";
        boolean isSuccess = customerService.delete(u);

        if(!isSuccess) msg = "The customer was not deleted.";

        return msg;
    }



}
