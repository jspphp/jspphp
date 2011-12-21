package com.jspphp.tools.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 
 * @author 史金波 描述：文件操作的工具类 版本: 1.0 创建时间: 2009-09-09
 */

public class TestRandomAccessFile {
	public static void testCreateFile() {
		String directory = "c:/";
		String name = "shijinbo.txt";
		File f = new File(directory, name);
		RandomAccessFile file = null;
		try {
			file = new RandomAccessFile(f, "rw");
			byte[] b = { 5, 10, 15, 20 };

			try {
				// 如果没有这行，文件也会生成，只是文件为空
				file.write(b, 0, 4);
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (null != file) {
				try {
					file.close();
				} catch (IOException e) {
					e.printStackTrace();

				}

			}

		}

	}

	public static void main(String[] args) {
		testCreateFile();
	}

}
