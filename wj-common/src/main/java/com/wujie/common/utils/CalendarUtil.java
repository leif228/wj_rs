package com.wujie.common.utils;

import java.text.SimpleDateFormat;
import java.util.*;

public class CalendarUtil {

	public final static String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

	public static String toHumanFormat(Calendar date) {
		String d = "", t = "";
		if (null != date) {
			d = new SimpleDateFormat("yyyy-MM-dd").format(date.getTime());
			t = new SimpleDateFormat("HH:mm").format(date.getTime());
		}

		return d + " " + t;
	}

	/**
	 * 将一个 年年年年月月日日 的字符串转换为日历类
	 * 
	 * @param parameterValue
	 */
	public static Calendar parseYYYYMMDD(String parameterValue) {
		try {
			String format = "yyyyMMdd";
			return parse(parameterValue, format);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	public static Calendar parseYYYYMM(String parameterValue) {
		try {
			String format = "yyyyMM";
			return parse(parameterValue, format);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	public static Calendar parseYYYY_MM(String parameterValue) {
		try {
			String format = "yyyy-MM";
			return parse(parameterValue, format);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	public static Calendar parseYYYY_MM_DD(String parameterValue) {
		try {
			String format = "yyyy-MM-dd";
			return parse(parameterValue, format);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	public static Calendar parseYY_MM_DD(String parameterValue) {
		try {
			String format = "yy-MM-dd";
			return parse(parameterValue, format);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	public static Calendar parseYYYY_MM_DD_HH_MM(String parameterValue) {
		try {
			String format = "yyyy-MM-dd HH:mm";
			return parse(parameterValue, format);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	public static Calendar parseYYYY_MM_DD_HH_MM_SS(String parameterValue) {
		try {
			String format = "yyyy-MM-dd HH:mm:ss";
			return parse(parameterValue, format);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	public static Calendar parseYYYYMMDDHHMMSS(String parameterValue) {
		try {
			String format = "yyyyMMddHHmmss";
			return parse(parameterValue, format);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	public static Calendar parse(String string, String format) {
		if (StringUtils.isEmpty(string)) 
			return null;
		
		if (format == null || format.equals("")) {
			format = "yyyy-MM-dd";
		}
		
		try {			
			Calendar result = Calendar.getInstance();
			Date dt = DateUtils.StrToDate(string, format);
			result.setTime(dt);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	/** 获取前N天 */
	public static Calendar getDay(int n) {
		try {
			Calendar date = getTheDayZero(now());
			date.add(Calendar.DATE, n);
			return date;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	public static String formatDate(Calendar date) {
		return formatDate(date, DEFAULT_PATTERN);
	}
	
	public static String formatDate(Calendar date, String pattern) {
		String format = "";
		try {
			if (null != date && StringUtils.isNotEmpty(pattern)) {
				format = new SimpleDateFormat(pattern).format(date.getTime());
			}
		} catch (Exception e) {
			format = "";
		}
		return format;
	}

	public static String toYYYY_MM(Calendar date) {
		return formatDate(date, "yyyy-MM");
	}

	public static String toYYYY_MM_DD(Calendar date) {
		return formatDate(date, "yyyy-MM-dd");
	}

	public static String toYYYY_MM_DD_HH_MM_SS(Calendar date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	public static String toYYYY_MM_DD_HH_MM(Calendar date) {
		return formatDate(date, "yyyy-MM-dd HH:mm");
	}

	public static String toHH_mm(Calendar date) {
		return formatDate(date, "HH:mm");
	}

	public static String toHH_mm_ss(Calendar date) {
		return formatDate(date, "HH:mm:ss");
	}

	public static String toYYYYMMDD(Calendar date) {
		return formatDate(date, "yyyyMMdd");
	}

	public static String toYYYYMM(Calendar date) {
		return formatDate(date, "yyyyMM");
	}

	public static String toYYYYMMDDHHmmss(Calendar date) {
		return formatDate(date, "yyyyMMddHHmmss");
	}

	public static String toYYYY(Calendar date) {
		return formatDate(date, "yyyy");
	}

	public static Calendar getYesterday() {
		Calendar result = Calendar.getInstance();
		result.add(Calendar.DATE, -1);
		return result;
	}

	/**
	 * 返回此日期的零点整，如2007-3-14 19:00:35 返回 2007-3-14 00:00:00
	 * 
	 * @param date
	 */
	public static Calendar getTheDayZero(Calendar date) {
		if (null == date)
			return null;
			
		Calendar result = (Calendar) date.clone();
		result.set(Calendar.HOUR_OF_DAY, 0);
		result.set(Calendar.MINUTE, 0);
		result.set(Calendar.SECOND, 0);
		result.set(Calendar.MILLISECOND, 0);
		return result;
	}

	public static Calendar getTheMonthZero(Calendar date) {
		if (null == date)
			return null;
		
		Calendar result = (Calendar) date.clone();
		result.set(Calendar.DAY_OF_MONTH, 1);
		result.set(Calendar.HOUR_OF_DAY, 0);
		result.set(Calendar.MINUTE, 0);
		result.set(Calendar.SECOND, 0);
		result.set(Calendar.MILLISECOND, 0);
		return result;
	}

	/**
	 * 返回此日期加上几分钟后的日期
	 * 
	 * @param date
	 * @param minutes
	 */
	public static Calendar addMinutes(Calendar date, int minutes) {
		if (null == date)
			return null;
		
		Calendar result = (Calendar) date.clone();

		result.add(Calendar.MINUTE, minutes);

		return result;
	}

	/**
	 * 返回此日期加上几小时后的日期
	 * 
	 * @param date
	 * @param huors
	 */
	public static Calendar addHours(Calendar date, int huors) {
		if (null == date)
			return null;
		
		Calendar result = (Calendar) date.clone();

		result.add(Calendar.HOUR, huors);

		return result;
	}

	/**
	 * 返回此日期加上几天后的日期
	 * 
	 * @param date
	 * @param days
	 */
	public static Calendar addDays(Calendar date, int days) {
		if (null == date)
			return null;
		
		Calendar result = (Calendar) date.clone();

		result.add(Calendar.DATE, days);

		return result;
	}

	public static Calendar addMonths(Calendar date, int months) {
		if (null == date)
			return null;
		
		Calendar result = (Calendar) date.clone();

		result.add(Calendar.MONTH, months);

		return result;
	}

	/**
	 * 返回此日期的午夜，如2007-3-14 19:00:35 返回 2007-3-14 23:59:59
	 * 
	 * @param date
	 */
	public static Calendar getTheDayMidnight(Calendar date) {
		if (null == date)
			return null;
		
		Calendar result = (Calendar) date.clone();
		result.set(Calendar.HOUR_OF_DAY, 23);
		result.set(Calendar.MINUTE, 59);
		result.set(Calendar.SECOND, 59);
		result.set(Calendar.MILLISECOND, 999);
		return result;
	}

	/**
	 * 返回此日期的设定时间，如2007-3-14 19:00:35 传入date , 4, 59,59,999 返回 2007-3-14
	 * 04:59:59
	 * 
	 * @param date
	 */
	public static Calendar getTheDayByCondition(Calendar date, int hour, int minute, int second, int milisecond) {
		if (null == date)
			return null;
		
		Calendar result = (Calendar) date.clone();
		result.set(Calendar.HOUR_OF_DAY, hour);
		result.set(Calendar.MINUTE, minute);
		result.set(Calendar.SECOND, second);
		result.set(Calendar.MILLISECOND, milisecond);
		return result;
	}

	public static int getYear(Calendar calendar) {
		if (null != calendar) {
			return calendar.get(Calendar.YEAR);
		}
		return -1;
	}

	public static int getMonth(Calendar calendar) {
		if (null != calendar) {
			return calendar.get(Calendar.MONTH) + 1;
		}
		return -1;
	}

	public static int getDay(Calendar calendar) {
		if (null != calendar) {
			return calendar.get(Calendar.DAY_OF_MONTH);
		}
		return -1;
	}

	public static int getHour(Calendar calendar) {
		if (null != calendar) {
			return calendar.get(Calendar.HOUR);
		}
		return -1;
	}

	public static int getMinute(Calendar calendar) {
		if (null != calendar) {
			return calendar.get(Calendar.MINUTE);
		}
		return -1;
	}

	public static int getSecond(Calendar calendar) {
		if (null != calendar) {
			return calendar.get(Calendar.SECOND);
		}
		return -1;
	}

	public static Calendar getOnYmdhms(int year, int month, int day, int hour, int minute, int second) {
		try {
			Calendar result = Calendar.getInstance();

			result.set(Calendar.YEAR, year);
			result.set(Calendar.MONTH, month - 1);
			result.set(Calendar.DAY_OF_MONTH, day);
			result.set(Calendar.HOUR_OF_DAY, hour);
			result.set(Calendar.MINUTE, minute);
			result.set(Calendar.SECOND, second);
			result.set(Calendar.MILLISECOND, 0);

			return result;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	public static int compare(Calendar c1, Calendar c2) {
		try {
			if (c1.before(c2))
				return -1;
			else if (c1.after(c2))
				return 1;
			else
				return 0;
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
	}

	public static Calendar now() {
		return Calendar.getInstance();
	}

	public static String toTimeStampFm(Calendar calendar) {
		return formatDate(calendar, "yyyy-MM-dd HH:mm:ss");
	}

	public static Calendar toCalnedar(Date date) {
		if (null == date)
			return null;
		Calendar result = now();
		result.setTime(date);
		return result;
	}

	// 按日期获取本周日期 format:"yyyy-MM-dd" //
	public static List<String> getWeekByCalendar(String format, Calendar calendar) {
		Date mdate = calendar.getTime();
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		day--;
		if (day == 0)
			day = 7;
		Date fdate;
		List<String> list = new ArrayList<String>();
		Long fTime = mdate.getTime() - day * 24 * 3600000;
		for (int i = 1; i < 5; i++) {
			fdate = new Date();
			fdate.setTime(fTime + (i * 24 * 3600000));
			list.add(new SimpleDateFormat(format).format(fdate));
		}
		return list;
	}

	// 按日期获取后7天日期 format:"yyyy-MM-dd" //
	public static List<String> get7DayByCalendar(String format, Calendar calendar) {
		Date mdate = calendar.getTime();
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		day--;
		if (day == 0)
			day = 7;
		Date fdate;
		List<String> list = new ArrayList<String>();
		Long fTime = mdate.getTime();
		for (int i = 0; i < 7; i++) {
			fdate = new Date();
			fdate.setTime(fTime + (i * 24 * 3600000));
			list.add(new SimpleDateFormat(format).format(fdate));
		}
		return list;
	}

	// 判断两日期之间的秒差
	public static int secondDiff(Calendar calendar1, Calendar calendar2) {
		int secDif = Math.round((calendar1.getTimeInMillis() - calendar2.getTimeInMillis()) / 1000);
		return secDif;
	}

	// 判断两日期之间的天数差
	public static int dayDiff(Calendar calendar1, Calendar calendar2) {
		int days = 0;
		Calendar c1, c2;
		if (calendar1.before(calendar2)) {
			c1 = CalendarUtil.getTheDayZero(calendar1);
			c2 = CalendarUtil.getTheDayZero(calendar2);

			while (c1.before(c2)) {
				days++;
				c1.add(Calendar.DAY_OF_YEAR, 1);
			}

			return days;
		} else {
			c1 = CalendarUtil.getTheDayZero(calendar2);
			c2 = CalendarUtil.getTheDayZero(calendar1);

			while (c1.before(c2)) {
				days++;
				c1.add(Calendar.DAY_OF_YEAR, 1);
			}

			return -1 * days;
		}
	}

	// 判断两日期之间的月差
	public static int monthDiff(Calendar calendar1, Calendar calendar2) {
		int months = 0;
		Calendar c1, c2;
		if (calendar1.before(calendar2)) {
			c1 = CalendarUtil.getTheMonthZero(calendar1);
			c2 = CalendarUtil.getTheMonthZero(calendar2);
		} else {
			c1 = CalendarUtil.getTheMonthZero(calendar2);
			c2 = CalendarUtil.getTheMonthZero(calendar1);
		}
		while (c1.before(c2)) {
			months++;
			c1.add(Calendar.MONTH, 1);
		}
		return months;
	}

	public static Calendar getStartTimeOfDay(long now) {
		String tz = "GMT+8";
		TimeZone curTimeZone = TimeZone.getTimeZone(tz);
		Calendar calendar = Calendar.getInstance(curTimeZone);
		calendar.setTimeInMillis(now);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
	}
	public static Calendar getEndTimeOfDay(long now) {
		String tz = "GMT+8";
		TimeZone curTimeZone = TimeZone.getTimeZone(tz);
		Calendar calendar = Calendar.getInstance(curTimeZone);
		calendar.setTimeInMillis(now);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar;
	}
	public static void main(String[] args) {
		Calendar now = Calendar.getInstance();
		int wNow = now.get(Calendar.DAY_OF_WEEK);
		System.out.println("wNow=" + wNow);

		Calendar d = CalendarUtil.getOnYmdhms(2016, 9, 28, 13, 28, 33);
		System.out.println(CalendarUtil.toHumanFormat(d));
		d = CalendarUtil.getOnYmdhms(2016, 9, 27, 13, 28, 33);
		System.out.println(CalendarUtil.toHumanFormat(d));
		d = CalendarUtil.getOnYmdhms(2016, 9, 26, 13, 28, 33);
		System.out.println(CalendarUtil.toHumanFormat(d));
		d = CalendarUtil.getOnYmdhms(2016, 9, 25, 13, 28, 33);
		System.out.println(CalendarUtil.toHumanFormat(d));
		d = CalendarUtil.getOnYmdhms(2016, 9, 24, 13, 28, 33);
		System.out.println(CalendarUtil.toHumanFormat(d));
		d = CalendarUtil.getOnYmdhms(2016, 9, 23, 13, 28, 33);
		System.out.println(CalendarUtil.toHumanFormat(d));
		d = CalendarUtil.getOnYmdhms(2015, 9, 22, 13, 28, 33);
		System.out.println(CalendarUtil.toHumanFormat(d));
		d = CalendarUtil.getOnYmdhms(2016, 9, 21, 13, 28, 33);
		System.out.println(CalendarUtil.toHumanFormat(d));
	}

}
