package com.ascending.mingqian.jdbc;

import com.ascending.mingqian.model.AccountInfo;
import com.ascending.mingqian.model.Record;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecordDao {
    //STEP 1: Database information
    static final String DB_URL = "jdbc:postgresql://localhost:5432/accounting";
    static final String USER = "admin";
    static final String PASS = "molly";

    public Record save(Record record){

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
            sql = "insert into record (type,amount, date, description, accountInfo_id) values " +
                    "(\'"+
                    record.getType()+"\',"+record.getAmount()+",\'"+record.getDate()+"\',\'"+record.getDescription()+"\',"+record.getAccountInfo_id() +
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
                int id = rs.getInt("id");
                String type = rs.getString("type");
                double amount = rs.getDouble("amount");
                Date date = rs.getDate("date");
                String description = rs.getString("description");
                int accountInfo_id = rs.getInt("accountInfo_id");
                //Fill the object
                Record record = new Record();
                record.setId(id);
                record.setType(type);
                record.setAmount(amount);
                record.setDate(date);
                record.setDescription(description);
                record.setAccountInfo_id(accountInfo_id);

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

    public static void main(String[] args) {
        RecordDao recordDao = new RecordDao();

//        Record record1 = new Record();
//        record1.setType("rent");
//        record1.setAmount(3300);
//        long time = System.currentTimeMillis();
//        java.sql.Timestamp timestamp = new java.sql.Timestamp(time);
//        record1.setDate(timestamp);
//        record1.setDescription("three months rent for agent");
//        record1.setAccountInfo_id(8);
//        recordDao.save(record1);

        List<Record> records = recordDao.getRecords();

        for (Record record : records) {
            System.out.println(record.getId() + " " + record.getType() + " " +
                    record.getAmount() + " " + record.getDate()+" "+record.getDescription()+" "+
                    record.getAccountInfo_id());

        }
    }
}