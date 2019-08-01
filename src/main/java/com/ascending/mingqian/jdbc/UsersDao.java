package com.ascending.mingqian.jdbc;

import com.ascending.mingqian.model.Users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersDao {
    //STEP 1: Database information
    static final String DB_URL = "jdbc:postgresql://localhost:5432/accounting";
    static final String USER = "admin";
    static final String PASS = "molly";
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public Users create(Users users){

        Connection conn = null;
        Statement stmt = null;
//        ResultSet rs = null;
        try {
            //STEP 2: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            //STEP 3: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();


            String sql;
            sql = "insert into users (name, password) values " +
                    "(\'"+
                    users.getName()+"\',\'"+users.getPassword()+
                    "\')";

            stmt.executeUpdate(sql);

        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            //STEP 6: finally block used to close resources
            try {
//                if(rs != null) rs.close();
                if(stmt != null) stmt.close();
                if(conn != null) conn.close();
            }
            catch(SQLException se) {
                se.printStackTrace();
            }
        }


        return users;
    }

    public void remove_id(int id){

        Connection conn = null;
        Statement stmt = null;
//        ResultSet rs = null;
        try {
            //STEP 2: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            //STEP 3: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();


            String sql1,sql2,sql3;
            sql1 = "delete from record where record.accountInfo_id in " +
                    "(select id from accountInfo where accountInfo.users_id = "+id+")";
            sql2 = "delete from accountInfo where accountInfo.users_id = "+id;
            sql3 = "delete from users where users.id = "+ id;

            stmt.executeUpdate(sql1);
            stmt.executeUpdate(sql2);
            stmt.executeUpdate(sql3);

        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            //STEP 6: finally block used to close resources
            try {
//                if(rs != null) rs.close();
                if(stmt != null) stmt.close();
                if(conn != null) conn.close();
            }
            catch(SQLException se) {
                se.printStackTrace();
            }
        }

    }

    public void update_password(String p,int id){
        Connection conn = null;
        Statement stmt = null;

        try {

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            //STEP 3: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();


            String sql;
            sql = "update users set password = \'"+ p +
                    " \' where id = "+id;

            stmt.executeUpdate(sql);

        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {

            try {
                if(stmt != null) stmt.close();
                if(conn != null) conn.close();
            }
            catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public List<Users> getUsers(){
        logger.info("Enter the method getUsers.");

        List<Users> usersList = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            //STEP 2: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 3: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "select * from Users";
            rs = stmt.executeQuery(sql);

            //STEP 4: Extract data from result set
            while(rs.next()){
                //Retrieve by column name
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String password = rs.getString("password");

                //Fill the object
                Users user = new Users();
                user.setId(id);
                user.setName(name);
                user.setPassword(password);

                usersList.add(user);

            }


        }
        catch(Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        finally {
            //STEP 6: finally block used to close resources
            try {
                if(rs != null) rs.close();
                if(stmt != null) stmt.close();
                if(conn != null) conn.close();
            }
            catch(SQLException se) {
                se.printStackTrace();
            }
        }

        logger.trace("Trace"+usersList.size());
        logger.debug("Debug"+usersList.size());
        logger.info("Info"+usersList.size());
        logger.warn("Warn"+usersList.size());
        logger.error("Error"+usersList.size());

        logger.info("Exit the method getUsers.");


        return usersList;



    }


//    public static void main(String[] args){
//        UsersDao usersDao = new UsersDao();
//
//        Users users1 = new Users();
//        users1.setName("Nancy");
//        users1.setPassword("nana1996");
//        usersDao.create(users1);
//
//        usersDao.remove_id(1);
//        usersDao.update_password("molly1996",3);
//
//        List<Users> users = usersDao.getUsers();
//
//        for(Users u : users){
//            System.out.println(u.getId()+" "+u.getName()+" "+u.getPassword());
//
//        }
//    }
}
