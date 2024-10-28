package com.seckill.order.controller;

import com.seckill.order.websocket.WebSocketServer;
import com.seckill.util.Result;
import com.seckill.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(value = "/ws")
public class WebSocketController {

    @Autowired
    private WebSocketServer webSocketServer;

    /***
     * 模拟给指定用户发消息
     */
    @GetMapping(value = "/send/{userid}")
    public Result sendMessage(@PathVariable(value = "userid")String userid, String msg) throws IOException {
        webSocketServer.sendMessage(msg,userid);
        return new Result(true, StatusCode.OK,"发送消息成功@");
    }
}