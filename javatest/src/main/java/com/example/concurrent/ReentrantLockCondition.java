package com.example.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by YanYadi on 2017/3/31.
 */
public class ReentrantLockCondition {

    /**
     * 使用两个协作线程，共同操作同个 StringBuilder 对象。
     * 第一个线程,追加 “_”号。第二个线程追加 “=” 号，各种执行 10 次。
     * 期望得的结果是 _=_=_=_=_=_=_=_=_=_=
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {

        ReentrantLock lock = new ReentrantLock();
        // 加上开关，保证在 第一个线程执行后，第二个线程才开始执行
        CountDownLatch secondPower = new CountDownLatch(1);
        // 工作线程开关，等待所有工作线程执行完毕
        CountDownLatch workCount = new CountDownLatch(2);
        StringBuilder stringBuilder = new StringBuilder();

        Condition firstCondition = lock.newCondition();
//        Condition secondCondition = lock.newCondition();
        new Worker(lock, stringBuilder, '_', firstCondition, workCount, secondPower, null).start();
        new Worker(lock, stringBuilder, '=', firstCondition, workCount, null, secondPower).start();

        workCount.await();
        System.out.println("---所有工作线程执行结束---");
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
            System.out.println("--- 工作线程开始执行");
            /**
             * 有如果自己的开关
             * 该线程需要等待一个信号，才能继续执行
             */
            if (selfLock != null) {
                try {
                    System.out.println("需要等待别人开锁===waiting");
                    selfLock.await();
                    Thread.currentThread().holdsLock()
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("-----准备获取锁");
            mLock.lock();
            System.out.println("-----已获取到锁");
            try {

                /**
                 * 控件其他线程的开关
                 */
                if (secondPower != null) {
                    System.out.println("--- 打开开关");
                    secondPower.countDown();
                }
                int i = 0;
                for (; i < 10; i++) {
                    System.out.println("追加一次");
                    stringBuilder.append(symbol);
                    try {

                        System.out.println("唤醒等待线程");
                        condition.signal();
                        System.out.println("释放锁");
                        condition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("------追加执行结束-(唤醒其它线程)----symbol=" + symbol);
                condition.signal();

            } finally {
                workerDownLatch.countDown();
                mLock.unlock();

            }
        }
    }
}
