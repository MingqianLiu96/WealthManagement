package com.ascending.mingqian.repository;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        UserDaoTest.class,
        AccountDaoTest.class,
        RecordDaoTest.class

})
public class TestAll {
}
