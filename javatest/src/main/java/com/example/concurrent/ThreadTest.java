package com.example.concurrent;

import java.util.concurrent.TimeUnit;

/**
 * Created by YanYadi on 2017/3/27.
 */
public class ThreadTest {
    public static void main(String[] args) {
        /**
         * �ܽ᣺
         * ���̻߳�ȡ�����󣬿���ʹ�õ��� wait �ͷŸ��������ȴ� ����һ�̡߳� ����notify ����
         * notifyAll ���Ѹ��̣߳�������̣߳�δ�õ�����notify���ú�û��Ч��������ǰ���Ǳ����ڻ����֮�󣬲ſ��Լ���ִ�У���
         *
         *
         */

        final Thread mainThread = Thread.currentThread();

        new Thread(){
            @Override
            public void run() {
                System.out.println("run---");
                synchronized (ThreadTest.class) {
                int i = 0;
                for (; i < 100; i++) {
                    System.out.println("i == " + i);
                    try {
//                            mainThread.interrupt();
                        TimeUnit.SECONDS.sleep(2);
                        ThreadTest.class.notify();
//                            if(i !=99)
                            ThreadTest.class.wait();

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("111111111 ��ȡ�� in main method");
                    }
                    }
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
                System.out.println("----- ��ȡ�� in loop method");
            }
        }
    }
}
