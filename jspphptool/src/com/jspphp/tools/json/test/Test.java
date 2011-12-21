package com.jspphp.tools.json.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.jspphp.tools.json.JsonUtil;

public class Test {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		// String s = "{status : 'success'}";
		// try {
		// System.out.println(" object : " + JsonUtil.getJSONString(s));
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		/*
		 * 将java对象转化为json对象
		 */
		Person p = new Person();
		p.setAge(20);
		p.setName("史金波");
		JSONArray json = JSONArray.fromObject(p);
		System.out.println(json.toString());
		// 封装的不错
		System.out.println(JsonUtil.getJSONString(p));

		/**
		 * 将java对象list转化为json对象：
		 */

		List<Person> lp = new ArrayList<Person>();
		lp.add(p);
		lp.add(p);
		lp.add(p);
		lp.add(p);
		json = JSONArray.fromObject(lp);
		System.out.println(json);// json 数组
		// json 字符转java 对象
		Map s = JsonUtil.getMapFromJson("{\"age\": 20,\"birthday\": {\"date\": 7,\"day\": 2,\"hours\": 20,\"minutes\": 58,\"month\": 11,\"seconds\": 14,\"time\": 1291726694296,\"timezoneOffset\": -480,\"year\": 110},\"name\": \"史金波\",\"update_time\": {\"date\": 7,\"day\": 2,\"hours\": 20,\"minutes\": 58,\"month\": 11,\"nanos\": 296000000,\"seconds\": 14,\"time\": 1291726694296,\"timezoneOffset\": -480,\"year\": 110}}");
		System.out.println(s.get("age"));
	}

}
