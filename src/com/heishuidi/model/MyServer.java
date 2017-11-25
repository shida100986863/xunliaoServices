package com.heishuidi.model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.heishuidi.common.Message;
import com.heishuidi.common.User;
import com.heishuidi.view.Server;

/**
 * 这是qq服务器监听某个客户端来连接服务器
 * 来一个客户端验证账号密码，成功后分配给他一个线程
 * 并保存这个连接
 * */
public class MyServer {
	public MyServer(){
		try{
			Server.jta.append("服务器已启动。监听端口9999\n");
			ServerSocket ss=new ServerSocket(9999);
			//一直在监听，来一个客户端验证账号密码，成功后分配给他一个线程
			//并保存这个连接
			while(true){
				Socket s=ss.accept();
				ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
				//第一次发送来的是用户名密码，所以用User包接收
				User u=(User)ois.readObject();
				//后续用消息包发送数据
				Message message=new Message();
				ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
				//if(u.getPasswd().equals("123456")){
				SqlTools sqlTools=new SqlTools();
				if(sqlTools.selectUser(u)){
					//目前只对密码做判断，为123456则登陆
					message.setMesType("1");
					//把登陆成功回应的包发送给客户端
					oos.writeObject(message);
					//单开一个线程，让该线程与客户端继续通信，并保存这个线程
					ServerToClientThread stc=new ServerToClientThread(s);
					stc.start();
					//开通完线程后把线程放到hashmap中
					SaveClientThread.addClientThread(u.getUser(), stc);
					//并通知其他在线用户更新他们的好友列表
					stc.notifyOther();
				}
				else{
					message.setMesType("2");
					oos.writeObject(message);
					s.close();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
