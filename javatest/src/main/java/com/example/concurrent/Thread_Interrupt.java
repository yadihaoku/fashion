package com.example.concurrent;

/**
 * Created by YanYadi on 2017/3/29.
 */
public class Thread_Interrupt {
    public static void main(String[] args) {
        /**
         * java �е��жϣ�ֻ������ boolean ��־λ������ж� interrupt ���������ú�
         * ֻ��ʹ�� Thread.interrupted() ���������øñ�־λ��
         * ���߳����еĹ����У����Ҫ�˳���ǰ�̣߳����Լ����ñ�־λ��
         */
        System.out.println("is interrupted---" + Thread.currentThread().isInterrupted());
        System.out.println("---- �����ж� interrupt() ----");
        Thread.currentThread().interrupt();
        System.out.println("---- is interrupted ---" + Thread.currentThread().isInterrupted());
        System.out.println("1-----����ж� Thread.interrupted() ----  " + Thread.interrupted());
        System.out.println("2-----����ж� Thread.interrupted() ----  " + Thread.interrupted());

        System.out.println("is interrupted---" + Thread.currentThread().isInterrupted());
    }
}
