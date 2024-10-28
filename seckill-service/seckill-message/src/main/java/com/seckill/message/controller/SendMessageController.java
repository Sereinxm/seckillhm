package com.seckill.message.controller;

import com.alibaba.fastjson.JSON;
import com.seckill.message.config.NettyWebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/msg")
public class SendMessageController {

    @Autowired
    private NettyWebSocketServer nettyWebSocketServer;

    /**
     * 发送消息
     */
    @GetMapping(value = "/{userid}")
    public String send(@PathVariable(value = "userid")String userid,@RequestParam Map<String,Object> dataMap) {
        nettyWebSocketServer.sendMessage(userid, JSON.toJSONString(dataMap));
        return "发送成功";
    }
}