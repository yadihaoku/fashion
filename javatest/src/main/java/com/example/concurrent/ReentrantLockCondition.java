package com.example.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by YanYadi on 2017/3/31.
 */
public class ReentrantLockCondition {

    /**
     * ʹ������Э���̣߳���ͬ����ͬ�� StringBuilder ����
     * ��һ���߳�,׷�� ��_���š��ڶ����߳�׷�� ��=�� �ţ�����ִ�� 10 �Ρ�
     * �����õĽ���� _=_=_=_=_=_=_=_=_=_=
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {

        ReentrantLock lock = new ReentrantLock();
        // ���Ͽ��أ���֤�� ��һ���߳�ִ�к󣬵ڶ����̲߳ſ�ʼִ��
        CountDownLatch secondPower = new CountDownLatch(1);
        // �����߳̿��أ��ȴ����й����߳�ִ�����
        CountDownLatch workCount = new CountDownLatch(2);
        StringBuilder stringBuilder = new StringBuilder();

        Condition firstCondition = lock.newCondition();
//        Condition secondCondition = lock.newCondition();
        new Worker(lock, stringBuilder, '_', firstCondition, workCount, secondPower, null).start();
        new Worker(lock, stringBuilder, '=', firstCondition, workCount, null, secondPower).start();

        workCount.await();
        System.out.println("---���й����߳�ִ�н���---");
        System.out.println("result=[" + stringBuilder + "]");

    }

    static class Worker extends Thread {
        public Worker(ReentrantLock lock, StringBuilder stringBuilder, char symbol, Condition condition, CountDownLatch countDownLatch,
                      CountDownLatch otherPower,
                      CountDownLatch selfLock
        ) {
            this.mLock = lock;
            this.stringBuilder = stringBuilder;
            this.symbol = symbol;
            this.condition = condition;
            this.workerDownLatch = countDownLatch;
            this.secondPower = otherPower;
            this.selfLock = selfLock;
        }

        private ReentrantLock mLock;

        private StringBuilder stringBuilder;
        private char symbol;
        private Condition condition;
        private CountDownLatch workerDownLatch;
        private CountDownLatch secondPower;
        private CountDownLatch selfLock;

        @Override
        public void run() {
            System.out.println("--- �����߳̿�ʼִ��");
            /**
             * ������Լ��Ŀ���
             * ���߳���Ҫ�ȴ�һ���źţ����ܼ���ִ��
             */
            if (selfLock != null) {
                try {
                    System.out.println("��Ҫ�ȴ����˿���===waiting");
                    selfLock.await();
                    Thread.currentThread().holdsLock()
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("-----׼����ȡ��");
            mLock.lock();
            System.out.println("-----�ѻ�ȡ����");
            try {

                /**
                 * �ؼ������̵߳Ŀ���
                 */
                if (secondPower != null) {
                    System.out.println("--- �򿪿���");
                    secondPower.countDown();
                }
                int i = 0;
                for (; i < 10; i++) {
                    System.out.println("׷��һ��");
                    stringBuilder.append(symbol);
                    try {

                        System.out.println("���ѵȴ��߳�");
                        condition.signal();
                        System.out.println("�ͷ���");
                        condition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("------׷��ִ�н���-(���������߳�)----symbol=" + symbol);
                condition.signal();

            } finally {
                workerDownLatch.countDown();
                mLock.unlock();

            }
        }
    }
}
