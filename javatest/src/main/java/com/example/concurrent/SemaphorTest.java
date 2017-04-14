package com.example.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Created by YanYadi on 2017/4/1.
 */
public class SemaphorTest {
    /**
     * Semaphore 信号量，可以控制某个资源同时被访问的个数。
     * 使用 acquire 获取一个信号量，使用 release 释放一个信号量。
     *
     */
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(2);


        ExecutorService executorService = Executors.newScheduledThreadPool(10);
        executorService.execute(new Runner(semaphore, "1"));
        executorService.execute(new Runner(semaphore, "2"));
        executorService.execute(new Runner(semaphore, "3"));
        executorService.execute(new Runner(semaphore, "4"));
        executorService.execute(new Runner(semaphore, "5"));
        executorService.execute(new Runner(semaphore, "7"));
        executorService.execute(new Runner(semaphore, "8"));
        executorService.execute(new Runner(semaphore, "9"));
        executorService.execute(new Runner(semaphore, "10"));
        executorService.execute(new Runner(semaphore, "11"));



        executorService.shutdown();

    }
    static class Runner implements Runnable{
        private Semaphore semaphore;
        private String name;
        Runner(Semaphore semaphore, String n){
            this.semaphore = semaphore;
            this.name = n;

        }

        @Override
        public void run() {
            try {
                System.out.println("---申请许可 --" + this.name);
                semaphore.acquire();
                System.out.println("---已经取到 --" + this.name);
                TimeUnit.SECONDS.sleep(5);
                System.out.println("--- over --" + this.name);
                System.out.println("---- Queue Length ---" + this.semaphore.getQueueLength());
                semaphore.release(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
