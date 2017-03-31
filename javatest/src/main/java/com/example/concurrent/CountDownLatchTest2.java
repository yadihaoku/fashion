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
        //�Զ�д�ڴ�ķ�ʽ����ȡĳһ�������ڴ�ƫ����
        //��ͬ������ʱ����������
//        Unsafe.getUnsafe().objectFieldOffset()


        String[] users = {"One", "Two", "Three", "Four", "Five"};
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (String name : users)
            executorService.submit(new Runner(name, countDownLatch, begin));

        executorService.shutdown();

        System.out.println("----MainThread---- Ready --- GO ---");
        //�����������߳�ִ��
        begin.countDown();

        //�÷����������̣߳���ȫ�����߳�ִ����Ϻ󡣸��߳�
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
                System.out.println(uName + "---- ����·��");
                TimeUnit.MILLISECONDS.sleep(new Random().nextInt(5000));
                //�ܲ���ɣ�������1
                if (downLatch != null)
                    downLatch.countDown();
                System.out.println("====== �ѵ����յ� --- by " + uName);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
