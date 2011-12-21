package com.jspphp.tools.collection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CollectionMap {
	@SuppressWarnings("unchecked")
	public static void test() {
		Map m = new HashMap();
		m.put("1", "shijinbo1");
		m.put("2", "shijinbo2");
		m.put("3", "shijinbo3");
		m.put("4", "shijinbo4");
		m.put("5", "shijinbo5");
		// 方式一
		Iterator it = m.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			System.out.println(entry.getKey());
			System.out.println(entry.getValue());
		}

		// 遍历键，通过键取值
		// 方式二
		for (Object key : m.keySet()) {
			System.out.println("键:" + key + "  值:" + m.get(key));
		}

	}

	public static void main(String[] args) {
		test();
	}

}
