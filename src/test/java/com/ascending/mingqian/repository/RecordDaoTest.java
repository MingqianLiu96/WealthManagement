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
    private long i;
    private long j;


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
        Record r1 = new Record();
        r1.setType("rent");
        r1.setAmount(3300);
        long time = System.currentTimeMillis();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(time);
        r1.setDate(timestamp);
        r1.setDescription("three months rent for agent");
        r1.setAccount_id(Long.valueOf(8));
        recordDao.save(r1);
        i = r1.getId();
    }
    @After
    public void cleanup(){
        logger.info("after method");

        Record r2 = recordDao.getRecordById(i);
        if(r2 != null){
            recordDao.delete(i);
        }
        Record r3 = recordDao.getRecordById(j);
        if (r3 != null){
            recordDao.delete(j);
        }
        Record r4 = recordDao.getRecordById(Long.valueOf(2));
        if(!r4.getType().equals("clothes")){

            r4.setType("clothes");
            recordDao.update(r4);
        }
        recordDao = null;

    }

    @Test
    public void getRecords(){
        List<Record> records = recordDao.getRecords();

        records.forEach(record -> System.out.println(record));

        assertEquals(records.size(),6);

    }

    @Test
    public void getRecordByAccountId(){
        List<Record>  r = recordDao.getRecordByAccountId(Long.valueOf(2));
        assertNotNull(r.size());
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
        j = record1.getId();
    }

    @Test
    public void update(){
        Record record2 = new Record();
        record2.setId(Long.valueOf(2));
        record2.setType("rent");
        recordDao.update(record2);
    }

    @Test
    public void delete(){
        recordDao.delete(Long.valueOf(i));

    }
}
