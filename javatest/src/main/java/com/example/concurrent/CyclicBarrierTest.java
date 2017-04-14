package com.example.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * Created by YanYadi on 2017/4/10.
 */

public class CyclicBarrierTest {
    /**
     * java 中的栅栏，可在全部线程完成后。进行回调
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
         * 重置 栅栏 计数
         */
        cyclicBarrier.reset();
        System.out.println("is Broken" + cyclicBarrier.isBroken());

        TimeUnit.SECONDS.sleep(2);
        System.out.println("is Broken" + cyclicBarrier.isBroken());

        WorkerTest worker2 = new WorkerTest(cyclicBarrier);
        worker2.start();

        TimeUnit.SECONDS.sleep(1);
        // worker2 会一直阻塞，因为已经调用 CyclicBarrier.reset()
        // 可以调用 worker2.interrupt() 或者 在其它线程中能够调用 CyclicBarrier.await() 方法
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
                System.out.println("准备出发----");
                System.out.println("=====等待--Waiting Number=" + cyclicBarrier.getNumberWaiting());
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("---奔跑中---");
        }
    }
}
