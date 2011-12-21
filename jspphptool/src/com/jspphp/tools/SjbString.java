package com.jspphp.tools;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SjbString {

	/**
	 * 分割字符串
	 * 
	 * @param str
	 *            String 原始字符串
	 * @param splitsign
	 *            String 分隔符
	 * @return String[] 分割后的字符串数组
	 */
	@SuppressWarnings("unchecked")
	public static String[] split(String str, String splitsign) {
		int index;
		if (str == null || splitsign == null)
			return null;
		ArrayList al = new ArrayList();
		while ((index = str.indexOf(splitsign)) != -1) {
			al.add(str.substring(0, index));
			str = str.substring(index + splitsign.length());
		}
		al.add(str);
		return (String[]) al.toArray(new String[0]);
	}

	/**
	 * 替换字符串
	 * 
	 * @param from
	 *            String 原始字符串
	 * @param to
	 *            String 目标字符串
	 * @param source
	 *            String 母字符串
	 * @return String 替换后的字符串
	 */
	public static String replace(String from, String to, String source) {
		if (source == null || from == null || to == null)
			return null;
		StringBuffer bf = new StringBuffer("");
		int index = -1;
		while ((index = source.indexOf(from)) != -1) {
			bf.append(source.substring(0, index) + to);
			source = source.substring(index + from.length());
			index = source.indexOf(from);
		}
		bf.append(source);
		return bf.toString();
	}

	/**
	 * 替换字符串，能够在HTML页面上直接显示(替换双引号和小于号)
	 * 
	 * @param str
	 *            String 原始字符串
	 * @return String 替换后的字符串
	 */
	public static String htmlencode(String str) {
		if (str == null) {
			return null;
		}

		return replace("\"", "&quot;", replace("<", "&lt;", str));
	}

	/**
	 * 替换字符串，将被编码的转换成原始码（替换成双引号和小于号）
	 * 
	 * @param str
	 *            String
	 * @return String
	 */
	public static String htmldecode(String str) {
		if (str == null) {
			return null;
		}

		return replace("&quot;", "\"", replace("&lt;", "<", str));
	}

	private static final String _BR = "<br/>";

	/**
	 * 在页面上直接显示文本内容，替换小于号，空格，回车，TAB
	 * 
	 * @param str
	 *            String 原始字符串
	 * @return String 替换后的字符串
	 */
	public static String htmlshow(String str) {
		if (str == null) {
			return null;
		}

		str = replace("<", "&lt;", str);
		str = replace(" ", "&nbsp;", str);
		str = replace("\r\n", _BR, str);
		str = replace("\n", _BR, str);
		str = replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;", str);
		return str;
	}

	/**
	 * 返回指定字节长度的字符串
	 * 
	 * @param str
	 *            String 字符串
	 * @param length
	 *            int 指定长度
	 * @return String 返回的字符串
	 */
	public static String toLength(String str, int length) {
		if (str == null) {
			return null;
		}
		if (length <= 0) {
			return "";
		}
		try {
			if (str.getBytes("GBK").length <= length) {
				return str;
			}
		} catch (Exception ex) {
		}
		StringBuffer buff = new StringBuffer();

		int index = 0;
		char c;
		length -= 3;
		while (length > 0) {
			c = str.charAt(index);
			if (c < 128) {
				length--;
			} else {
				length--;
				length--;
			}
			buff.append(c);
			index++;
		}
		buff.append("...");
		return buff.toString();
	}

	/**
	 * 判断是否为整数
	 * 
	 * @param str
	 *            传入的字符串
	 * @return 是整数返回true,否则返回false
	 */
	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 判断是否为浮点数，包括double和float
	 * 
	 * @param str
	 *            传入的字符串
	 * @return 是浮点数返回true,否则返回false
	 */
	public static boolean isDouble(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 判断输入的字符串是否符合Email样式.
	 * 
	 * @param str
	 *            传入的字符串
	 * @return 是Email样式返回true,否则返回false
	 */
	public static boolean isEmail(String str) {
		Pattern pattern = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 判断输入的字符串是否为纯汉字
	 * 
	 * @param str
	 *            传入的字符窜
	 * @return 如果是纯汉字返回true,否则返回false
	 */
	public static boolean isChinese(String str) {
		Pattern pattern = Pattern.compile("[\u0391-\uFFE5]+$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 是否为空白,包括null和""
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		return str == null || str.trim().length() == 0;
	}

	/**
	 * 判断是否为质数
	 * 
	 * @param x
	 * @return
	 */
	public static boolean isPrime(int x) {
		if (x <= 7) {
			if (x == 2 || x == 3 || x == 5 || x == 7)
				return true;
		}
		int c = 7;
		if (x % 2 == 0)
			return false;
		if (x % 3 == 0)
			return false;
		if (x % 5 == 0)
			return false;
		int end = (int) Math.sqrt(x);
		while (c <= end) {
			if (x % c == 0) {
				return false;
			}
			c += 4;
			if (x % c == 0) {
				return false;
			}
			c += 2;
			if (x % c == 0) {
				return false;
			}
			c += 4;
			if (x % c == 0) {
				return false;
			}
			c += 2;
			if (x % c == 0) {
				return false;
			}
			c += 4;
			if (x % c == 0) {
				return false;
			}
			c += 6;
			if (x % c == 0) {
				return false;
			}
			c += 2;
			if (x % c == 0) {
				return false;
			}
			c += 6;
		}
		return true;
	}

	/**
	 * 人民币转成大写
	 * 
	 * @param value
	 * @return String
	 */
	public static String hangeToBig(double value) {
		char[] hunit = { '拾', '佰', '仟' }; // 段内位置表示
		char[] vunit = { '万', '亿' }; // 段名表示
		char[] digit = { '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖' }; // 数字表示
		long midVal = (long) (value * 100); // 转化成整形
		String valStr = String.valueOf(midVal); // 转化成字符串

		String head = valStr.substring(0, valStr.length() - 2); // 取整数部分
		String rail = valStr.substring(valStr.length() - 2); // 取小数部分

		String prefix = ""; // 整数部分转化的结果
		String suffix = ""; // 小数部分转化的结果
		// 处理小数点后面的数
		if (rail.equals("00")) { // 如果小数部分为0
			suffix = "整";
		} else {
			suffix = digit[rail.charAt(0) - '0'] + "角" + digit[rail.charAt(1) - '0'] + "分"; // 否则把角分转化出来
		}
		// 处理小数点前面的数
		char[] chDig = head.toCharArray(); // 把整数部分转化成字符数组
		char zero = '0'; // 标志'0'表示出现过0
		byte zeroSerNum = 0; // 连续出现0的次数
		for (int i = 0; i < chDig.length; i++) { // 循环处理每个数字
			int idx = (chDig.length - i - 1) % 4; // 取段内位置
			int vidx = (chDig.length - i - 1) / 4; // 取段位置
			if (chDig[i] == '0') { // 如果当前字符是0
				zeroSerNum++; // 连续0次数递增
				if (zero == '0') { // 标志
					zero = digit[0];
				} else if (idx == 0 && vidx > 0 && zeroSerNum < 4) {
					prefix += vunit[vidx - 1];
					zero = '0';
				}
				continue;
			}
			zeroSerNum = 0; // 连续0次数清零
			if (zero != '0') { // 如果标志不为0,则加上,例如万,亿什么的
				prefix += zero;
				zero = '0';
			}
			prefix += digit[chDig[i] - '0']; // 转化该数字表示
			if (idx > 0)
				prefix += hunit[idx - 1];
			if (idx == 0 && vidx > 0) {
				prefix += vunit[vidx - 1]; // 段结束位置应该加上段名如万,亿
			}
		}

		if (prefix.length() > 0)
			prefix += '圆'; // 如果整数部分存在,则有圆的字样
		return prefix + suffix; // 返回正确表示
	}

	/**
	 * 全角字符转半角字符
	 * 
	 * @param QJStr
	 * @return String
	 */
	public static final String QJToBJChange(String QJStr) {
		char[] chr = QJStr.toCharArray();
		String str = "";
		for (int i = 0; i < chr.length; i++) {
			chr[i] = (char) ((int) chr[i] - 65248);
			str += chr[i];
		}
		return str;
	}

	/**
	 * 去掉字符串中重复的子字符串
	 * 
	 * @param str
	 * @return String
	 */
	public static String removeSameString(String str) {
		Set<String> mLinkedSet = new LinkedHashSet<String>();
		String[] strArray = str.split(" ");
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < strArray.length; i++) {
			if (!mLinkedSet.contains(strArray[i])) {
				mLinkedSet.add(strArray[i]);
				sb.append(strArray[i] + " ");
			}
		}
		System.out.println(mLinkedSet);
		return sb.toString().substring(0, sb.toString().length() - 1);
	}

	/**
	 * 过滤特殊字符
	 */
	public static String encoding(String src) {
		if (src == null)
			return "";
		StringBuilder result = new StringBuilder();
		if (src != null) {
			src = src.trim();
			for (int pos = 0; pos < src.length(); pos++) {
				switch (src.charAt(pos)) {
				case '\"':
					result.append("&quot;");
					break;
				case '<':
					result.append("&lt;");
					break;
				case '>':
					result.append("&gt;");
					break;
				case '\'':
					result.append("&apos;");
					break;
				case '&':
					result.append("&amp;");
					break;
				case '%':
					result.append("&pc;");
					break;
				case '_':
					result.append("&ul;");
					break;
				case '#':
					result.append("&shap;");
					break;
				case '?':
					result.append("&ques;");
					break;
				default:
					result.append(src.charAt(pos));
					break;
				}
			}
		}
		return result.toString();
	}

	/**
	 * 反过滤特殊字符
	 * 
	 * @param src
	 * @return
	 */
	public static String decoding(String src) {
		if (src == null)
			return "";
		String result = src;
		result = result.replace("&quot;", "\"").replace("&apos;", "\'");
		result = result.replace("&lt;", "<").replace("&gt;", ">");
		result = result.replace("&amp;", "&");
		result = result.replace("&pc;", "%").replace("&ul", "_");
		result = result.replace("&shap;", "#").replace("&ques", "?");
		return result;
	}

	/**
	 * 判断是不是合法手机 handset 手机号码
	 */
	public static boolean isHandset(String handset) {
		try {
			if (!handset.substring(0, 1).equals("1")) {
				return false;
			}
			if (handset == null || handset.length() != 11) {
				return false;
			}
			String check = "^[0123456789]+$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(handset);
			boolean isMatched = matcher.matches();
			if (isMatched) {
				return true;
			} else {
				return false;
			}
		} catch (RuntimeException e) {
			return false;
		}
	}

	/**
	 * 字符串匹配的算法.
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static String getMaxMatch(String a, String b) {
		StringBuffer tmp = new StringBuffer();
		String maxString = "";
		int max = 0;
		int len = 0;
		char[] aArray = a.toCharArray();
		char[] bArray = b.toCharArray();
		int posA = 0;
		int posB = 0;
		while (posA < aArray.length - max) {
			posB = 0;
			while (posB < (bArray.length - max)) {
				if (aArray[posA] == bArray[posB]) {
					len = 1;
					tmp = new StringBuffer();
					tmp.append(aArray[posA]);
					while ((posA + len < aArray.length) && (posB + len < bArray.length) && (aArray[posA + len] == bArray[posB + len])) {
						tmp.append(aArray[posA + len]);
						len++;
					}
					if (len > max) {
						max = len;
						maxString = tmp.toString();
					}
				}
				posB++;
			}
			posA++;
		}
		return maxString;
	}

	/**
	 * 如果是null，则返回空字符串，如果是非空值则返回该字符串头尾不为空白字符的字符串
	 * 
	 * @param str
	 */
	public static String toNoNullTrimedString(String str) {
		if (str == null) {
			return "";
		}
		return new String(str.trim());
	}

	/**
	 * 如果是null，则返回空字符串，如果是非空值则返回该对象所toString后的字符串
	 * 
	 * @param obj
	 */
	public static String toNoNullString(Object obj) {
		if (obj == null)
			return "";
		return obj.toString();
	}

	/**
	 * 本方法把一个Throwable的StackTrace作为一个字符串输出，以利于对StackTrace的操作。<br />
	 * 通常用于把抛出的Exception转化成字符串进行后续操作。
	 */
	public static String exceptionToStackTrace(Throwable throwable) {
		StringBuffer retu = new StringBuffer();
		StackTraceElement[] traces = throwable.getStackTrace();
		for (StackTraceElement ste : traces) {
			retu.append("\n").append(ste.toString());
		}
		return retu.substring(1);
	}

	/**
	 * 判断字符串是否为Null或空字符串
	 * 
	 * @param String
	 *            要判断的字符串
	 * @return String true-是空字符串,false-不是空字符串
	 */
	public static boolean nil(String s) {
		if (s == null || s.equals("")) {
			return true;
		}
		return false;
	}

	/**
	 * 字符串数组转换为字符串,使用逗号分隔
	 */
	public static String split(String[] s, String spliter) {
		if (s == null || s.equals(""))
			return "";
		if (s.length == 1)
			return s[0];
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length; i++) {
			sb.append(s[i]).append(spliter);
		}
		sb.deleteCharAt(sb.lastIndexOf(spliter));
		return sb.toString();
	}

	/**
	 * 如果第一个字符串不为空则返回该字符串,否则返回第二个
	 */
	public static String nil(String s, String _default) {
		if (s.endsWith(null) || s == null || s == "" || s.equals(""))
			return _default;
		else
			return s;
	}

	/**
	 * 判断字符串数组是否为空
	 */
	public static boolean nil(String[] s) {
		return (s == null || s.length == 0);
	}

	/**
	 * 如果数组为空,则返回空数组
	 */
	public static String[] notNil(String[] s) {
		if (s == null || s.length == 0) {
			return new String[0];
		}
		return s;
	}

	/**
	 * 改变字符串编码到gbk
	 */
	public static String toGBK(String src) {
		if (nil(src))
			return "";
		String s = null;
		try {
			s = new String(src.getBytes("ISO-8859-1"), "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * 改变字符串编码到utf8
	 */
	public static String toUTF8(String src) {
		if (nil(src))
			return "";
		String s = null;
		try {
			s = new String(src.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * 转换String到boolean
	 */
	public static boolean parseBoolean(String flag) {
		if (nil(flag))
			return false;
		else if (flag.equals("true") || flag.equals("1") || flag.equals("是") || flag.equals("yes"))
			return true;
		else if (flag.equals("false") || flag.equals("0") || flag.equals("否") || flag.equals("no"))
			return false;
		return false;
	}

	/**
	 * 转换String到int <br>
	 * null或空字符,返回0 <br>
	 * true返回1 <br>
	 * false返回0
	 */
	public static int parseInt(String flag) {
		if (nil(flag))
			return 0;
		else if (flag.equals("true"))
			return 1;
		else if (flag.equals("false"))
			return 0;
		return Integer.parseInt(flag);
	}

	/**
	 * 转换String到long
	 */
	public static long parseLong(String flag) {
		if (nil(flag))
			return 0;
		return Long.parseLong(flag);
	}

	/**
	 * 字符填充
	 * 
	 * @param source
	 *            源字符串
	 * @param filler
	 *            填充字符,如0或*等
	 * @param length
	 *            最终填充后字符串的长度
	 * @return 最终填充后字符串
	 */
	public static String fill(String source, String filler, int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length - source.length(); i++) {
			sb.append(filler);
		}
		sb.append(source);
		return sb.toString();
	}

	/**
	 * 从开头到spliter字符,截取字符串数组中的每一项
	 * 
	 * @param arr
	 *            源字符串数组
	 * @param spliter
	 *            切割符
	 * @return 切割后的字符串数组
	 */
	public static String[] subStrBefore(String[] arr, String spliter) {
		for (int i = 0; i < arr.length; i++) {
			arr[i] = arr[i].substring(0, arr[i].indexOf(spliter));
		}
		return arr;
	}

	/**
	 * 
	 * 将字串转成日期，字串格式: yyyy-MM-dd
	 * 
	 * @param string
	 *            String
	 * @return Date
	 */

	public static Date parseDate(String string) {
		try {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			return (Date) formatter.parse(string);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * 字符串数组中是否包含指定的字符串。
	 * 
	 * @param strings
	 *            字符串数组
	 * @param string
	 *            字符串
	 * @param caseSensitive
	 *            是否大小写敏感
	 * @return 包含时返回true，否则返回false
	 */

	public static boolean contains(String[] strings, String string, boolean caseSensitive) {
		for (int i = 0; i < strings.length; i++) {
			if (caseSensitive == true) {
				if (strings[i].equals(string)) {
					return true;
				}
			} else {
				if (strings[i].equalsIgnoreCase(string)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 字符串数组中是否包含指定的字符串。大小写敏感。
	 * 
	 * @param strings
	 *            字符串数组
	 * @param string
	 *            字符串
	 * @return 包含时返回true，否则返回false
	 */

	public static boolean contains(String[] strings, String string) {
		return contains(strings, string, true);
	}

	/**
	 * 不区分大小写判定字符串数组中是否包含指定的字符串。
	 * 
	 * @param strings
	 *            字符串数组
	 * @param string
	 *            字符串
	 * @return 包含时返回true，否则返回false
	 */

	public static boolean containsIgnoreCase(String[] strings, String string) {
		return contains(strings, string, false);
	}

	/**
	 * 返回一个整数数组
	 * 
	 * @param s
	 *            String[]
	 * @return int[]
	 */
	public static int[] parseInt(String[] s) {
		if (s == null) {
			return (new int[0]);
		}
		int[] result = new int[s.length];
		try {
			for (int i = 0; i < s.length; i++) {
				result[i] = Integer.parseInt(s[i]);
			}
		} catch (NumberFormatException ex) {
		}
		return result;
	}

	/**
	 * 返回一个整数数组
	 * 
	 * @param s
	 *            String
	 * @return int[]
	 */
	public static int[] split(String s) {
		if (nil(s))
			return new int[0];
		if (s.indexOf(",") > -1) {
			return SjbString.splitB(s, ",");
		} else {
			int[] i = new int[1];
			i[0] = Integer.parseInt(s);
			return i;
		}
	}

	/**
	 * 返回一个整数数组
	 * 
	 * @param s
	 *            String
	 * @param spliter
	 *            分隔符如逗号
	 * @return int[]
	 */
	public static int[] splitB(String s, String spliter) {
		if (s == null || s.indexOf(spliter) == -1) {
			return (new int[0]);
		}
		String[] ary = s.split(spliter);
		int[] result = new int[ary.length];
		try {
			for (int i = 0; i < ary.length; i++) {
				result[i] = Integer.parseInt(ary[i]);
			}
		} catch (NumberFormatException ex) {
		}
		return result;
	}

	/**
	 * 将整型数组合并为用字符分割的字符串
	 * 
	 * @param int[]
	 * @return String
	 */
	public static String join(int[] arr) {
		if (arr == null || arr.length == 0)
			return "";
		StringBuffer sb = new StringBuffer();
		for (int i = 0, len = arr.length; i < len; i++) {
			sb.append(",").append(arr[i]);
		}
		sb.deleteCharAt(0);
		return sb.toString();
	}

	/**
	 * 将字符串的第一个字母大写
	 * 
	 * @param s
	 *            String
	 * @return String
	 */
	public static String firstCharToUpperCase(String s) {
		if (s == null || s.length() < 1) {
			return "";
		}
		char[] arrC = s.toCharArray();
		arrC[0] = Character.toUpperCase(arrC[0]);
		return String.copyValueOf(arrC);
	}

	/**
	 * 根据当前字节长度取出加上当前字节长度不超过最大字节长度的子串
	 * 
	 * @param str
	 * @param currentLen
	 * @param MAX_LEN
	 * @return
	 */
	public static String getSubStr(String str, int currentLen, int MAX_LEN) {
		int i;
		for (i = 0; i < str.length(); i++) {
			if (str.substring(0, i + 1).getBytes().length + currentLen > MAX_LEN) {
				break;
			}
		}
		if (i == 0) {
			return "";
		} else {
			return str.substring(0, i);
		}
	}

	/**
	 * 首字母大写
	 * 
	 * @param s
	 * @return
	 */
	public static String capitalize(String s) {
		if (s.length() == 0)
			return s;
		char first = s.charAt(0);
		char capitalized = Character.toUpperCase(first);
		return capitalized + s.substring(1);
	}

	public static void main(String[] args) {
		String[] numbers = { "12345", "-12345", "123.45", "-123.45", ".12345", "-.12345", "a12345", "12345a", "123.a45" };
		for (String str : numbers) {
			System.out.println(str + "=" + isInteger(str) + " " + isDouble(str));
		}

		String[] emails = { "1@2.com", "1.2@3.com", "1@3.4.5.com" };
		for (String str : emails) {
			System.out.println(str + "=" + isEmail(str));
		}
		String[] chineses = { "中国", "1中国", "中国1", "1中国2", "中1国" };
		for (String str : chineses) {
			System.out.println(str + "=" + isChinese(str));
		}
		String shi = "s h i j i n b o";
		System.out.println(com.jspphp.tools.SjbString.removeSameString(shi));

	}

}
