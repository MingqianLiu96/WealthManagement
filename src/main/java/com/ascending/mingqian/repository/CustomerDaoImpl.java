package com.ascending.mingqian.repository;

import com.ascending.mingqian.model.Account;
import com.ascending.mingqian.model.Customer;
import com.ascending.mingqian.model.Record;
import com.ascending.mingqian.model.View;
import com.ascending.mingqian.util.HibernateUtil;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerDaoImpl implements CustomerDao {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RecordDao recordDao;
    @Autowired
    private AccountDao accountDao;

    @Override
    public boolean save(Customer customer){
        Transaction transaction = null;
        boolean isSuccess = true;

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.save(customer);
            transaction.commit();
        }
        catch (Exception e){
            isSuccess = false;
            if (transaction != null)transaction.rollback();
            logger.error(e.getMessage());
        }
        if(isSuccess) logger.debug(String.format("The customer %s was inserted into the table", customer.toString()));

        return isSuccess;
    }

    @Override
    public boolean delete(String customerName){


//            Customer u = getCustomerByName(customerName);
//            if(u != null) {
//
//                List<Account> accounts = accountDao.getAccountByCustomerId(u.getId());
//                for (Account a : accounts) {
//                    List<Record> records = recordDao.getRecordByAccountId(a.getId());
//
//                    for (Record r : records) {
//                        recordDao.delete(r.getId());
//                    }
//                    accountDao.delete(a.getId());
//                }
//            }

                String hql = "DELETE Customer where name = :customerName";
                int deletedCount = 0;
                Transaction transaction = null;
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {

                    Customer u = getCustomerByName(customerName);
                    session.delete(u);

                    Query<Customer> query = session.createQuery(hql);
                    query.setParameter("customerName", customerName.toLowerCase());

                    transaction = session.beginTransaction();
                    deletedCount = query.executeUpdate();
                    transaction.commit();
                } catch (Exception e) {
                    if (transaction != null) transaction.rollback();
                    logger.error(e.getMessage());
                }
                logger.debug(String.format("The customer %s was deleted", customerName));
                return deletedCount >= 1 ? true : false;


    }


    @Override
    public boolean delete(Long id){

        Customer u = getCustomerById(id);
        if(u != null) {

            List<Account> accounts = accountDao.getAccountByCustomerId(u.getId());
            for (Account a : accounts) {
                List<Record> records = recordDao.getRecordByAccountId(a.getId());

                for (Record r : records) {
                    recordDao.delete(r.getId());
                }
                accountDao.delete(a.getId());
            }
        }

        String hql = "DELETE Customer where id = :id";
        int deletedCount = 0;
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Query<Customer> query = session.createQuery(hql);
            query.setParameter("id", id);

            transaction = session.beginTransaction();
            deletedCount = query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage());
        }
        logger.debug(String.format("The customer %d was deleted", id));
        return deletedCount >= 1 ? true : false;


    }



    @Override
    public boolean update(Customer customer){
        Transaction transaction = null;
        boolean isSuccess = true;

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.saveOrUpdate(customer);
            transaction.commit();
        }
        catch (Exception e){
            isSuccess = false;
            if (transaction != null)transaction.rollback();
            logger.error(e.getMessage());
        }
        if(isSuccess) logger.debug(String.format("The customer %s was inserted into the table", customer.toString()));

        return isSuccess;

    }

    @Override
    public List<Customer> getCustomers(){
        String hql = "FROM Customer";
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Customer> query = session.createQuery(hql);
            return query.list();
        }

    }

    @Override
    public Customer getCustomerByName(String customerName){
        if(customerName == null) return null;

        String hql = "FROM Customer as u left join fetch u.accounts as acc left join fetch acc.records where lower(u.name) = :name";

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Customer> query = session.createQuery(hql);
            query.setParameter("name",customerName.toLowerCase());

            Customer customer = query.uniqueResult();
            if(customer != null) {
                logger.debug(customer.toString());
            }
                return customer;



        }

    }
    public Customer getCustomerById(Long id){
        if(id == null) return null;

        String hql = "FROM Customer as customer where customer.id = :id";

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Customer> query = session.createQuery(hql);
            query.setParameter("id",id);

            Customer customer = query.uniqueResult();
            if(customer != null) {
                logger.debug(customer.toString());
            }
            return customer;



        }

    }

}
