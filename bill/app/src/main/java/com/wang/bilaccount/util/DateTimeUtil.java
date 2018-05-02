package com.wang.bilaccount.util;


import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/11/28/028.
 */

public class DateTimeUtil {

    /**
     * String 转换 Date
     *
     * @param str
     * @param format
     * @return
     */
    public static Date string2Date(String str, String format) {
        try {
            return new SimpleDateFormat(format).parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static String stringFromat(String str, String formatOld, String format) {
        String newDate = "";
        if (TextUtils.isEmpty(str) == false) {
            try {
                Date date = new SimpleDateFormat(formatOld).parse(str);
                newDate = date2String(date.getTime(), format);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return newDate;
    }

    /**
     * Date（long） 转换 String
     *
     * @param time
     * @param format
     * @return
     */
    public static String date2String(long time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String s = sdf.format(time);
        return s;
    }


    /**
     * 年份加减
     *
     * @param time
     * @param format
     * @return
     */
    public static String addYear(String time, String format, int amount) {
        Date date = DateTimeUtil.string2Date(time, format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, amount);
        date = calendar.getTime();
        return DateTimeUtil.date2String(date.getTime(), format);

    }

    public static long getDateStr(long today, int Num) {
        Date nowDate = new Date(today);
        Date newDate2 = new Date(nowDate.getTime() - (long) Num * 24 * 60 * 60 * 1000);
        return newDate2.getTime();
    }

    /**
     * 根据提供的年月日获取该月份的第一天
     *
     * @param
     * @return
     * @Description: (这里用一句话描述这个方法的作用)
     * @Author: gyz
     * @Since: 2017-1-9下午2:26:57
     */
    public static long getSupportBeginDayofMonth(Date date) {
        date.getTime();
        Calendar startDate = Calendar.getInstance();
        startDate.setTime(date);
        startDate.set(Calendar.DAY_OF_MONTH, 1);
        startDate.set(Calendar.HOUR_OF_DAY, 0);
        startDate.set(Calendar.MINUTE, 0);
        startDate.set(Calendar.SECOND, 0);
        startDate.set(Calendar.MILLISECOND, 0);
        Date firstDate = startDate.getTime();
        return firstDate.getTime();

    }

    /**
     * 根据提供的年月获取该月份的最后一天
     * @Description: (这里用一句话描述这个方法的作用)
     * @Author: gyz
     * @Since: 2017-1-9下午2:29:38
     * @param date
     * @return
     */
    public static long getSupportEndDayofMonth(Date date) {
        Calendar startDate = Calendar.getInstance();
        startDate.setTime(date);
        startDate.set(Calendar.DAY_OF_MONTH, startDate.getActualMaximum(Calendar.DAY_OF_MONTH));
        startDate.set(Calendar.HOUR_OF_DAY, 23);
        startDate.set(Calendar.MINUTE, 59);
        startDate.set(Calendar.SECOND, 59);
        startDate.set(Calendar.MILLISECOND, 999);
        Date firstDate = startDate.getTime();
        return firstDate.getTime();
    }

    public static long getTimeLong(String time, String format) {
        Date date = null;
        try {
            date = new SimpleDateFormat(format).parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date == null) {
            return 0;
        }
        return date.getTime();
    }
}
