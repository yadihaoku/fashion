package com.example.scope;

/**
 * Created by YanYadi on 2017/4/19.
 */

public class ScopeTest {

    String des;
    public static void main(String[] args) {
        // Visibility Package
        System.out.println(Tools.getTools());
        // Visibility Public
        System.out.println(Tools.getTools1());
        // Visibility Private
//        System.out.println(Tools.getTools2());


        Tree appleTree = new Tree();
        appleTree.age = 10;
        System.out.println(appleTree.age);
        System.out.println(appleTree.height);
        appleTree.sayHi();
        /**
         * java 中 private 修饰符，规定 成员变量只能在  class 中使用。
         * 但是对于内部类(静态)，外部类访问它的 private 成员变量时。
         * 编译器，会生成一些特殊的方法。转换为以成员方法的形式，获取成员变量。
         *
         * 比如：
         * System.out.println(appleTree.age);
         * 编译后的形式如下：
         * 使用 javap -c xxx.claxx ;可以反编译代码
         * System.out.println(ScopeTest.Tree.access$100(appleTree));
         *
         * 在静态内部类，会生成一个静态方法 access$100 。
         *
         *
         */
        ScopeTest scopeTest = new ScopeTest();
        scopeTest.des = "asfas";
        scopeTest.test();
    }
    private void test(){
        int age =10;
        DyInner dyInner = new DyInner(){
            public void say(){
                System.out.println(age);
                System.out.println(des);
            }
        };
        dyInner.say();
        System.out.println(dyInner.age);
    }

    public static class Tree{
        int age;
        int height;

        private void sayHi(){
            System.out.println("hello");
        }
    }
    class DyInner{
        int age;
        void say(){
            System.out.println(des);
        }

    }

}
