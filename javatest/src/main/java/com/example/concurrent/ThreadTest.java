package com.example.concurrent;

import java.util.concurrent.TimeUnit;

/**
 * Created by YanYadi on 2017/3/27.
 */
public class ThreadTest {
    public static void main(String[] args) {
        /**
         * 总结：
         * 在线程获取到锁后，可以使用调用 wait 释放该锁，并等待 【另一线程】 调用notify 或者
         * notifyAll 唤醒该线程（如果该线程，未得到锁，notify调用后没有效果。所以前提是必须在获得锁之后，才可以继续执行）。
         *
         *
         */

       final Thread mainThread = Thread.currentThread();

        new Thread(){
            @Override
            public void run() {
                System.out.println("run---");
//                synchronized (ThreadTest.class) {
                    int i = 0;
                    for (; i < 100; i++) {
                        System.out.println("i == " + i);
                        try {
//                            mainThread.interrupt();
                            TimeUnit.SECONDS.sleep(2);
                            ThreadTest.class.notify();
//                            if(i !=99)
//                            ThreadTest.class.wait();

                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("111111111 获取锁 in main method");
                        }
//                    }
                }
            }
        }.start();

        loop();
    }

    public synchronized static void loop() {

            int j = 0;
            for (; j < 100; j++) {
                System.out.println("j === " + j);
                try {
                    System.out.println("waiting");
                    ThreadTest.class.notify();
                    if(j != 99)
                    ThreadTest.class.wait(1000);
                    System.out.println("waited");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("----- 获取锁 in loop method");
                }
        }
    }
}
