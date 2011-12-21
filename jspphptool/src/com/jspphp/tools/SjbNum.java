package com.jspphp.tools;

import java.text.DecimalFormat;

public class SjbNum {
	/**
	 * 判断任意一个整数是否素数
	 * 
	 * @param n
	 * @return boolean
	 */
	public static boolean isPrimes(int n) {
		for (int i = 2; i <= Math.sqrt(n); i++) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 获得任意一个整数的阶乘，递归
	 * 
	 * @param n
	 * @return n!
	 */
	public static int factorial(int n) {
		if (n == 1) {
			return 1;
		}
		return n * factorial(n - 1);
	}

	/**
	 * 将指定byte数组以16进制的形式打印到控制台
	 * 
	 * @param hint
	 *            String
	 * @param b
	 *            byte[]
	 * @return void
	 */
	public static void printHexString(String hint, byte[] b) {
		System.out.print(hint);
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			System.out.print(hex.toUpperCase() + " ");
		}
		System.out.println("");
	}

	/**
	 * int 转 double
	 * 
	 * @param num
	 * @param d
	 * @return
	 */
	public static double decimalFormatD(int num, double d) {
		String format = "0.";
		String result = "";
		double db;

		for (int i = 0; i < num; i++)
			format = format.concat("0");

		DecimalFormat decimal = new DecimalFormat(format);
		result = decimal.format(d);
		db = Double.parseDouble(result);

		return db;
	}

	/**
	 * int 转 float
	 * 
	 * @param num
	 * @param f
	 * @return
	 */
	public static float decimalFormatF(int num, float f) {
		String format = "0.";
		String result = "";
		float fl;

		for (int i = 0; i < num; i++)
			format = format.concat("0");

		DecimalFormat decimal = new DecimalFormat(format);
		result = decimal.format(f);
		fl = Float.parseFloat(result);

		return fl;
	}

	/**
	 * double 转 String
	 * 
	 * @param f
	 * @return
	 */
	public static String doubleToString(double f) {
		String s = "";
		double a = 0;

		while (f >= 1) {

			a = f % ((double) 10);

			s = String.valueOf((int) a) + s;
			f = (f - a) / 10;
		}
		return s;
	}

	/**
	 * 一个for循环打印九九乘法表
	 */
	public void nineNineMultiTable() {
		for (int i = 1, j = 1; j <= 9; i++) {
			System.out.print(i + "*" + j + "=" + i * j + " ");
			if (i == j) {
				i = 0;
				j++;
				System.out.println();
			}
		}
	}

}
