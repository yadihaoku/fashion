package com.example.concurrent;

import java.util.concurrent.TimeUnit;

import static com.example.concurrent.ClassicSynchronized.Timeout.head;

/**
 * Created by YanYadi on 2017/6/21.
 */
public class ClassicSynchronized {
    //空闲等待时间
    private static final long IDLE_TIMEOUT_MILLIS = TimeUnit.SECONDS.toMillis(20);
    private static final long IDLE_TIMEOUT_NANOS = TimeUnit.MILLISECONDS.toNanos(IDLE_TIMEOUT_MILLIS);

    public static void main(String[] args) {

        Timeout timeout10 = new Timeout(SimplePrintTimeOut.newIns("wait 10s"));
        Timeout timeout16 = new Timeout(SimplePrintTimeOut.newIns("wait 16s"));
        Timeout timeout5 = new Timeout(SimplePrintTimeOut.newIns("wait 5s"));
        Timeout timeout51 = new Timeout(SimplePrintTimeOut.newIns("wait 5s"));
        Timeout timeout9 = new Timeout(SimplePrintTimeOut.newIns("wait 9s"));

        scheduleTimeout(timeout10, 10_000);
        scheduleTimeout(timeout16, 16_000);
        scheduleTimeout(timeout9, 9_000);
        scheduleTimeout(timeout5, 5_000);
        scheduleTimeout(timeout51, 5_000);
    }

    static Timeout awaitTenSecond() throws InterruptedException {
        Timeout node = head.next;
        //为空，等待一段时间
        if (node == null) {
            final long waitNanos = System.nanoTime();
            Timeout.class.wait(IDLE_TIMEOUT_MILLIS,1);

            final Timeout realNode = (System.nanoTime() - waitNanos) >= IDLE_TIMEOUT_NANOS && head.next == null ?
                    head : null;
            System.out.println("========== wait nanos = "+  (System.nanoTime() - waitNanos));
            System.out.println("========== head.next "+  head.next);
            System.out.println("========== node=null -> realNode = " + realNode + " waitTimeout "+((System.nanoTime() - waitNanos) >= IDLE_TIMEOUT_NANOS)+"  is head =" +(realNode==head));
            return realNode;
        }
        final long waitNanos = node.remainingNanos(System.nanoTime());
        //未超时，进入等待
        if (waitNanos > 0) {
            long waitMillis = TimeUnit.NANOSECONDS.toMillis(waitNanos);
            int  realWaitNanos = (int) (waitNanos % 1_000_000);
            System.out.println("==== wait nanos = " + waitNanos);
            System.out.println("==== start waiting   millis="+ waitMillis + "  nanos=" + realWaitNanos);
            Timeout.class.wait(waitMillis, realWaitNanos);
            return null;
        }
        System.out.println("= remove node ");
        head.next = node.next;
        node.next = null;
        return node;
    }

    /**
     * 开始监听超时操作
     *
     * @param node    超时节点
     * @param timeout 超时时间，毫秒数
     */
    static void scheduleTimeout(Timeout node, long timeout) {
        synchronized (Timeout.class) {
            if (head == null) {
                head = new Timeout();
                new WatchDog().start();
            }
            final long now = System.nanoTime();
            node.timeOutAt = now + TimeUnit.MILLISECONDS.toNanos(timeout);

            for (Timeout prev = head; true; prev = prev.next) {
                if (prev.next == null || node.timeOutAt < prev.next.timeOutAt) {
                    node.next = prev.next;
                    prev.next = node;
                    if (prev == head) {
                        Timeout.class.notifyAll();
                    }
                    break;
                }
            }
        }
    }

    interface TimeOutCallback {
        void onTimeout();
    }

    static class WatchDog extends Thread {
        @Override public void run() {
            while (true) {
                try {
                    Timeout timeout = null;
                    synchronized (Timeout.class) {
                        timeout = awaitTenSecond();

                        //返回空，进行下一轮轮询
                        if (timeout == null) continue;
                        //queue 是 empty . now return
                        if (timeout == head) {
                            head = null;
                            return;
                        }
                    }
                    timeout.timeout();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Timeout {

        Timeout(TimeOutCallback callback){
            mCallback = callback;
        }
        Timeout(){}

        static Timeout head;
        Timeout next;
        private long timeOutAt;

        private TimeOutCallback mCallback;

        /**
         * 剩余等待时间
         *
         * @param now
         * @return
         */
        long remainingNanos(long now) {
            return timeOutAt - now;
        }

        void timeout() {
            if (mCallback != null)
                mCallback.onTimeout();
            System.out.println("timeout ... destroy resource.  timeoutAt = " + timeOutAt);
        }
    }

    static class SimplePrintTimeOut implements TimeOutCallback {

        private String string;

        private SimplePrintTimeOut(String s) {
            string = s;
        }

        static TimeOutCallback newIns(String s) {
            return new SimplePrintTimeOut(s);
        }

        @Override public void onTimeout() {
            System.out.println("timeout: " + string);
        }
    }
}
