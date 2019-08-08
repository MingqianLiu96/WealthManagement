package com.ascending.mingqian.repository;

import com.ascending.mingqian.model.Record;

import java.util.List;

public interface RecordDao {
    boolean save(Record record);
    boolean update(Record record);
    boolean delete(Long id);
    List<Record> getRecords();
    Record getRecordByAccountId(Long accountId);
}
