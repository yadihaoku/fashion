package com.example.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by YanYadi on 2017/4/12.
 */

public class Utils {

    public static void main(String[] args) {
        C c = new C();
        B b = new B();
        b.des = "ass";

        Map map = obj2Map(c);
        Map map1 = obj2Map(b);
        System.out.println(map);
        System.out.println(map1);
    }

    static class A {
        private String name = "A";
        private int age;
        private transient long crateTime;

    }

    static class B extends A {
        private String name = "B";
        private String des = "from B";
        private static final int height = 10;

    }

    static class C extends B {
        private int age = 10;
        private String hello = "hello";
    }

    public static Map obj2Map(Object obj) {
        List<Field> fields = getAllField(obj.getClass());
        Map map = new HashMap();
        try {
            for (Field field : fields) {
                final int mod = field.getModifiers();
                if ((Modifier.TRANSIENT & mod) != 0 || (Modifier.STATIC & mod) != 0) continue;
                String fName = field.getName();
                field.setAccessible(true);
                map.put(fName, field.get(obj));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return map;
    }

    private static List<Field> getAllField(Class clazz) {
        List<Field> fields = new ArrayList<>();
        if (clazz.getSuperclass() != null) {
            fields.addAll(getAllField(clazz.getSuperclass()));
        }
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        return fields;
    }
}
