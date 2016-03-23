package com.pb.mybatis.entity;

public class User {

	Integer age; // 年龄
	Integer gender; // 性别
	Integer id;
	String mail; // 邮箱
	String nickname;
	String password;
	String site; // 个人站点
	String username;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(Integer id) {
		super();
		this.id = id;
	}

	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public User(String password, String username, String nickname,
			Integer gender, Integer age, String mail, String site) {
		super();
		this.password = password;
		this.username = username;
		this.nickname = nickname;
		this.gender = gender;
		this.age = age;
		this.mail = mail;
		this.site = site;
	}

	public User(Integer id, Integer age, Integer gender, String mail,
			String nickname, String password, String site, String username) {
		super();
		this.age = age;
		this.gender = gender;
		this.id = id;
		this.mail = mail;
		this.nickname = nickname;
		this.password = password;
		this.site = site;
		this.username = username;
	}

	public Integer getAge() {
		return age;
	}

	public Integer getGender() {
		return gender;
	}

	public Integer getId() {
		return id;
	}

	public String getMail() {
		return mail;
	}

	public String getNickname() {
		return nickname;
	}

	public String getPassword() {
		return password;
	}

	public String getSite() {
		return site;
	}

	public String getUsername() {
		return username;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}