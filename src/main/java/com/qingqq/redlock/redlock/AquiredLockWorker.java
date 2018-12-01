package com.qingqq.redlock.redlock;

/**
 * 作者: 顷强强
 * 日期：2018/11/30.
 * 邮件：qiangqiang@leoao.com
 * TODO 获得锁后工作接口
 */
public interface AquiredLockWorker<T> {

    T doWorkerHadGetLock(String arg) throws Exception;


}
