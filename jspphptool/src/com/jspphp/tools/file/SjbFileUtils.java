package com.jspphp.tools.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 
 * @author 史金波 描述：文件操作的工具类 版本: 1.0 创建时间: 2009-09-09
 */

public class SjbFileUtils {

	/**
	 * 读取文本文件内容，以行的形式读取
	 * 
	 * @param filePathAndName
	 *            带有完整绝对路径的文件名
	 * @return String 返回文本文件的内容
	 * @throws IOException
	 */

	public static String readFileContent(String filePathAndName) {
		try {
			return readFileContent(filePathAndName, null, null, 1024);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 读取文本文件内容，以行的形式读取
	 * 
	 * @param filePathAndName
	 *            带有完整绝对路径的文件名
	 * @param encoding
	 *            文本文件打开的编码方式 例如 GBK,UTF-8
	 * @return String 返回文本文件的内容
	 */
	public static String readFileContent(String filePathAndName, String encoding) throws IOException {
		return readFileContent(filePathAndName, encoding, null, 1024);
	}

	/**
	 * 读取文本文件内容，以行的形式读取
	 * 
	 * @param filePathAndName
	 *            带有完整绝对路径的文件名
	 * @param bufLen
	 *            设置缓冲区大小
	 * @return String 返回文本文件的内容
	 */
	public static String readFileContent(String filePathAndName, int bufLen) throws IOException {
		return readFileContent(filePathAndName, null, null, bufLen);
	}

	/**
	 * 读取文本文件内容，以行的形式读取
	 * 
	 * @param filePathAndName
	 *            带有完整绝对路径的文件名
	 * @param encoding
	 *            文本文件打开的编码方式 例如 GBK,UTF-8
	 * @param sep
	 *            分隔符 例如：#，默认为空格
	 * @return String 返回文本文件的内容
	 */
	public static String readFileContent(String filePathAndName, String encoding, String sep) throws IOException {
		return readFileContent(filePathAndName, encoding, sep, 1024);
	}

	/**
	 * 读取文本文件内容，以行的形式读取
	 * 
	 * @param filePathAndName
	 *            带有完整绝对路径的文件名
	 * @param encoding
	 *            文本文件打开的编码方式 例如 GBK,UTF-8
	 * @param sep
	 *            分隔符 例如：#，默认为\n;
	 * @param bufLen
	 *            设置缓冲区大小
	 * @return String 返回文本文件的内容
	 */
	public static String readFileContent(String filePathAndName, String encoding, String sep, int bufLen) throws IOException {
		if (filePathAndName == null || filePathAndName.equals("")) {
			return "";
		}
		if (sep == null || sep.equals("")) {
			sep = "\n";
		}
		if (!new File(filePathAndName).exists()) {
			return "";
		}
		StringBuffer str = new StringBuffer("");
		FileInputStream fs = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			fs = new FileInputStream(filePathAndName);
			if (encoding == null || encoding.trim().equals("")) {
				isr = new InputStreamReader(fs);
			} else {
				isr = new InputStreamReader(fs, encoding.trim());
			}
			br = new BufferedReader(isr, bufLen);

			String data = "";
			while ((data = br.readLine()) != null) {
				str.append(data).append(sep);
			}

		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (br != null)
					br.close();
				if (isr != null)
					isr.close();
				if (fs != null)
					fs.close();
			} catch (IOException e) {
				throw e;
			}

		}
		return str.toString();
	}

	/**
	 * 新建一个文件并写入内容
	 * 
	 * @param path
	 *            文件全路径
	 * @param fileName
	 *            文件名
	 * @param content
	 *            内容
	 * @return boolean
	 * @throws IOException
	 */

	public static boolean newFile(String path, String fileName, StringBuffer content) throws IOException {

		return newFile(path, fileName, content, 1024, false);
	}

	/**
	 * 新建一个文件并写入内容
	 * 
	 * @param path
	 *            文件全路径
	 * @param fileName
	 *            文件名
	 * @param content
	 *            内容
	 * @return boolean
	 * @throws IOException
	 */
	public static boolean newFile(String path, String fileName, StringBuffer content, boolean isWrite) throws IOException {

		return newFile(path, fileName, content, 1024, isWrite);
	}

