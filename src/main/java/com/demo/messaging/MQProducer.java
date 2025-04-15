package com.demo.messaging;

import jakarta.annotation.Resource;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.jms.JMSConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;
import jakarta.jms.TextMessage;

@Stateless
public class MQProducer {

    @Inject
    @JMSConnectionFactory("jms/wmqCF")
    JMSContext context;
  
    @Resource(lookup = "jms/queue1")
    Queue queue;

    @Resource(lookup = "jms/remote1")
    Queue remoteQueue;

    public String sendLocalMessage(String message) throws Exception {
        try {
            TextMessage textMessage = context.createTextMessage();
            textMessage.setText(message);
            context.createProducer().send(queue, textMessage);

            // JMS ヘッダー情報を取得
            String messageId = textMessage.getJMSMessageID();
            long timestamp = textMessage.getJMSTimestamp();
            String correlationId = textMessage.getJMSCorrelationID();

            return String.format("{\"message\": \"%s\", \"messageId\": \"%s\", \"timestamp\": %d, \"correlationId\": \"%s\"}",
                                message, messageId, timestamp, correlationId);
        } catch (Exception e) {
            throw new Exception("ローカルキューへの送信に失敗しました  " + e.getMessage(), e);
        }
    }

    public String sendRemoteMessage(String message) throws Exception {
        try {
            TextMessage textMessage = context.createTextMessage();
            textMessage.setText(message);
            context.createProducer().send(remoteQueue, textMessage);

            // JMS ヘッダー情報を取得
            String messageId = textMessage.getJMSMessageID();
            long timestamp = textMessage.getJMSTimestamp();
            String correlationId = textMessage.getJMSCorrelationID();

            return String.format("{\"message\": \"%s\", \"messageId\": \"%s\", \"timestamp\": %d, \"correlationId\": \"%s\"}",
                                message, messageId, timestamp, correlationId);
            
        } catch (Exception e) {
            throw new Exception("リモートキューへの送信に失敗しました  " + e.getMessage(), e);
        }
    }
}