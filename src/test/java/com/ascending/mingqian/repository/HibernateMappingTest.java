package com.ascending.mingqian.repository;

import com.ascending.mingqian.model.User;
import com.ascending.mingqian.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class HibernateMappingTest {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void mappingTest() {
        String hql = "FROM User";
        List<User> users = null;

        try(
            Session session = HibernateUtil.getSessionFactory().openSession()){
                Query<User> query = session.createQuery(hql);
                users = query.list();
            }

        catch (Exception e){
            logger.error(e.getMessage());
        }
        Assert.assertNotNull(users);
    }
}
