package com.heishuidi.model;

import java.util.HashMap;
import java.util.Iterator;

public class SaveClientThread {
	//String �����û���¼���û���ServerToClientThread����ͻ����е��߳�
	public static HashMap hm=new HashMap<String,ServerToClientThread>();
	public static void addClientThread(String uid,ServerToClientThread ct){
		hm.put(uid, ct);
	}
	public static ServerToClientThread getClientThread(String uid){
		return (ServerToClientThread) hm.get(uid);
	}
	//ɾ������߳�
	public static void removeClientThread(String uid){
		hm.remove(uid);
	}
	//���ظ��û���ǰ���ߵ��û�
	public static String getAllOnLineUser(){
		Iterator it=hm.keySet().iterator();
		String res="";
		while(it.hasNext()){
			res+=it.next().toString()+" ";
		}
		return res;
	}
}
