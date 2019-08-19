package com.ascending.mingqian.repository;

import com.ascending.mingqian.model.Account;
import com.ascending.mingqian.model.Record;
import com.ascending.mingqian.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class AccountDaoImpl implements AccountDao {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RecordDao recordDao;

    @Override
    public boolean save(Account account){
        Transaction transaction = null;
        boolean isSuccess = true;

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.save(account);
            transaction.commit();
        }
        catch (Exception e){
            isSuccess = false;
            if (transaction != null)transaction.rollback();
            logger.error(e.getMessage());
        }
        if(isSuccess)
            logger.debug(String.format("The account %s was inserted into the table",account.toString()));

        return isSuccess;
    }

    @Override
    public boolean delete(Long id){

        List<Record> records = recordDao.getRecordByAccountId(id);
        if(records != null) {
            for (Record r : records) {
                recordDao.delete(r.getId());
            }
        }

        String hql = "DELETE Account where id = :accountId";
        int deletedCount = 0;
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Account> query = session.createQuery(hql);
            query.setParameter("accountId",id);

            transaction = session.beginTransaction();
            deletedCount = query.executeUpdate();
            transaction.commit();
        }
        catch (Exception e){
            if (transaction != null)transaction.rollback();
            logger.error(e.getMessage());
        }
        logger.debug(String.format("The account %d was deleted",id));
        return deletedCount >= 1 ? true : false;


    }

    @Override
    public boolean update(Account account){
        Transaction transaction = null;
        boolean isSuccess = true;

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.saveOrUpdate(account);
            transaction.commit();
        }
        catch (Exception e){
            isSuccess = false;
            if (transaction != null)transaction.rollback();
            logger.error(e.getMessage());
        }
        if(isSuccess) logger.debug(String.format("The account %s was inserted into the table",account.toString()));

        return isSuccess;

    }

    @Override
    public List<Account> getAccounts(){
        String hql = "FROM Account as a left join fetch a.records";
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Account> query = session.createQuery(hql);
            return query.list();
        }

    }

    @Override
    public List<Account> getAccountByUserId(Long userId){
        //if(id == null) return null;

        String hql = "FROM Account a where a.user.id = :id";

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Account> query = session.createQuery(hql);
            query.setParameter("id",userId);
//
//            Account account = query.uniqueResult();
//            logger.debug(account.toString());

            return query.list();

        }

    }
    @Override
    public Account getAccountById(Long id){
        if(id == null) return null;

        String hql = "FROM Account as a left join fetch a.records where a.id = :id";

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Account> query = session.createQuery(hql);
            query.setParameter("id",id);

            Account account = query.uniqueResult();
            if(account != null) {
                logger.debug(account.toString());
            }
            return account;
        }

    }
}
