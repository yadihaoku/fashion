package com.example.designPattern;

/**
 * Created by yanyadi on 2017/4/29.
 */

public class Prototype implements Cloneable {
    private String name;
    private int age;
    private Object object = new Object();

    public static void main(String[] args) {

        Prototype prototype = new Prototype();
        prototype.age  = 10 ;
        prototype.name = "YanMuHe";

        /**
         * clone 底层为native方法，实际是一种浅copy。
         * 值类型的字段，会进行复制。而引用类型的字段，还是持有的原引用。
         *
         * 实现深copy，有两种方式可选择。
         * 1. 使用 serializable 序列化该对象。
         * 2. clone 每一个引用对象。
         */
        Prototype p2 = (Prototype) prototype.clone();

        p2.age = 100;

        prototype.test();
        p2.test();


    }

    private void test(){
        System.out.println(String.format("{age : %d, name :  %s} ", age,name));
        System.out.println("hash="+ object.hashCode());
    }

    @Override
    public Object clone() {
        try {

            return super.clone();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
