package com.ascending.mingqian.jdbc;

import com.ascending.mingqian.model.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {
    //STEP 1: Database information
    static final String DB_URL = "jdbc:postgresql://localhost:5432/accounting";
    static final String USER = "admin";
    static final String PASS = "molly";
    public Account create(Account account){

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
            sql = "insert into account (balance, accountType,users_id) values " +
                    "("+
                    account.getBalance()+",\'"+account.getAccountType()+"\',"+account.getUsers_id() +
                    ")";

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


        return account;
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


            String sql1,sql2;
            sql1 = "delete from record where record.account_id = " + id;
            sql2 = "delete from account where id = "+id;


            stmt.executeUpdate(sql1);
            stmt.executeUpdate(sql2);


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

    public void update_balance(double b,int id){
        Connection conn = null;
        Statement stmt = null;

        try {

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            //STEP 3: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();


            String sql;
            sql = "update account set balance = \'"+ b +
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

    public List<Account> getAccountInfos() {
        List<Account> accountInfoList = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            //STEP 2: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            //STEP 3: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM account";
            rs = stmt.executeQuery(sql);
            //STEP 4: Extract data from result set
            while(rs.next()) {
                //Retrieve by column name
                int id  = rs.getInt("id");
                double balance = rs.getDouble("balance");
                String accountType = rs.getString("accountType");
                int users_id = rs.getInt("users_id");
                //Fill the object
                Account account = new Account();
                account.setId(id);
                account.setBalance(balance);
                account.setAccountType(accountType);
                account.setUsers_id(users_id);

                accountInfoList.add(account);

            }
        }
        catch(Exception e){
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
        return accountInfoList;
    }
    public static void main(String[] args){
        AccountDao accountInfoDao = new AccountDao();

//        Account accountInfo1 = new Account();
//        accountInfo1.setBalance(1200);
//        accountInfo1.setAccountType("Wechat Pay");
//        accountInfo1.setUsers_id(2);
//        accountInfoDao.create(accountInfo1);

        List<Account> accountInfos = accountInfoDao.getAccountInfos();

        for(Account account : accountInfos){
            System.out.println(account.getId()+" "+account.getBalance()+" "+
                    account.getAccountType()+" "+account.getUsers_id());

        }
    }
}
