package com.jspphp.tools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * Description: 一般工具 * 史金波 2009-09-09
 * 
 * @version 4.0
 */

public class SjbAppTools {
	private static String[] SBC_CASE_NUMS = { "１", "２", "３", "４", "５", "６",
			"７", "８", "９", "０" };

	/**
	 * * 获得当前年 * *
	 * 
	 * @return String 当前年的字符串，格式：yyyy
	 */
	public static String getThisYear() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		return sdf.format(new Date());
	}

	/**
	 * * 获得当前日期和时间 * *
	 * 
	 * @return String 当前日期和时间，格式：yyyy-MM-dd HH:mm:ss
	 */
	public static String getCurDateTime() {
		SimpleDateFormat nowDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return nowDate.format(new Date());
	}

	/**
	 * * 将日期dt与当前比较 比较的格式为format("yyyy-MM-dd HH:mm:ss"等) * *
	 * 
	 * @param dt
	 *            String *
	 * @param format
	 *            String *
	 * @return int 0 表示相等 -1表示小于当前日期 1 表示大于当前日期 -2 表示出错
	 */
	public static int compToCurDateTime(String dt, String format) {
		int retValue;
		try {
			SimpleDateFormat df = new SimpleDateFormat(format);
			retValue = df.format(df.parse(dt)).compareTo(
					df.format(new java.util.Date()));
		} catch (Exception e) {
			retValue = -2;
		}
		return retValue;
	}

	/**
	 * * 获得当前日期 * *
	 * 
	 * @return String 当前日期，格式：yyyy-MM-dd
	 */
	public static String getCurDate() {
		SimpleDateFormat nowDate = new SimpleDateFormat("yyyy-MM-dd");
		return nowDate.format(new Date());
	}

	/**
	 * * 获得当前日期 yyMMdd 格式 * *
	 * 
	 * @return String 当前日期，格式：yyMMdd
	 */
	public static String getCurDate(String pattern) {
		return strToDate(null);
	}

	/**
	 * * 获得指定日期的yyMMdd格式 *
	 * 
	 * @param dateStr
	 *            字符串格式日期 yyyy-MM-dd 或者 yyyy-MM-dd HH:mm:ss *
	 * @return String 指定日期，格式：yyMMdd 如：080101
	 */
	public static String strToDate(String dateStr) {
		Date date = dateStr == null || dateStr.length() == 0 ? new Date()
				: SjbAppTools.StrToDate(dateStr);
		SimpleDateFormat nowDate = new SimpleDateFormat("yyMMdd");
		return nowDate.format(date);
	}

	/**
	 * * 将日期时间转换成yyy-MM-dd HH:mm:ss的格式 * *
	 * 
	 * @param date
	 *            Date 要转换的日期时间 *
	 * @return String 转换后的日期时间
	 */
	public static String getDateTime(Date date) {
		SimpleDateFormat nowDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return nowDate.format(date);
	}

	/**
	 * * 将日期转换成yyy-MM-dd的格式 * *
	 * 
	 * @param date
	 *            Date 要转换的日期 *
	 * @return String 转换后的日期
	 */
	public static String getDate(Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

	/**
	 * * 将日期时间转换成 pattern 格式的时间 * *
	 * 
	 * @param date
	 *            Date 要转换的日期时间 *
	 * @param pattern
	 *            时间模式 为null时默认为“yyy-MM-dd HH:mm:ss” *
	 * @return String 转换后的日期时间
	 */
	public static String getDateTime(Date date, String pattern) {
		if (pattern == null)
			pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	/** 字符串转化为日期 */
	public static Date StrToDate(String str) {
		Date returnDate = null;
		if (str != null) {
			DateFormat df = DateFormat.getDateInstance();
			try {
				returnDate = df.parse(str);
			} catch (Exception e) {
				System.err
						.println("SjbAppTools [Date StrToDate(String str)] Exception");
				return returnDate;
			}
		}
		return returnDate;
	}

	/**
	 * * 字符串转换为日期（包含小时分） * *
	 * 
	 * @param str *
	 * @return Date
	 */
	public static Date StrToDateTime(String str) {
		Date returnDate = null;
		if (str != null) {
			DateFormat df = DateFormat.getDateTimeInstance();
			try {
				int strLength = str.length();
				if (strLength < 11) {
					str += " 00:00:00";
				} else if (strLength > 11 && strLength < 14) {
					str += ":00:00";
				} else if (strLength > 14 && strLength < 17) {
					str += ":00";
				}
				returnDate = df.parse(str);
			} catch (Exception e) {
				return returnDate;
			}
		}
		return returnDate;
	}

	/**
	 * * 字符串转换为日期（包含小时分） * *
	 * 
	 * @param str *
	 * @param pattern
	 *            时间模式 为null时默认为“yyy-MM-dd HH:mm:ss” *
	 * @return Date
	 */
	public static Date StrToDateTime(String str, String pattern) {
		Date returnDate = null;
		if (pattern == null)
			pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			returnDate = sdf.parse(str);
		} catch (Exception e) {
			return returnDate;
		}
		return returnDate;
	}

	/**
	 * * 字符串转换为日期（包含小时分）yymmddhhmm * *
	 * 
	 * @param str *
	 * @return Date
	 */
	public static Date StringToDateTime(String str) {
		StringBuffer tmpStr = new StringBuffer();
		tmpStr.append(SjbAppTools.getThisYear().substring(0, 2)).append(
				str.substring(0, 2)).append("-").append(str.substring(2, 4))
				.append("-").append(str.substring(4, 6)).append(" ").append(
						str.substring(6, 8)).append(":").append(
						str.substring(8, 10));
		return StrToDateTime(tmpStr.toString());
	}

	/**
	 * * 字符串转换为整数 * *
	 * 
	 * @param str
	 *            String 要转换的字符串 *
	 * @return int 转换后的整数
	 */
	public static int getInt(String str) {
		if ((str != null) && (!"".equals(str))) {
			if (str.charAt(0) == '+')
				// 带正负号的数也有效

				str = str.substring(1);
			try {
				return Integer.parseInt(str);
			} catch (Exception e) {
				return 0;// 转换错误时，返回0
			}
		} else {
			// 字符串为空时返回0
			return 0;
		}
	}

	/**
	 * 
	 * 字符串转换为长整数 *
	 * 
	 * @param str
	 *            String 要转换的字符串
	 * 
	 * @return long 转换后的长整数
	 */
	public static long getLong(String str) {
		if ((str != null) && (!"".equals(str))) {
			if (str.charAt(0) == '+') // 带正负号的数也有效
				str = str.substring(1);
			try {
				return Long.parseLong(str);
			} catch (Exception e) {
				return 0;// 转换错误时，返回0
			}
		} else {
			// 字符串为空时返回0
			return 0;
		}
	}

	// /**
	// *
	// 字符串转换为double
	// * *
	// @param
	// str
	// String
	// 要转换的字符串
	// *
	// @return
	// double
	// 转换后的double
	// */
	public static double getDouble(String str) {
		if (str != null && !"".equals(str)) {
			try {
				return Double.parseDouble(str);
			} catch (Exception e) {
				return 0;// 转换错误时返回0
			}
		} else {
			// 字符串为空时，返回0
			return 0;
		}
	}

	/**
	 * // * // 字符串转换为GB2312编码的字符串 // * * //
	 * 
	 * @param //
	 *            str // String // 要转换的字符串 // * //
	 * @return // String // GB2312的字符串 //
	 */
	public static String getGBK(String str) {
		if (str == null) {
			return "";
		}
		String temp;
		try {
			byte[] buf = str.trim().getBytes("iso8859-1");
			temp = new String(buf, "GBK");
		} catch (Exception e) { // 转换错误时，返回原字符串
			temp = str;
		}
		return temp;
	}

	// /**
	// *
	// 字符串转换为GB2312编码的字符串，没有调用trim()方法
	// * *
	// @param
	// str
	// String
	// 要转换的字符串
	// *
	// @return
	// String
	// GB2312的字符串
	// */
	public static String getGBKNoTrim(String str) {
		if (str == null) {
			return "";
		}
		String temp;
		try {
			byte[] buf = str.getBytes("iso8859-1");
			temp = new String(buf, "GBK");
		} catch (Exception e) { // 转换错误时，返回原字符串
			temp = str;
		}
		return temp;
	}

	// /**
	// *
	// 字符串转换为iso8859-1编码的字符串
	// * *
	// @param
	// str
	// String
	// 要转换的字符串
	// *
	// @return
	// String
	// iso8859-1的字符串
	// */
	public static String getISO(String str) {
		if (str == null) {
			return "";
		}
		String temp;
		try {
			byte[] buf = str.trim().getBytes("GBK");
			temp = new String(buf, "iso8859-1");
		} catch (Exception e) { // 转换错误时，返回原字符串
			temp = str;
		}
		return temp;
	}

	// /**
	// *
	// 字符串转换为UTF-8编码的字符串
	// * *
	// @param
	// str
	// String
	// 要转换的字符串
	// *
	// @return
	// String
	// UTF-8的字符串
	// */
	public static String GBK2UTF8(String str) {
		if (str == null) {
			return "";
		}
		String temp;
		try {
			byte[] buf = str.trim().getBytes("GBK");
			temp = new String(buf, "UTF-8");
		} catch (Exception e) { // 转换错误时，返回原字符串
			temp = str;
		}
		return temp;
	}

	// /**
	// *
	// 字符串转换为iso8859-1编码的字符串，没有调用trim()方法
	// * *
	// @param
	// str
	// String
	// 要转换的字符串
	// *
	// @return
	// String
	// iso8859-1的字符串
	// */
	public static String getISONoTrim(String str) {
		if (str == null) {
			return "";
		}
		String temp;
		try {
			byte[] buf = str.getBytes("GBK");
			temp = new String(buf, "iso8859-1");
		} catch (Exception e) { // 转换错误时，返回原字符串
			temp = str;
		}
		return temp;
	}

	// /**
	// *
	// 字符串转换为UTF-8编码的字符串
	// * *
	// @param
	// str
	// String
	// 要转换的字符串
	// *
	// @param
	// encodingFrom
	// *
	// @param
	// encodingTo
	// *
	// @return
	// String
	// */
	public static String charsetEncoding(String str, String encodingFrom,
			String encodingTo) {
		if (str == null) {
			return "";
		}
		String temp;
		try {
			byte[] buf = str.trim().getBytes(encodingFrom);
			temp = new String(buf, encodingTo);
		} catch (Exception e) { // 转换错误时，返回原字符串
			temp = str;
		}
		return temp;
	}

	// /**
	// *
	// 将字符串按分隔符转换为ArrayList对象
	// * *
	// @param
	// str
	// String
	// 要转换的字符串
	// *
	// @param
	// delim
	// String
	// 分隔符
	// *
	// @return
	// ArrayList
	// */
	public static ArrayList<String> getStringTokenizer(String str, String delim) {
		StringTokenizer st = new StringTokenizer(str, delim);
		ArrayList<String> lists = new ArrayList<String>();
		String tmp;
		while (st.hasMoreTokens()) {
			tmp = st.nextToken().trim();
			if (!"".equals(tmp)) {
				lists.add(tmp);
			}
		}
		return lists;
	}

	/**
	 * * 去掉字符串两边的空格，为空时返回“” * *
	 * 
	 * @return String
	 */
	public static String trim(String args) {
		if (args == null) {
			return "";
		} else {
			return args.trim();
		}
	}

	/**
	 * * 根据最大长度maxLen截断字符串srcString，中英文都算一个单位长度， 同时支持Iso8859-1,GB2312 * *
	 * 
	 * @param srcString
	 *            原字符串 *
	 * @param maxLen
	 *            截取长度，中英文都算一个单位长度。 *
	 * @return 截取后的字符串
	 */
	public static String trimByEncode(String srcString, int maxLen) {
		if (srcString == null)
			return "";
		if (srcString.length() <= maxLen || maxLen <= 0)
			return srcString.trim();
		int num = 0, i = 0, c;
		for (; i < srcString.length();) {
			c = srcString.charAt(i);
			if (c > 256) // 为中文且编码格式为GB2312时c>256
			{
				i++;
				num++;
			} else if (c > 'z' && c < 256)
			// 为中文且编码格式为ISO8859-1时c>'z'&<256
			{
				i = i + 2;
				num++;
			} else
			// 为英文字母
			{
				i++;
				num++;
			}
			if (num >= maxLen) {
				break;
			}
		}
		return srcString.substring(0, i);
	}

	// /**
	// *
	// Description:
	// 取字符串的固定长度部分,用"..."结尾
	// * *
	// @param
	// src
	// 源字符串
	// *
	// @param
	// len
	// 截取长度
	// *
	// @return
	// 截取后的字符串
	// */
	public static String getSizedString(String src, int len) {
		if (src == null)
			return "";
		String str = trimByEncode(src, len);
		if (src.length() > str.length())// 源串长大于新串长度
			str += "...";
		return str;
	}

	// /**
	// *
	// 截取前缀和后缀之间的字符串
	// *
	// @param
	// str
	// String
	// 原字符串
	// *
	// @param
	// prefix
	// String
	// 字符前缀
	// *
	// @param
	// postfix
	// String
	// 字符后缀
	// *
	// @return
	// */
	public static String trimBranch(String str, String prefix, String postfix) {
		if (str == null || str.equalsIgnoreCase("")) {
			return "";
		}
		int pos0 = -1;
		int pos1 = 0;
		if (prefix != null && !prefix.equalsIgnoreCase("")) {
			pos0 = str.indexOf(prefix);
		} else {
			pos0 = 0;
		}
		if (postfix != null && !postfix.equalsIgnoreCase("")) {
			pos1 = str.indexOf(postfix);
		} else {
			pos1 = str.length() - 1;
		}
		if (pos0 > -1 && pos1 > pos0) {
			str = str.substring(pos0 + prefix.length(), pos1);
		}
		return str;
	} // 字符串替空,不需要去掉空格

	public static String ConvertNull(String string) {
		if (string == null) {
			return "";
		} else {
			return string;
		}
	}

	/** 转换成HTML空格符 */
	public static String ConvertBlank(String str) {
		if (str == null) {
			return " ";
		} else {
			return str;
		}
	}

	/**
	 * * 判断字符串中是否含有全角的阿拉伯数字 * *
	 * 
	 * @param num *
	 * @return true：有；false：没有
	 */
	public static boolean isContainSBCCaseNum(String num) {
		for (int i = 0; i < 10; i++) {
			if (num.indexOf(SBC_CASE_NUMS[i]) >= 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * *
	 * 
	 * @param arr
	 * @param first3Num *
	 * @return boolean
	 */
	public static boolean isMobileExist(int[] arr, int first3Num) {
		boolean haveIn = false;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == first3Num) {
				haveIn = true;
				break;
			}
		}
		return haveIn;
	}

	/**
	 * 
	 * 将null值或""值转为字符串"NULL"，其它的转为带两个单引号的字符串
	 * 
	 * @param str
	 *            String *
	 * @return String
	 */
	public static String toSQLNull(String str) {
		if (str == null || str.length() == 0) {
			return "NULL";
		} else {
			return "'" + str + "'";
		}
	}

	/**
	 * * 得到系统的换行符 * *
	 * 
	 * @return String
	 */
	public static String getLineSeparator() {
		return System.getProperty("line.separator");
	}

	/**
	 * * 返回整点值，如：0-10之间的数，返回10，11-20之间的数，返回20 * *
	 * 
	 * @param num *
	 * @return int
	 */
	public static int getWholeNum(int num) {
		if (num <= 10) {
			return 10;
		} else if (num > 10 && num <= 20) {
			return 20;
		} else if (num > 20 && num <= 30) {
			return 30;
		} else if (num > 30 && num <= 40) {
			return 40;
		} else if (num > 40 && num <= 50) {
			return 50;
		}
		return 0;
	}

	/**
	 * * 计算短信分割后的条数 * *
	 * 
	 * @param smsLength
	 *            短信长度 *
	 * @return 分割后的短信条数
	 */
	public static int countSmsNum(int smsLength) {
		int smsNum;
		if (smsLength == 0) {
			smsNum = 0;
		} else if (smsLength <= 70) {
			smsNum = 1;
		} else if ((smsLength % 65) == 0) {
			smsNum = smsLength / 65;
		} else {
			smsNum = smsLength / 65 + 1;
		}
		return smsNum;
	}

	/**
	 * 
	 * 判断一个元素是否存在数组中 *
	 * 
	 * @param arr
	 *            数组 *
	 * @param str
	 *            元素 *
	 * @return 存在返回元素在数组中的位置，不存在返回-1
	 */
	public static int getArrayIndex(String[] arr, String str) {
		if (arr == null || str == null)
			return -1;
		for (int i = 0; i < arr.length; i++) {
			String tmpID = arr[i];
			if (str.equalsIgnoreCase(tmpID))
				return i;
		}
		return -1;
	}

	// //数组中是否有重复值
	public static boolean isRepeat(Object obj[]) {
		boolean ret = false;
		for (int i = 0; i < obj.length - 1; i++) {
			Object tmp = obj[i];
			for (int j = i + 1; j < obj.length; j++) {
				if (tmp.equals(obj[j])) // 有重复
				{
					return true;
				}
			}
		}
		return ret;
	}

	/**
	 * 判断一个字符串是否含有中文字符 *
	 * 
	 * @param str
	 *            待判断的字符串
	 * @return boolean
	 */
	public static boolean isExistChinese(String str) {
		if (str == null || str.length() == 0) {
			return false;
		}
		java.util.regex.Pattern p = java.util.regex.Pattern
				.compile("[\u4e00-\u9fa5]");
		java.util.regex.Matcher m = p.matcher(str);
		return m.find();
	}

	public static void main(String[] args) {
		boolean b = isExistChinese("过滤内容aaaa");
		System.out.println("b = " + b);
		b = isExistChinese("_' + fdfsd423423");
		System.out.println("b = " + b);
	}

	/**
	 * * 校验时间是否超过指定的期限 * *
	 * 
	 * @param field
	 *            "y"：年 "m"：月 "d"：日 *
	 * @param amount
	 *            指定的期限 *
	 * @param strDate
	 *            日期 *
	 * @return boolean true：超过期限 false：没有超过期限
	 */
	public static boolean isOverTime(String field, int amount, String strDate) {
		Calendar limitDate = Calendar.getInstance();
		limitDate.setTime(StrToDate(strDate));
		if ("y".equalsIgnoreCase(field)) {
			limitDate.add(Calendar.YEAR, amount);
		} else if ("m".equalsIgnoreCase(field)) {
			limitDate.add(Calendar.MONTH, amount);
		} else if ("d".equalsIgnoreCase(field)) {
			limitDate.add(Calendar.DAY_OF_MONTH, amount);
		}
		limitDate.add(Calendar.DAY_OF_MONTH, -1);
		Calendar currDate = Calendar.getInstance();
		currDate.setTime(StrToDate(getCurDate()));
		if (currDate.after(limitDate)) {
			return true;
		}
		return false;
	}

	public static String getHtml(String content) {
		String txt = content.replaceAll("\"", "");
		txt = txt.replaceAll("'", "\'");
		return txt;
	}

}
