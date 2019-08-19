package com.ascending.mingqian.service;

import com.ascending.mingqian.model.Record;
import com.ascending.mingqian.repository.RecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordService {
    @Autowired
    private RecordDao recordDao;

    boolean save(Record record){
        return recordDao.save(record);
    }
    boolean update(Record record){
        return recordDao.update(record);
    }
    boolean delete(Long id){
        return recordDao.delete(id);
    }
    List<Record> getRecords(){
        return recordDao.getRecords();
    }
    Record getRecordById(Long id){
        return recordDao.getRecordById(id);
    }
    List<Record> getRecordByAccountId(Long accountId){
        return recordDao.getRecordByAccountId(accountId);
    }
}
