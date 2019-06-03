package com.example;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.TimeZone;

public class MyClass {
    public static void main(String[] args) throws UnsupportedEncodingException {


//        System.out.println(TimeUnit.SECONDS.toNanos(20) % 1_000_000);

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


//        assert  string != null;
//
//        System.out.println(factor(243));
//        System.out.println(1e9);
//        System.out.println(1e9F);

//        char yiJing = 0x4DC3;
//        System.out.println(yiJing);
//
//        System.out.println(UUID.randomUUID().toString());
//        System.out.println((int)(1032/179));
//        String s1 = new String("sdfasdf");
//        String s2 = new String("sdfasdf");
//        String s3 = "sdfasdf";
//        String s4 = "sdfasdf";
//        System.out.println(s1.hashCode());
//        System.out.println(s2.hashCode());
//        System.out.println(s3.hashCode());
//        System.out.println(s2==s1);
//        System.out.println(s3==s4);
//        System.out.println(s2==s4);
//        System.out.println(null instanceof String);
//        Temp t1 = new Temp();
//        Temp t2 = new Temp();
//
//        System.out.println(t1 == t2);
//        System.out.println(t1.equals( t2) );

//        String a = "1";
//        System.out.println(byte2hex(a.getBytes("utf-8")))
//        byte b[] =  {12,99};

//        long time = System.currentTimeMillis();
//        final int delayed =(int) Math.max(0,1000L - time);
//        System.out.println(delayed);
//        System.out.println((int)time);
//        System.out.println(1000 - (int)time);

        TimeZone tz = TimeZone.getTimeZone("GMT+8");
        System.out.println(tz.getID());
        System.out.println(tz.getDisplayName());
        System.out.println(Arrays.toString(TimeZone.getAvailableIDs()));
        System.out.println(tz);
    }
    public static byte[] byte2hex1(byte[] bts) { // 二进制转字符串
        byte chars[] = new byte[bts.length * 2];
        int c = 0;
        for (byte b : bts) {
            chars[c++] = HEX_BYTES[b >> 4 & 0xF];
            chars[c++] = HEX_BYTES[b  & 0xF];
        }
        return chars;
    }
    static byte convert(byte b){
        byte r = (byte) (HEX_BYTES[b >> 4 & 0xF] | HEX_BYTES[b  & 0x0F]);
        return r;
    }
    static final char[] HEX_DIGITS =
            { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    static final byte[] HEX_BYTES =
            { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    public static String hex(byte [] data) {
        char[] result = new char[data.length * 2];
        int c = 0;
        for (byte b : data) {
            result[c++] = HEX_DIGITS[(b >> 4) & 0xf];
            result[c++] = HEX_DIGITS[b & 0xf];
        }
        return new String(result);
    }

    public static String byte2hex(byte[] b) { // 二进制转字符串
        StringBuilder hs =  new StringBuilder();
        String stmp = "";
        char chars[] = new char[b.length * 2];
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs.append("0");
            hs.append(stmp);
        }
        return hs.toString();
    }
//    public static String byte2hex(byte[] bts) { // 二进制转字符串
//        char [] result = new char[bts.length * 2];
//        int c = 0;
//        for(byte bt : bts){
//            result[c++] =
//        }
//        char chars[] = new char[b.length * 2];
//        for (int n = 0; n < b.length; n++) {
//            stmp = (Integer.toHexString(b[n] & 0XFF));
//            if (stmp.length() == 1)
//                hs.append("0");
//            hs.append(stmp);
//        }
//        return hs.toString();
//    }

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
