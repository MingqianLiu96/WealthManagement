package com.ascending.mingqian.jdbc;

import com.ascending.mingqian.model.Account;

import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AccountDaoTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private AccountDao accountInfoDao;
    @BeforeClass
    public static void initAllTest(){
        System.out.println("*********Start Test......*********");
    }

    @AfterClass
    public static void endAllTest(){
        System.out.println("*********Test are done!!!*********");
    }

    @Before
    public void init(){
        accountInfoDao = new AccountDao();
    }

    @After
    public void cleanup(){
        accountInfoDao = null;
    }
    @Test
    public void getUsersTest(){

        List<Account> accountInfos = accountInfoDao.getAccountInfos();

        for(Account accountInfo : accountInfos){
            System.out.println(accountInfo.getId()+" "+accountInfo.getBalance()+" "+
                    accountInfo.getAccountType()+" "+accountInfo.getUsers_id());

        }
        System.out.println("Test 1");

    }

    //@Ignore
    @Test
    public void createTest(){
        Account accountInfo1 = new Account();
        accountInfo1.setBalance(1200);
        accountInfo1.setAccountType("Wechat Pay");
        accountInfo1.setUsers_id(2);
        accountInfoDao.create(accountInfo1);
        System.out.println("Test 2");
    }

    @Test
    public void remove_idTest(){
        accountInfoDao.remove_id(2);
        System.out.println("Test 3");
    }

    @Test
    public void update_passwordTest(){
        accountInfoDao.update_balance(250,4);
        System.out.println("Test 4");
    }

}
