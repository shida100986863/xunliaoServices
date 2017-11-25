package com.heishuidi.model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Savepoint;
import java.util.HashMap;
import java.util.Iterator;

import com.heishuidi.common.Message;
import com.heishuidi.common.MessageType;
import com.heishuidi.view.Server;

public class ServerToClientThread extends Thread{
	Message mreturn=null;//返回给客户端包的类型
	Socket s;
	public ServerToClientThread(Socket s){
		this.s=s;
	}
	//让该方法发送个包通知其他用户更新在线用户列表
	//iuser当前上线的用户名
//	public void notifyOther(String iuser){
//		//得到所有在线人的线程
//		HashMap hm=SaveClientThread.hm;
//		Iterator it=hm.keySet().iterator();
//		//循环遍历在线用户发消息
//		while(it.hasNext()){
//			Message m=new Message();
//			//返回当前上线的用户名
//			m.setCon(iuser);
//			m.setMesType(MessageType.message_ret_onLineFriend);
//			//获取在线人的id
//			String onLineUserId=it.next().toString();
//			System.out.println("当前在线的是【"+onLineUserId+"】");
//			//发送给在线的第一个人、第二个、第三个、、、
//			m.setGetter(onLineUserId);
//			try{
//				ObjectOutputStream oos=new ObjectOutputStream(SaveClientThread.getClientThread(onLineUserId).s.getOutputStream());
//				oos.writeObject(m);
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//		}
//	}
	//新用户登陆得到所有在线人的线程
	public void notifyOther(){

		//HashMap <String,ServerToClientThread>hm=SaveClientThread.hm;
		//获取服务器哪个线程在运行，根据运行的线程一个个通知
		String iuser=SaveClientThread.getAllOnLineUser();
		String []friends=iuser.split(" ");
		mreturn=new Message();
		for(int i=0;i<friends.length;i++){
			ServerToClientThread s1=SaveClientThread.getClientThread(friends[i]);
			//设置返回为当前在线用户的包
			mreturn.setMesType(MessageType.message_ret_onLineFriend);
			//设置返回的内容为当前在线的所有用户，空格分隔
			mreturn.setCon(iuser);
			//发给谁
			mreturn.setGetter(friends[i]);
			try{
				//ServerToClientThread s=((ServerToClientThread)it.next());
				ObjectOutputStream oos=new ObjectOutputStream(s1.s.getOutputStream());
				oos.writeObject(mreturn);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	//下线时通知其他人更新
	public void notifyOther1(String id){
		mreturn=new Message();
		//得到所有在线人的线程
		//HashMap <String,ServerToClientThread>hm=SaveClientThread.hm;
		String iuser=SaveClientThread.getAllOnLineUser();
		String []friends=iuser.split(" ");
		for(int i=0;i<friends.length;i++){
			ServerToClientThread s1=SaveClientThread.getClientThread(friends[i]);
			//设置返回为当前在线用户的包
			mreturn.setMesType(MessageType.message_downLine);
			mreturn.setCon(iuser);
			mreturn.setGetter(friends[i]);
			try{
				//ServerToClientThread s=((ServerToClientThread)it.next());
				if(s1!=null&&s1.isAlive()){
					ObjectOutputStream oos=new ObjectOutputStream(s1.s.getOutputStream());
					oos.writeObject(mreturn);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	public void run(){
		boolean b=true;
		while(b){
			//该线程可以一直接收客户端消息
			try{
				/**
				 * 第一次发送的是用户名密码，验证完后进入此类
				 * 在此类中对用户后来发送来的数据进行处理
				 * */
				//新建一个输入流接收后续用户发给服务器的数据
				//一直阻塞在这里等待消息
				ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
				ObjectOutputStream oos=null;
				Message m=(Message)ois.readObject();
				//对从客户端取得的消息进行类型判断，然后做相应的处理
				//如果是请求获取在线用户好友的包
				if(m.getMesType().equals(MessageType.message_get_onLineFriend)){
					//System.out.println(m.getSender()+"要他的朋友");
					//把服务器在线的所有好友给客户端返回
					//String res=SaveClientThread.getAllOnLineUser();
					//Message m1=new Message();
					//设置包的类型和在线好友的列表和接收者是谁
					//m1.setMesType(MessageType.message_ret_onLineFriend);
					//m1.setCon(res);
					//m1.setGetter(m.getSender());
					//oos=new ObjectOutputStream(s.getOutputStream());
					//oos.writeObject(m1);
					this.notifyOther();//通知其他用户
				}
				//如果是普通包转发
				else if(m.getMesType().equals(MessageType.message_message)){
					Server.jta.append(m.getSender()+"给"+m.getGetter()+"说："+m.getCon()+"\n");
					//取得接收者的通讯线程socket
					ServerToClientThread sc=SaveClientThread.getClientThread(m.getGetter());
					//转发给接受者
					oos=new ObjectOutputStream(sc.s.getOutputStream());
					oos.writeObject(m);
				}
				//如果是下线包则终止服务器里这个进程，然后通知其他用户更新
				else if(m.getMesType().equals(MessageType.message_downLine)){
					String id=m.getSender();
					Server.jta.append(id+"下线了\n");
					b=false;
					ServerToClientThread sc=SaveClientThread.getClientThread(id);
					SaveClientThread.removeClientThread(m.getSender());
					if(sc!=null&&sc.isAlive()){
						Server.jta.append("通知成功\n");
						this.notifyOther1(m.getSender());//通知其他用户
						ois.close();
						sc.s.close();
						sc.stop();
						System.exit(0);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	} 
}
