package com.ascending.mingqian.service;

import com.ascending.mingqian.init.AppInitializer;
import com.ascending.mingqian.model.Account;
import com.ascending.mingqian.model.Customer;
import com.ascending.mingqian.model.Record;
import org.junit.*;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class RecordServiceTest {
    @Autowired
    private RecordService recordService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private AccountService accountService;
    private static Logger logger = LoggerFactory.getLogger(RecordServiceTest.class);
    private long i;
    private long j;
    private long customerId;
    private long accountId;


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


        Customer u = new Customer();
        u.setName("Molly");
        u.setPassword("molly");
        customerService.save(u);
        customerId = u.getId();


        Account a1 = new Account();
        a1.setAccountType("credit");
        a1.setBalance(145);
        a1.setCustomer(customerService.getCustomerById((customerId)));
        accountService.save(a1);
        accountId = a1.getId();

        Record r1 = new Record();
        r1.setType("rent");
        r1.setAmount(3300);
        long time = System.currentTimeMillis();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(time);
        r1.setDate(timestamp);
        r1.setDescription("three months rent for agent");
        r1.setAccount(accountService.getAccountById(accountId));
        recordService.save(r1);
        i = r1.getId();
    }
    @After
    public void cleanup(){
        logger.info("after method");

        Record r2 = recordService.getRecordById(i);
        if(r2 != null){
            recordService.delete(i);
        }
        Record r3 = recordService.getRecordById(j);
        if (r3 != null){
            recordService.delete(j);
        }
        Record r4 = recordService.getRecordById(i);
        if(r4 != null) {
            if (!r4.getType().equals("rent")) {
                r4.setType("rent");
                recordService.update(r4);
            }
        }

        accountService.delete(accountId);
        customerService.delete(customerId);
        recordService = null;

    }

    @Test
    public void getRecords(){
        List<Record> records = recordService.getRecords();

        records.forEach(record -> System.out.println(record));

        assertNotNull(records.size());

    }

    @Test
    public void getRecordByAccountId(){
        List<Record>  r = recordService.getRecordByAccountId(accountId);
        assertNotNull(r.size());
    }

    @Test
    public void getRecordById(){
        Record r = recordService.getRecordById(i);
        logger.info(r.toString());
        System.out.println(r);
        assertNotNull(r.toString());
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
        record1.setAccount(accountService.getAccountById(accountId));
        recordService.save(record1);
        j = record1.getId();
    }

    @Test
    public void update(){
        Record record2 = new Record();
        record2.setId(i);
        record2.setType("clothes");
        recordService.update(record2);
    }

    @Test
    public void delete(){
        recordService.delete(Long.valueOf(i));

    }
}