	/**
	 * 新建一个文件并写入内容
	 * 
	 * @param path
	 *            文件全路径
	 * @param fileName
	 *            文件名
	 * @param content
	 *            内容
	 * @param bufLen
	 *            设置缓冲区大小
	 * @param isWrite
	 *            是否追加写入文件
	 * @return boolean
	 * @throws IOException
	 */
	public static boolean newFile(String path, String fileName, StringBuffer content, int bufLen, boolean isWrite) throws IOException {
		if (path == null || path.equals("") || content == null || content.equals(""))
			return false;
		boolean flag = false;
		FileWriter fw = null;
		BufferedWriter bw = null;

		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();

		}
		try {
			fw = new FileWriter(path + File.separator + fileName, isWrite);
			bw = new BufferedWriter(fw, bufLen);
			bw.write(content.toString());
			flag = true;
		} catch (IOException e) {
			System.out.println("写入文件出错");
			flag = false;
			throw e;
		} finally {
			if (bw != null) {
				bw.flush();
				bw.close();
			}
			if (fw != null)
				fw.close();
		}

		return flag;
	}

	/**
	 * 新建一个文件并写入内容
	 * 
	 * @param path
	 *            文件全路径
	 * @param fileName
	 *            文件名
	 * @param content
	 *            内容
	 * @return boolean
	 * @throws IOException
	 */
	public static boolean newFile(String path, String fileName, String content) throws IOException {

		return newFile(path, fileName, content, 1024, false);
	}

	/**
	 * 新建一个文件并写入内容
	 * 
	 * @param path
	 *            文件全路径
	 * @param fileName
	 *            文件名
	 * @param content
	 *            内容
	 * @param isWrite
	 *            是否追加写入文件
	 * @return boolean
	 * @throws IOException
	 */
	public static boolean newFile(String path, String fileName, String content, boolean isWrite) throws IOException {

		return newFile(path, fileName, content, 1024, isWrite);
	}

	/**
	 * 新建一个文件并写入内容
	 * 
	 * @param path
	 *            文件全路径
	 * @param fileName
	 *            文件名
	 * @param content
	 *            内容
	 * @param bufLen
	 *            设置缓冲区大小
	 * @param isWrite
	 *            是否追加写入文件
	 * @return boolean
	 * @throws IOException
	 */
	public static boolean newFile(String path, String fileName, String content, int bufLen, boolean isWrite) throws IOException {

		if (path == null || path.equals("") || content == null || content.equals(""))
			return false;
		boolean flag = false;
		FileWriter fw = null;
		BufferedWriter bw = null;

		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();

		}
		try {
			fw = new FileWriter(path + File.separator + fileName, isWrite);
			bw = new BufferedWriter(fw, bufLen);
			bw.write(content);
			flag = true;
		} catch (IOException e) {
			System.out.println("写入文件出错");
			flag = false;
			throw e;
		} finally {
			if (bw != null) {
				bw.flush();
				bw.close();
			}
			if (fw != null)
				fw.close();
		}

		return flag;
	}

	/**
	 * 新建一个目录
	 * 
	 * @param filePath
	 *            路径
	 * @return boolean flag
	 * @throws Exception
	 */
	public static boolean newFolder(String filePath) throws Exception {
		boolean flag = false;
		if (filePath == null || filePath.equals("") || filePath.equals("null")) {
			return flag;
		}
		try {
			File myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.mkdirs();
			}
			flag = true;
		} catch (Exception e) {
			throw e;
		}
		return flag;
	}

	/**
	 * 复制单个文件
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf.txt
	 * @param newPath
	 *            String 复制后路径 如：f:
	 * @return boolean
	 */
	public static boolean copyFile(String oldPath, String newPath) throws IOException {
		return copyFile(oldPath, newPath, null);
	}

	/**
	 * 复制单个文件
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf.txt
	 * @param newPath
	 *            String 复制后路径 如：f:/
	 * @return boolean
	 */
	public static boolean copyFile(String oldPath, String newPath, String newFileName) throws IOException {

		boolean flag = false;
		if (oldPath == null || newPath == null || newPath.equals("") || oldPath.equals("")) {
			return flag;
		}
		InputStream inStream = null;
		FileOutputStream fs = null;
		try {
			int bytesum = 0;
			int byteread = 0;
			File file = null;
			file = new File(newPath);
			if (!file.exists()) {
				file.mkdirs();
			}
			file = new File(oldPath);
			if (file.exists()) { // 文件存在时
				inStream = new FileInputStream(oldPath); // 读入原文件
				if (newFileName == null || newFileName.equals("")) {
					newFileName = file.getName();
				}
				fs = new FileOutputStream(newPath + File.separator + newFileName);
				byte[] buffer = new byte[1024 * 8];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				flag = true;
			}
		} catch (IOException e) {
			throw e;

		} finally {
			try {
				if (fs != null) {
					fs.close();
				}
				if (inStream != null) {
					inStream.close();
				}
			} catch (IOException e) {
				throw e;
			}
		}
		return flag;
	}

	/**
	 * 复制整个文件夹内容
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf/ff
	 */
	public static void copyFolder(String oldPath, String newPath) throws Exception {
		if (oldPath == null || newPath == null || newPath.equals("") || oldPath.equals("")) {
			return;
		}
		FileInputStream input = null;
		FileOutputStream output = null;
		try {
			File temp = null;
			temp = new File(newPath);
			if (!temp.exists()) {
				temp.mkdirs();// 如果文件夹不存在 则建立新文件夹
			}
			temp = new File(oldPath);
			String[] file = temp.list();
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}

				if (temp.isFile()) {
					input = new FileInputStream(temp);
					output = new FileOutputStream(newPath + File.separator + (temp.getName()).toString());
					byte[] b = new byte[1024 * 8];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();

				}
				if (temp.isDirectory()) {// 如果是子文件夹
					copyFolder(oldPath + File.separator + file[i], newPath + File.separator + file[i]);
				}
			}

		} catch (Exception e) {
			throw e;

		} finally {
			try {
				if (output != null)
					output.close();
				if (input != null)
					input.close();
			} catch (Exception e) {
				throw e;
			}
		}

	}

	/**
	 * 移动单个文件
	 * 
	 * @param srcFile
	 *            源文件 如：c:/test.txt
	 * @param destPath
	 *            目标目录 如：c：/test
	 * @return boolean
	 */

	public static boolean Move(String srcFile, String destPath) {
		return Move(srcFile, destPath, null);
	}

	/**
	 * 移动单个文件
	 * 
	 * @param srcFile
	 *            源文件 如：c:/test.txt
	 * @param destPath
	 *            目标目录 如：c：/test
	 * @param newFileName
	 *            新文件名 如：newTest.txt
	 * @return boolean
	 */
	public static boolean Move(String srcFile, String destPath, String newFileName) {
		boolean flag = false;
		if (srcFile == null || srcFile.equals("") || destPath == null || destPath.equals("")) {
			return flag;
		}
		File file = new File(srcFile);
		File dir = new File(destPath);
		if (newFileName == null || newFileName.equals("") || newFileName.equals("null"))
			newFileName = file.getName();
		flag = file.renameTo(new File(dir, newFileName));

		return flag;
	}

	/**
	 * 移动文件到指定目录
	 * 
	 * @param oldPath
	 *            如：c:/fqf.txt
	 * @param newPath
	 *            如：d:/
	 */
	public static void moveFile(String oldPath, String newPath) throws Exception {
		if (copyFile(oldPath, newPath))
			delFile(oldPath);

	}

	/**
	 * 移动文件到指定目录
	 * 
	 * @param oldPath
	 *            如：c:/fqf.txt
	 * @param newPath
	 *            如：d:/
	 * @param newFileName
	 *            新文件名 如：newTest.txt
	 */
	public static void moveFile(String oldPath, String newPath, String newFileName) throws Exception {
		if (copyFile(oldPath, newPath))
			delFile(oldPath);

	}

	/**
	 * 移动文件夹到指定目录
	 * 
	 * @param oldPath
	 *            如：c:/fqf.txt
	 * @param newPath
	 *            如：d:/
	 */
	public static void moveFolder(String oldPath, String newPath) throws Exception {
		copyFolder(oldPath, newPath);
		delFolder(oldPath);
	}

	/**
	 * 删除文件
	 * 
	 * @param filePathAndName
	 *            文件路径及名称 如c:/fqf.txt
	 */
	public static void delFile(String filePathAndName) {
		if (filePathAndName == null || filePathAndName.equals("") || filePathAndName.equals("null"))
			return;
		try {
			File myDelFile = new File(filePathAndName);
			if (myDelFile.exists())
				myDelFile.delete();

		} catch (Exception e) {
			System.out.println("删除文件操作出错");
			e.printStackTrace();

		}

	}

	/**
	 * 删除文件夹
	 * 
	 * @param folderPath
	 *            文件夹路径及名称 如c:/fqf
	 */
	public static void delFolder(String folderPath) {
		if (folderPath == null || folderPath.equals(""))
			return;
		try {
			File myFilePath = new File(folderPath);
			if (myFilePath.isDirectory() && myFilePath.exists()) {
				delAllFile(folderPath); // 删除完里面所有内容
				myFilePath.delete(); // 删除空文件夹
			}

		} catch (Exception e) {
			System.out.println("删除文件夹操作出错");
			e.printStackTrace();

		}

	}

	/**
	 * 删除文件夹里面的所有文件
	 * 
	 * @param path
	 *            文件夹路径 如 c:/fqf
	 */

	public static void delAllFile(String path) {
		if (path == null || path.equals(""))
			return;
		File file = new File(path);
		if (!file.exists() || !file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + File.separator + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + File.separator + tempList[i]);// 再删除空文件夹
			}
		}
	}

	/**
	 * 删除目录以及当前目录下的文件或子目录。用递归就可以实现。
	 * 
	 * @param filepath
	 * @throws Exception
	 */

	public static void del(String filepath) throws Exception {
		if (filepath == null || filepath.equals("") || filepath.equals("null"))
			return;
		try {
			File f = new File(filepath);// 定义文件路径
			if (f.exists() && f.isDirectory()) {// 判断是文件还是目录
				if (f.listFiles().length == 0) {// 若目录下没有文件则直接删除
					f.delete();
				} else {// 若有则把文件放进数组，并判断是否有下级目录
					File delFile[] = f.listFiles();
					int i = f.listFiles().length;
					for (int j = 0; j < i; j++) {
						if (delFile[j].isDirectory()) {
							del(delFile[j].getAbsolutePath());// 递归调用del方法并取得子目录路径
						}
						delFile[j].delete();// 删除文件
					}
				}
				del(filepath);// 递归调用
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 创建文件目录
	 * 
	 * @param destDirName
	 * @return
	 */
	public static boolean createDir(String destDirName) {
		File dir = new File(destDirName);
		if (dir.exists()) {
			// System.out.println("创建目录" + destDirName + "失败，目标目录已经存在");
			return false;
		}
		if (!destDirName.endsWith(File.separator)) {
			destDirName = destDirName + File.separator;
		}
		// 创建目录
		if (dir.mkdirs()) {
			System.out.println("创建目录" + destDirName + "成功！");
			return true;
		} else {
			System.out.println("创建目录" + destDirName + "失败！");
			return false;
		}
	}

	public static void main(String[] args) {

		// String zifu = readFileContent("C:/44/GPRSLOGDaoImpl.java");
		// String zifu = "private static boolean isPlayEnd(String officeID,
		// String
		// log_time) {";
		//
		// // zifu = zifu.replaceAll("p.*\\)", "");
		// zifu = zifu.re
		// replaceAll("p.*\\)", "");
		// System.out.println(zifu);
		//		

		// Pattern p = Pattern.compile("public.*\\)");
		// Pattern p = Pattern.compile("append\\(\".*\\)");
		// String s = readFileContent("C:/44/TMovieScheduleDaoImpl.java");
		// Matcher m = p.matcher(s);
		// while (m.find()) {
		// System.out.println(m.group());
		//
		// }
		System.out.println(createDir("C:/1/1/1/1/1//1/1/1//1/1/1/"));

	}
}
