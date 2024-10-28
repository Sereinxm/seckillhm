package com.seckill.message.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = "seckill-message")
public interface MessageFeign {

    /**
     * 发送消息
     */
    @GetMapping(value = "/msg/{userid}")
    String send(@PathVariable(value = "userid") String userid, @RequestParam Map<String, Object> dataMap);
}