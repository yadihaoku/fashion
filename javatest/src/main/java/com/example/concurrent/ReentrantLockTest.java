package com.example.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by YanYadi on 2017/3/29.
 */
public class ReentrantLockTest {
    private volatile static String result;

    /** ReentrantLock 作用跟 synchronized 关键字相同，
     * synchronized 关键字 锁的获取跟释放全由系统自动控制。
     * 而使用 ReentrantLock 需要手动获取锁、释放锁。要注意的是
     * 释放锁时，一定要要在 finally 块中。否则如果发生异常，会导致锁不能正常释放。
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        final ReentrantLock reentrantLock = new ReentrantLock();

        WorkerWrite write = new WorkerWrite(reentrantLock);
        write.start();
        // 主线程休眠一秒，保证 WorkerWrite 线程先跑起来
        TimeUnit.SECONDS.sleep(1);
        new Thread() {
            @Override
            public void run() {
                //读取线程 闭锁，当读取结束后，打开门锁
                CountDownLatch countDownLatch = new CountDownLatch(1);
                WorkerRead workerRead = new WorkerRead(reentrantLock, countDownLatch);
                workerRead.start();
                try {
                    System.out.println("等待读取结束");
                    System.out.println("-------------------------");
                    System.out.println("result =[" + result + "]");
                    System.out.println("-------------------------");
                    //等待读线程结束
                    countDownLatch.await();
                    System.out.println("读取完了");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    static class WorkerRead extends Thread {
        ReentrantLock mLock;

        CountDownLatch countDownLatch;

        public WorkerRead(ReentrantLock lock, CountDownLatch latch) {
            mLock = lock;
            countDownLatch = latch;
        }

        @Override
        public void run() {
            System.out.println("--读取线程 Is locked = " + mLock.isLocked());
            System.out.println("--读取线程 准备获取锁  threadId = " + Thread.currentThread().getId());
            mLock.lock();
            System.out.println("--读取线程 已经获取锁  threadId = " + Thread.currentThread().getId());
            try {
                System.out.println("-------------------------");
                System.out.println("result =[" + result + "]");
                System.out.println("-------------------------");
            } finally {
                mLock.unlock();
                countDownLatch.countDown();
            }
        }
    }

    static class WorkerWrite extends Thread {
        ReentrantLock mLock;

        public WorkerWrite(ReentrantLock lock) {
            mLock = lock;
        }

        @Override
        public void run() {
            System.out.println("Is locked = " + mLock.isLocked());
            System.out.println("获取锁  threadId = " + Thread.currentThread().getId());
            mLock.lock();
            System.out.println("已获取到锁  threadId = " + Thread.currentThread().getId());
            final long startTime = System.currentTimeMillis();
            try {
                while (true)
                    //运行100秒后退出
                    if (System.currentTimeMillis() - startTime > 15_000) {
                        result = "SUCCESS";
                        break;
                    }
            } finally {
                mLock.unlock();
                System.out.println("已释放锁==");
            }
        }
    }
}
