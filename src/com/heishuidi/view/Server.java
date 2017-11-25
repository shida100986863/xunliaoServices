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
	JFrame frame=new JFrame("Ѷ�ķ�����");
	public static JTextArea jta;//�ı���ʾ����ʾ������ת����Ϣ
	JScrollPane jsp;//�ı���ʾ����ӹ�����
	JPanel jp1;	//����
	JButton jb_start,jb_stop;//����ֹͣ��ť
	public static void main(String[] args) {
		new Server();
	}
	public Server(){
		//���Ǳ���ͼƬ·��   ��ȡͼƬ·�����������Դ��jar��
		ImageIcon img = new ImageIcon(Server.class.getResource("/image/server_backgroup.jpg"));
		JLabel imgLabel = new JLabel(img);//������ͼ���ڱ�ǩ�
		//ע�������ǹؼ�����������ǩ��ӵ�jfram��LayeredPane����
		frame.getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));
		//���ñ�����ǩ��λ��this.getWidth(), this.getHeight()
		imgLabel.setBounds(0,0,img.getIconWidth(), img.getIconHeight());

		jp1=new JPanel();
		jb_start=new JButton("����������");
		jb_start.addActionListener(this);
		jb_start.setOpaque(false);
		jb_stop=new JButton("�������ر�");
		jb_stop.addActionListener(this);
		jb_stop.setOpaque(false);
		jp1.add(jb_start);
		jp1.add(jb_stop);	
		jp1.setOpaque(false);
		//�ı���Ҫ��ӵ���ʾ��Ϣ��
		final ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("/image/server_backgroup.jpg"));
		//�ػ汳��ͼƬ
		jta = new JTextArea() {  
			//Image image = imageIcon.getImage();  �ڰ׵�
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
		jb_start=new JButton("����������");
		jb_start.addActionListener(this);
		jb_start.setOpaque(false);
		jb_stop=new JButton("�������ر�");
		jb_stop.addActionListener(this);
		jb_stop.setOpaque(false);
		jp1=new JPanel();
		jp1.add(jb_start);//�ı���ʾ��
		jp1.add(jb_stop);	//�ı������
		
		Container content = frame.getContentPane(); //���뵽����
	    content.add(jsp,"Center");
	    content.add(jp1,"South");
	    //ע����������������Ϊ͸��������LayeredPane����еı���������ʾ������
	    ((JPanel)content).setOpaque(false); 
		frame.setIconImage((new ImageIcon(this.getClass().getResource("/image/server_logo.gif")).getImage()));
		frame.setSize(500,400);
		frame.setLocation(400,100);
		frame.setVisible(true);  
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO �Զ����ɵķ������
		//������������ťťִ��
		if(e.getSource()==jb_start){
			Server_Success m=new Server_Success();
			m.start();
		}
		if(e.getSource()==jb_stop){
			//setVisible(false);//���ش���
			this.dispose();
			System.exit(0);//�˳�����
		}
	}
}
