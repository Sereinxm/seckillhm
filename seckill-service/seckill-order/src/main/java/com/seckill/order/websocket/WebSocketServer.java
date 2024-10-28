package com.seckill.order.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

@Slf4j
@ServerEndpoint("/socket/{userid}")
@Component
public class WebSocketServer {

    // 存放每个客户端对应的WebSocketServer，使用现成安全的Set容器
    private static CopyOnWriteArraySet<WebSocketServer> webSocketServerSet = new CopyOnWriteArraySet<>();

    // 存放客户端的连接会话，需要使用会话来发送数据给客户端
    private static Map<String, Session> sessionMap = new HashMap<>();

    //用户唯一标识符
    private String userid;

    //连接建立成功调用的方法
    @OnOpen
    public void onOpen(@PathParam("userid") String userid, Session session) {
        this.userid = userid;
        sessionMap.put(userid, session);

        webSocketServerSet.add(this);

        try {
            sendMessage("连接成功", userid);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //连接关闭调用的方法
    @OnClose
    public void onClose() {
        webSocketServerSet.remove(this);
        sessionMap.remove(userid);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 接受到的客户端的消息
     */
    @OnMessage
    public void onMessage(String message) {
        log.info("收到的客户端消息：" + message);
    }

    //发生异常处理方法
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    //群发消息
    public void sendMessage(String message) throws IOException {
        for (Map.Entry<String, Session> sessionEntry : sessionMap.entrySet()) {
            sessionEntry.getValue().getBasicRemote().sendText(message);
        }
    }

    //给指定用户发消息
    public void sendMessage(String message, String userid) throws IOException {
        sessionMap.get(userid).getBasicRemote().sendText(message);
    }
}