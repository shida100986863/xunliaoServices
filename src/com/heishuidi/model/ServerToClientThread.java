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
	Message mreturn=null;//���ظ��ͻ��˰�������
	Socket s;
	public ServerToClientThread(Socket s){
		this.s=s;
	}
	//�ø÷������͸���֪ͨ�����û����������û��б�
	//iuser��ǰ���ߵ��û���
//	public void notifyOther(String iuser){
//		//�õ����������˵��߳�
//		HashMap hm=SaveClientThread.hm;
//		Iterator it=hm.keySet().iterator();
//		//ѭ�����������û�����Ϣ
//		while(it.hasNext()){
//			Message m=new Message();
//			//���ص�ǰ���ߵ��û���
//			m.setCon(iuser);
//			m.setMesType(MessageType.message_ret_onLineFriend);
//			//��ȡ�����˵�id
//			String onLineUserId=it.next().toString();
//			System.out.println("��ǰ���ߵ��ǡ�"+onLineUserId+"��");
//			//���͸����ߵĵ�һ���ˡ��ڶ�����������������
//			m.setGetter(onLineUserId);
//			try{
//				ObjectOutputStream oos=new ObjectOutputStream(SaveClientThread.getClientThread(onLineUserId).s.getOutputStream());
//				oos.writeObject(m);
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//		}
//	}
	//���û���½�õ����������˵��߳�
	public void notifyOther(){

		//HashMap <String,ServerToClientThread>hm=SaveClientThread.hm;
		//��ȡ�������ĸ��߳������У��������е��߳�һ����֪ͨ
		String iuser=SaveClientThread.getAllOnLineUser();
		String []friends=iuser.split(" ");
		mreturn=new Message();
		for(int i=0;i<friends.length;i++){
			ServerToClientThread s1=SaveClientThread.getClientThread(friends[i]);
			//���÷���Ϊ��ǰ�����û��İ�
			mreturn.setMesType(MessageType.message_ret_onLineFriend);
			//���÷��ص�����Ϊ��ǰ���ߵ������û����ո�ָ�
			mreturn.setCon(iuser);
			//����˭
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
	//����ʱ֪ͨ�����˸���
	public void notifyOther1(String id){
		mreturn=new Message();
		//�õ����������˵��߳�
		//HashMap <String,ServerToClientThread>hm=SaveClientThread.hm;
		String iuser=SaveClientThread.getAllOnLineUser();
		String []friends=iuser.split(" ");
		for(int i=0;i<friends.length;i++){
			ServerToClientThread s1=SaveClientThread.getClientThread(friends[i]);
			//���÷���Ϊ��ǰ�����û��İ�
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
			//���߳̿���һֱ���տͻ�����Ϣ
			try{
				/**
				 * ��һ�η��͵����û������룬��֤���������
				 * �ڴ����ж��û����������������ݽ��д���
				 * */
				//�½�һ�����������պ����û�����������������
				//һֱ����������ȴ���Ϣ
				ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
				ObjectOutputStream oos=null;
				Message m=(Message)ois.readObject();
				//�Դӿͻ���ȡ�õ���Ϣ���������жϣ�Ȼ������Ӧ�Ĵ���
				//����������ȡ�����û����ѵİ�
				if(m.getMesType().equals(MessageType.message_get_onLineFriend)){
					//System.out.println(m.getSender()+"Ҫ��������");
					//�ѷ��������ߵ����к��Ѹ��ͻ��˷���
					//String res=SaveClientThread.getAllOnLineUser();
					//Message m1=new Message();
					//���ð������ͺ����ߺ��ѵ��б�ͽ�������˭
					//m1.setMesType(MessageType.message_ret_onLineFriend);
					//m1.setCon(res);
					//m1.setGetter(m.getSender());
					//oos=new ObjectOutputStream(s.getOutputStream());
					//oos.writeObject(m1);
					this.notifyOther();//֪ͨ�����û�
				}
				//�������ͨ��ת��
				else if(m.getMesType().equals(MessageType.message_message)){
					Server.jta.append(m.getSender()+"��"+m.getGetter()+"˵��"+m.getCon()+"\n");
					//ȡ�ý����ߵ�ͨѶ�߳�socket
					ServerToClientThread sc=SaveClientThread.getClientThread(m.getGetter());
					//ת����������
					oos=new ObjectOutputStream(sc.s.getOutputStream());
					oos.writeObject(m);
				}
				//��������߰�����ֹ��������������̣�Ȼ��֪ͨ�����û�����
				else if(m.getMesType().equals(MessageType.message_downLine)){
					String id=m.getSender();
					Server.jta.append(id+"������\n");
					b=false;
					ServerToClientThread sc=SaveClientThread.getClientThread(id);
					SaveClientThread.removeClientThread(m.getSender());
					if(sc!=null&&sc.isAlive()){
						Server.jta.append("֪ͨ�ɹ�\n");
						this.notifyOther1(m.getSender());//֪ͨ�����û�
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
