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
        System.out.println("���˳�");
    }
    static void header(){
        System.out.println("-------------------------------------------------");
        System.out.println("-------------------------------------------------");
        System.out.println("--------  ͼ  ��  ��   ��   ϵ   ͳ  ------------");
        System.out.println("-------------------------------------------------");
        System.out.println("-------------------------------------------------");
    }

    static void mainMenu(){
        System.out.println("\n1.ͼ�����\n2.������\n3.�˳�ϵͳ");
    }

    static int readOperate(){
        System.out.print("��ѡ����Ĳ�����");
        final int op = scanner.nextInt();
        return op;
    }

    static void bookManageMenu(){
        System.out.println("\n1.�������\n2.ͼ���޸�\n3.���Ԥ��\n4.����");
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


        Book book1 =new Book("����������","978-7-5391-5372-8");
        //��ȡ����
        System.out.println(book1.getBookName());


//        char a = 'a';
//        String A = "abcdfd";

    }

}
