package com.example.garbage;

import java.util.concurrent.TimeUnit;

/**
 * Created by yanyadi on 2019/4/26.
 */

public class FinalizeTest {


    static FinalizeTest OutBean;

    public static void main(String[] args) throws InterruptedException {

        FinalizeTest bean = new FinalizeTest();

        int x = 3;
        if(x > 2)
            bean = null;

//        System.runFinalization();
        System.gc();

        new Thread(){
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(OutBean);


            }
        }.start();

        System.out.println(bean);


    }


    @Override
    protected void finalize() throws Throwable {

        System.out.println("已调用 finalize()");

        OutBean = this;


    }
}
