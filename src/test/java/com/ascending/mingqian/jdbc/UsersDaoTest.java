package com.ascending.mingqian.jdbc;

import com.ascending.mingqian.model.Users;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class UsersDaoTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private UsersDao usersDao;
    @BeforeClass
    public static void initAllTest(){
        System.out.println("*********Start Test*********");
    }

    @Before
    public void init(){
        usersDao = new UsersDao();
    }

    @After
    public void cleanup(){
        usersDao = null;
    }
    @Test
    public void getUsersTest(){

        List<Users> users = usersDao.getUsers();
        int expectedNumOfUsers = 5;
        for(Users u : users){
            System.out.println(u.getId()+" "+u.getName()+" "+u.getPassword());

        }
        //Assert.assertEquals(expectedNumOfUsers,users.size());
    }

    //@Ignore
    @Test
    public void saveTest(){

        Users users1 = new Users();
        users1.setName("Nancy");
        users1.setPassword("nana1996");
        usersDao.save(users1);
        logger.debug(String.format("the user %s was inserted into the table.",users1.toString()));
        logger.info("test");
    }

}
