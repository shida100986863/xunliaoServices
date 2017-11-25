package com.heishuidi.view;

import com.heishuidi.model.MyServer;
//启动接收客户端通信端口的线程。服务器可以做别的
public class Server_Success extends Thread{
	public void run(){
		new MyServer();
	}
}
