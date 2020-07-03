package com.wujie.common.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 * 
 * @author wujie
 */
public class DateUtils
{
    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取当前Date型日期
     * 
     * @return Date() 当前日期
     */
    public static Date getNowDate()
    {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     * 
     * @return String
     */
    public static String getDate()
    {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime()
    {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String getTime(String format)
    {
        return dateTimeNow(format);
    }

    public static final String dateTimeNow()
    {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format)
    {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date)
    {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String toYYYY_MM_DD_HH_MM_SS(final Date date)
    {
        return parseDateToStr(YYYY_MM_DD_HH_MM_SS, date);
    }

    public static final String parseDateToStr(final String format, final Date date)
    {
    	if (null == date)
    		return "";
    	
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts)
    {
        try
        {
            return new SimpleDateFormat(format).parse(ts);
        }
        catch (ParseException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath()
    {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime()
    {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }
    

    /**
     * 字符串转换成日期
     * @param time
     * @param pattern
     * @return
     */
	public static Date StrToDate(String time, String pattern)
	{
		if (pattern == null || pattern.equals("")) {
			pattern = "yyyy-MM-dd";
		}
		
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(pattern);
			ParsePosition pos = new ParsePosition(0);
			Date dt1 = formatter.parse(time, pos);
			return dt1;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	/**
	 * 字符串转换成YYYY_MM_DD_HH_MM_SS日期
	 * @param time
	 * @return
	 */
	public static Date parseDate(String time) {
		if (StringUtils.isBlank(time)) {
			return null;
		}
		return StrToDate(time, YYYY_MM_DD);
	}

	/**
	 * 字符串转换成YYYYMMDDHHMMSS日期
	 * @param time
	 * @return
	 */
	public static Date parseYYYYMMDDHHMMSS(String time) {
		return StrToDate(time, YYYYMMDDHHMMSS);
	}

	/**
	 * 返回此日期加上几天后的日期
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date addDays(Date date, int days) {
		if (null == date)
			return null;
		
		Calendar result = Calendar.getInstance();
		result.setTime(date);
		result.add(Calendar.DATE, days);

		return result.getTime();
	}
	
	/**
	 * 返回此日期加上几小时后的日期
	 * @param date
	 * @param hours
	 * @return
	 */
	public static Date addHours(Date date, int hours) {
		if (null == date)
			return null;
		
		Calendar result = Calendar.getInstance();
		result.setTime(date);
		result.add(Calendar.HOUR, hours);

		return result.getTime();
	}

	/**
	 * 两个时间之间相差距离多少天
	 * @param one
	 * @param two
	 * @return
	 */
	public static long getDistanceDays(Date one, Date two) {
		long days = 0;
		try {
			long time1 = one.getTime();
			long time2 = two.getTime();
			long diff;
			if (time1 < time2) {
				diff = time2 - time1;
			} else {
				diff = time1 - time2;
			}
			days = diff / (1000 * 60 * 60 * 24);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return days;
	}

	/**
	 * 两个时间之间相差距离多少小时
	 * @param one
	 * @param two
	 * @return
	 */
	public static long getDistanceHours(Date one, Date two) {
		long hours = 0;
		try {
			long time1 = one.getTime();
			long time2 = two.getTime();
			long diff;
			if (time1 < time2) {
				diff = time2 - time1;
			} else {
				diff = time1 - time2;
			}
			hours = diff / (1000 * 60 * 60);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hours;
	}

	/**
	 * 两个时间之间相差距离多少分钟
	 * @param one
	 * @param two
	 * @return
	 */
	public static long getDistanceMinutes(Date one, Date two) {
		long minutes = 0;
		try {
			long time1 = one.getTime();
			long time2 = two.getTime();
			long diff;
			if (time1 < time2) {
				diff = time2 - time1;
			} else {
				diff = time1 - time2;
			}
			minutes = diff / (1000 * 60);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return minutes;
	}
}
