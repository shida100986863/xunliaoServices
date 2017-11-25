package com.heishuidi.model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.heishuidi.common.Message;
import com.heishuidi.common.User;
import com.heishuidi.view.Server;

/**
 * ����qq����������ĳ���ͻ��������ӷ�����
 * ��һ���ͻ�����֤�˺����룬�ɹ���������һ���߳�
 * �������������
 * */
public class MyServer {
	public MyServer(){
		try{
			Server.jta.append("�������������������˿�9999\n");
			ServerSocket ss=new ServerSocket(9999);
			//һֱ�ڼ�������һ���ͻ�����֤�˺����룬�ɹ���������һ���߳�
			//�������������
			while(true){
				Socket s=ss.accept();
				ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
				//��һ�η����������û������룬������User������
				User u=(User)ois.readObject();
				//��������Ϣ����������
				Message message=new Message();
				ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
				//if(u.getPasswd().equals("123456")){
				SqlTools sqlTools=new SqlTools();
				if(sqlTools.selectUser(u)){
					//Ŀǰֻ���������жϣ�Ϊ123456���½
					message.setMesType("1");
					//�ѵ�½�ɹ���Ӧ�İ����͸��ͻ���
					oos.writeObject(message);
					//����һ���̣߳��ø��߳���ͻ��˼���ͨ�ţ�����������߳�
					ServerToClientThread stc=new ServerToClientThread(s);
					stc.start();
					//��ͨ���̺߳���̷߳ŵ�hashmap��
					SaveClientThread.addClientThread(u.getUser(), stc);
					//��֪ͨ���������û��������ǵĺ����б�
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
