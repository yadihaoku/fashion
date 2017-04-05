package com.example.extend_call;

/**
 * Created by YanYadi on 2017/4/5.
 */

public class Main {
    public static void main(String[] args) {
        ListView listView = new ListView();
        listView.sayHi();

        //子类重写父类的方法后，如果要调用父类方法，只能在 子类内部 进行处理。
        //
    }
}
