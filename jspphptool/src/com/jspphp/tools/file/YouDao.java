package com.jspphp.tools.file;

/**
 * 
 * @author 史金波 描述：文件操作的工具类 版本: 1.0 创建时间: 2009-09-09
 */

public class YouDao {

	public static void main(String[] args) {
		String zifu = SjbFileUtils.readFileContent("C:/222222222.xml");
		String result = "";
		String[] items = zifu.split("<item>");
		for (int i = 1; i < items.length; i++) {
			result = items[i].substring(items[i].indexOf("<word>") + 6, items[i].indexOf("</word>"));
			System.out.print(result + " ");
		}

	}

}
