package com.jspphp.tools.xml;

public class XMLResult {
	public static int INVALID = -1;
	public static int TAG = 0;
	public static int ATTRIBUTE = 1;
	public static int CONTENT = 2;

	private String data = null;
	private String xpath = null;
	private int type = XMLResult.INVALID;

	public XMLResult() {
	}

	public XMLResult(int type, String data, String xpath) {
		this.type = type;
		this.data = data;
		this.xpath = xpath;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getXpath() {
		return xpath;
	}

	public void setXpath(String xpath) {
		this.xpath = xpath;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
