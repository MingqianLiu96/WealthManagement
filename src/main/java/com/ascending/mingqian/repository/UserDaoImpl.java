package com.ascending.mingqian.repository;

import com.ascending.mingqian.model.User;
import com.ascending.mingqian.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UserDaoImpl implements UserDao{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

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
        String hql = "DELETE User where name = :userName1";
        int deletedCount = 0;
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<User> query = session.createQuery(hql);
            query.setParameter("userName1",userName);

            transaction = session.beginTransaction();
            deletedCount = query.executeUpdate();
            transaction.commit();
        }
        catch (Exception e){
            if (transaction != null)transaction.rollback();
            logger.error(e.getMessage());
        }
        logger.debug(String.format("The user %s was deleted",userName));
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
        String hql = "FROM User";
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<User> query = session.createQuery(hql);
            return query.list();
        }

    }

    @Override
    public User getUserByName(String userName){
        if(userName == null) return null;

        String hql = "FROM User as user where lower(user.name) = :name";

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<User> query = session.createQuery(hql);
            query.setParameter("name",userName.toLowerCase());

            User user = query.uniqueResult();
            logger.debug(user.toString());

            return user;

        }

    }


}