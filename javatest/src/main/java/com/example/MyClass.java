package com.example;

import java.util.regex.Pattern;

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
//        char one = (char) ('一');
//        System.out.println((char) (one + 2));
//        System.out.println((char) 0x4e02);

//        System.out.println( new MyClass().num2Voice(1110558));

//       boolean b = false;
//        b |=true;
//        b |=true;
//        b |=false;
//        System.out.println(b);
//
//        System.out.println(10/3);
//        System.out.println(3/2);
//        tryFinally();

//        System.out.println(String.format ("%s","asdfsadf"));

        String string = "var flashvars={f:'http://video.ums.uc.cn/video/wemedia/004ac28b851d436eaf5f646703b2bd0c/259fae061fde098f3f0cb3aeb763ab56-85386432-2.mp4?auth_key=1492150155-0-0-69f545c9f4288eb17f9dad3b9ebc4435',c:0,loaded:'loadedHandler',p:1};\t\t\n" +
                "\t\tvar params={bgcolor:'#FFF',allowFullScreen:true,allowScriptAccess:'always',wmode:'transparent'};\n" +
                "\t\tCKobject.embedSWF('https://vs.6no.cc/player/player.swf','a1','ckplayer_a1','100%','460px',flashvars,params);\n" +
                "\t\tvar video=['http://video.ums.uc.cn/video/wemedia/004ac28b851d436eaf5f646703b2bd0c/259fae061fde098f3f0cb3aeb763ab56-85386432-2.mp4?auth_key=1492150155-0-0-69f545c9f4288eb17f9dad3b9ebc4435'];\n" +
                "\t\t\tvar support=['iPad','iPhone','ios','android+false','msie10+false'];\n" +
                "\t\tCKobject.embedHTML5('a1','ckplayer_a1','100%','230px',video,flashvars,support);";

        Pattern pattern = Pattern.compile("video", Pattern.MULTILINE |Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        System.out.println(pattern.matcher(string).find());
        System.out.println(string);

    }

    static void tryFinally(){
        if(3 >2)
            throw new RuntimeException("error");
        try {

            System.out.println("in try block");
        }finally {
            System.out.println("in finally block");
        }
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
        String[] units = {"", "十", "百", "千", "万","十","百","千"};
        return units[len];
    }

    String getNumStr(char ch) {
        final int i = ch - '1' +1;
        switch (i) {

            case 1:
                return "一";
            case 2:
                return "二";
            case 3:
                return "三";
            case 4:
                return "四";
            case 5:
                return "五";
            case 6:
                return "六";
            case 7:
                return "七";
            case 8:
                return "八";
            case 9:
                return "九";


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
