package com.jspphp.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class SjbFile {

	private String message = "";

	/**
	 * 列出某文件夹及其子文件夹下面的文件，并可根据扩展名过滤
	 * 
	 * @param path
	 */
	public static void list(File path) {
		if (!path.exists()) {
			System.out.println("文件名称不存在!");
		} else {
			if (path.isFile()) {
				if (path.getName().toLowerCase().endsWith(".pdf")
						|| path.getName().toLowerCase().endsWith(".doc")
						|| path.getName().toLowerCase().endsWith(".html")
						|| path.getName().toLowerCase().endsWith(".htm")) {
					System.out.println(path);
					System.out.println(path.getName());
				}
			} else {
				File[] files = path.listFiles();
				for (int i = 0; i < files.length; i++) {
					list(files[i]);
				}
			}
		}
	}

	/**
	 * 拷贝一个目录或者文件到指定路径下
	 * 
	 * @param source
	 * @param target
	 */
	public static void copy(File source, File target) {
		File tarpath = new File(target, source.getName());
		if (source.isDirectory()) {
			tarpath.mkdir();
			File[] dir = source.listFiles();
			for (int i = 0; i < dir.length; i++) {
				copy(dir[i], tarpath);
			}
		} else {
			try {
				InputStream is = new FileInputStream(source);
				OutputStream os = new FileOutputStream(tarpath);
				byte[] buf = new byte[1024];
				int len = 0;
				while ((len = is.read(buf)) != -1) {
					os.write(buf, 0, len);
				}
				is.close();
				os.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 将源文件拷贝到目标文件
	 * 
	 * @param src
	 *            写源文件地址，需文件名
	 * @param des
	 *            写目标文件地址，无需文件名
	 */
	public boolean copyFile(String src, String des) {
		File srcFile = new File(src);
		File desDir = new File(des);
		File desFile = new File(des + "/" + srcFile.getName());
		// 判断源文件是否存在
		if (!srcFile.exists()) {
			this.setMessage("源文件不存在！");
			return false;
		} else if (!srcFile.isFile()) {
			this.setMessage("源文件格式错！");
			return false;
		}
		// 判断源文件是否存在
		if (!desDir.exists()) {
			this.setMessage("目标目录不存在！");
			return false;
		} else if (!desDir.isDirectory()) {
			this.setMessage("不是有效的目录！");
			return false;
		}
		BufferedReader reader = null;
		BufferedWriter writer = null;
		String str;
		try {
			reader = new BufferedReader(new FileReader(srcFile));
			writer = new BufferedWriter(new FileWriter(desFile));
			// 判断目标文件是否存在及其格式，不存在就创建，格式不对先删除，存在就替代
			if (!desFile.exists() || !desFile.isFile()) {
				if (desFile.exists()) {
					desFile.delete();
				}
				desFile.createNewFile();
			}
			// 从源文件读取数据，并写入目标文件
			str = reader.readLine();
			while (str != null) {
				writer.write(str);
				writer.newLine();
				str = reader.readLine();
			}
		} catch (IOException e) {
			this.setMessage(e.getMessage());
			return false;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					this.setMessage(e.getMessage());
				}
			}
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					this.setMessage(e.getMessage());
				}
			}
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	private List fileList = new ArrayList();

	/**
	 * 列出所有文件
	 * 
	 * @param srcFile
	 */
	@SuppressWarnings("unchecked")
	private void file(File srcFile) {
		if (srcFile.isDirectory()) {
			String[] files = srcFile.list();

			for (int i = 0; i < files.length; i++) {
				File f = new File(srcFile + "/" + files[i]);
				// 如果是文件加入列表，否则递归列出
				if (f.isFile()) {
					fileList.add(f);
				} else
					file(f);
			}
		} else
			this.setMessage(srcFile.getAbsolutePath() + "不是目录");
	}

	/**
	 * 建立目录
	 * 
	 * @param des
	 * @throws IOException
	 */
	private void mkdir(File des) {
		if (!des.exists() || !des.isDirectory()) {
			mkdir(des.getParentFile());
			if (des.exists()) {
				des.delete();
			}
			des.mkdir();
		}
	}

	/**
	 * 复制目录 将源目录下所有文件拷贝到目标目录下
	 * 
	 * @param src
	 *            源目录
	 * @param des
	 *            目标目录
	 */
	public boolean copyDir(String src, String des) {
		File srcFile = new File(src);
		if (!srcFile.exists()) {
			this.setMessage("源目录不存在！");
			return false;
		} else if (!srcFile.isDirectory()) {
			this.setMessage(src + "不是有效的目录！");
			return false;
		}
		file(srcFile);

		for (int i = 0; i < fileList.size(); i++) {
			String srcName = ((File) fileList.get(i)).getPath();
			String desName = srcName.substring(src.length(), srcName.length());
			desName = des + desName;
			File dir = new File(desName).getParentFile();
			mkdir(dir);

			if (!copyFile(srcName, dir.getPath())) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {

		SjbFile t = new SjbFile();
		System.out.println(t.copyFile("D:/aaa.txt", "E:"));
		String src = "D:/asdf";
		String des = "E:/adf";
		System.out.println(t.copyDir(src, des));
		System.out.println(t.getMessage());
	}
}
