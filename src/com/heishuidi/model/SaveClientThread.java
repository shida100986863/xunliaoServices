package com.heishuidi.model;

import java.util.HashMap;
import java.util.Iterator;

public class SaveClientThread {
	//String 保存用户登录的用户名ServerToClientThread保存客户运行的线程
	public static HashMap hm=new HashMap<String,ServerToClientThread>();
	public static void addClientThread(String uid,ServerToClientThread ct){
		hm.put(uid, ct);
	}
	public static ServerToClientThread getClientThread(String uid){
		return (ServerToClientThread) hm.get(uid);
	}
	//删除这个线程
	public static void removeClientThread(String uid){
		hm.remove(uid);
	}
	//返回给用户当前在线的用户
	public static String getAllOnLineUser(){
		Iterator it=hm.keySet().iterator();
		String res="";
		while(it.hasNext()){
			res+=it.next().toString()+" ";
		}
		return res;
	}
}
