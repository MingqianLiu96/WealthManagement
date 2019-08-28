package com.ascending.mingqian.repository;

import com.ascending.mingqian.model.Customer;

import java.util.List;

public interface CustomerDao {
    boolean save(Customer customer);
    boolean update(Customer customer);
    boolean delete(String customerName);
    boolean delete(Long id);
    List<Customer> getCustomers();
    Customer getCustomerById(Long id);
    Customer getCustomerByName(String customerName);
}
