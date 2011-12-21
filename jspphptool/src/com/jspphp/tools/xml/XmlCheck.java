package com.jspphp.tools.xml;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @ClassName: XmlCheck
 * @Description: TODO 解析XML文件
 * @author 史金波 创建日期：2009-09-09 Email:JSPPHP@126.COM
 * 
 */
public class XmlCheck {

	public static boolean parseKdm(String fileName) {
		SAXReader reader = new SAXReader();
		try {
			Document doc = reader.read(fileName);
			Element root = doc.getRootElement();
			Element age = root.element("age");
			System.out.println(age.getStringValue());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public static void main(String[] args) {
		parseKdm("src/com/jspphp/tools/xml/student.xml");
	}
}
