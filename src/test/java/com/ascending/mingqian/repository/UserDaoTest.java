package com.ascending.mingqian.repository;


import com.ascending.mingqian.model.Account;
import com.ascending.mingqian.model.Record;
import com.ascending.mingqian.model.User;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class UserDaoTest {
    private RecordDao recordDao;
    private UserDao userDao;
    private AccountDao accountDao;
    private static Logger logger = LoggerFactory.getLogger(RecordDaoTest.class);
    private long i;
    private long j;
    private long userId;
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
        userDao = new UserDaoImpl();

        User u = new User();
        u.setName("Garnet");
        u.setPassword("garnet");
        userDao.save(u);
        userId = u.getId();

        accountDao = new AccountDaoImpl();
        Account a1 = new Account();
        a1.setAccountType("credit");
        a1.setBalance(145);
        a1.setUser(userDao.getUserById((userId)));
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
        User userGarnet = userDao.getUserByName("Garnet");
        if(userGarnet != null){
            userDao.delete(userId);
        }

        User userNancy = userDao.getUserByName("Nancy");
        if(userNancy != null){
            userDao.delete("Nancy");
        }

        User user2 = userDao.getUserByName("Garnet");
        if(user2 != null) {
            if (!user2.getPassword().equals("garnet")) {
                user2.setPassword("garnet");
                userDao.update(user2);
            }
        }
        userDao = null;
    }

    @Test
    public void getUsers(){
        List<User> users = userDao.getUsers();
        users.forEach(user -> System.out.println(user));
        assertEquals(users.size(),1);
    }

    @Test
    public void getUserByName(){
        String name = "Garnet";
        User user = userDao.getUserByName(name);
        System.out.println(user);
        assertNotNull(user.getPassword());
        assertNotNull(user.getId());

    }

    @Test
    public void save(){
        User user = new User();
        user.setName("Nancy");
        user.setPassword("nana1996");
        userDao.save(user);
        j = user.getId();
    }

    @Test
    public void update(){
        User user = new User();
        user.setId(i);
        user.setPassword("hhhhhh");
        userDao.update(user);
    }

    @Test
    public void delete(){
        userDao.delete("Garnet");
    }

}
