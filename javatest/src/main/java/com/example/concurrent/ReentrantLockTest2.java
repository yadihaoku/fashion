package com.example.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by YanYadi on 2017/5/27.
 */
public class ReentrantLockTest2 {
    public static void main(String[] args) {
        new Runner().start();
    }

    static class BackgroundThread extends Thread {
        ReentrantLock lock;
        Condition condition;

        BackgroundThread(ReentrantLock l, Condition c) {
            lock = l;
            condition = c;
        }

        @Override public void run() {
            lock.lock();
            System.out.println("=================background-start");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("=================background-end");
            //唤醒其它等待线程
            condition.signal();
            lock.unlock();
        }
    }

    static class Runner extends Thread {
        ReentrantLock lock;
        Condition condition;

        Runner() {
            lock = new ReentrantLock();
            condition = lock.newCondition();
        }

        @Override public void run() {
            lock.lock();

            int i = 0;
            while (i++ < 5) {
                new BackgroundThread(lock, condition).start();
                try {
                    System.out.println("----------lock");
                    condition.await();
                    System.out.println("----------receive single");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            lock.unlock();
        }
    }
}
