package com.ascending.mingqian.service;

import com.ascending.mingqian.model.Customer;
import com.ascending.mingqian.repository.CustomerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerDao customerDao;

    public boolean save(Customer customer){
        return customerDao.save(customer);
    }
    public boolean update(Customer customer){
        return customerDao.update(customer);
    }
    public boolean delete(String customerName){
        return customerDao.delete(customerName);
    }
    public boolean delete(Long id){
        return customerDao.delete(id);
    }
    public List<Customer> getCustomers(){
        return customerDao.getCustomers();
    }
    public Customer getCustomerById(Long id){
        return customerDao.getCustomerById(id);
    }
    public Customer getCustomerByName(String customerName){
        return customerDao.getCustomerByName(customerName);
    }
}
