package com.ascending.mingqian.repository;

import com.ascending.mingqian.model.Account;
import com.ascending.mingqian.model.Customer;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class AccountDaoTest {
    private AccountDao accountDao;
    private CustomerDao customerDao;
    private static Logger logger = LoggerFactory.getLogger(AccountDaoTest.class);
    private long i;
    private long j ;
    private long customerId;


    @BeforeClass
    public static  void initTestAll(){

        logger.info("*********BeforeClass:Start testing...*********");
    }

    @AfterClass
    public static void  endTestAll(){
        logger.info("*********AfterClass:tests are done...*********");

    }

    @Before
    //TODO update setup and tear to add and remove data
    public void init() {
        logger.info("before method");
        accountDao = new AccountDaoImpl();
        customerDao = new CustomerDaoImpl();
        Customer u = new Customer();
        u.setName("Molly");
        u.setPassword("molly");
        customerDao.save(u);
        customerId = u.getId();

//        u = customerDao.getCustomerByName(u.getName());
//        customerId = u.getId();

        Account a1 = new Account();
        a1.setAccountType("credit");
        a1.setBalance(145);
        a1.setCustomer(customerDao.getCustomerById(customerId));
        accountDao.save(a1);

        i = a1.getId();


    }
    @After
    public void cleanup(){
        logger.info("after method");

        Account a2 = accountDao.getAccountById(i);
        if(a2 != null){
            accountDao.delete(i);
        }
        Account a3 = accountDao.getAccountById(j);
        if (a3 != null){
            accountDao.delete(j);
        }
        Account a4 = accountDao.getAccountById(i);
        if(a4 != null) {
            if (!a4.getAccountType().equals("credit") || a4.getBalance() != (double) 145 || a4.getCustomer().getId() != 3) {
                a4.setBalance(145);
                a4.setAccountType("credit");
                a4.setCustomer(customerDao.getCustomerById(customerId));
                accountDao.update(a4);
            }
        }
        customerDao.delete("Molly");
        customerDao = null;
        accountDao = null;
    }

    @Test
    public void getAccounts(){
        List<Account> accounts = accountDao.getAccounts();
        accounts.forEach(account -> System.out.println(account));
        assertEquals(accounts.size(), 1);



    }

    @Test
    public void getAccountByCustomerId(){
        List<Account> a = accountDao.getAccountByCustomerId(customerId);
        assertNotNull(a.size());
    }

    @Test
    public void getAccountById(){
        Account a = accountDao.getAccountById(i);
        logger.info(a.toString());
        System.out.println(a);
        assertNotNull(a.toString());
    }

    @Test
    public void save(){
        Account account1 = new Account();
        account1.setBalance(1200);
        account1.setAccountType("Wechat Pay");
        account1.setCustomer(customerDao.getCustomerById(customerId));
//        Customer u = new Customer();
//        u.setId(customerId);
//        account1.setCustomer(u);
        accountDao.save(account1);
        j = account1.getId();
    }

    @Test
    public void update(){
        Account account2 = new Account();
        account2.setId(i);
        account2.setBalance(1200);
        account2.setAccountType("credit");
        account2.setCustomer(customerDao.getCustomerById(customerId));
        accountDao.update(account2);
    }

    @Test
    public void delete(){
       accountDao.delete(Long.valueOf(i));

    }

}
