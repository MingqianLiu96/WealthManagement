package com.ascending.mingqian.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageService {
    @Autowired
    private Logger logger;
    @Autowired
    private AmazonSQS amazonSQS;

    public String createQueue(String queueName){
        String queueUrl = null;

        try{
            queueUrl = getQueueUrl(queueName);

        }
        catch (QueueDoesNotExistException e){
            CreateQueueRequest createQueueRequest = new CreateQueueRequest(queueName);
            queueUrl = amazonSQS.createQueue(createQueueRequest).getQueueUrl();
        }
        return queueUrl;
    }

    public void listQueue(){
        ListQueuesResult lq_result = amazonSQS.listQueues();
        System.out.println("Your SQS Queue URLs:");
            for (String url : lq_result.getQueueUrls()) {
                System.out.println(url);
            }



    }

    public void deleteQueue(String queueName){
        String queue_url = getQueueUrl(queueName);
        amazonSQS.deleteQueue(queue_url);
    }

    public String getQueueUrl(String queueName){
        return amazonSQS.getQueueUrl(queueName).getQueueUrl();
    }

    public List<Message> getMessages(String queueName){
        String queueUrl = getQueueUrl(queueName);
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(queueUrl);
        List<Message> messages = amazonSQS.receiveMessage(receiveMessageRequest).getMessages();
        return messages;
    }

    public void sendMessage(String queueName, String msg) {
        Map<String, MessageAttributeValue> messageAttributes = new HashMap();
        MessageAttributeValue messageAttributeValue = new MessageAttributeValue();
        messageAttributeValue.withDataType("String").withStringValue("File URL in S3");
        messageAttributes.put("message", messageAttributeValue);
        String queueUrl = getQueueUrl(queueName);
        SendMessageRequest sendMessageRequest = new SendMessageRequest();
        sendMessageRequest.withQueueUrl(queueUrl)
                .withMessageBody(msg)
                .withMessageAttributes(messageAttributes);
        amazonSQS.sendMessage(sendMessageRequest);
    }

    public void deleteMessage(String queueName) {
        String queueUrl = getQueueUrl(queueName);

            // receive messages from the queue
            List<Message> messages = amazonSQS.receiveMessage(queueUrl).getMessages();

            // delete messages from the queue
//            for (Message m : messages) {
//               amazonSQS.deleteMessage(queueUrl, m.getReceiptHandle());
//            }

    }

}
