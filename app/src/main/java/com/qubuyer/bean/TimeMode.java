package com.qubuyer.bean;

import java.io.Serializable;

/**
 * @date 创建时间:2019/1/3
 * @author Susong
 * @description 时间模型
 & @version
 */
public class TimeMode implements Serializable {
    private long year;
    private long month;
    private long day;
    private long hour;
    private long minute;
    private long second;

    public long getYear() {
        return year;
    }

    public void setYear(long year) {
        this.year = year;
    }

    public long getMonth() {
        return month;
    }

    public void setMonth(long month) {
        this.month = month;
    }

    public long getDay() {
        return day;
    }

    public void setDay(long day) {
        this.day = day;
    }

    public long getHour() {
        return hour;
    }

    public void setHour(long hour) {
        this.hour = hour;
    }

    public long getMinute() {
        return minute;
    }

    public void setMinute(long minute) {
        this.minute = minute;
    }

    public long getSecond() {
        return second;
    }

    public void setSecond(long second) {
        this.second = second;
    }
}
