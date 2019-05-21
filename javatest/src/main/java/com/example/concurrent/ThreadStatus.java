package com.example.concurrent;

import java.util.concurrent.TimeUnit;

/**
 * Created by yanyadi on 2019/5/4.
 */

public class ThreadStatus {

    static volatile boolean lock = false;

    static Object objectLock = new Object();

    public static synchronized void main(String[] args) throws InterruptedException {
        final Thread thread  = new Thread(){
            @Override
            public void run() {
                while(!lock);

                test();

                synchronized (objectLock){
                    try {
                        //出让锁
                        objectLock.wait();

                        //再次出让锁
                        objectLock.wait(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }



            }
        };

        //线程创建成功之后，没有 start 时的状态是，NEW
        System.out.println(thread.getState().name() + "-线程创建成功之后，没有 start 时的状态");

        thread.start();
        Thread.yield();
        sleeep(1);

        //线程启动之后的状态，RUNNABLE
        System.out.println(thread.getState().name() + "-线程启动之后的状态");

        //打开开关，让出cpu时间
        lock = true;
        Thread.yield();
        sleeep(1);
        //线程启动之后, 在锁打开 时的状态，BLOCKED
        System.out.println(thread.getState().name() + "-线程启动之后, 在锁打开 时的状态，尝试获取锁ing,调用 test 方法");


        new Thread(){
            @Override
            public void run() {
                // 睡眠1秒，目的是为了，让上面的线程执行完成 test 方法，走入 同步代码块中。
                sleeep(1000);

                synchronized (objectLock){
                    Thread.yield();
                    sleeep(1);
                    System.out.println(thread.getState().name() + "- 等待获取对象锁时的状态");

                    objectLock.notify();
                }
                Thread.yield();
                sleeep(100);
                synchronized (objectLock){
                    System.out.println(thread.getState().name() + " - 在有限的时间内，等待获取对象锁时的状态");

                    sleeep(1 * 1000);
                    System.out.println(thread.getState().name() + "- 等待获取对象锁超时的状态");
                    objectLock.notify();
                }

                try {
                    thread.join();

                    System.out.println(thread.getState().name() + "-线程执行完毕时的状态");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        System.out.println("Main thread ended.");
    }

    public static synchronized void test(){
        System.out.println(Thread.currentThread().getState().name() + "- 已获取到锁");

    }

    static void sleeep(long time){
        try {
            TimeUnit.MILLISECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
