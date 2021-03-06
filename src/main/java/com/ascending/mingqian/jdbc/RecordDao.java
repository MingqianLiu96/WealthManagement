package com.ascending.mingqian.jdbc;


import com.ascending.mingqian.model.Record;

import java.sql.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecordDao {
    //STEP 1: Database information
    static final String DB_URL = "jdbc:postgresql://localhost:5432/accounting";
    static final String USER = "admin";
    static final String PASS = "molly";

    public Record create(Record record){

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
            sql = "insert into record (type,amount, date, description, account_id) values " +
                    "(\'"+
                    record.getType()+"\',"+record.getAmount()+",\'"+record.getDate()+"\',\'"+record.getDescription()+"\'," +
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


        return record;
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


            String sql;
            sql = "delete from record where id = " + id;


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

    }

    public void update_amount(double a,long id){
        Connection conn = null;
        Statement stmt = null;

        try {

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            //STEP 3: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();


            String sql;
            sql = "update record set amount = \'"+ a +
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


    public List<Record> getRecords() {
        List<Record> recordList = new ArrayList();
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
            sql = "SELECT * FROM record";
            rs = stmt.executeQuery(sql);
            //STEP 4: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                long id = rs.getInt("id");
                String type = rs.getString("type");
                double amount = rs.getDouble("amount");
                Date date = rs.getDate("date");
                String description = rs.getString("description");
                long account_id = rs.getInt("account_id");
                //Fill the object
                Record record = new Record();
                record.setId(id);
                record.setType(type);
                record.setAmount(amount);
                record.setDate(date);
                record.setDescription(description);
               // record.setAccount_id(account_id);

                recordList.add(record);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //STEP 6: finally block used to close resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return recordList;
    }

//    public static void main(String[] args) {
//        RecordDao recordDao = new RecordDao();
//
//        Record record1 = new Record();
//        record1.setType("rent");
//        record1.setAmount(3300);
//        long time = System.currentTimeMillis();
//        java.sql.Timestamp timestamp = new java.sql.Timestamp(time);
//        record1.setDate(timestamp);
//        record1.setDescription("three months rent for agent");
//        record1.setAccount_id(8);
//        recordDao.create(record1);
//
//        List<Record> records = recordDao.getRecords();
//
//        for (Record record : records) {
//            System.out.println(record.getId() + " " + record.getType() + " " +
//                    record.getAmount() + " " + record.getDate()+" "+record.getDescription()+" "+
//                    record.getAccount_id());
//
//        }
//    }
}