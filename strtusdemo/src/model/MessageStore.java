package model;

import java.util.Date;

public class MessageStore {

	private String	message;
	private int		age;
	private Date	birthday;

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

	public MessageStore() {

		setMessage("Hello Struts User");
	}

	public String getMessage() {

		return message;
	}

	public void setMessage(String message) {

		this.message = message;
	}
}
