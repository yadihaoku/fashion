package com.example.extend_call;

import java.lang.reflect.Method;

/**
 * Created by YanYadi on 2017/4/5.
 */

public class Main {
    /**
     * 当超类对象引用变量引用子类对象时，被引用对象的类型而不是引用变量的类型决定了调用谁的成员方法，
     * 但是这个被调用的方法必须是在超类中定义过的，也就是说被子类覆盖的方法。这句话对多态进行了一个概括。
     * 其实在继承链中对象方法的调用存在
     * 一个优先级：this.show(O)、super.show(O)、this.show((super)O)、super.show((super)O)。
     *
     * @param args
     */
    public static void main(String[] args) {
        ListView listView = new ListView();
        //子类重写父类的方法后，如果要调用父类方法，只能在 子类内部 进行处理。
        listView.sayHi();

        System.out.println("===========================");
        BaseView baseView = new ListView();
        // 实际调用的是 BaseView.show(BaseView);
        // 因为向下转型，最终调用 ListView.show(BaseView);
        baseView.show(listView);


        System.out.println("===========================");
        // 实际调用的是 ListView.show(BaseView)
        listView.show(baseView);

        System.out.println("===========================");
        // 实际调用 ListView.show(ListView)
        listView.show(listView);

        Method methods[] = ListView.class.getDeclaredMethods();
        for(Method method : methods){
            System.out.println(method.toString());
        }
        //使用 javap -c 反汇编之后，得到
        /**
         * Compiled from "Main.java"
         public class com.example.extend_call.Main {
         public com.example.extend_call.Main();
         Code:
         0: aload_0
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         4: return

         public static void main(java.lang.String[]);
         Code:
         0: new           #2                  // class com/example/extend_call/ListView
         3: dup
         4: invokespecial #3                  // Method com/example/extend_call/ListView."<init>":()V
         7: astore_1
         8: aload_1
         9: invokevirtual #4                  // Method com/example/extend_call/ListView.sayHi:()V
         12: getstatic     #5                  // Field java/lang/System.out:Ljava/io/PrintStream;
         15: ldc           #6                  // String ===========================
         17: invokevirtual #7                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
         20: new           #2                  // class com/example/extend_call/ListView
         23: dup
         24: invokespecial #3                  // Method com/example/extend_call/ListView."<init>":()V
         27: astore_2
         28: aload_2
         29: aload_1
         30: invokevirtual #8                  // Method com/example/extend_call/BaseView.show:(Lcom/example/extend_call/BaseView;)V
         33: getstatic     #5                  // Field java/lang/System.out:Ljava/io/PrintStream;
         36: ldc           #6                  // String ===========================
         38: invokevirtual #7                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
         41: aload_1
         42: aload_2
         43: invokevirtual #9                  // Method com/example/extend_call/ListView.show:(Lcom/example/extend_call/BaseView;)V
         46: getstatic     #5                  // Field java/lang/System.out:Ljava/io/PrintStream;
         49: ldc           #6                  // String ===========================
         51: invokevirtual #7                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
         54: aload_1
         55: aload_1
         56: invokevirtual #10                 // Method com/example/extend_call/ListView.show:(Lcom/example/extend_call/ListView;)V
         59: return
         }

         */
    }
}
