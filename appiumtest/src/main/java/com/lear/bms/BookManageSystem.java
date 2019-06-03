package com.lear.bms;

import com.lear.bean.Book;

import java.util.Scanner;

/**
 * Created by YanYadi on 2017/12/30.
 */
public class BookManageSystem {

    public static final int OP_EXIT = 3;
    public static final int OP_BOOK_MANAGE = 1;
    public static final int OP_BURRO_MANAGE = 2;
    static    Scanner scanner;

    public static void main(String[] args) {
        header();
        scanner = new Scanner(System.in);

        int op;
        Logic:
        while (true){

            mainMenu();
            op = readOperate();
            switch (op){
                case OP_BOOK_MANAGE:
                    bookManageMenu();
                    break;
                case OP_BURRO_MANAGE:

                    break;
                default:
                    break Logic;
            }

        }
        System.out.println("已退出");
    }
    static void header(){
        System.out.println("-------------------------------------------------");
        System.out.println("-------------------------------------------------");
        System.out.println("--------  图  书  管   理   系   统  ------------");
        System.out.println("-------------------------------------------------");
        System.out.println("-------------------------------------------------");
    }

    static void mainMenu(){
        System.out.println("\n1.图书管理\n2.租借管理\n3.退出系统");
    }

    static int readOperate(){
        System.out.print("请选择你的操作：");
        final int op = scanner.nextInt();
        return op;
    }

    static void bookManageMenu(){
        System.out.println("\n1.新书入库\n2.图书修改\n3.库存预览\n4.返回");
        switch (readOperate()){
            case 1:
                newBookInput();
                break;
            case 2:

                break;
            case 3:

                break;
            default:
                return;
        }
    }

    static void newBookInput(){


        Book book1 =new Book("给宝宝看病","978-7-5391-5372-8");
        //获取书名
        System.out.println(book1.getBookName());


//        char a = 'a';
//        String A = "abcdfd";

    }

}
