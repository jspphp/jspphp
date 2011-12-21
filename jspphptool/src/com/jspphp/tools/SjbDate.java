package com.jspphp.tools;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class SjbDate {
	/**
	 * 将某个日期以固定格式转化成字符串
	 * 
	 * @param date
	 * @return String
	 */
	public static String dateToStr(java.util.Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	/**
	 * 格式化日期
	 * 
	 * @param dateStr
	 *            字符型日期
	 * @param format
	 *            格式
	 * @return 返回日期
	 */
	public static java.util.Date parseDate(String dateStr, String format) {
		java.util.Date date = null;
		try {
			java.text.DateFormat df = new java.text.SimpleDateFormat(format);
			String dt = dateStr.replaceAll("-", "/");
			if ((!dt.equals("")) && (dt.length() < format.length())) {
				dt += format.substring(dt.length()).replaceAll("[YyMmDdHhSs]",
						"0");
			}
			date = (java.util.Date) df.parse(dt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * @param dateStr
	 * @return Date
	 */
	public static java.util.Date parseDate(String dateStr) {
		return parseDate(dateStr, "yyyy/MM/dd");
	}

	/**
	 * 格式化输出日期
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            格式
	 * @return 返回字符型日期
	 */
	public static String format(java.util.Date date, String format) {
		String result = "";
		try {
			if (date != null) {
				java.text.DateFormat df = new java.text.SimpleDateFormat(format);
				result = df.format(date);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String format(java.util.Date date) {
		return format(date, "yyyy/MM/dd");
	}

	/**
	 * 返回年份
	 * 
	 * @param date
	 *            日期
	 * @return 返回年份
	 */
	public static int getYear(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.YEAR);
	}

	/**
	 * 返回月份
	 * 
	 * @param date
	 *            日期
	 * @return 返回月份
	 */
	public static int getMonth(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.MONTH) + 1;
	}

	/**
	 * 返回日份
	 * 
	 * @param date
	 *            日期
	 * @return 返回日份
	 */
	public static int getDay(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.DAY_OF_MONTH);
	}

	/**
	 * 返回小时
	 * 
	 * @param date
	 *            日期
	 * @return 返回小时
	 */
	public static int getHour(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.HOUR_OF_DAY);
	}

	/**
	 * 返回分钟
	 * 
	 * @param date
	 *            日期
	 * @return 返回分钟
	 */
	public static int getMinute(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.MINUTE);
	}

	/**
	 * 返回秒钟
	 * 
	 * @param date
	 *            日期
	 * @return 返回秒钟
	 */
	public static int getSecond(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.SECOND);
	}

	/**
	 * 返回毫秒
	 * 
	 * @param date
	 *            日期
	 * @return 返回毫秒
	 */
	public static long getMillis(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.getTimeInMillis();
	}

	/**
	 * 返回字符型日期
	 * 
	 * @param date
	 *            日期
	 * @return 返回字符型日期
	 */
	public static String getDate(java.util.Date date) {
		return format(date, "yyyy/MM/dd");
	}

	/**
	 * 返回字符型时间
	 * 
	 * @param date
	 *            日期
	 * @return 返回字符型时间
	 */
	public static String getTime(java.util.Date date) {
		return format(date, "HH:mm:ss");
	}

	/**
	 * 返回字符型日期时间
	 * 
	 * @param date
	 *            日期
	 * @return 返回字符型日期时间
	 */
	public static String getDateTime(java.util.Date date) {
		return format(date, "yyyy/MM/dd HH:mm:ss");
	}

	/**
	 * 日期相加
	 * 
	 * @param date
	 *            日期
	 * @param day
	 *            天数
	 * @return 返回相加后的日期
	 */
	public static java.util.Date addDate(java.util.Date date, int day) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTimeInMillis(getMillis(date) + ((long) day) * 24 * 3600 * 1000);
		return c.getTime();
	}

	/**
	 * 日期相减
	 * 
	 * @param date
	 *            日期
	 * @param date1
	 *            日期
	 * @return 返回相减后的日期
	 */
	public static int diffDate(java.util.Date date, java.util.Date date1) {
		return (int) ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
	}

	/**
	 * 取得指定月份的第一天
	 * 
	 * @param strdate
	 *            String
	 * @return String
	 */
	public static String getMonthBegin(String strdate) {
		java.util.Date date = parseDate(strdate);
		return format(date, "yyyy-MM") + "-01";
	}

	/**
	 * 取得指定月份的最后一天
	 * 
	 * @param strdate
	 *            String
	 * @return String
	 */
	public static String getMonthEnd(String strdate) {
		java.util.Date date = parseDate(getMonthBegin(strdate));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		return formatDate(calendar.getTime());
	}

	/**
	 * 常用的格式化日期
	 * 
	 * @param date
	 *            Date
	 * @return String
	 */
	public static String formatDate(java.util.Date date) {
		return formatDateByFormat(date, "yyyy-MM-dd");
	}

	/**
	 * 以指定的格式来格式化日期
	 * 
	 * @param date
	 *            Date
	 * @param format
	 *            String
	 * @return String
	 */
	public static String formatDateByFormat(java.util.Date date, String format) {
		String result = "";
		if (date != null) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				result = sdf.format(date);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * Description: 获取GMT8时间
	 * 
	 * @return 将当前时间转换为GMT8时区后的Date
	 */
	public static Date getGMT8Time() {
		Date gmt8 = null;
		try {
			Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"),
					Locale.CHINESE);
			Calendar day = Calendar.getInstance();
			day.set(Calendar.YEAR, cal.get(Calendar.YEAR));
			day.set(Calendar.MONTH, cal.get(Calendar.MONTH));
			day.set(Calendar.DATE, cal.get(Calendar.DATE));
			day.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY));
			day.set(Calendar.MINUTE, cal.get(Calendar.MINUTE));
			day.set(Calendar.SECOND, cal.get(Calendar.SECOND));
			gmt8 = day.getTime();
		} catch (Exception e) {
			System.out.println("获取GMT8时间 getGMT8Time() error !");
			e.printStackTrace();
			gmt8 = null;
		}
		return gmt8;
	}

	/**
	 * 解析一个日期之间的所有月份
	 * 
	 * @param beginDateStr
	 * @param endDateStr
	 * @return
	 */
	public static ArrayList<String> getMonthList(String beginDateStr,
			String endDateStr) {
		// 指定要解析的时间格式
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM");
		// 返回的月份列表
		String sRet = "";

		// 定义一些变量
		Date beginDate = null;
		Date endDate = null;

		GregorianCalendar beginGC = null;
		GregorianCalendar endGC = null;
		ArrayList<String> list = new ArrayList<String>();

		try {
			// 将字符串parse成日期
			beginDate = f.parse(beginDateStr);
			endDate = f.parse(endDateStr);

			// 设置日历
			beginGC = new GregorianCalendar();
			beginGC.setTime(beginDate);

			endGC = new GregorianCalendar();
			endGC.setTime(endDate);

			// 直到两个时间相同
			while (beginGC.getTime().compareTo(endGC.getTime()) <= 0) {
				sRet = beginGC.get(Calendar.YEAR) + "-";

				String s = beginGC.get(Calendar.MONTH) < 9 ? "0"
						+ (beginGC.get(Calendar.MONTH) + 1) : String
						.valueOf((beginGC.get(Calendar.MONTH) + 1));
				sRet += s;
				list.add(sRet);
				// 以月为单位，增加时间
				beginGC.add(Calendar.MONTH, 1);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 解析一个日期段之间的所有日期
	 * 
	 * @param beginDateStr
	 *            开始日期
	 * @param endDateStr
	 *            结束日期
	 * @return
	 */
	public static ArrayList<String> getDayList(String beginDateStr,
			String endDateStr) {
		// 指定要解析的时间格式
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");

		// 定义一些变量
		Date beginDate = null;
		Date endDate = null;

		Calendar beginGC = null;
		Calendar endGC = null;
		ArrayList<String> list = new ArrayList<String>();

		try {
			// 将字符串parse成日期
			beginDate = f.parse(beginDateStr);
			endDate = f.parse(endDateStr);

			// 设置日历
			beginGC = Calendar.getInstance();
			beginGC.setTime(beginDate);

			endGC = Calendar.getInstance();
			endGC.setTime(endDate);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			// 直到两个时间相同
			while (beginGC.getTime().compareTo(endGC.getTime()) <= 0) {

				list.add(sdf.format(beginGC.getTime()));
				// 以日为单位，增加时间
				beginGC.add(Calendar.DAY_OF_MONTH, 1);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static ArrayList<Integer> getYearList() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		Calendar c = null;
		c = Calendar.getInstance();
		c.setTime(new Date());
		int currYear = Calendar.getInstance().get(Calendar.YEAR);

		int startYear = currYear - 5;
		int endYear = currYear + 10;
		for (int i = startYear; i < endYear; i++) {
			list.add(new Integer(i));
		}
		return list;
	}

	public static int getCurrYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	/**
	 * 得到某一年周的总数
	 * 
	 * @param year
	 * @return
	 */
	public static LinkedHashMap<Integer, String> getWeekList(int year) {
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		Calendar c = new GregorianCalendar();
		c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
		int count = getWeekOfYear(c.getTime());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dayOfWeekStart = "";
		String dayOfWeekEnd = "";
		for (int i = 1; i <= count; i++) {
			dayOfWeekStart = sdf.format(getFirstDayOfWeek(year, i));
			dayOfWeekEnd = sdf.format(getLastDayOfWeek(year, i));
			map.put(new Integer(i), "第" + i + "周(从" + dayOfWeekStart + "至"
					+ dayOfWeekEnd + ")");
		}
		return map;

	}

	/**
	 * 得到一年的总周数
	 * 
	 * @param year
	 * @return
	 */
	public static int getWeekCountInYear(int year) {
		Calendar c = new GregorianCalendar();
		c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
		int count = getWeekOfYear(c.getTime());
		return count;
	}

	/**
	 * 取得当前日期是多少周
	 * 
	 * @param date
	 * @return
	 */
	public static int getWeekOfYear(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setMinimalDaysInFirstWeek(7);
		c.setTime(date);

		return c.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 得到某年某周的第一天
	 * 
	 * @param year
	 * @param week
	 * @return
	 */
	public static Date getFirstDayOfWeek(int year, int week) {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, 1);

		Calendar cal = (GregorianCalendar) c.clone();
		cal.add(Calendar.DATE, week * 7);

		return getFirstDayOfWeek(cal.getTime());
	}

	/**
	 * 得到某年某周的最后一天
	 * 
	 * @param year
	 * @param week
	 * @return
	 */
	public static Date getLastDayOfWeek(int year, int week) {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, 1);

		Calendar cal = (GregorianCalendar) c.clone();
		cal.add(Calendar.DATE, week * 7);

		return getLastDayOfWeek(cal.getTime());
	}

	/**
	 * 得到某年某月的第一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getFirestDayOfMonth(int year, int month) {
		month = month - 1;
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month);

		int day = c.getActualMinimum(Calendar.DAY_OF_MONTH);

		c.set(Calendar.DAY_OF_MONTH, day);
		return c.getTime();

	}

	/**
	 * 提到某年某月的最后一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getLastDayOfMonth(int year, int month) {
		month = month - 1;
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month);
		int day = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		c.set(Calendar.DAY_OF_MONTH, day);
		return c.getTime();
	}

	/**
	 * 取得当前日期所在周的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
		return c.getTime();
	}

	/**
	 * 取得当前日期所在周的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
		return c.getTime();
	}

	public static void main(String[] args) {
		List<String> l = SjbDate.getMonthList("2009-01-01", "2010-09-09");
		List<String> l1 = SjbDate.getDayList("2009-01-01", "2010-09-09");
		List<Integer> l2 = SjbDate.getYearList();
		System.out.println(l.toString());
		System.out.println(l1.toString());
		System.out.println(l2.toString());
	}
}
