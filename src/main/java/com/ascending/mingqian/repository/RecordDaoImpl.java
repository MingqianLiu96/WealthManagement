package com.ascending.mingqian.repository;

import com.ascending.mingqian.model.Account;
import com.ascending.mingqian.model.Record;
import com.ascending.mingqian.model.User;
import com.ascending.mingqian.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class RecordDaoImpl implements RecordDao{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean save(Record record){
        Transaction transaction = null;
        boolean isSuccess = true;

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.save(record);
            transaction.commit();
        }
        catch (Exception e){
            isSuccess = false;
            if (transaction != null)transaction.rollback();
            logger.error(e.getMessage());
        }
        if(isSuccess)
            logger.debug(String.format("The record %s was inserted into the table",record.toString()));

        return isSuccess;
    }

    @Override
    public boolean delete(Long id){


        String hql = "DELETE Record where id = :recordId";
        int deletedCount = 0;
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Record> query = session.createQuery(hql);
            query.setParameter("recordId",id);

            transaction = session.beginTransaction();
            deletedCount = query.executeUpdate();
            transaction.commit();
        }
        catch (Exception e){
            if (transaction != null)transaction.rollback();
            logger.error(e.getMessage());
        }
        logger.debug(String.format("The record %d was deleted",id));
        return deletedCount >= 1 ? true : false;


    }

    @Override
    public boolean update(Record record){
        Transaction transaction = null;
        boolean isSuccess = true;

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.saveOrUpdate(record);
            transaction.commit();
        }
        catch (Exception e){
            isSuccess = false;
            if (transaction != null)transaction.rollback();
            logger.error(e.getMessage());
        }
        if(isSuccess) logger.debug(String.format("The record %s was inserted into the table",record.toString()));

        return isSuccess;

    }

    @Override
    public List<Record> getRecords(){
        String hql = "FROM Record as r left join fetch r.account left join fetch r.account.user";
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Record> query = session.createQuery(hql);
            return query.list();
        }

    }

    @Override
    public List<Record> getRecordByAccountId(Long accountId){
        //if(id == null) return null;
        String hql = "FROM Record r where r.account.id = :id";

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Record> query = session.createQuery(hql);
            query.setParameter("id",accountId);
//
//            Record record = query.uniqueResult();
//            logger.debug(record.toString());

            return query.list();

        }

    }

    @Override
    public Record getRecordById(Long id){
        //if(id == null) return null;
        String hql = "FROM Record r  where r.id = :id";

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Record> query = session.createQuery(hql);
            query.setParameter("id",id);

            Record record = query.uniqueResult();
            if (record != null) {
                logger.debug(record.toString());
            }
            return record;

        }

    }
}
