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

//        Pattern pattern = Pattern.compile("video", Pattern.MULTILINE |Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

//        System.out.println(pattern.matcher(string).find());
//        System.out.println(string);
//        assert  string != null;
//
//        System.out.println(factor(243));
//        System.out.println(1e9);
//        System.out.println(1e9F);

        String s1 = new String("sdfasdf");
        String s2 = new String("sdfasdf");
        String s3 = "sdfasdf";
        String s4 = "sdfasdf";
        System.out.println(s1.hashCode());
        System.out.println(s2.hashCode());
        System.out.println(s3.hashCode());
        System.out.println(s2==s1);
        System.out.println(s3==s4);
        System.out.println(s2==s4);
//        System.out.println(null instanceof String);
//        Temp t1 = new Temp();
//        Temp t2 = new Temp();
//
//        System.out.println(t1 == t2);
//        System.out.println(t1.equals( t2) );

    }

    static class Temp{
        @Override public int hashCode() {
            return 1;
        }

        @Override public boolean equals(Object obj) {
            return true;
        }
    }
    private static String factor(int n) {
        for (int i = 2; i < n; i++) {
            int x = n / i;
            if (x * i == n) return factor(x) + " × " + i;
        }
        return Integer.toString(n);
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
