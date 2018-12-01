package com.qingqq.redlock.redlock;


import com.qingqq.redlock.exception.UnableToAquireLockException;

/**
 * 作者: 顷强强
 * 日期：2018/11/30.
 * 邮件：qiangqiang@leoao.com
 * TODO 获取锁管理器
 */
public interface DistributedLocker {


   <T> T lock(String lockName,AquiredLockWorker<T> aquiredLockWorker,String arg) throws UnableToAquireLockException,Exception;


   <T> T lock(String lockName,AquiredLockWorker<T> aquiredLockWorker,String arg,int lockTime) throws UnableToAquireLockException,Exception;
}
