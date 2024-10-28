package com.seckill.order.config;

import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

public interface DistributedLocker {
    /**
     * lock(), 拿不到lock就不罢休，不然线程就一直block
     */
    RLock lock(String lockKey);
    /**
     * timeout为加锁时间，单位为秒
     */
    RLock lock(String lockKey, long timeout);
    /**
     * timeout为加锁时间，时间单位由unit确定
     */
    RLock lock(String lockKey, TimeUnit unit, long timeout);
    /**
     * tryLock()，马上返回，拿到lock就返回true，不然返回false。
     * 带时间限制的tryLock()，拿不到lock，就等一段时间，超时返回false.
     */
    boolean tryLock(String lockKey, TimeUnit unit, long waitTime, long leaseTime);
    /**
     * 解锁
     */
    void unLock(String lockKey);
    /**
     * 解锁
     */
    void unLock(RLock lock);
}