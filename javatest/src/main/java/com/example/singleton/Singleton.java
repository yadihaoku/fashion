package com.example.singleton;

/**
 * Created by yanyadi on 2019/4/25.
 */

public class Singleton {


    private Singleton() {
        System.out.println("init");
    }


    public static void main(String[] args) {
        System.out.println("run");


        System.out.println(InnerClassSingleton.INSTANCE);
    }


    public static class InnerClassSingleton {


        static {
            System.out.println("load");
        }

        static final Singleton INSTANCE = new Singleton();

    }
}