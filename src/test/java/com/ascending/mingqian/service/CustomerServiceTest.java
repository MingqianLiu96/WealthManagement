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

        Account a1 = new Account();
        a1.setAccountType("credit");
        a1.setBalance(145);
        a1.setCustomer(customerService.getCustomerById((customerId)));
        accountService.save(a1);
        accountId = a1.getId();

        Record r1 = new Record();
        r1.setType("rent");
        r1.setAmount(3300);
        long time = System.currentTimeMillis();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(time);
        r1.setDate(timestamp);
        r1.setDescription("three months rent for agent");
        //r1.setAccount(accountService.getAccountById(accountId));
        Account a = new Account();
        a.setId(accountId);
        r1.setAccount(a);
        System.out.println(r1);
        recordService.save(r1);
        i = r1.getId();

    }
    @After
    public void cleanup(){
        logger.info("after method");
        Customer customer2 = customerService.getCustomerByName("Garnet");
        if(customer2 != null) {
            if (!customer2.getPassword().equals("garnet")) {
                customer2.setPassword("garnet");
                customerService.update(customer2);
            }
        }

        Customer customerGarnet = customerService.getCustomerByName("Garnet");
        if(customerGarnet != null){
            customerService.delete(customerId);
        }

        Customer customerNancy = customerService.getCustomerByName("Nancy");
        if(customerNancy != null){
            customerService.delete("Nancy");
        }


        customerService = null;
    }

    @Test
    @JsonView(View.Customer.class)
    public void getCustomers(){
        List<Customer> customers = customerService.getCustomers();
        customers.forEach(customer -> System.out.println(customer));
        assertEquals(customers.size(),1);
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
        customerService.delete("Garnet");
    }

}
