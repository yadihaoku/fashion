package com.example.concurrent;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by YanYadi on 2017/3/29.
 */
public class CountDownLatchTest2 {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        CountDownLatch begin = new CountDownLatch(1);
//        countDownLatch = null;
        //以读写内存的方式，获取某一变量的内存偏移量
        //在同步方法时，可以做到
//        Unsafe.getUnsafe().objectFieldOffset()


        String[] users = {"One", "Two", "Three", "Four", "Five"};
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (String name : users)
            executorService.submit(new Runner(name, countDownLatch, begin));

        executorService.shutdown();

        System.out.println("----MainThread---- Ready --- GO ---");
        //打开锁，放子线程执行
        begin.countDown();

        //该方法会阻塞线程，当全部子线程执行完毕后。该线程
        countDownLatch.await();
        System.out.println("----MainThread---- OVER         ---");
    }

    static class Runner implements Runnable {
        String uName;
        CountDownLatch downLatch;
        CountDownLatch beginLatch;

        Runner(String name, CountDownLatch latch, CountDownLatch begin) {
            uName = name;
            downLatch = latch;
            beginLatch = begin;
        }

        @Override
        public void run() {
            try {
                beginLatch.await();
                System.out.println(uName + "---- 正在路上");
                TimeUnit.MILLISECONDS.sleep(new Random().nextInt(5000));
                //跑步完成，记数减1
                if (downLatch != null)
                    downLatch.countDown();
                System.out.println("====== 已到达终点 --- by " + uName);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
