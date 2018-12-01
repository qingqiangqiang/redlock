package com.qingqq.redlock.test;

import com.qingqq.redlock.redlock.AquiredLockWorker;
import com.qingqq.redlock.redlock.impl.RedisLocker;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * 作者: 顷强强
 * 日期：2018/11/30.
 * 邮件：qiangqiang@leoao.com
 * TODO what is this.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedlockTest {


    @Autowired
    RedisLocker distributedLocker;


    @Autowired
    AquiredLockWorker aquiredLockWorker;

    @Test
    public void redlockTest() {
        try{
            System.out.println("redlock test begin....");

            List<Integer> list = new ArrayList<>();
            for (int i=0;i<10;i++){
                list.add(i);
            }
            //执行并发
            list.parallelStream().forEach(item->{
                try{
                    distributedLocker.lock("redlock",aquiredLockWorker,"第"+(item+1)+"个请求");
                }catch (Exception e){
                    System.out.println("lock "+e);
                }
            });

            System.out.println("redlock test end...");
        }catch (Exception e){
            System.out.println("Exception "+e);
        }
    }


    @Test
    public void redlock_new_thread_Test() {
        try{
            System.out.println("redlock test begin....");

            CountDownLatch startSignal = new CountDownLatch(1);
            CountDownLatch doneSignal = new CountDownLatch(5);
            List<Integer> list = new ArrayList<>();
            for (int i=0;i<5;i++){
                list.add(i);
            }
            //执行并发
            list.parallelStream().forEach(item->{
                new Thread(new Worker(startSignal, doneSignal)).start();
            });
            startSignal.countDown(); // let all threads proceed
            doneSignal.await();
            System.out.println("redlock test end...");
        }catch (Exception e){
            System.out.println("Exception "+e);
        }
    }


    class Worker implements Runnable {
        private final CountDownLatch startSignal;
        private final CountDownLatch doneSignal;
//
        Worker(CountDownLatch startSignal, CountDownLatch doneSignal) {
            this.startSignal = startSignal;
            this.doneSignal = doneSignal;
        }

        public void run() {
            try {
                startSignal.await();
                distributedLocker.lock("test", new AquiredLockWorker<Object>() {

                    @Override
                    public Object doWorkerHadGetLock(String arg) throws Exception {
                        doTask();
                        return null;
                    }
                }, "");
            } catch (Exception e) {

            }
        }

        void doTask() {
            System.out.println("do 获得锁后的任务......" );
            System.out.println(Thread.currentThread().getName() + " start");
            int ms = 100;
            System.out.println(Thread.currentThread().getName() + " sleep " + ms + "ms");
            try {
                Thread.sleep(ms);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " end");
            doneSignal.countDown();
        }

    }

}
