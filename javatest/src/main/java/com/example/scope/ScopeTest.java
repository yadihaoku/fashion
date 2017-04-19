package com.example.scope;

/**
 * Created by YanYadi on 2017/4/19.
 */

public class ScopeTest {

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
         * 但是对于内部类，访问基 private 成员变量时。
         * 编译器，会生成一些特殊的方法。转换为以成员方法的形式，获取成员变量。
         *
         * 比如：
         * System.out.println(appleTree.age);
         * 编译后的形式如下：
         * System.out.println(ScopeTest.Tree.access$100(appleTree));
         *
         */
    }

    public static class Tree{
        private int age;
        private int height;

        private void sayHi(){
            System.out.println("hello");
        }
    }

}
