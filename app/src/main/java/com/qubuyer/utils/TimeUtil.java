package com.qubuyer.utils;

import android.text.format.DateUtils;

import com.qubuyer.bean.TimeMode;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
    /**
     * 计算两个时间段相差时间
     *
     * @return
     */
    public static TimeMode parseRemainTime(long diff) {
        long nd = 1000 * 24 * 60 * 60;//一天的毫秒数
        long nh = 1000 * 60 * 60;//一小时的毫秒数
        long nm = 1000 * 60;//一分钟的毫秒数
        long ns = 1000;//一秒钟的毫秒数
        long day = diff / nd;//计算差多少天
        long hour = diff % nd / nh;//计算差多少小时
        long min = diff % nd % nh / nm;//计算差多少分钟
        long sec = diff % nd % nh % nm / ns;//计算差多少秒
        TimeMode timeMode = new TimeMode();
        timeMode.setDay(day);
        timeMode.setHour(hour);
        timeMode.setMinute(min);
        timeMode.setSecond(sec);
        return timeMode;
    }

    /**
     * 获取：昨天 今天 明天 后天
     */
    public static String getChineseDay(Date date) {
        if (DateUtils.isToday(date.getTime())) return "今天";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        if (DateUtils.isToday(calendar.getTime().getTime())) return "昨天";
        calendar.add(Calendar.DATE, -2);
        if (DateUtils.isToday(calendar.getTime().getTime())) return "明天";
        calendar.add(Calendar.DATE, -1);
        if (DateUtils.isToday(calendar.getTime().getTime())) return "后天";
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        switch (week) {
            case 7:
                return "周一";
            case 1:
                return "周二";
            case 2:
                return "周三";
            case 3:
                return "周四";
            case 4:
                return "周五";
            case 5:
                return "周六";
            case 6:
                return "周日";
        }
        return "";
    }


    /**
     * 方法描述:获取某天，-1是昨天，0是今天，1是明天依次类推
     *
     * @param i 格式M月dd日这样的话显示的就是3月12日，否则 MM月dd日格式会选择03月12日
     * @return
     */
    public static String getMoutianDay(int i) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM月dd日");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String dateString = "";
        try {
            calendar.add(Calendar.DATE, i);
            date = calendar.getTime();
            dateString = formatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateString;
    }
}
