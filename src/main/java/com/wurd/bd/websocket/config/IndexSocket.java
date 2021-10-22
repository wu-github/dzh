package com.wurd.bd.websocket.config;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.atomic.AtomicInteger;

@ServerEndpoint(value = "/index")
@Component
public class IndexSocket {

    private static AtomicInteger count = new AtomicInteger(0);

    @OnOpen
    public void onOpen(Session session) {
        count.incrementAndGet();
        System.out.println(count.get());
        this.sendMessage("success: " + count.get(), session);
    }

    @OnClose
    public void onClose(Session session) {
        count.decrementAndGet();
        System.out.println(count.get());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println(session.getId() + message);
    }

    @OnError
    public void onError(Session session, Throwable e) {
        e.printStackTrace();
    }

    private void sendMessage(String message, Session toSession) {
        try {
            System.out.println(toSession.getId() + message);
            toSession.getBasicRemote().sendText(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}