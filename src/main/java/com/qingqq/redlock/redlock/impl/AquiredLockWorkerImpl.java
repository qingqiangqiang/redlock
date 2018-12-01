package com.qingqq.redlock.redlock.impl;


import com.qingqq.redlock.redlock.AquiredLockWorker;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * 作者: 顷强强
 * 日期：2018/11/30.
 * 邮件：qiangqiang@leoao.com
 * TODO 获得锁后的任务实现
 */
@Component
public class AquiredLockWorkerImpl implements AquiredLockWorker<Object> {

    public Object doWorkerHadGetLock(String arg) throws Exception {

        System.out.println("do 获得锁后的任务......" + arg);
        System.out.println(Thread.currentThread().getName() + " start");
        int ms = 100;
        System.out.println(Thread.currentThread().getName() + " sleep " + ms + "ms");
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " end");
        return null;
    }
}
