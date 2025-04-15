package com.demo.messaging;

import jakarta.annotation.Resource;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.jms.JMSConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSConsumer;
import jakarta.jms.Destination;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;

@Stateless
public class MQConsumer {

    @Inject
    @JMSConnectionFactory("jms/wmqCF")
    JMSContext context;

    @Resource(lookup = "jms/queue1")
    Destination dest;

    // タイムアウト時間（ミリ秒単位）
    private static final long RECEIVE_TIMEOUT = 5000; // 例: 5秒

    public String recvMessage() throws Exception {
        try {
            JMSConsumer consumer = context.createConsumer(dest);

            // タイムアウト付きで受信
            Message message = consumer.receive(RECEIVE_TIMEOUT);

            if (message == null) {
                throw new Exception("メッセージ受信タイムアウト");
            }

            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;

                // JMS ヘッダー情報を取得
                String messageId = textMessage.getJMSMessageID();
                long timestamp = textMessage.getJMSTimestamp();
                String correlationId = textMessage.getJMSCorrelationID();
                return String.format("{\"message\": \"%s\", \"messageId\": \"%s\", \"timestamp\": %d, \"correlationId\": \"%s\"}",
                                    textMessage.getText(), messageId, timestamp, correlationId);
                                    
            } else {
                throw new Exception("受信したメッセージがテキスト形式ではありません");
            }
        } catch (Exception e) {
            throw new Exception("メッセージの受信に失敗しました  " + e.getMessage(), e);
        }
    }

}

