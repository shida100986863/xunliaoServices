package com.heishuidi.common;
/**
 * 用户信息类，用于封装用户名密码
 * */
public class User  implements java.io.Serializable{
	private String user;
	private String passwd;
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
}
