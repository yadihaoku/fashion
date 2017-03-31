package com.example.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by YanYadi on 2017/3/29.
 */
public class ReentrantLockTest {
    private volatile static String result;

    /** ReentrantLock ���ø� synchronized �ؼ�����ͬ��
     * synchronized �ؼ��� ���Ļ�ȡ���ͷ�ȫ��ϵͳ�Զ����ơ�
     * ��ʹ�� ReentrantLock ��Ҫ�ֶ���ȡ�����ͷ�����Ҫע�����
     * �ͷ���ʱ��һ��ҪҪ�� finally ���С�������������쳣���ᵼ�������������ͷš�
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        final ReentrantLock reentrantLock = new ReentrantLock();

        WorkerWrite write = new WorkerWrite(reentrantLock);
        write.start();
        // ���߳�����һ�룬��֤ WorkerWrite �߳���������
        TimeUnit.SECONDS.sleep(1);
        new Thread() {
            @Override
            public void run() {
                //��ȡ�߳� ����������ȡ�����󣬴�����
                CountDownLatch countDownLatch = new CountDownLatch(1);
                WorkerRead workerRead = new WorkerRead(reentrantLock, countDownLatch);
                workerRead.start();
                try {
                    System.out.println("�ȴ���ȡ����");
                    System.out.println("-------------------------");
                    System.out.println("result =[" + result + "]");
                    System.out.println("-------------------------");
                    //�ȴ����߳̽���
                    countDownLatch.await();
                    System.out.println("��ȡ����");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    static class WorkerRead extends Thread {
        ReentrantLock mLock;

        CountDownLatch countDownLatch;

        public WorkerRead(ReentrantLock lock, CountDownLatch latch) {
            mLock = lock;
            countDownLatch = latch;
        }

        @Override
        public void run() {
            System.out.println("--��ȡ�߳� Is locked = " + mLock.isLocked());
            System.out.println("--��ȡ�߳� ׼����ȡ��  threadId = " + Thread.currentThread().getId());
            mLock.lock();
            System.out.println("--��ȡ�߳� �Ѿ���ȡ��  threadId = " + Thread.currentThread().getId());
            try {
                System.out.println("-------------------------");
                System.out.println("result =[" + result + "]");
                System.out.println("-------------------------");
            } finally {
                mLock.unlock();
                countDownLatch.countDown();
            }
        }
    }

    static class WorkerWrite extends Thread {
        ReentrantLock mLock;

        public WorkerWrite(ReentrantLock lock) {
            mLock = lock;
        }

        @Override
        public void run() {
            System.out.println("Is locked = " + mLock.isLocked());
            System.out.println("��ȡ��  threadId = " + Thread.currentThread().getId());
            mLock.lock();
            System.out.println("�ѻ�ȡ����  threadId = " + Thread.currentThread().getId());
            final long startTime = System.currentTimeMillis();
            try {
                while (true)
                    //����100����˳�
                    if (System.currentTimeMillis() - startTime > 15_000) {
                        result = "SUCCESS";
                        break;
                    }
            } finally {
                mLock.unlock();
                System.out.println("���ͷ���==");
            }
        }
    }
}
