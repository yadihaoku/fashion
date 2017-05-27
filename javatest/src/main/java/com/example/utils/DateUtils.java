package com.example.utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;


/**
 * @author sailor
 */
public class DateUtils {
    /**
     * 15分之前
     */
    public static final int STATUS_QUARTER_BEFORE = 1;
    /**
     * 今天
     */
    public static final int STATUS_TODAY = 2;
    /**
     * 昨天
     */
    public static final int STATUS_YESTERDAY = 3;
    /**
     * 其他日期
     */
    public static final int STATUS_OTHER_DATE = 4;
    public static final int FIFTEEN_MINUTES = 1000 * 60 * 15;

    // 默认日期格式
    public static final String[] parsePatterns = new String[]{"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "HH:mm", "yyyy-MM-dd HH:mm", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd", "yyyyMMdd",
            "MM月dd日 HH:mm", "yyyyMMddHHmmssSSS", "yyyy年MM月dd日 HH:mm", "MM-dd HH:mm"
            // 这里可以增加更多的日期格式，用得多的放在前面
    };

    /**
     * 根据当前给定的日期获取当前天是星期几(中国版的)
     *
     * @param date 任意时间
     * @return
     */
    public static String getChineseWeek(Date date) {
        final String dayNames[] = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar c = getCalendar(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        return dayNames[dayOfWeek - 1];

    }

    /**
     * @param date 格式 2011-5-12
     * @return 星期
     */
    public static String getWeek(String date) {
        Date dat = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dat = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return getChineseWeek(dat);
    }

    /**
     * 【由date类型获取字符串类型】
     *
     * @param date
     * @param parsePattern :转换后的格式，如"yyyy-MM-dd" 或"yyyy年MM月dd日"
     * @return
     */
    public static String getStringByDate(Date date, String parsePattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(parsePattern);
        String dateStr = dateFormat.format(date);
        return dateStr;
    }

    /**
     * @param date
     * @return
     * @description 【获取当前日期日历】
     */
    public static Calendar getCalendar(Date date) {
        if (date == null)
            return null;
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        return c;
    }

    /**
     * 清空日历的时间部分，只留日期
     * <p/>
     * <p/>
     * 2015年12月11日
     *
     * @param c
     * @return void 返回类型
     */
    public static void clearTime(Calendar c) {
        c.clear(Calendar.MILLISECOND);
        c.clear(Calendar.MINUTE);
        c.clear(Calendar.SECOND);
        c.set(Calendar.HOUR_OF_DAY, 0);
    }

    /**
     * 获取当天的日期  yyyy-MM-dd
     *
     * @return String    返回类型
     */
    public static String getToday() {
        return formatDate(Calendar.getInstance());
    }

    /**
     * 获取当天的日期  yyyy-MM-dd  00:00:00
     *
     * @return String    返回类型
     */
    public static Calendar getTodayCalendar() {
        return clearHMS(Calendar.getInstance());
    }

    public static Calendar clearHMS(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    /**
     * 从指定的日期，复制并增加或减少指定的天数
     *
     * @param base     指定的日期
     * @param dayCount 天数，可以为负数
     * @return
     */
    public static Calendar jumpDate(Calendar base, int dayCount) {

        Calendar tmpCalendar = (Calendar) base.clone();
        tmpCalendar.add(Calendar.DATE, dayCount);
        return tmpCalendar;
    }

    /**
     * 返回今天的最后一秒的时间
     * @return
     */
    public static Calendar getTodayLastTime(){
        Calendar  tmpCalendar =  jumpDate(getTodayCalendar(), 1);
        tmpCalendar.add(Calendar.SECOND, -1);
        return tmpCalendar;
    }
    /**
     * 从指定的日期，复制并增加或减少指定的天数
     *
     * @param base     指定的日期
     * @param discount 天数，可以为负数
     * @return
     */
    public static Calendar jumpDate(Calendar base,int type, int discount) {

        Calendar tmpCalendar = (Calendar) base.clone();
        tmpCalendar.add(type, discount);
        return tmpCalendar;
    }

    /**
     * 获取当天的日期  yyyy-MM-dd  00:00:00
     *
     * @return String    返回类型
     */
    public static Calendar toCalendar(int year, int month, int date, int hour, int minute, int second) {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.YEAR, year);
        today.set(Calendar.MONTH, month);
        today.set(Calendar.DATE, date);
        today.set(Calendar.HOUR_OF_DAY, hour);
        today.set(Calendar.MINUTE, minute);
        today.set(Calendar.SECOND, second);
        today.set(Calendar.MILLISECOND, 0);
        return today;
    }

    /**
     * 获取当天的日期  yyyy-MM-dd  00:00:00
     *
     * @return String    返回类型
     */
    public static Calendar toCalendar(int year, int month, int date) {

        return toCalendar(year, month, date, 0, 0, 0);
    }

    /**
     * 获取当前时间
     * <p/>
     * <p/>
     * 2015年12月7日
     *
     * @return Calendar 返回类型
     */
    public static Calendar getNow() {
        return Calendar.getInstance(Locale.getDefault());
    }

    /**
     * 返回当前时间 yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getNowString() {
        return formatDateParam(Calendar.getInstance());
    }

    public static String formatDate(Calendar e) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(e.getTime());
    }

    public static String formatDateParam(Calendar date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date.getTime());
    }

    public static String getTimeByTimeZone(TimeZone tz) {
        Calendar ca = Calendar.getInstance(tz);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return sdf.format(ca.getTime());
    }

    /**
     * 获取当前，所属月份的第一天
     * <p/>
     * <p/>
     * 2015年12月7日
     *
     * @return Calendar 返回类型
     */
    public static Calendar getMonthFirstDay() {
        Calendar now = getTodayCalendar();
        int day = now.get(Calendar.DAY_OF_MONTH) - 1;
        now.add(Calendar.DATE, day * -1);
        return now;
    }

    /**
     * 获取当前，所属月份的第一天
     * <p/>
     * <p/>
     * 2015年12月7日
     *
     * @return Calendar 返回类型
     */
    public static Calendar getBeforeSevenDay() {
        Calendar now = getTodayCalendar();
        now.add(Calendar.DATE, -7);
        return now;
    }

    /**
     * 获取当前，所属周的第一天
     * <p/>
     * <p/>
     * 2015年12月7日
     *
     * @return Calendar 返回类型
     */
    public static Calendar getWeekFirstDay() {
        Calendar now = getTodayCalendar();
        now.add(Calendar.DATE, getWeekFirstDayIncrement(now.get(Calendar.DAY_OF_WEEK)) * -1);
        return now;
    }

    /**
     * 格式化日期为 12月-12日
     * <p/>
     * <p/>
     * 2015年12月8日
     *
     * @param c
     * @return String 返回类型
     */
    public static String formatDateMD_CN(Calendar c) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
        return sdf.format(c.getTime());
    }

