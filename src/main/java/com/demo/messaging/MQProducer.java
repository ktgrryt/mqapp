package com.demo.messaging;

import jakarta.annotation.Resource;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.jms.JMSConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;

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
      context.createProducer().send(queue, message);
      return message + " enqueued.";
    }

    public String sendRemoteMessage(String message) throws Exception {
      context.createProducer().send(remoteQueue, message);
      return message + " remote enqueued.";
    }

}
