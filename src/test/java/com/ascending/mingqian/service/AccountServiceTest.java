package com.ascending.mingqian.service;

import com.ascending.mingqian.init.AppInitializer;
import com.ascending.mingqian.model.Account;
import com.ascending.mingqian.model.User;


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
public class AccountServiceTest {
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;
    private static Logger logger = LoggerFactory.getLogger(AccountServiceTest.class);
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

        User u = new User();
        u.setName("Molly");
        u.setPassword("molly");
        userService.save(u);
        userId = u.getId();

//        u = userService.getUserByName(u.getName());
//        userId = u.getId();

        Account a1 = new Account();
        a1.setAccountType("credit");
        a1.setBalance(145);
        a1.setUser(userService.getUserById(userId));
        accountService.save(a1);

        i = a1.getId();


    }
    @After
    public void cleanup(){
        logger.info("after method");

        Account a2 = accountService.getAccountById(i);
        if(a2 != null){
            accountService.delete(i);
        }
        Account a3 = accountService.getAccountById(j);
        if (a3 != null){
            accountService.delete(j);
        }
        Account a4 = accountService.getAccountById(i);
        if(a4 != null) {
            if (!a4.getAccountType().equals("credit") || a4.getBalance() != (double) 145 || a4.getUser().getId() != 3) {
                a4.setBalance(145);
                a4.setAccountType("credit");
                a4.setUser(userService.getUserById(userId));
                accountService.update(a4);
            }
        }
        userService.delete("Molly");
        userService = null;
        accountService = null;
    }

    @Test
    public void getAccounts(){
        List<Account> accounts = accountService.getAccounts();
        accounts.forEach(account -> System.out.println(account));
        assertEquals(accounts.size(), 1);



    }

    @Test
    public void getAccountByUserId(){
        List<Account> a = accountService.getAccountByUserId(userId);
        assertNotNull(a.size());
    }

    @Test
    public void getAccountById(){
        Account a = accountService.getAccountById(i);
        logger.info(a.toString());
        System.out.println(a);
        assertNotNull(a.toString());
    }

    @Test
    public void save(){
        Account account1 = new Account();
        account1.setBalance(1200);
        account1.setAccountType("Wechat Pay");
        account1.setUser(userService.getUserById(userId));
//        User u = new User();
//        u.setId(userId);
//        account1.setUser(u);
        accountService.save(account1);
        j = account1.getId();
    }

    @Test
    public void update(){
        Account account2 = new Account();
        account2.setId(i);
        account2.setBalance(1200);
        account2.setAccountType("credit");
        account2.setUser(userService.getUserById(userId));
        accountService.update(account2);
    }

    @Test
    public void delete(){
        accountService.delete(Long.valueOf(i));

    }

}
