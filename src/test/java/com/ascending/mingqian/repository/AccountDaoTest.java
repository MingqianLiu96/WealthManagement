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
    private long i;
    private long j ;


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
        Account a1 = new Account();
        a1.setAccountType("credit");
        a1.setBalance(145);
        a1.setUsers_id(3);
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
        Account a4 = accountDao.getAccountById(Long.valueOf(9));
        if(!a4.getAccountType().equals("alipay") || a4.getBalance()!= (double)5000 || a4.getUsers_id() != 4){
            a4.setBalance(5000);
            a4.setAccountType("alipay");
            a4.setUsers_id(Long.valueOf(4));
            accountDao.update(a4);
        }
        accountDao = null;
    }

    @Test
    public void getAccounts(){
        List<Account> accounts = accountDao.getAccounts();

        accounts.forEach(account -> System.out.println(account));
        assertEquals(accounts.size(),10);

    }

    @Test
    public void getAccountByUserId(){
        List<Account> a = accountDao.getAccountByUserId(Long.valueOf(2));
        assertNotNull(a.size());
    }

    @Test
    public void getAccountById(){
        Account a = accountDao.getAccountById(Long.valueOf(2));
        assertNotNull(a.toString());
    }

    @Test
    public void save(){
        Account account1 = new Account();
        account1.setBalance(1200);
        account1.setAccountType("Wechat Pay");
        account1.setUsers_id(Long.valueOf(2));
        accountDao.save(account1);
        j = account1.getId();
    }

    @Test
    public void update(){
        Account account2 = new Account();
        account2.setId(Long.valueOf(9));
        account2.setBalance(1200);
        account2.setAccountType("credit");
        account2.setUsers_id(Long.valueOf(4));
        accountDao.update(account2);
    }

    @Test
    public void delete(){
       accountDao.delete(Long.valueOf(i));

    }

}
