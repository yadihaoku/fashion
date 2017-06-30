package com.example.primer;

/**
 * Created by YanYadi on 2017/6/30.
 */
public class Extend {
    static class A{
        public void say(){
            System.out.println("A say");
        }
    }
    static class B extends A{
        @Override public void say() {
            System.out.println("B say");
        }
    }

    public static void main(String[] args) {
        B b = new B();
        b.say();
        A a = b;
        a.say();
    }
}

