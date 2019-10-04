package com.ascending.mingqian.service;


import com.ascending.mingqian.init.AppInitializer;
import com.ascending.mingqian.model.Account;
import com.ascending.mingqian.model.Customer;
import com.ascending.mingqian.model.Record;
import com.ascending.mingqian.model.View;
import com.fasterxml.jackson.annotation.JsonView;
import org.junit.*;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class CustomerServiceTest {
    @Autowired
    private RecordService recordService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private AccountService accountService;
    private static Logger logger = LoggerFactory.getLogger(RecordServiceTest.class);
    private long i;
    private long j;
    private long customerId;
    private long accountId;



    @BeforeClass
    public static  void initTestAll(){

        logger.info("*********BeforeClass:Start testing...*********");

    }

    @AfterClass
    public static void  endTestAll(){
        logger.info("*********AfterClass:tests are done...*********");



    }

    @Before
    public void init() {
        logger.info("before method");

        Customer u = new Customer();
        u.setName("Garnet");
        u.setPassword("garnet");
        customerService.save(u);
        customerId = u.getId();


    }
    @After
    public void cleanup(){
        logger.info("after method");
        Customer u = customerService.getCustomerById(customerId);
        if(u != null) {
            customerService.delete(customerId);
        }
        Customer u2 = customerService.getCustomerById(j);
        if(u2 != null) {
            customerService.delete(j);
        }


        customerService = null;
    }

    @Test
    @JsonView(View.Customer.class)
    public void getCustomers(){
        List<Customer> customers = customerService.getCustomers();
        customers.forEach(customer -> System.out.println(customer));
        assertEquals(customers.size(),5);
    }

    @Test
    public void getCustomerByName(){
        String name = "Garnet";
        Customer customer = customerService.getCustomerByName(name);
        System.out.println(customer);
        assertNotNull(customer.getPassword());
        assertNotNull(customer.getId());

    }

    @Test
    public void save(){
        Customer customer = new Customer();
        customer.setName("Nancy");
        customer.setPassword("nana1996");
        customerService.save(customer);
        j = customer.getId();
    }

    @Test
    public void update(){
        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setPassword("hhhhhh");
        customerService.update(customer);
    }

    @Test
    public void delete(){
        customerService.delete(customerId);
    }

}
