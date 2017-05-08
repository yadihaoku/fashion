package com.example.extend_call;

/**
 * Created by YanYadi on 2017/4/5.
 */
public class ListView extends BaseView{
    @Override
    public void description() {
        System.out.println("ListView ListView!!!");
    }

    @Override public void show(BaseView baseView) {
        System.out.println("listView and baseView");
    }
     public void show(ListView listView) {
        System.out.println("listView and listView");
    }

}
