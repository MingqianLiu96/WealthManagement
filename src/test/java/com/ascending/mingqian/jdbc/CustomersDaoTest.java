package com.ascending.mingqian.jdbc;

import com.ascending.mingqian.model.Customer;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class CustomersDaoTest {
   // private Logger logger = LoggerFactory.getLogger(this.getClass());
    private CustomersDao customersDao;
    private static Logger logger = LoggerFactory.getLogger(CustomersDaoTest.class);


    @BeforeClass
    public static  void initTestAll(){

        logger.info("*********BeforeClass:Start testing...*********");
    }
    @AfterClass
    public static void  endTestAll(){
        logger.info("*********AfterClass:tests are done...*********");

    }

    @Before
    public void init(){
        logger.info("before method");
        customersDao = new CustomersDao();
    }

    @After
    public void cleanup(){
        logger.info("after method");
        customersDao = null;
    }


    //@Ignore
    @Test
    public void createTest(){

        Customer users1 = new Customer();
        users1.setName("Nancy");
        users1.setPassword("nana1996");
        customersDao.create(users1);
     //   logger.debug(String.format("the user %s was inserted into the table.",users1.toString()));
     //   logger.info("test");
        //branch1
    }

    @Test
    public void remove_idTest(){
        customersDao.remove_id(Long.valueOf(1));
    }

    @Test
    public void update_passwordTest(){
        customersDao.update_password("mollymolly",Long.valueOf(3));
    }

    @Test
    public void getCustomersTest(){

        List<Customer> customers = customersDao.getCustomers();
        //   int expectedNumOfCustomers = 5;
        for(Customer u : customers){
            System.out.println(u.getId()+" "+u.getName()+" "+u.getPassword());

        }
        //     Assert.assertEquals(expectedNumOfCustomers,customers.size());
    }


}
