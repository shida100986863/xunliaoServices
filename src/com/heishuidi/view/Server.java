package com.heishuidi.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.heishuidi.model.MyServer;
import com.heishuidi.model.SaveClientThread;

public class Server extends JFrame implements ActionListener{
	JFrame frame=new JFrame("讯聊服务器");
	public static JTextArea jta;//文本显示框显示服务器转发消息
	JScrollPane jsp;//文本显示框添加滚动条
	JPanel jp1;	//容器
	JButton jb_start,jb_stop;//启动停止按钮
	public static void main(String[] args) {
		new Server();
	}
	public Server(){
		//这是背景图片路径   获取图片路径方法，可以打成jar包
		ImageIcon img = new ImageIcon(Server.class.getResource("/image/server_backgroup.jpg"));
		JLabel imgLabel = new JLabel(img);//将背景图放在标签里。
		//注意这里是关键，将背景标签添加到jfram的LayeredPane面板里。
		frame.getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));
		//设置背景标签的位置this.getWidth(), this.getHeight()
		imgLabel.setBounds(0,0,img.getIconWidth(), img.getIconHeight());

		jp1=new JPanel();
		jb_start=new JButton("服务器启动");
		jb_start.addActionListener(this);
		jb_start.setOpaque(false);
		jb_stop=new JButton("服务器关闭");
		jb_stop.addActionListener(this);
		jb_stop.setOpaque(false);
		jp1.add(jb_start);
		jp1.add(jb_stop);	
		jp1.setOpaque(false);
		//文本框要添加的显示消息框
		final ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("/image/server_backgroup.jpg"));
		//重绘背景图片
		jta = new JTextArea() {  
			//Image image = imageIcon.getImage();  黑白的
			//Image grayImage = GrayFilter.createDisabledImage(image);  
			{  
				setOpaque(false);  
			} 
		    public void paint(Graphics g) { 
		    	g.drawImage(imageIcon.getImage(), 0, 0, this);  
		    	super.paint(g);  
		    }  
		}; 
		jta.setForeground(Color.white);
		jta.selectAll();
		jsp=new JScrollPane(jta);  
		jb_start=new JButton("服务器启动");
		jb_start.addActionListener(this);
		jb_start.setOpaque(false);
		jb_stop=new JButton("服务器关闭");
		jb_stop.addActionListener(this);
		jb_stop.setOpaque(false);
		jp1=new JPanel();
		jp1.add(jb_start);//文本显示框
		jp1.add(jb_stop);	//文本输入框
		
		Container content = frame.getContentPane(); //加入到容器
	    content.add(jsp,"Center");
	    content.add(jp1,"South");
	    //注意这里，将内容面板设为透明。这样LayeredPane面板中的背景才能显示出来。
	    ((JPanel)content).setOpaque(false); 
		frame.setIconImage((new ImageIcon(this.getClass().getResource("/image/server_logo.gif")).getImage()));
		frame.setSize(500,400);
		frame.setLocation(400,100);
		frame.setVisible(true);  
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		//如果点击启动按钮钮执行
		if(e.getSource()==jb_start){
			Server_Success m=new Server_Success();
			m.start();
		}
		if(e.getSource()==jb_stop){
			//setVisible(false);//隐藏窗体
			this.dispose();
			System.exit(0);//退出程序
		}
	}
}
