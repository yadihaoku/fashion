package com.example.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by YanYadi on 2017/4/12.
 */

public class LockSupportTest {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("park");
        WorkThread thread = new WorkThread();
        thread.start();
        TimeUnit.SECONDS.sleep(5);

        LockSupport.unpark(thread);
        System.out.println("--OK--");
    }

    static class WorkThread extends Thread{
        @Override
        public void run() {
            System.out.println("等待授权");
            LockSupport.park();
            System.out.println("取得授权");
        }
    }


}
