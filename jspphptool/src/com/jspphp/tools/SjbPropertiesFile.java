package com.jspphp.tools;

/*
 操作属性文件，可以为我们的程序带来更方便的移植性，下面是一个示例，可以读、写、更改属性
 读采用了两种方式，一种是采用Properties类，另外一种是采用资源绑定类ResourceBundle类，
 下面是源程序，里面有详细的注释：
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * 对属性文件（xx.properties）的操作 注：属性文件一定要放在当前工程的根目录下，也就是放在与src目录在同一个目录下（我的JDevelop
 * 是这样的）
 */
public class SjbPropertiesFile {
	public SjbPropertiesFile() {
	}

	/**
	 * 采用Properties类取得属性文件对应值
	 * 
	 * @parampropertiesFileNameproperties文件名，如a.properties
	 * @parampropertyName属性名
	 * @return根据属性名得到的属性值，如没有返回""
	 */
	public static String getValueByPropertyName(String propertiesFileName,
			String propertyName) {
		String s = "";
		Properties p = new Properties();// 加载属性文件读取类
		FileInputStream in;
		try {
			// propertiesFileName如test.properties
			in = new FileInputStream(propertiesFileName);// 以流的形式读入属性文件
			p.load(in);// 属性文件将该流加入的可被读取的属性中
			in.close();// 读完了关闭
			s = p.getProperty(propertyName);// 取得对应的属性值
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * 采用ResourceBundel类取得属性文件对应值，这个只能够读取，不可以更改及写新的属性
	 * 
	 * @parampropertiesFileNameWithoutPostfixproperties文件名，不带后缀
	 * @parampropertyName属性名
	 * @return根据属性名得到的属性值，如没有返回""
	 */
	public static String getValueByPropertyName_(
			String propertiesFileNameWithoutPostfix, String propertyName) {
		String s = "";
		// 如属性文件是test.properties，那此时propertiesFileNameWithoutPostfix的值就是test
		ResourceBundle bundel = ResourceBundle
				.getBundle(propertiesFileNameWithoutPostfix);
		s = bundel.getString(propertyName);
		return s;
	}

	/**
	 * 更改属性文件的值，如果对应的属性不存在，则自动增加该属性
	 * 
	 * @parampropertiesFileNameproperties文件名，如a.properties
	 * @parampropertyName属性名
	 * @parampropertyValue将属性名更改成该属性值
	 * @return是否操作成功
	 */
	public static boolean changeValueByPropertyName(String propertiesFileName,
			String propertyName, String propertyValue) {
		boolean writeOK = true;
		Properties p = new Properties();
		InputStream in;
		try {

			in = new FileInputStream(propertiesFileName);
			p.load(in);//
			in.close();
			p.setProperty(propertyName, propertyValue);// 设置属性值，如不属性不存在新建
			// p.setProperty("testProperty","testPropertyValue");
			FileOutputStream out = new FileOutputStream(propertiesFileName);// 输出流
			p.store(out, "");// 设置属性头，如不想设置，请把后面一个用""替换掉
			out.flush();// 清空缓存，写入磁盘
			out.close();// 关闭输出流
		} catch (Exception e) {
			e.printStackTrace();
		}
		return writeOK;
	}

}
