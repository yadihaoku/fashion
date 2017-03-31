package com.example.concurrent;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by YanYadi on 2017/3/29.
 */
public class CountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(4);
//        countDownLatch = null;
        //以读写内存的方式，获取某一变量的内存偏移量
        //在同步方法时，可以做到
//        Unsafe.getUnsafe().objectFieldOffset()

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        executorService.submit(new Runner("One", countDownLatch));
        executorService.submit(new Runner("Two", countDownLatch));
        executorService.submit(new Runner("Three", countDownLatch));
        executorService.submit(new Runner("Four", countDownLatch));
        executorService.shutdown();
    }

    static class Runner implements Runnable {
        String uName;
        CountDownLatch downLatch;

        Runner(String name, CountDownLatch latch) {
            uName = name;
            downLatch = latch;
        }

        @Override
        public void run() {
            try {
                TimeUnit.MILLISECONDS.sleep(new Random().nextInt(5000));
                downLatch.countDown();
                System.out.println("ready!---" + uName);
                if (downLatch != null)
                    downLatch.await();
                System.out.println("======over ---" + uName);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
