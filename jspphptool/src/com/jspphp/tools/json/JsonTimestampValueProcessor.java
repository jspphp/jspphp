package com.jspphp.tools.json;

import java.text.SimpleDateFormat;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class JsonTimestampValueProcessor implements JsonValueProcessor {
	private String format = "yyyy-MM-dd HH:mm:ss";

	public JsonTimestampValueProcessor() {

	}

	public JsonTimestampValueProcessor(String format) {
		this.format = format;
	}

	public Object processArrayValue(Object value, JsonConfig jsonConfig) {
		return process(value, jsonConfig);
	}

	public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
		return process(value, jsonConfig);
	}

	private Object process(Object value, JsonConfig jsonConfig) {
		if (value instanceof java.sql.Timestamp) {
			String str = new SimpleDateFormat(format).format((java.sql.Timestamp) value);
			return str;
		}
		return value == null ? null : value.toString();
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

}
