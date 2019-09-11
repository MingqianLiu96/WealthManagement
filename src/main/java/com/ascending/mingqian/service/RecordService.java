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

    public boolean save(Record record){
        return recordDao.save(record);
    }
    public boolean update(Record record){
        return recordDao.update(record);
    }
    public boolean delete(Long id){
        return recordDao.delete(id);
    }
    public List<Record> getRecords(){
        return recordDao.getRecords();
    }
    public Record getRecordById(Long id){
        return recordDao.getRecordById(id);
    }
    public List<Record> getRecordByAccountId(Long accountId){
        return recordDao.getRecordByAccountId(accountId);
    }
}
