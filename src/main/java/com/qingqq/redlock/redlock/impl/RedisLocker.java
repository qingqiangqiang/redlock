package com.qingqq.redlock.redlock.impl;

import com.qingqq.redlock.exception.UnableToAquireLockException;
import com.qingqq.redlock.redis.RedissonConnector;
import com.qingqq.redlock.redlock.AquiredLockWorker;
import com.qingqq.redlock.redlock.DistributedLocker;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 作者: 顷强强
 * 日期：2018/11/30.
 * 邮件：qiangqiang@leoao.com
 * TODO what is this.
 */
@Component
public class RedisLocker implements DistributedLocker {



    @Resource
    RedissonConnector redissonConnector;

    public <T> T lock(String lockName, AquiredLockWorker<T> aquiredLockWorker,String arg) throws UnableToAquireLockException, Exception {

        return lock(lockName, aquiredLockWorker, arg,100);
    }

    public <T> T lock(String lockName, AquiredLockWorker<T> aquiredLockWorker,String arg, int lockTime) throws UnableToAquireLockException, Exception {

        RedissonClient redisson= redissonConnector.getClient();

        RLock lock = redisson.getLock(lockName);
        boolean success = lock.tryLock(100, lockTime, TimeUnit.SECONDS);
        if (success) {
            try {
                return aquiredLockWorker.doWorkerHadGetLock(arg);
            } finally {
                lock.unlock();
            }
        }
        throw new UnableToAquireLockException();
    }
}
