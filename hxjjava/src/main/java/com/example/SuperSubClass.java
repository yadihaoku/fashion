package com.example;

/**
 * Created by yanyadi on 2019/4/27.
 */

class SuperClass{
    private int n;
    SuperClass(){
        System.out.println("SuperClass()");
    }
    SuperClass(int n){
        System.out.println("SuperClass(int n)");
        this.n = n;
    }
}
class SubClass extends SuperClass{
    private int n;
    SubClass(){
        System.out.println("Subclass");
    }
    SubClass(int n){
        super(100);
        System.out.println("SuperClass(int n)"+n);
        this.n = n;
    }
}
public class SuperSubClass {
    public static void main(String[] args) {
        SubClass ac1 =new SubClass();
        SubClass ac2 = new SubClass(100);
    }
}
