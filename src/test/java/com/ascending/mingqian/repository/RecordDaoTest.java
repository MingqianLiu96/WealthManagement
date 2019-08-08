package com.ascending.mingqian.repository;

import com.ascending.mingqian.model.Record;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertEquals;

public class RecordDaoTest {
    private RecordDao recordDao;
    private static Logger logger = LoggerFactory.getLogger(RecordDaoTest.class);


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
        recordDao = new RecordDaoImpl();
    }
    @After
    public void cleanup(){
        logger.info("after method");
        recordDao = null;
    }

    @Test
    public void getRecords(){
        List<Record> records = recordDao.getRecords();

        records.forEach(record -> System.out.println(record));

        assertEquals(records.size(),5);

    }

    @Test
    public void getRecordByAccountId(){
        Record r = recordDao.getRecordByAccountId(Long.valueOf(2));
        assertNotNull(r.getAmount());
    }

    @Test
    public void save(){
        Record record1 = new Record();
        record1.setType("rent");
        record1.setAmount(3300);
        long time = System.currentTimeMillis();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(time);
        record1.setDate(timestamp);
        record1.setDescription("three months rent for agent");
        record1.setAccount_id(Long.valueOf(8));

        recordDao.save(record1);
    }

    @Test
    public void update(){
        Record record2 = new Record();
        record2.setId(Long.valueOf(6));
        record2.setType("rent");
        record2.setAmount(4300);
        long time = System.currentTimeMillis();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(time);
        record2.setDate(timestamp);
        record2.setDescription("three months rent for agent");
        record2.setAccount_id(Long.valueOf(8));

        recordDao.update(record2);
    }

    @Test
    public void delete(){
        recordDao.delete(Long.valueOf(6));

    }
}
