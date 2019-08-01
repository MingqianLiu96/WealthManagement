package com.ascending.mingqian.jdbc;

import com.ascending.mingqian.model.AccountInfo;

import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AccountInfoDaoTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private AccountInfoDao accountInfoDao;
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
        accountInfoDao = new AccountInfoDao();
    }

    @After
    public void cleanup(){
        accountInfoDao = null;
    }
    @Test
    public void getUsersTest(){

        List<AccountInfo> accountInfos = accountInfoDao.getAccountInfos();

        for(AccountInfo accountInfo : accountInfos){
            System.out.println(accountInfo.getId()+" "+accountInfo.getBalance()+" "+
                    accountInfo.getAccountType()+" "+accountInfo.getUsers_id());

        }

    }

    //@Ignore
    @Test
    public void createTest(){
        AccountInfo accountInfo1 = new AccountInfo();
        accountInfo1.setBalance(1200);
        accountInfo1.setAccountType("Wechat Pay");
        accountInfo1.setUsers_id(2);
        accountInfoDao.create(accountInfo1);
    }

    @Test
    public void remove_idTest(){
        accountInfoDao.remove_id(8);
    }

    @Test
    public void update_passwordTest(){
        accountInfoDao.update_balance(250,4);
    }

}
