package com.nineclient.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class DateUtil {
	public static SimpleDateFormat DATE_AND_TIME_FORMATER = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat sdf = new SimpleDateFormat();
	public static SimpleDateFormat YEAR_MONTH_DAY_FORMATER = new SimpleDateFormat(
			"yyyy-MM-dd");
	public static SimpleDateFormat NEW_YEAR_MONTH_DAY_FORMATER = new SimpleDateFormat(
			"yyyyMMdd");

	public static SimpleDateFormat MONTH_DAY_FORMATER = new SimpleDateFormat(
			"MM月dd日");

	public static SimpleDateFormat YEAR_MONTH_FORMATER = new SimpleDateFormat(
			"yyyy年MM月");

	public static SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy/MM/dd");

	/**
	 * 日期 yyyy/MM/dd
	 * 
	 * @param date
	 * @return
	 */
	public static String getYYYYMMDD(Date date) {
		if (date == null)
			return null;
		return FORMAT.format(date);
	}

	/**
	 * @param date
	 *            yyyy-MM-dd HH:mm:ss
	 * @return Date
	 */
	public static Date getDateTime(String date) {
		if (date == null)
			return null;
		try {
			return DATE_AND_TIME_FORMATER.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static Date getDateTime(String date, SimpleDateFormat format) {
		if (date == null)
			return null;
		try {
			return format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 格式转换 获取年月日日期 yyyy/MM/DD
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDateTimeYYYYMMDD(Date date) {
		if (date == null)
			return null;
		String time = FORMAT.format(date);
		return getDateTime(time);
	}

	/**
	 * 获取前一天 获取年月日日期 yyyy/MM/DD
	 * 
	 * @param date
	 * @return
	 */
	public static Date getBeforDateTimeYYYYMMDD(Date date) {
		if (date == null)
			return null;
		long t = date.getTime() - 1000 * 24 * 60 * 60;
		Date newDate = new Date(t);
		String time = FORMAT.format(newDate);
		return getDateTime(time);
	}

	/**
	 * @param date
	 *            日期
	 * @param format
	 *            需要转成的格式
	 * @return
	 */
	public static Date getDateTimeYYYYMMDD(Date date, SimpleDateFormat format) {
		if (date == null)
			return null;
		String time = format.format(date);
		return getDateTime(time, format);
	}

	
	//求上一个小时的时间
	public static Calendar getDateOfLastHour(Calendar date) {  
	    Calendar lastDate = (Calendar) date.clone();  
	    
	    lastDate.add(Calendar.HOUR, -1);  // 
	   
	    return lastDate;  
	}
	
	//求上一个天的当天时间
	public static Calendar getDateOfLastDay(Calendar date) {  
	    Calendar lastDate = (Calendar) date.clone();  
	  
	    lastDate.add(Calendar.DATE, -1);   
	    return lastDate;  
	}
	
	//返回上一个月的日期，格式为yyyy-MM  string 类型
	public static String getLastMonth(Calendar date,int num) {  
		SimpleDateFormat d = new SimpleDateFormat("yyyy-MM");
	    Calendar lastDate = (Calendar) date.clone();  
	    lastDate.add(Calendar.MONTH, (0-num));  //上一个小时
	 
	    return d.format(lastDate.getTime());
	}
	
	//求上一个天的当天时间 格式   yyyy-MM-dd
	public static String getStrOfLastDay(Calendar date) {  
		 SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		Calendar lastDate = (Calendar) date.clone();  
	  
	    lastDate.add(Calendar.DATE, -1);  
	    String dstr = s.format(lastDate.getTime());
	    return dstr;  
	}
	//求上一个的月当天时间
	public static Calendar getDateOfLastMonth(Calendar date) {  
	    Calendar lastDate = (Calendar) date.clone();  
	    lastDate.add(Calendar.MONTH, -1);  //上一个月
	
	    return lastDate;  
	}
	//求上一个月的月份 返回  string格式yyyy-MM
	public static String getDateOfLastMonthStr(Calendar date) {  
		SimpleDateFormat d = new SimpleDateFormat("yyyy-MM");
	    Calendar lastDate = (Calendar) date.clone();  
	    lastDate.add(Calendar.MONTH, -1);  //上一个月
	    return d.format(lastDate.getTime());  
	}
	//求上一个天的时间 返回  string格式yyyy-MM-dd
	public static String getDateOfLastDayStr(Calendar date) {  
		SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");
	    Calendar lastDate = (Calendar) date.clone();  
	    lastDate.add(Calendar.DATE, -1);  //上一个月
	    return d.format(lastDate.getTime());  
	}
	//获得上一个小时的时间 返回 string 格式 yyyy-MM-dd HH
	public static String getDateOfLastHourStr(Calendar date) {  
		SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH");
	    Calendar lastDate = (Calendar) date.clone();  
	    lastDate.add(Calendar.HOUR, -1);  //上一个小时
	    return d.format(lastDate.getTime());  
	}
	//获取传来日期的小时string 格式 yyyy-MM-dd HH
	public static String getHourStrOfDate(Date date){
		SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd HH");
		return ss.format(date);
	}
	
	//获取传来日期的小时string 格式 yyyy-MM-dd HH
	public static String getStrOfDate(Date date){
		SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd");
		return ss.format(date);
	}

	//获取传来日期的小时string 格式 yyyy-MM
	public static String getMonthStrOfDate(Date date){
		SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM");
		return ss.format(date);
	}
	/**
	 * 获取下一天的 日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date getNextDAYDateYYYYMMDD(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_YEAR, 1);
		return c.getTime();
	}

	/**
	 * 获取当前年份的最后一月的第一天 yyyy-MM-01 00:00:00
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastMonthFirstDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int year = c.YEAR;
		Calendar cNew = Calendar.getInstance();
		Date nowDate = new Date();
		cNew.setTime(nowDate);
		String yyyyMMdd = "";
		if (year < cNew.YEAR) {
			yyyyMMdd = year + "-" + "12-01 00:00:00";
		} else {
			yyyyMMdd = cNew.get(cNew.YEAR) + "-" + cNew.get(cNew.MONTH) + "-"
					+ "01 00:00:00";
		}
		Date dat = new Date();
		try {
			dat = DATE_AND_TIME_FORMATER.parse(yyyyMMdd);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dat;
	}

	/**
	 * 获取下一个月的日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date getNextMONTHDateYYYYMMDD(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, 1);
		return c.getTime();
	}

	/**
	 * 字符串转换成Timestamp
	 * 
	 * @param date
	 *            日期字符串 格式为：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static Timestamp dateStringToTimestamp(String date) {
		try {
			return new Timestamp(DATE_AND_TIME_FORMATER.parse(date).getTime());
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 将字符串转化成想要的Timestamp格式
	 * 
	 * @param date
	 *            日期字符串
	 * @param pattern
	 *            想要的格式
	 * @return
	 */

	public static Timestamp dateStringToTimestamp(String date, String pattern) {
		try {
			return new Timestamp(new SimpleDateFormat(pattern).parse(date)
					.getTime());
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 格式化日期为yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 *            要格式化的日期
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String formatDateAndTime(Date date) {
		return DATE_AND_TIME_FORMATER.format(date);
	}

	/**
	 * 格式化日期为MM月dd日
	 * 
	 * @param date
	 *            要格式化的日期
	 * @return MM月dd日
	 */
	public static String formatMonthDay(Date date) {
		return MONTH_DAY_FORMATER.format(date);
	}

	/**
	 * 格式化日期为yyyy-MM-dd
	 * 
	 * @param date
	 *            要格式化的日期
	 * @return yyyy-MM-dd
	 */
	public static String formatYearMonthDay(Date date) {
		return YEAR_MONTH_DAY_FORMATER.format(date);
	}

	/**
	 * 得到某一天的日期字符串（按不同的日期格式）
	 * 
	 * @param num
	 *            与当前日期相差的天数 例如：-1 得到的是昨天的日期 1得到的是明天的日期
	 * @param formatType
	 *            日期的格式：1-'yyyyMMdd' 2-'YYYY-MM-DD'
	 * @return 某一天的日期字符串
	 */
	public static String getYearMonthDay(int num, int formatType) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, num);
		String dateString = null;
		switch (formatType) {
		case 1:
			dateString = NEW_YEAR_MONTH_DAY_FORMATER.format(cal.getTime());
			break;
		case 2:
			dateString = YEAR_MONTH_DAY_FORMATER.format(cal.getTime());
			break;
		default:
			throw null;
		}
		return dateString;
	}

	/**
	 * 获取指定天数前的日期的格式化字符串
	 * 
	 * @param days
	 *            指定天数
	 * @return yyyy-MM-dd
	 */
	public static String dayOfBefore(Integer days) {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		c.add(Calendar.DAY_OF_YEAR, -(days));
		return formatter.format(c.getTime());
	}
	
	public static String dayOfBefore(Date date,Integer days) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		c.add(Calendar.DAY_OF_YEAR, -(days));
		return formatter.format(c.getTime());
	}

	public static String dayOfAfter(Integer days) {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		c.add(Calendar.DAY_OF_YEAR, days);
		return formatter.format(c.getTime());
	}

	/**
	 * 获取当天的日期： 日期格式如下 2009-04-11 00：00：00
	 * 
	 * @return 指定的日期字符串
	 */
	public static String getToday(SimpleDateFormat simpleDateFormat) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return simpleDateFormat.format(calendar.getTime());
	}

	/**
	 * 获取当天的日期： 日期格式如下 2009-04-11 23:59:59
	 * 
	 * @return 指定的日期字符串
	 */
	public static String getTodays(SimpleDateFormat simpleDateFormat) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return simpleDateFormat.format(calendar.getTime());
	}

	/**
	 * 获取前几周的时间 日期格式如下 2009-04-11 23:59:59
	 */
	public static String getTodayByWeek(int count) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR)
				- dayOfWeek + 7 * count + 1);
		return DATE_AND_TIME_FORMATER.format(calendar.getTime());
	}

	/**
	 * 获取当前日期下月份的第一天
	 * 
	 * @param date
	 *            日期
	 * @return 指定的日期字符串
	 */
	public static String getFirstDayOfCurrentMonth(
			SimpleDateFormat simpleDateFormat) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return simpleDateFormat.format(calendar.getTime());
	}

	/**
	 * 获取当前日期下当前周的第一天日期 “星期一”算是一周的第一天
	 * 
	 * @return 指定的日期字符串
	 */
	public static String getFirstDayOfCurrentWeek(
			SimpleDateFormat simpleDateFormat) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return simpleDateFormat.format(calendar.getTime());
	}

	/**
	 * 获取离当前时间多少天以前的日期 如参数为30： 则返回30天之前的日期时间
	 * 
	 * @param day
	 *            指定的天数
	 * @return 指定的日期字符串
	 */
	public static String getBeforeDays(int day,
			SimpleDateFormat simpleDateFormat) {
		Calendar today = Calendar.getInstance();
		today.add(Calendar.DAY_OF_YEAR, -day);
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		return simpleDateFormat.format(today.getTime());
	}

	/**
	 * 获取本年的第一天日期
	 * 
	 * @param simpleDateFormat
	 * @return
	 */
	public static String getFirstDayOfCurrentYear(
			SimpleDateFormat simpleDateFormat) {
		return getFirstDayOfBeforeYear(0, simpleDateFormat);
	}

	/**
	 * 获取某一年的第一天日期
	 * 
	 * @param year
	 *            与本年相差的年数
	 * @param simpleDateFormat
	 * @return
	 */
	public static String getFirstDayOfBeforeYear(int year,
			SimpleDateFormat simpleDateFormat) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, -year);
		calendar.set(Calendar.DAY_OF_YEAR, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return simpleDateFormat.format(calendar.getTime());
	}

	/**
	 * 获取当前日期下月份的第一天
	 * 
	 * @param date
	 *            日期格式字符串
	 * @return 获取当前日期下月份的第一天 格式如下 2009-04-01
	 */
	public static String getFirstMonthDayOfCurrentDay(String date,
			SimpleDateFormat simpleDateFormat) {
		Date d = null;
		Calendar calendar = new GregorianCalendar();
		try {
			d = simpleDateFormat.parse(date);
			calendar.setTime(d);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return simpleDateFormat.format(calendar.getTime());
	}

	/**
	 * 获取当前日期下月份的最后一天
	 * 
	 * @param date
	 *            日期格式字符串
	 * @return 获取当前日期下月份的最后一天 格式如下 2009-04-31
	 */
	public static String getLastMonthDayOfCurrentDay(String date,
			SimpleDateFormat simpleDateFormat) {
		Date d = null;
		Calendar calendar = new GregorianCalendar();
		try {
			d = simpleDateFormat.parse(date);
			calendar.setTime(d);
			calendar.set(Calendar.DATE, 1);
			calendar.roll(Calendar.DATE, -1);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return simpleDateFormat.format(calendar.getTime());
	}

	// 判断时间date1是否在时间date2之前
	// 时间格式 2005-4-21 16:16:34
	public static boolean isDateBefore(String date1, String date2) {
		try {
			DateFormat df = DateFormat.getDateTimeInstance();
			return df.parse(date1).before(df.parse(date2));
		} catch (ParseException e) {
			System.out.print("[SYS] " + e.getMessage());
			return false;
		}
	}

	// 判断当前时间是否在时间date2之前
	// 时间格式 2005-4-21 16:16:34
	public static boolean isDateBefore(String date2) {
		try {
			Date date1 = new Date();
			DateFormat df = DateFormat.getDateTimeInstance();

			if (date2.indexOf(":") > 0) {
				return date1.before(df.parse(date2));
			} else {
				return date1.before(df.parse(date2 + " 00:00:00"));
			}

		} catch (ParseException e) {
			System.out.print("[SYS] " + e.getMessage());
			return false;
		}
	}

	public static synchronized Date parseDateFormat(final String strDate) {
		final String pattern = "yyyy-MM-dd HH:mm:ss";
		return parseDateFormat(strDate, pattern);
	}

	public static synchronized Date parseDateFormat(final String strDate,
			final String pattern) {
		synchronized (sdf) {
			Date date = null;
			sdf.applyPattern(pattern);
			try {
				date = sdf.parse(strDate);
			} catch (final Exception e) {
			}
			return date;
		}
	}

	public static synchronized String getDateFormat(final java.util.Date date,
			final String pattern) {
		synchronized (sdf) {
			if (null == date) {
				return "";
			}
			String str = null;
			sdf.applyPattern(pattern);
			str = sdf.format(date);
			return str;
		}
	}

	public static String getFormat(final String strDate, String pattern) {

		return getFormat(strDate, "yyyy-MM-dd HH:mm:ss", pattern);

	}

	/**
	 * 转换日期字符串格式
	 * 
	 * @modifier 赵旺
	 * @2010-8-29
	 * @param strDate
	 *            要转换的日期字符串
	 * @param fromPattern
	 *            转换前的格式，如：YYYYMMDD
	 * @param toPattern
	 *            转换后的格式，如：YYYY-MM-DD
	 * @return
	 */
	public static String getFormat(final String strDate, String fromPattern,
			String toPattern) {
		Date date = parseDateFormat(strDate, fromPattern);
		return getDateFormat(date, toPattern);

	}

	public static String[] getDateFormat(final String date, final String pattern) {
		final String[] obj = new String[4];
		final Date start = parseDateFormat(date, pattern);
		obj[0] = getDateFormat(start, "yyyyMMdd");
		obj[1] = getDateFormat(start, "yyMMdd");
		obj[2] = getDateFormat(start, "HH");
		obj[3] = getDateFormat(start, "yyyy-MM-dd");
		return obj;
	}

	/*
	 * 新增节目的方法
	 */
	// 'day', '今日'], ['week', '本周'], ['month', '本月']，
	public static String[] getBeforeDays(String day) {
		String toDay = getTodays(DATE_AND_TIME_FORMATER);
		String[] days = new String[2];
		if (day.equals("week")) {
			days[0] = getBeforeDays(6, DATE_AND_TIME_FORMATER);
		} else if (day.equals("month")) {
			days[0] = getBeforeDays(29, DATE_AND_TIME_FORMATER);
		} else {
			days[0] = getToday(DATE_AND_TIME_FORMATER);
		}
		days[1] = toDay;
		return days;
	}

	/*
	 * public static String[] getBeforeDays(String day, String fomatter) {
	 * SimpleDateFormat f; if (StringHelper.hasText(fomatter)) { f = new
	 * SimpleDateFormat(fomatter); } else { f = DATE_AND_TIME_FORMATER; }
	 * 
	 * String toDay = getTodays(f); String[] days = new String[2]; if
	 * (day.equals("week")) { days[0] = getBeforeDays(6, f); } else if
	 * (day.equals("month")) { days[0] = getBeforeDays(29, f); } else { days[0]
	 * = getToday(f); } days[1] = toDay; return days; }
	 */

	@SuppressWarnings("deprecation")
	public static String getDistanceTime(String timestr) {
		long time2 = Long.parseLong(timestr);
		Date now = new Date();
		long day = 0;// 天数
		long hour = 0;// 小时
		long min = 0;// 分钟
		try {
			long time1 = now.getTime();
			time2 = time2 * 1000l;
			long diff;
			if (time1 < time2) {
				diff = time2 - time1;
			} else {
				diff = time1 - time2;
			}
			day = diff / (24 * 60 * 60 * 1000);
			hour = (diff / (60 * 60 * 1000));
			min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String rs = "";
		if (hour == 0) {
			rs = min + "分钟前";
			return rs;
		}
		if (day == 0 && hour <= 4) {
			rs = hour + "小时前";
			return rs;
		}
		SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");//
		String d = format.format(time2);
		Date date = null;
		try {
			date = format.parse(d);// 把字符类型的转换成日期类型的！
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		if (date != null) {
			if (now.getDate() - date.getDate() == 0) {// 当前时间和时间戳转换来的时间的天数对比
				DateFormat df2 = new SimpleDateFormat("HH:mm");
				rs = "今天  " + df2.format(time2);
				return rs;
			} else if (now.getDate() - date.getDate() == 1) {
				DateFormat df2 = new SimpleDateFormat("HH:mm");
				rs = "昨天  " + df2.format(time2);
				return rs;
			} else {
				DateFormat df2 = new SimpleDateFormat("MM-dd HH:mm");
				rs = df2.format(time2);
				return rs;
			}
		} else {
			DateFormat df2 = new SimpleDateFormat("MM-dd HH:mm");
			rs = df2.format(time2);
			return rs;
		}
	}

	public static Date getDate(Object timeString) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Long time = new Long(timeString.toString());
		String d = format.format(time * 1000);
		Date date = new Date();
		try {
			date = format.parse(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static long parseUnixDateFormat(String time, final String pattern) {
		return DateUtil.parseDateFormat(time, pattern).getTime() / 1000;
	}

	/**
	 * 前几个小时
	 * 
	 * @author 王彬 @2012-12-17
	 * @param hour
	 * @return
	 */
	public static String hourOfBefore(int hour) {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH");
		c.add(Calendar.HOUR, -(hour));
		return formatter.format(c.getTime());
	}

	public static String minuteOfAfter(Date date, int minute) {

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		c.add(Calendar.MINUTE, +(minute));
		return formatter.format(c.getTime());
	}

	public static long dateinterval(Date date1, Date date2) {

		long l = date1.getTime() - date2.getTime();
		long day = l / (24 * 60 * 60 * 1000);
		long hour = (l / (60 * 60 * 1000) - day * 24);
		long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		// System.out.println("" + day + "天" + hour + "小时" + min + "分" + s +
		// "秒");
		return min;
	}

	public static String hourOfafter(int hour, String time) {
		Date date = parseDateFormat(time, "yyyy-MM-dd HH");
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH");
		c.add(Calendar.HOUR, +(hour));
		return formatter.format(c.getTime());
	}

	public static void main(String[] args) {
		
        System.out.println(dayOfBefore(new Date(), 3));
	}

	public static long getStartTimestamp(Date date) {
		Calendar nowDate = new GregorianCalendar();
		nowDate.setTime(date);
		nowDate.set(Calendar.HOUR_OF_DAY, 0);
		nowDate.set(Calendar.MINUTE, 0);
		nowDate.set(Calendar.SECOND, 0);
		nowDate.set(Calendar.MILLISECOND, 0);
		return nowDate.getTimeInMillis();
	}

	public static long getEndTimestamp(Date date) {
		Calendar nowDate = new GregorianCalendar();
		nowDate.setTime(date);
		nowDate.set(Calendar.HOUR_OF_DAY, 23);
		nowDate.set(Calendar.MINUTE, 59);
		nowDate.set(Calendar.SECOND, 59);
		nowDate.set(Calendar.MILLISECOND, 999);
		return nowDate.getTimeInMillis();
	}

	/**
	 * @param timeStr
	 * @param formatPattern
	 *            yyyy-MM-dd HH:mm:ss 或者 yyyy-MM-dd
	 * @return
	 */
	public static Date formateDate(String timeStr, String formatPattern) {
		Date time = null;
		if (null != timeStr && !"".equals(timeStr)) {
			DateFormat sdf = new SimpleDateFormat(formatPattern);
			try {
				time = sdf.parse(timeStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return time;
	}
	
	
	public static String formateDateToString(Date timeDate, String formatPattern) {
		String time = null;
		if (null != timeDate ){
			DateFormat sdf = new SimpleDateFormat(formatPattern);
			time = sdf.format(timeDate);
		}
		return time;
	}

	// string 日期转unix时间
	public static long getUnixTime(String str) {
		long epoch = 0;
		try {
			epoch = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
					.parse(str).getTime() / 1000;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return epoch;
	}

	public static String getUnixTimeToStr(String unixTime) {
		long timeLong = Long.parseLong(unixTime);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(timeLong);

		return sdf.format(c.getTime());

	}

	public List<String> getHourList(String timeHourStr) {
		List<String> hourTimeStr = new ArrayList();
		for (int i = 0; i < 24; i++) {
			String temp = "";
			if (i < 10) {
				temp = timeHourStr + " 0" + i;
			} else {
				temp = timeHourStr + " " + i;
			}
			hourTimeStr.add(temp);
		}

		return hourTimeStr;
	}

	/**
	 * @param date 时间
	 * @param day  天数
	 * @return     返回传入时间的前几天
	 */
	public static Date getBeforeDay(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, day);
		date = calendar.getTime();
		return date;
	}
	
	
	//求上一个月的第一天的时间string类型
	public static String getDateOfLastMonthStrFirstDay(Calendar date) {  
	  Calendar lastDate = (Calendar) date.clone();  
	  lastDate.add(Calendar.MONTH, -1);  //上一个月
	  String lastDateStr = sdf.format(lastDate.getTime());
	  return lastDateStr.substring(0, 7)+"-01";  
	}

	//求上一个月的第一天的时间string类型
	public static Date getDateOfLastMonthFirstDay(Calendar date)  {  
	Calendar lastDate = (Calendar) date.clone();  
	lastDate.add(Calendar.MONTH, -1);  //上一个月
	String lastDateStr = sdf.format(lastDate.getTime());
	String str = lastDateStr.substring(0, 7)+"-01 00:00:00"; 
	Date   re = null;
	try {
		re =  sdf.parse(str);
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}  
	return re;
	}
	//求上一个小时的时间
	public static Date getDateOfLastHourFormat(Calendar date){ 
	  Calendar lastDate = (Calendar) date.clone();  
	  lastDate.add(Calendar.HOUR, -1);  // 上一小时
	 Date d = lastDate.getTime();
	 Date newDate =null;
	try {
		newDate = sdf.parse(sdf.format(d).substring(0, 13)+":00:00");
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 
	  return newDate;  
	}
	
	
	//求上一个小时的时间String
	public static String getDateOfLastHourString(Calendar date) { 
	Calendar lastDate = (Calendar) date.clone();  
	lastDate.add(Calendar.HOUR, -1);  // 上一小时
	Date d = lastDate.getTime();
	return sdf.format(d).substring(0, 13);  
	}
	
	
	
	//获取两个时间段内的所有时间
	public  static Date getDate(String dateString){
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		Date  date = null;
		try {
			date = s.parse(dateString);
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	//返回 yyyy-MM-dd 格式的string 日期
	public  static String getDayStr(Date date){
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		return s.format(date);
	}
	// int calendarType 参数calendarType 该例是  Calendar.DAY_OF_YEAR
 public static List<String> getDateList(String startStr,String endStr ,int calendarType){
		  SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
			Date start = getDate(startStr);  
	        Date end = getDate(endStr);
	        ArrayList<Date> ret = new ArrayList<Date>();  
	        Calendar calendar = Calendar.getInstance();  
	        calendar.setTime(start);  
	        Date tmpDate = calendar.getTime();  
	        long endTime = end.getTime();  
	        while(tmpDate.before(end)||tmpDate.getTime() == endTime){  
	            ret.add(calendar.getTime());  
	            calendar.add(calendarType, 1);  
	            tmpDate = calendar.getTime();  
	        }         
	          
	        Date[] dates = new Date[ret.size()];  
	        Date[] dateArray = ret.toArray(dates); 
	        List <String> list = new ArrayList<String>();
	        for(Date d :dateArray){
	        	list.add(s.format(d));
	        }
	        return list;        
	    }
 
 /**
  * 获取两个时间之间的月份
  * 
 * @param minDate
 * @param maxDate
 * @return
 * @throws ParseException
 */
public static List<String> getMonthList(String minDate, String maxDate) throws ParseException{
	    ArrayList<String> result = new ArrayList<String>();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月

	    Calendar min = Calendar.getInstance();
	    Calendar max = Calendar.getInstance();

	    min.setTime(sdf.parse(minDate));
	    min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

	    max.setTime(sdf.parse(maxDate));
	    max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

	    Calendar curr = min;
	    while (curr.before(max)) {
	     result.add(sdf.format(curr.getTime()));
	     curr.add(Calendar.MONTH, 1);
	    }

	    return result;
	  }
}
