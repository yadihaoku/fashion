package com.example.timer;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by YanYadi on 2017/5/24.
 */
public class TimerTest {

    public static void main(String[] args) throws InterruptedException {
        Timer timer = new Timer("clearOrderStorage-Task",true);
        ClearTask task = new ClearTask();
        timer.schedule( task,10_000, 2_000);
        Thread.currentThread().sleep(30_000);
        timer.cancel();;
    }
    static class ClearTask extends TimerTask{

        @Override public void run() {
            System.out.println("clear---");
        }
    }
}
