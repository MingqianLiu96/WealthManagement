package com.ascending.mingqian.repository;

import com.ascending.mingqian.model.Account;
import com.ascending.mingqian.model.User;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class AccountDaoTest {
    private AccountDao accountDao;
    private UserDao userDao;
    private static Logger logger = LoggerFactory.getLogger(AccountDaoTest.class);
    private long i;
    private long j ;
    private long userId;


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
        userDao = new UserDaoImpl();
        User u = new User();
        u.setName("Molly");
        u.setPassword("molly");
        userDao.save(u);
        userId = u.getId();

        Account a1 = new Account();
        a1.setAccountType("credit");
        a1.setBalance(145);
        a1.setUsers_id(userId);
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
            if (!a4.getAccountType().equals("credit") || a4.getBalance() != (double) 145 || a4.getUsers_id() != 3) {
                a4.setBalance(145);
                a4.setAccountType("credit");
                a4.setUsers_id(userId);
                accountDao.update(a4);
            }
        }
        userDao.delete("Molly");
        userDao = null;
        accountDao = null;
    }

    @Test
    public void getAccounts(){
        List<Account> accounts = accountDao.getAccounts();

        accounts.forEach(account -> System.out.println(account));
        assertEquals(accounts.size(),1);

    }

    @Test
    public void getAccountByUserId(){
        List<Account> a = accountDao.getAccountByUserId(userId);
        assertNotNull(a.size());
    }

    @Test
    public void getAccountById(){
        Account a = accountDao.getAccountById(i);
        assertNotNull(a.toString());
    }

    @Test
    public void save(){
        Account account1 = new Account();
        account1.setBalance(1200);
        account1.setAccountType("Wechat Pay");
        account1.setUsers_id(userId);
        accountDao.save(account1);
        j = account1.getId();
    }

    @Test
    public void update(){
        Account account2 = new Account();
        account2.setId(i);
        account2.setBalance(1200);
        account2.setAccountType("credit");
        account2.setUsers_id(userId);
        accountDao.update(account2);
    }

    @Test
    public void delete(){
       accountDao.delete(Long.valueOf(i));

    }

}
