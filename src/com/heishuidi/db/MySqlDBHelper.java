package com.heishuidi.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.heishuidi.common.User;

public class MySqlDBHelper {
	private String DbDriver="org.gjt.mm.mysql.Driver";
	private String DbUrl="jdbc:mysql://127.0.0.1:3306/xunliao";
	private String DbUser="root";
	private String DbPwd="root";
	private Connection conn=null;//���ݿ�����
	private Statement stmt=null;//���ݿ����
	private ResultSet rs=null;//������ѯ��䱣������
	public boolean SerchUser(String sql,User u){
		try{
			Class.forName(DbDriver);
			//���ݿ�����
			conn=DriverManager.getConnection(DbUrl,DbUser,DbPwd);
			stmt=conn.createStatement();//������������ʵ����
			rs=stmt.executeQuery(sql);
			while(rs.next()){
				//���ݿ��ȡ�����û�������
				String user1=rs.getString("user");
				String pwd1=rs.getString("pwd");
				//�������û�������
				String user2=u.getUser();
				String pwd2=u.getPasswd();
				if(user1.equals(user2)&&pwd1.equals(pwd2)){
					return true;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
}
