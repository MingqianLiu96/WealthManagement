package com.ascending.mingqian.jdbc;

import com.ascending.mingqian.model.Customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomersDao {
    //STEP 1: Database information
    static final String DB_URL = "jdbc:postgresql://localhost:5432/accounting";
    static final String USER = "admin";
    static final String PASS = "molly";
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public Customer create(Customer customers){

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
            sql = "insert into customers (name, password) values " +
                    "(\'"+
                    customers.getName()+"\',\'"+customers.getPassword()+
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


        return customers;
    }

    public void remove_id(long id){

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
            sql1 = "delete from record where record.account_id in " +
                    "(select id from account where account.customers_id = "+id+")";
            sql2 = "delete from account where account.customers_id = "+id;
            sql3 = "delete from customers where customers.id = "+ id;

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

    public void update_password(String p,long id){
        Connection conn = null;
        Statement stmt = null;

        try {

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            //STEP 3: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();


            String sql;
            sql = "update customers set password = \'"+ p +
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

    public List<Customer> getCustomers(){
        logger.info("Enter the method getCustomers.");

        List<Customer> customerList = new ArrayList();
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
            sql = "select * from Customers";
            rs = stmt.executeQuery(sql);

            //STEP 4: Extract data from result set
            while(rs.next()){
                //Retrieve by column name
                long id = rs.getInt("id");
                String name = rs.getString("name");
                String password = rs.getString("password");

                //Fill the object
                Customer customer = new Customer();
                customer.setId(id);
                customer.setName(name);
                customer.setPassword(password);

                customerList.add(customer);

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

        logger.trace("Trace"+ customerList.size());
        logger.debug("Debug"+ customerList.size());
        logger.info("Info"+ customerList.size());
        logger.warn("Warn"+ customerList.size());
        logger.error("Error"+ customerList.size());

        logger.info("Exit the method getCustomers.");


        return customerList;



    }


//    public static void main(String[] args){
//        CustomersDao customersDao = new CustomersDao();
//
//        Customers customers1 = new Customers();
//        customers1.setName("Nancy");
//        customers1.setPassword("nana1996");
//        customersDao.create(customers1);
//
//        customersDao.remove_id(1);
//        customersDao.update_password("molly1996",3);
//
//        List<Customers> customers = customersDao.getCustomers();
//
//        for(Customers u : customers){
//            System.out.println(u.getId()+" "+u.getName()+" "+u.getPassword());
//
//        }
//    }
}
