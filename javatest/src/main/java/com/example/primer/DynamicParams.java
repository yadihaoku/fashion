package com.example.primer;

/**
 * Created by YanYadi on 2017/7/31.
 */
public class DynamicParams {

    public static void main(String[] args) {

        test();
    }
    static void test(int ...args){
        System.out.println(args.length);
    }
}
