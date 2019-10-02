package com.ascending.mingqian.service;


import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.*;
import com.ascending.mingqian.init.AppInitializer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class MessageServiceMockAWSTest {
    @Mock(answer = Answers.RETURNS_DEEP_STUBS) private AmazonSQS amazonSQS;
    @Autowired @Spy private Logger logger;
    @InjectMocks
    private MessageService messageService;

    private String queueName = "mingqian";

    String queueUrl = "https://sqs.us-east-1.amazonaws.com/900356963669/mingqian";

    @Before
    public void setUp() throws IOException {
        logger.info(">>>>>>>>>>>>>> Start test");
        MockitoAnnotations.initMocks(this);




    }

    @After
    public void tearDown(){
        logger.info(">>>>>>>>>>>>>> End test");
    }

    @Test
    public void createQueue(){
        when(amazonSQS.getQueueUrl(queueName).getQueueUrl()).thenThrow(new QueueDoesNotExistException("the queue is not exist"));

        messageService.createQueue(queueName);

        verify(amazonSQS, timeout(10).times(1)).createQueue(any(CreateQueueRequest.class));
    }

    @Test
    public  void listQueue(){
        when(amazonSQS.getQueueUrl(queueName).getQueueUrl()).thenThrow(new QueueDoesNotExistException("the queue is not exist"));

        messageService.listQueue();
        verify(amazonSQS, times(1)).listQueues();
    }

    @Test
    public void deleteQueue(){
        when(amazonSQS.getQueueUrl(anyString()).getQueueUrl()).thenReturn(queueUrl);

        messageService.deleteQueue(anyString());
        verify(amazonSQS, times(1)).deleteQueue(anyString());
    }

    @Test
    public void deleteMessage(){
        List<Message> messages = new ArrayList<>();
        Message message = new Message();
        message.setReceiptHandle("fake");
        messages.add(message);
        when(amazonSQS.getQueueUrl(anyString()).getQueueUrl()).thenReturn(queueUrl);
        when(amazonSQS.receiveMessage(anyString()).getMessages()).thenReturn(messages);

        //when(amazonSQS.receiveMessage(anyString())).thenReturn(any(ReceiveMessageResult.class));

        messageService.deleteMessage(anyString());

        verify(amazonSQS, times(2)).receiveMessage(anyString()).getMessages();
        //verify(amazonSQS, times(1)).receiveMessage(queueUrl).getMessages();
       // verify(amazonSQS, times(1)).deleteMessage(anyString(),anyString());
    }

}
