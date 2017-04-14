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
     * Semaphore �ź��������Կ���ĳ����Դͬʱ�����ʵĸ�����
     * ʹ�� acquire ��ȡһ���ź�����ʹ�� release �ͷ�һ���ź�����
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
                System.out.println("---������� --" + this.name);
                semaphore.acquire();
                System.out.println("---�Ѿ�ȡ�� --" + this.name);
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
