package com.demo.rest;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

import com.demo.messaging.MQConsumer;
import com.demo.messaging.MQProducer;

@Stateless
@Path("/")
public class MessageApi {

    @Inject
    MQProducer producer;

    @Inject
    MQConsumer consumer;

    @GET
    @Path("/ping")
    public String ping(){
        return "OK";
    }

    @POST
    @Path("/sendlocal")
    public String sendLocal(@FormParam("msg") String message) {
        try {
            return producer.sendLocalMessage(message);
        } catch (Exception e) {
            return "ローカルキューへの送信に失敗しました: " + e.getMessage();
        }
    }

    @POST
    @Path("/sendremote")
    public String sendRemote(@FormParam("msg") String message) {
        try {
            return producer.sendRemoteMessage(message);
        } catch (Exception e) {
            return "リモートキューへの送信に失敗しました: " + e.getMessage();
        }
    }

    @GET
    @Path("/recv")
    public String recv() {
        try {
            return consumer.recvMessage();
        } catch (Exception e) {
            return "Error " + e.getMessage();
        }
    }
}
