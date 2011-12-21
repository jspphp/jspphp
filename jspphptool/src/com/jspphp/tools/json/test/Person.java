package com.jspphp.tools.json.test;

import java.sql.Timestamp;
import java.util.Date;

public class Person {
	private String name;
	private int age;
	private Date birthday = new java.util.Date();
	private java.sql.Timestamp update_time = new Timestamp(new Date().getTime());// 更新时间
	
	
	
	
	
	
	
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public java.sql.Timestamp getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(java.sql.Timestamp updateTime) {
		update_time = updateTime;
	}

}
