package com.heishuidi.view;

import com.heishuidi.model.MyServer;
//�������տͻ���ͨ�Ŷ˿ڵ��̡߳����������������
public class Server_Success extends Thread{
	public void run(){
		new MyServer();
	}
}
