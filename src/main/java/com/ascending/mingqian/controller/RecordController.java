package com.ascending.mingqian.controller;

import com.ascending.mingqian.model.Record;

import com.ascending.mingqian.service.AccountService;
import com.ascending.mingqian.service.RecordService;

import com.ascending.mingqian.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/records"})
public class RecordController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RecordService recordService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public List<Record> getRecords(){
        return recordService.getRecords();
    }

    @RequestMapping(value = "/account/{accountId}",method = RequestMethod.GET,produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Record> getRecords(@PathVariable(name = "accountId") Long a){

        List<Record> records = recordService.getRecordByAccountId(a);

        return records;
    }

    @RequestMapping(value = "/{recordId}",method = RequestMethod.GET,produces = {MediaType.APPLICATION_JSON_VALUE})
    public Record getRecord(@PathVariable(name = "recordId") Long id){
        Record record = recordService.getRecordById(id);
        return record;
    }

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public String createRecord(@RequestBody Record record){
        logger.debug("Record: " + record.toString());
        String msg = "The record was created.";
        boolean isSuccess = recordService.save(record);

        if(!isSuccess) msg = "The record was not created.";

        return msg;
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public String updateRecord(@RequestBody Record record){
        logger.debug("Record: " + record.toString());
        String msg = "The record was updated.";
        boolean isSuccess = recordService.update(record);

        if(!isSuccess) msg = "The record was not updated.";

        return msg;
    }

    @RequestMapping(value = "/{recordId}", method = RequestMethod.DELETE, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public String deleteRecord(@PathVariable(name = "recordId") Long id){
        logger.debug("Record Id: " + id);
        String msg = "The record was deleted.";

        boolean isSuccess = recordService.delete(id);

        if(!isSuccess) msg = "The record was not deleted.";

        return msg;
    }

}
