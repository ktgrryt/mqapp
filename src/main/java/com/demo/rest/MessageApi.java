package com.demo.rest;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import com.demo.messaging.MQProducer;

@Stateless
@Path("/")
public class MessageApi {

    @Inject
    MQProducer producer;

    @GET
    @Path("/ping")
    public String ping(){
        return "OK";
    }

    @POST
    @Path("/sendlocal")
    public String sendLocal(@FormParam("msg") String message) throws Exception {
        // 1件メッセージ送信
        return producer.sendLocalMessage(message);
    }
    @POST
    @Path("/sendremote")
    public String sendRemote(@FormParam("msg") String message) throws Exception {
        // 1件メッセージ送信
        return producer.sendRemoteMessage(message);
    }

}
