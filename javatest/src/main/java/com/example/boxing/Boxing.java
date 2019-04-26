package com.example.boxing;

/**
 * Created by yanyadi on 2019/4/25.
 */

public class Boxing {

    public static void main(String[] args) {


        int maxVal = Integer.MAX_VALUE;

        long startTime = System.currentTimeMillis();
        System.out.println("start");
        long sum = 0L;
        //如果使用 Long 型，在执行加法运算时， i 将自动装箱为 Long ，
        //而使用long 型，将直接参与运算。在我本机上测试，执行效率相差10倍以上。
        //Long sum = 0L;


        for(int i=0 ; i < maxVal; i++)
            sum += i;




        System.out.println("elapsed ms -->" + (System.currentTimeMillis() - startTime) );
        System.out.println(sum);
        System.out.println("end");


    }
}
