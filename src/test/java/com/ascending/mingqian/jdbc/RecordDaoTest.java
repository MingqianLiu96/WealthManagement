package com.ascending.mingqian.jdbc;

import com.ascending.mingqian.model.Record;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class RecordDaoTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private RecordDao recordDao;
    @BeforeClass
    public static void initAllTest(){
        System.out.println("*********Start Test*********");
    }

    @AfterClass
    public static void endAllTest(){
        System.out.println("*********End Test*********");
    }

    @Before
    public void init(){
        recordDao = new RecordDao();
    }

    @After
    public void cleanup(){
        recordDao = null;
    }
    @Test
    public void getUsersTest(){

        List<Record> recordList = recordDao.getRecords();

        for(Record record : recordList){
            System.out.println(record.getId() + " " + record.getType() + " " +
                    record.getAmount() + " " + record.getDate()+" "+record.getDescription()+" "+
                    record.getAccountInfo_id());
        }

    }

    //@Ignore
    @Test
    public void saveTest(){
        Record record1 = new Record();
        record1.setType("rent");
        record1.setAmount(3300);
        long time = System.currentTimeMillis();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(time);
        record1.setDate(timestamp);
        record1.setDescription("three months rent for agent");
        record1.setAccountInfo_id(8);
        recordDao.save(record1);

    }

}