    /**
     * 格式化日期为 11-12
     * <p/>
     * <p/>
     * 2015年12月8日
     *
     * @param c
     * @return String 返回类型
     */
    public static String formatDateMD(Calendar c) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        return sdf.format(c.getTime());
    }

    /**
     * 2015年12月7日
     *
     * @param dayOfWeek
     * @return int 返回类型
     */
    static int getWeekFirstDayIncrement(int dayOfWeek) {
        switch (dayOfWeek) {
            case Calendar.SUNDAY:
                return 6;
            case Calendar.SATURDAY:
                return 5;
            case Calendar.FRIDAY:
                return 4;
            case Calendar.THURSDAY:
                return 3;
            case Calendar.WEDNESDAY:
                return 2;
            case Calendar.TUESDAY:
                return 1;
            default:
                return 0;
        }
    }

    /**
     * 【获取时间间隔分钟】
     *
     * @param minDate
     * @param maxDate
     * @return
     */
    public static double getIntervalMinutes(long minDate, long maxDate) {
        double mins = (maxDate - minDate) * 1.0 / 60;
        return mins;
    }

    /**
     * @param minDate
     * @param maxDate
     * @return
     * @description 【获取时间间隔分钟】
     */
    public static double getIntervalMinutes(Date minDate, Date maxDate) {
        double days = 0.0;
        if (minDate == null || maxDate == null) {
            return days;
        }
        try {
            long interval = maxDate.getTime() - minDate.getTime();
            days = Double.valueOf(interval) / 1000 / 60;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return days;
    }

    /**
     * @param minDate
     * @param maxDate
     * @return
     * @description 【获取时间间隔小时】
     */
    public static double getIntervalHours(Date minDate, Date maxDate) {
        double days = 0.0;
        if (minDate == null || maxDate == null) {
            return days;
        }
        try {
            days = getIntervalMinutes(minDate, maxDate) / 60;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return days;
    }

    /**
     * 校验是否隔夜
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static boolean checkIsIntervalDay(Date startDate, Date endDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        int firstDay = c.get(Calendar.DAY_OF_MONTH);
        c.setTime(endDate);
        int lastDay = c.get(Calendar.DAY_OF_MONTH);
        return firstDay == lastDay ? true : false;
    }

    /**
     * 校验是否隔年
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static boolean checkIsIntervalYear(Date startTime, Date endTime) {
        Calendar c = Calendar.getInstance();
        c.setTime(startTime);
        int startYear = c.get(Calendar.YEAR);
        c.setTime(endTime);
        int endYear = c.get(Calendar.YEAR);
        return endYear - startYear > 0;
    }

    /**
     * 获取间隔时间戳
     *
     * @return
     */
    public static int getIntervalDays(Date minDate, Date maxDate) {
        Calendar minC = getCalendar(minDate);
        Calendar maxC = getCalendar(maxDate);
        double offer = maxC.getTimeInMillis() - minC.getTimeInMillis();
        return (int) round(offer / 1000 / 60 / 60 / 24, 0, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 获取时间间隔年
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int getIntervalYears(Date date1, Date date2) {
        Calendar firstCal = Calendar.getInstance();
        Calendar secondCal = Calendar.getInstance();

        firstCal.setTime(date1);
        secondCal.setTime(date2);

        int year = firstCal.get(Calendar.YEAR) - secondCal.get(Calendar.YEAR);
        int month = firstCal.get(Calendar.MONTH) - secondCal.get(Calendar.MONTH);
        int day = firstCal.get(Calendar.DAY_OF_MONTH) - secondCal.get(Calendar.DAY_OF_MONTH);

        // 计算实际年份月份天数差
        year = year - ((month > 0) ? 0 : ((month < 0) ? 1 : ((day >= 0 ? 0 : 1))));
        month = (month < 0) ? (day > 0 ? 12 + month : 12 + month - 1) : (day >= 0 ? month : month - 1);

        firstCal.add(Calendar.MONTH, -1);
        day = (day < 0) ? (perMonthDays(firstCal)) + day : day;
        int timeStr = year;
        return timeStr;

    }

    // 判断一个时间所在月有多少天
    public static int perMonthDays(Calendar cal) {
        int maxDays = 0;
        int month = cal.get(Calendar.MONTH);
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.MARCH:
            case Calendar.MAY:
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.OCTOBER:
            case Calendar.DECEMBER:
                maxDays = 31;
                break;
            case Calendar.APRIL:
            case Calendar.JUNE:
            case Calendar.SEPTEMBER:
            case Calendar.NOVEMBER:
                maxDays = 30;
                break;
            case Calendar.FEBRUARY:
                if (isLeap(cal.get(Calendar.YEAR))) {
                    maxDays = 29;
                } else {
                    maxDays = 28;
                }
                break;
        }
        return maxDays;
    }

    // 判断某年是否是闰年
    public static boolean isLeap(int year) {
        boolean leap = false;
        if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
            leap = true;
        }
        return leap;
    }

    /**
     * 获得给定时间戳对应的时间
     *
     * @param timeLong
     * @return
     */
    public static String getTimeStringByLong(long timeLong) {
        String result = "";
        try {
            Date curDate = new Date();
            Date pDate = getCalendarByLong(timeLong).getTime();

            if (checkIsIntervalDay(pDate, curDate)) {
                result = "今天 " + getStringByDate(pDate, parsePatterns[2]);
            } else {
                result = getStringByDate(pDate, parsePatterns[3]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @param dateString yyyy-MM-dd
     * @return int[]    返回类型
     * @throws
     * @Title: getDateDistance
     */
    public static int[] getDateDistance(String dateString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date date = simpleDateFormat.parse(dateString);
            return getDateDistance(getCalendar(date).getTimeInMillis());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param dateString yyyy-MM-dd
     * @return int[]    返回类型
     * @throws
     * @Title: getDateDistance
     */
    public static int[] getDateDistanceSecond(String dateString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            Date date = simpleDateFormat.parse(dateString);
            return getDateDistance(getCalendar(date).getTimeInMillis());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getyyyyMMddData(String dateString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            Date date = simpleDateFormat.parse(dateString);
            return new SimpleDateFormat("yyyy-MM-dd").format(date);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return "";
    }

    public static String getMMddDate(String dateString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            Date date = simpleDateFormat.parse(dateString);
            return new SimpleDateFormat("MM月dd日").format(date);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return "";
    }

    public static String getMMddDateStyle(String dateString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            Date date = simpleDateFormat.parse(dateString);
            return new SimpleDateFormat("MM-dd").format(date);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return "";
    }

    public static String getHHmmDataStyle(String dateString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = simpleDateFormat.parse(dateString);
            return new SimpleDateFormat("HH:mm").format(date);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return "";
    }

    /**
     * 把指定格式的字符串，转换为 Calendar
     * <p/>
     * <p/>
     * 2015年12月7日
     *
     * @param dateString
     * @param pattern    可以为空，默认为  yyyy-MM-dd
     * @return Calendar 返回类型
     */
    public static Calendar parse(String dateString, String pattern) {
        if (pattern == null)
            pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        try {
            Date date = simpleDateFormat.parse(dateString);
            Calendar calendar = Calendar.getInstance(Locale.getDefault());
            calendar.setTime(date);
            return calendar;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 把指定格式的字符串，转换为 Calendar
     */
    public static Calendar parse(String dateString) {
        return parse(dateString, parsePatterns[0]);
    }


    /**
     * 返回指定时间，与当前时间的间隔时间 <br />
     * 索引0 可能的值为下：<Br />
     * <pre>
     *     {@link DateUtils#STATUS_QUARTER_BEFORE} 15分钟之前<br />
     *     {@link DateUtils#STATUS_TODAY}          今天 <br />
     *     {@link DateUtils#STATUS_YESTERDAY}      昨天 <br />
     *     {@link DateUtils#STATUS_OTHER_DATE}     其他日期  <br />
     *     </pre>
     * <p/>
     * <p/>
     * 2015年12月8日
     *
     * @param date
     * @return int[] 返回类型
     */
    public static int[] getDateDistance(long date) {
        int[] result = new int[5];
        long distance = System.currentTimeMillis() - date;
        if (FIFTEEN_MINUTES > distance) {
            result[0] = STATUS_QUARTER_BEFORE;
            if (distance > 0)
                result[1] = (int) (distance / 60 / 1000);
            else {
                result[1] = 0;
                result[2] = -1;
            }
            return result;
        }
        Calendar now = Calendar.getInstance(Locale.getDefault());
        Calendar fuckDay = (Calendar) now.clone();
        fuckDay.setTimeInMillis(date);

        int month = fuckDay.get(Calendar.MONTH);
        int day = fuckDay.get(Calendar.DAY_OF_MONTH);
        int hour = fuckDay.get(Calendar.HOUR_OF_DAY);
        int minute = fuckDay.get(Calendar.MINUTE);
        int year = fuckDay.get(Calendar.YEAR);
        result[3] = hour;
        result[4] = minute;

        if (year == now.get(Calendar.YEAR) && month == now.get(Calendar.MONTH) && day == now.get(Calendar.DAY_OF_MONTH)) {
            result[0] = STATUS_TODAY;
            return result;
        }
        now.add(Calendar.DATE, -1);
        if (year == now.get(Calendar.YEAR) && month == now.get(Calendar.MONTH) && day == now.get(Calendar.DAY_OF_MONTH)) {
            result[0] = STATUS_YESTERDAY;
            return result;
        }
        result[0] = STATUS_OTHER_DATE;
        result[1] = month + 1;
        result[2] = day;
        return result;
    }

    /**
     * 比较两个时期，是否是同一天
     * <p/>
     * 2015年12月14日
     *
     * @param s
     * @param e
     * @return boolean 返回类型
     */
    public static boolean isSameDay(Calendar s, Calendar e) {
        return s.get(Calendar.YEAR) == e.get(Calendar.YEAR) && s.get(Calendar.MONTH) == e.get(Calendar.MONTH) && s.get(Calendar.DATE) == e.get(Calendar.DATE);
    }

    /**
     * 格式化订单时间
     *
     * @param timeLong
     * @return
     */
    public static String formatOrderTime(long timeLong) {
        String result = "";
        try {
            Date pDate = getCalendarByLong(timeLong).getTime();

            result = getStringByDate(pDate, parsePatterns[3]);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 将long类型转换为Calendar类型
     *
     * @param timeLong
     * @return
     */
    public static Calendar getCalendarByLong(long timeLong) {
        Calendar c = null;
        try {
            c = Calendar.getInstance();
            c.setTimeInMillis(timeLong);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }

    public static double round(double value, int scale, int roundingMode) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(scale, roundingMode);
        double d = bd.doubleValue();
        bd = null;
        return d;
    }

    /**
     * 获取和timestamp差dayOffset天的格式化后的时间
     *
     * @param timestamp
     * @param formatStr
     * @param dayOffset
     * @return
     */
    public static String getCalendarByDayOffset(long timestamp, String formatStr, int dayOffset) {
        Calendar c = getCalendarByLong(timestamp);
        c.add(Calendar.DAY_OF_MONTH, dayOffset);
        return new SimpleDateFormat(formatStr).format(c.getTime());
    }

}
