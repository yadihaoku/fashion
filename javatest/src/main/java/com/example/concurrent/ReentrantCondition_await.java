package com.example.concurrent;

import com.example.utils.DateUtils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by YanYadi on 2017/5/27.
 */
public class ReentrantCondition_await {
    public static void main(String[] args) throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();
        reentrantLock.lock();
        new FirmlyLock(reentrantLock).start();
        System.out.println("--start wait-3s-" + DateUtils.getNowString());
        reentrantLock.newCondition().await(3, TimeUnit.SECONDS);
        System.out.println("--wait over--" + DateUtils.getNowString());

        reentrantLock.unlock();
        System.out.println("game over"+ DateUtils.getNowString());
    }

    static class FirmlyLock extends Thread{
        ReentrantLock lock;
        FirmlyLock(ReentrantLock lock){
            this.lock = lock;
        }

        @Override public void run() {
            System.out.println("--try acquire lock");
            lock.lock();
            System.out.println("--acquire lock--");
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unlock();
        }
    }
}
