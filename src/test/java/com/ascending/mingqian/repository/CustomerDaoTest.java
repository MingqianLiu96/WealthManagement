package com.ascending.mingqian.repository;


import com.ascending.mingqian.model.Account;
import com.ascending.mingqian.model.Customer;
import com.ascending.mingqian.model.Record;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class CustomerDaoTest {
    private RecordDao recordDao;
    private CustomerDao customerDao;
    private AccountDao accountDao;
    private static Logger logger = LoggerFactory.getLogger(RecordDaoTest.class);
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
        customerDao = new CustomerDaoImpl();

        Customer u = new Customer();
        u.setName("Garnet");
        u.setPassword("garnet");
        customerDao.save(u);
        customerId = u.getId();

        accountDao = new AccountDaoImpl();
        Account a1 = new Account();
        a1.setAccountType("credit");
        a1.setBalance(145);
        a1.setCustomer(customerDao.getCustomerById((customerId)));
        accountDao.save(a1);
        accountId = a1.getId();

        recordDao = new RecordDaoImpl();
        Record r1 = new Record();
        r1.setType("rent");
        r1.setAmount(3300);
        long time = System.currentTimeMillis();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(time);
        r1.setDate(timestamp);
        r1.setDescription("three months rent for agent");
        //r1.setAccount(accountDao.getAccountById(accountId));
        Account a = new Account();
        a.setId(accountId);
        r1.setAccount(a);
        System.out.println(r1);
        recordDao.save(r1);
        i = r1.getId();

    }
    @After
    public void cleanup(){
        logger.info("after method");
        Customer customerGarnet = customerDao.getCustomerByName("Garnet");
        if(customerGarnet != null){
            customerDao.delete(customerId);
        }

        Customer customerNancy = customerDao.getCustomerByName("Nancy");
        if(customerNancy != null){
            customerDao.delete("Nancy");
        }

        Customer customer2 = customerDao.getCustomerByName("Garnet");
        if(customer2 != null) {
            if (!customer2.getPassword().equals("garnet")) {
                customer2.setPassword("garnet");
                customerDao.update(customer2);
            }
        }
        customerDao = null;
    }

    @Test
    public void getCustomers(){
        List<Customer> customers = customerDao.getCustomers();
        customers.forEach(customer -> System.out.println(customer));
        assertEquals(customers.size(),1);
    }

    @Test
    public void getCustomerByName(){
        String name = "Garnet";
        Customer customer = customerDao.getCustomerByName(name);
        System.out.println(customer);
        assertNotNull(customer.getPassword());
        assertNotNull(customer.getId());

    }

    @Test
    public void save(){
        Customer customer = new Customer();
        customer.setName("Nancy");
        customer.setPassword("nana1996");
        customerDao.save(customer);
        j = customer.getId();
    }

    @Test
    public void update(){
        Customer customer = new Customer();
        customer.setId(i);
        customer.setPassword("hhhhhh");
        customerDao.update(customer);
    }

    @Test
    public void delete(){
        customerDao.delete("Garnet");
    }

}
