package com.example.concurrent;

/**
 * Created by YanYadi on 2017/3/29.
 */
public class Thread_Interrupt {
    public static void main(String[] args) {
        /**
         * java 中的中断，只是设置 boolean 标志位。如果中断 interrupt 方法被调用后。
         * 只能使用 Thread.interrupted() 方法，重置该标志位。
         * 在线程运行的过程中，如果要退出当前线程，可以监听该标志位。
         */
        System.out.println("is interrupted---" + Thread.currentThread().isInterrupted());
        System.out.println("---- 调用中断 interrupt() ----");
        Thread.currentThread().interrupt();
        System.out.println("---- is interrupted ---" + Thread.currentThread().isInterrupted());
        System.out.println("1-----清除中断 Thread.interrupted() ----  " + Thread.interrupted());
        System.out.println("2-----清除中断 Thread.interrupted() ----  " + Thread.interrupted());

        System.out.println("is interrupted---" + Thread.currentThread().isInterrupted());
    }
}
