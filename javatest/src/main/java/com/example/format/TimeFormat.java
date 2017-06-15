package com.example.format;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by YanYadi on 2017/6/13.
 */
public class TimeFormat {
    public static void main(String[] args) throws ParseException {
        String date = "2017-06-13T03:06:37.613Z";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        System.out.println(localSimpleDateFormat.format(simpleDateFormat.parse(date)));
    }
}
