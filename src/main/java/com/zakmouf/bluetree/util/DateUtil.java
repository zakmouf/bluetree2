package com.zakmouf.bluetree.util;

import java.util.Calendar;
import java.util.Date;

public abstract class DateUtil {

    public static Integer daysBetween(Date date1, Date date2) {
	long diff = date1.getTime() - date2.getTime();
	long oneDay = 24L * 3600L * 1000L;
	return (int) (diff / oneDay);
    }

    public static Date today() {
	Calendar calendar = Calendar.getInstance();
	calendar.set(Calendar.HOUR_OF_DAY, 0);
	calendar.set(Calendar.MINUTE, 0);
	calendar.set(Calendar.SECOND, 0);
	calendar.set(Calendar.MILLISECOND, 0);
	return calendar.getTime();
    }

    public static Date getDate(Integer day, Integer month, Integer year) {
	Calendar calendar = Calendar.getInstance();
	calendar.clear();
	calendar.set(Calendar.DAY_OF_MONTH, day);
	calendar.set(Calendar.MONTH, month);
	calendar.set(Calendar.YEAR, year);
	return calendar.getTime();
    }

    public static Integer getDay(Date date) {
	Calendar calendar = Calendar.getInstance();
	calendar.setTime(date);
	return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static Integer getMonth(Date date) {
	Calendar calendar = Calendar.getInstance();
	calendar.setTime(date);
	return calendar.get(Calendar.MONTH);
    }

    public static Integer getYear(Date date) {
	Calendar calendar = Calendar.getInstance();
	calendar.setTime(date);
	return calendar.get(Calendar.YEAR);
    }

    public static Date addDays(Date date, Integer days) {
	Calendar calendar = Calendar.getInstance();
	calendar.setTime(date);
	calendar.add(Calendar.DAY_OF_MONTH, days);
	return calendar.getTime();
    }

    public static Date addMonths(Date date, Integer months) {
	Calendar calendar = Calendar.getInstance();
	calendar.setTime(date);
	calendar.add(Calendar.MONTH, months);
	return calendar.getTime();
    }

    public static Date addYears(Date date, Integer years) {
	Calendar calendar = Calendar.getInstance();
	calendar.setTime(date);
	calendar.add(Calendar.YEAR, years);
	return calendar.getTime();
    }

}