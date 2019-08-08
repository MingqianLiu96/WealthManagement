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
                    record.getAccount_id());
        }

    }

    //@Ignore
    @Test
    public void createTest(){
        Record record1 = new Record();
        record1.setType("rent");
        record1.setAmount(3300);
        long time = System.currentTimeMillis();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(time);
        record1.setDate(timestamp);
        record1.setDescription("three months rent for agent");
        record1.setAccount_id(Long.valueOf(8));
        recordDao.create(record1);

    }

    @Test
    public void remove_idTest(){

        recordDao.remove_id(Long.valueOf(3));
    }

    @Test
    public void update_passwordTest(){
        recordDao.update_amount(25.89,Long.valueOf(4));
    }

}
