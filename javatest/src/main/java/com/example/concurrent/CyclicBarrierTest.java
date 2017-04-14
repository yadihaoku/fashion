package com.example.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * Created by YanYadi on 2017/4/10.
 */

public class CyclicBarrierTest {
    /**
     * java �е�դ��������ȫ���߳���ɺ󡣽��лص�
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);

        WorkerTest worker = new WorkerTest(cyclicBarrier);
        worker.start();
        WorkerTest worker1 = new WorkerTest(cyclicBarrier);
        worker1.start();
        TimeUnit.SECONDS.sleep(2);
        System.out.println("Waiting Number=" + cyclicBarrier.getNumberWaiting());

        System.out.println("is Broken" + cyclicBarrier.isBroken());
        /**
         * ���� դ�� ����
         */
        cyclicBarrier.reset();
        System.out.println("is Broken" + cyclicBarrier.isBroken());

        TimeUnit.SECONDS.sleep(2);
        System.out.println("is Broken" + cyclicBarrier.isBroken());

        WorkerTest worker2 = new WorkerTest(cyclicBarrier);
        worker2.start();

        TimeUnit.SECONDS.sleep(1);
        // worker2 ��һֱ��������Ϊ�Ѿ����� CyclicBarrier.reset()
        // ���Ե��� worker2.interrupt() ���� �������߳����ܹ����� CyclicBarrier.await() ����
        worker2.interrupt();

        System.out.println(cyclicBarrier.isBroken());
    }


    static class WorkerTest extends Thread {
        CyclicBarrier cyclicBarrier;

        WorkerTest(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                System.out.println("׼������----");
                System.out.println("=====�ȴ�--Waiting Number=" + cyclicBarrier.getNumberWaiting());
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("---������---");
        }
    }
}
