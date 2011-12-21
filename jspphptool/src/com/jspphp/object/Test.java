package com.jspphp.object;

import java.util.ArrayList;
import java.util.List;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Person p = new Person();
		p.setAge(24);
		p.setName("shijinbo");
		Person p1 = new Person();
		p1.setAge(241);
		p1.setName("史金波");
		List<Person> lp = new ArrayList<Person>();
		lp.add(p);
		lp.add(p1);
		System.out.println(TypeUtil.typeToString("Person",lp));

	}

}
