package com.seckill.order.controller;

import com.seckill.order.config.RedissonDistributedLocker;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/redisson")
public class RedissonController {

    @Autowired
    private RedissonDistributedLocker distributedLocker;

    /**
     * 多个用户实现加锁操作，只允许有一个用户可以获取到对应锁
     */
    @GetMapping(value = "/lock/{time}")
    public String lock(@PathVariable(value = "time") Long time) throws InterruptedException {
        System.out.println("当前休眠标识时间：" + time);

        //获取锁
        RLock rlock = distributedLocker.lock("UUUUU");
        System.out.println("执行休眠：" + time);

        TimeUnit.SECONDS.sleep(time);

        System.out.println("===========休眠完成，准备释放锁：" + time);
        //释放锁
        distributedLocker.unLock(rlock);
        return "OK";
    }
}