package com.example;

public class MyClass {
    public static void main(String[] args) {


//        List<? extends Time> dates = new ArrayList<Time>();
//        dates.get(0);
//
//        List<? extends Date> list2 =  dates;
//
//        List<?  super Date> dates1 = new ArrayList<>();
//        dates1.get(0);
//        dates1.add(new Date(1L));
//
//        dates1.add(new Date(1L));
//        dates1.add(new Time(1L));
//
//        List<? super B> list = new ArrayList<B>();
//        list = new ArrayList<A>();
//        list.add(new D());
//        list.add(new B());
//        char one = (char) ('һ');
//        System.out.println((char) (one + 2));
//        System.out.println((char) 0x4e02);

//        System.out.println( new MyClass().num2Voice(1110558));
        System.out.println((int)2.4);
        System.out.println((int)2.8);
    }

      String num2Voice(int number) {

        StringBuilder numStrings = new StringBuilder();
        String numbers = Integer.toString(number);
        for (int i = 0; i < numbers.length(); i++) {
            char tmp = numbers.charAt(i);
            numStrings.append(getNumStr(tmp) );
            if(tmp !='0')
                numStrings.append(getNumUnit(numbers.length()-i-1) );
        }

        return numStrings.toString();

    }

    String getNumUnit(int len) {
        String[] units = {"", "ʮ", "��", "ǧ", "��","ʮ","��","ǧ"};
        return units[len];
    }

    String getNumStr(char ch) {
        final int i = ch - '1' +1;
        switch (i) {

            case 1:
                return "һ";
            case 2:
                return "��";
            case 3:
                return "��";
            case 4:
                return "��";
            case 5:
                return "��";
            case 6:
                return "��";
            case 7:
                return "��";
            case 8:
                return "��";
            case 9:
                return "��";


        }
        return "";
    }


    static class A {
        public void a() {
        }
    }

    static class B extends A {
        public void b() {
        }

        ;
    }

    static class C extends A {
        public void c() {
        }
    }

    static class D extends B {
        public void d() {
        }
    }
}
