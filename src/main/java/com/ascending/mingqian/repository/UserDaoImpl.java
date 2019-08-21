package com.ascending.mingqian.repository;

import com.ascending.mingqian.model.Account;
import com.ascending.mingqian.model.Record;
import com.ascending.mingqian.model.User;
import com.ascending.mingqian.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.internal.expression.NullLiteralExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class UserDaoImpl implements UserDao{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RecordDao recordDao;
    @Autowired
    private AccountDao accountDao;

    @Override
    public boolean save(User user){
        Transaction transaction = null;
        boolean isSuccess = true;

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        }
        catch (Exception e){
            isSuccess = false;
            if (transaction != null)transaction.rollback();
            logger.error(e.getMessage());
        }
        if(isSuccess) logger.debug(String.format("The user %s was inserted into the table",user.toString()));

        return isSuccess;
    }

    @Override
    public boolean delete(String userName){


            User u = getUserByName(userName);
            if(u != null) {

                List<Account> accounts = accountDao.getAccountByUserId(u.getId());
                for (Account a : accounts) {
                    List<Record> records = recordDao.getRecordByAccountId(a.getId());

                    for (Record r : records) {
                        recordDao.delete(r.getId());
                    }
                    accountDao.delete(a.getId());
                }
            }

                String hql = "DELETE User where name = :userName1";
                int deletedCount = 0;
                Transaction transaction = null;
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//                    User u = getUserByName(userName);
//                    session.delete(u);

                    Query<User> query = session.createQuery(hql);
                    query.setParameter("userName1", userName.toLowerCase());

                    transaction = session.beginTransaction();
                    deletedCount = query.executeUpdate();
                    transaction.commit();
                } catch (Exception e) {
                    if (transaction != null) transaction.rollback();
                    logger.error(e.getMessage());
                }
                logger.debug(String.format("The user %s was deleted", userName));
                return deletedCount >= 1 ? true : false;


    }


    @Override
    public boolean delete(Long id){

        User u = getUserById(id);
        if(u != null) {

            List<Account> accounts = accountDao.getAccountByUserId(u.getId());
            for (Account a : accounts) {
                List<Record> records = recordDao.getRecordByAccountId(a.getId());

                for (Record r : records) {
                    recordDao.delete(r.getId());
                }
                accountDao.delete(a.getId());
            }
        }

        String hql = "DELETE User where id = :id";
        int deletedCount = 0;
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Query<User> query = session.createQuery(hql);
            query.setParameter("id", id);

            transaction = session.beginTransaction();
            deletedCount = query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage());
        }
        logger.debug(String.format("The user %d was deleted", id));
        return deletedCount >= 1 ? true : false;


    }



    @Override
    public boolean update(User user){
        Transaction transaction = null;
        boolean isSuccess = true;

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.saveOrUpdate(user);
            transaction.commit();
        }
        catch (Exception e){
            isSuccess = false;
            if (transaction != null)transaction.rollback();
            logger.error(e.getMessage());
        }
        if(isSuccess) logger.debug(String.format("The user %s was inserted into the table",user.toString()));

        return isSuccess;

    }

    @Override
    public List<User> getUsers(){
        String hql = "FROM User as u left join fetch u.accounts as acc left join fetch acc.records";
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<User> query = session.createQuery(hql);
            return query.list();
        }

    }

    @Override
    public User getUserByName(String userName){
        if(userName == null) return null;

        String hql = "FROM User as u left join fetch u.accounts as acc left join fetch acc.records where lower(u.name) = :name";

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<User> query = session.createQuery(hql);
            query.setParameter("name",userName.toLowerCase());

            User user = query.uniqueResult();
            if(user != null) {
                logger.debug(user.toString());
            }
                return user;



        }

    }
    public User getUserById(Long id){
        if(id == null) return null;

        String hql = "FROM User as user where user.id = :id";

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<User> query = session.createQuery(hql);
            query.setParameter("id",id);

            User user = query.uniqueResult();
            if(user != null) {
                logger.debug(user.toString());
            }
            return user;



        }

    }

}
