package com.ascending.mingqian.service;


import com.ascending.mingqian.init.AppInitializer;
import com.ascending.mingqian.model.Account;
import com.ascending.mingqian.model.Record;
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
public class UserServiceTest {
    @Autowired
    private RecordService recordService;
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    private static Logger logger = LoggerFactory.getLogger(RecordServiceTest.class);
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

        User u = new User();
        u.setName("Garnet");
        u.setPassword("garnet");
        userService.save(u);
        userId = u.getId();

        Account a1 = new Account();
        a1.setAccountType("credit");
        a1.setBalance(145);
        a1.setUser(userService.getUserById((userId)));
        accountService.save(a1);
        accountId = a1.getId();

        Record r1 = new Record();
        r1.setType("rent");
        r1.setAmount(3300);
        long time = System.currentTimeMillis();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(time);
        r1.setDate(timestamp);
        r1.setDescription("three months rent for agent");
        //r1.setAccount(accountService.getAccountById(accountId));
        Account a = new Account();
        a.setId(accountId);
        r1.setAccount(a);
        System.out.println(r1);
        recordService.save(r1);
        i = r1.getId();

    }
    @After
    public void cleanup(){
        logger.info("after method");
        User userGarnet = userService.getUserByName("Garnet");
        if(userGarnet != null){
            userService.delete(userId);
        }

        User userNancy = userService.getUserByName("Nancy");
        if(userNancy != null){
            userService.delete("Nancy");
        }

        User user2 = userService.getUserByName("Garnet");
        if(user2 != null) {
            if (!user2.getPassword().equals("garnet")) {
                user2.setPassword("garnet");
                userService.update(user2);
            }
        }
        userService = null;
    }

    @Test
    public void getUsers(){
        List<User> users = userService.getUsers();
        users.forEach(user -> System.out.println(user));
        assertEquals(users.size(),1);
    }

    @Test
    public void getUserByName(){
        String name = "Garnet";
        User user = userService.getUserByName(name);
        System.out.println(user);
        assertNotNull(user.getPassword());
        assertNotNull(user.getId());

    }

    @Test
    public void save(){
        User user = new User();
        user.setName("Nancy");
        user.setPassword("nana1996");
        userService.save(user);
        j = user.getId();
    }

    @Test
    public void update(){
        User user = new User();
        user.setId(i);
        user.setPassword("hhhhhh");
        userService.update(user);
    }

    @Test
    public void delete(){
        userService.delete("Garnet");
    }

}
