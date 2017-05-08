package com.example.extend_call;

/**
 * Created by YanYadi on 2017/4/5.
 */
public class BaseView {

    public final void sayHi(){
        System.out.println("from BaseView!  ");
        this.description();
    }

    public void description(){
        System.out.println("I'm BaseView。");
    }

    public void show(BaseView baseView){
        System.out.println("baseView  and  baseView");
    }
}
