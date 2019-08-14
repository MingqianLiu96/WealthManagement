package com.ascending.mingqian.repository;


import com.ascending.mingqian.model.User;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class UserDaoTest {
    private UserDao userDao;
    private static Logger logger = LoggerFactory.getLogger(UserDaoTest.class);
    private long i;
    private long j;



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

        User user1 = new User();
        user1.setName("Garnet");
        user1.setPassword("garnet");
        userDao.save(user1);
        i = user1.getId();

    }
    @After
    public void cleanup(){
        logger.info("after method");
        User userGarnet = userDao.getUserByName("Garnet");

        if(userGarnet != null){
            System.out.println("garnet is not null");
            userDao.delete("Garnet");
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
