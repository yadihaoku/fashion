package com.example.regexp;

import java.util.regex.Pattern;

/**
 * Created by YanYadi on 2017/8/14.
 */
public class ValidatePattern {
    public static void main(String[] args) {
        String num = "0.021";
        String abc = "ads0.23";
        String abc123 = "0.123sd";
        Pattern pattern = Pattern.compile("^\\d\\.\\d*$");
        System.out.println(pattern.matcher(num).find());
        System.out.println(pattern.matcher(abc).find());
        System.out.println(pattern.matcher(abc123).find());

    }
}
