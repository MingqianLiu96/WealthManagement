package com.ascending.mingqian.jdbc;

import com.ascending.mingqian.model.User;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class UsersDaoTest {
   // private Logger logger = LoggerFactory.getLogger(this.getClass());
    private UsersDao usersDao;
    private static Logger logger = LoggerFactory.getLogger(DemoTest.class);


    @BeforeClass
    public static  void initTestAll(){

        logger.info("*********BeforeClass:Start testing...*********");
    }
    @AfterClass
    public static void  endTestAll(){
        logger.info("*********AfterClass:tests are done...*********");

    }

    @Before
    public void init(){
        logger.info("before method");
        usersDao = new UsersDao();
    }

    @After
    public void cleanup(){
        logger.info("after method");
        usersDao = null;
    }


    //@Ignore
    @Test
    public void createTest(){

        User users1 = new User();
        users1.setName("Nancy");
        users1.setPassword("nana1996");
        usersDao.create(users1);
     //   logger.debug(String.format("the user %s was inserted into the table.",users1.toString()));
     //   logger.info("test");
        //branch1
    }

    @Test
    public void remove_idTest(){
        usersDao.remove_id(1);
    }

    @Test
    public void update_passwordTest(){
        usersDao.update_password("mollymolly",3);
    }

    @Test
    public void getUsersTest(){

        List<User> users = usersDao.getUsers();
        //   int expectedNumOfUsers = 5;
        for(User u : users){
            System.out.println(u.getId()+" "+u.getName()+" "+u.getPassword());

        }
        //     Assert.assertEquals(expectedNumOfUsers,users.size());
    }


}
