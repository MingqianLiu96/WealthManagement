package com.ascending.mingqian.service;

import com.amazonaws.util.IOUtils;
import com.ascending.mingqian.init.AppInitializer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class MessageServiceTest {
    @Autowired
    private Logger logger;
    @Autowired
    private MessageService messageService;

    private String queueName = "mingqian";

    @Before
    public void setUp() throws IOException {
        logger.info(">>>>>>>>>>>>>> Start test");
    }

    @After
    public void tearDown(){
        logger.info(">>>>>>>>>>>>>> End test");
    }

    @Test
    public void createQueue(){
        messageService.createQueue("mingqian96");
    }

    @Test
    public  void listQueue(){
        messageService.listQueue();
    }

    @Test
    public void deleteQueue(){
        messageService.deleteQueue("mingqian96");
    }

    @Test
    public void deleteMessage(){
        messageService.deleteMessage(queueName);
    }

}
