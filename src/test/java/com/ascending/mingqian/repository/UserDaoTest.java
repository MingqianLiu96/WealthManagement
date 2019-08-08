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
//        userDao = new UserDaoImpl();
//
//        User user1 = new User();
//        user1.setName("Garnet");
//        user1.setPassword("garnet");
//        userDao.save(user1);

    }
    @After
    public void cleanup(){
        logger.info("after method");
//        User userGarnet = userDao.getUserByName("Garnet");

//        if(userGarnet != null){
//            System.out.println("garnet is not null");
//            userDao.delete("Garnet");
//        }
//        User userNancy = userDao.getUserByName("Nancy");
//        if(userNancy != null){
//            userDao.delete("Nancy");
//        }
//
//        User user2 = userDao.getUserByName("Rebecca");
//        if(!user2.getPassword().equals("virginia")) {
//            user2.setId(Long.valueOf(4));
//            user2.setName("Rebecca");
//            user2.setPassword("virginia");
//            userDao.update(user2);
//        }

        userDao = null;
    }

    @Test
    public void getUsers(){
        List<User> users = userDao.getUsers();
        users.forEach(user -> System.out.println(user));
        assertEquals(users.size(),4);
    }

    @Test
    public void getUserByName(){
        String name = "Ada";
        User user = userDao.getUserByName(name);
        assertNotNull(user.getPassword());
        assertNotNull(user.getId());

    }

    @Test
    public void save(){
        User users1 = new User();
        users1.setName("Nancy");
        users1.setPassword("nana1996");
        userDao.save(users1);
    }

    @Test
    public void update(){
        User users2 = new User();
        users2.setId(Long.valueOf(4));
        users2.setName("Rebecca");
        users2.setPassword("hhhhhh");
        userDao.update(users2);
    }

    @Test
    public void delete(){

        userDao.delete("Garnet");
    }

}
