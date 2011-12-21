package com.jspphp.tools.search;

import java.util.Arrays;

/**
 * 二分查找法 参数 sourceInt，targetStr 效率： max(count)<=log2(n+1)+1
* @author 史金波
 * 创建日期：2009-09-09 Email:JSPPHP@126.COM
 */

public class HalfBinarySearch {
	static int max   = 0;
	static int min   = 0;
	static int half  = 0;
	static int count = 0;

	// 隐藏构造方法
	private HalfBinarySearch() {

	}

	/**
	 * int[]类型匹配,返回与targetStr等值sourceInt[]数组下标
	 * 
	 * @param sourceInt
	 * @param targetStr
	 * @return
	 */
	public static int search(int[] sourceInt, Integer targetStr) {
		if (!init(sourceInt, targetStr)) {
			return -1;
		} else {
			return subSearch(sourceInt, targetStr, 0, sourceInt.length);
		}
	}

	/**
	 * 递归方式实现查找细节 待改进： 数组中具有相同大小的数时处理方式
	 * 
	 * @param sourceInt
	 * @param targetStr
	 * @param min
	 * @param max
	 */
	private static Integer subSearch(int[] sourceInts, Integer targetInt,
			int minNum, int maxNum) {
		count++;
		max = maxNum;
		min = minNum;
		half = (max + min) / 2;
		if (sourceInts[half] == targetInt) {
			return half;
		} else if (min == max || half == max || half == min) {
			return -1;
		} else if (sourceInts[half] > targetInt) {
			return subSearch(sourceInts, targetInt, min, half);
		} else {
			return subSearch(sourceInts, targetInt, half, max);
		}
	}

	/**
	 * 先决执行 1.排序 2.初判断
	 * 
	 * @param sourceInt
	 * @param targetStr
	 * @return false：不存在符合要求的数
	 */
	private static boolean init(int[] sourceInt, Integer targetStr) {
		boolean flag = false;
		// 产生递增序列
		Arrays.sort(sourceInt);
		if (sourceInt[0] > targetStr) {
			System.out.println("不存在符合要求的数,因为过小");
		} else if (sourceInt[sourceInt.length - 1] < targetStr) {
			System.out.println("不存在符合要求的数,因为过大");
		} else {
			flag = true;
		}
		return flag;
	}

	/**
	 * max(count)<=log2(n+1)+1
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		int searcher = 9999;
		int[] is = new int[10000];
		Long time1 = System.currentTimeMillis();
		for (int i = 0; i < is.length; i++) {
			is[i] = i;
			System.out.println(is[i]);
		}
		Long time2 = System.currentTimeMillis();
		int num = search(is, searcher);
		Long time3 = System.currentTimeMillis();
		System.out.println("下标：" + num);
		System.out.println("查询次数" + count);
		System.out.println("初始时间" + (time2 - time1));
		System.out.println("查询时间" + (time3 - time2));
		System.out.println("num:" + is[num]);
		Long time4 = System.currentTimeMillis();
		for (int i = 0; i < is.length; i++) {
			if (is[i] == searcher) {
				break;
			}
		}
		Long time5 = System.currentTimeMillis();
		System.out.println("遍历时间" + (time5 - time4));
	}
}
