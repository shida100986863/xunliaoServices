package com.heishuidi.common;
/**
 * �û���Ϣ�࣬���ڷ�װ�û�������
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
