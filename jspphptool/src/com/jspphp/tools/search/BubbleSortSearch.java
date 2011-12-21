package com.jspphp.tools.search;

public class BubbleSortSearch {
	/**
	 * 冒泡排序
	 * @param a
	 * @return
	 */
	public static int[] sort(int[] a) {
		for (int i = 0; i < a.length - 1; i++)
			for (int j = i + 1; j < a.length; j++) {
				if (a[j] < a[i]) {
					int temp = a[j];
					a[j] = a[i];
					a[i] = temp;
				}
			}
		return a;
	}

	public static void main(String[] args) {
		int arrayA[] = { 1, 5, 4, 23, 8, 6, 7, 9, 16, 32 };
		int[] arrayB = sort(arrayA);
		for (int i = 0; i < arrayB.length; i++) {
			System.out.println(arrayB[i]);
		}
	}

}
