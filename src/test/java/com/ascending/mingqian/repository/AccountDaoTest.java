package com.ascending.mingqian.repository;

import com.ascending.mingqian.model.Account;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class AccountDaoTest {
    private AccountDao accountDao;
    private static Logger logger = LoggerFactory.getLogger(AccountDaoTest.class);


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
    }
    @After
    public void cleanup(){
        logger.info("after method");
        accountDao = null;
    }

    @Test
    public void getAccounts(){
        List<Account> accounts = accountDao.getAccounts();

        accounts.forEach(account -> System.out.println(account));
        assertEquals(accounts.size(),9);

    }

    @Test
    public void getAccountByUserId(){
        Account a = accountDao.getAccountByUserId(Long.valueOf(2));
        assertNotNull(a.getId());
    }

    @Test
    public void save(){
        Account account1 = new Account();
        account1.setBalance(1200);
        account1.setAccountType("Wechat Pay");
        account1.setUsers_id(Long.valueOf(2));
        accountDao.save(account1);
    }

    @Test
    public void update(){
        Account account2 = new Account();
        account2.setId(Long.valueOf(9));
        account2.setBalance(1200);
        account2.setAccountType("credit");
        account2.setUsers_id(Long.valueOf(3));
        accountDao.update(account2);
    }

    @Test
    public void delete(){
       accountDao.delete(Long.valueOf(3));

    }

}
