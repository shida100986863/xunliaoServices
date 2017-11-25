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
	private Connection conn=null;//数据库连接
	private Statement stmt=null;//数据库操作
	private ResultSet rs=null;//用作查询语句保存结果集
	public boolean SerchUser(String sql,User u){
		try{
			Class.forName(DbDriver);
			//数据库连接
			conn=DriverManager.getConnection(DbUrl,DbUser,DbPwd);
			stmt=conn.createStatement();//加载驱动程序实例化
			rs=stmt.executeQuery(sql);
			while(rs.next()){
				//数据库获取来的用户名密码
				String user1=rs.getString("user");
				String pwd1=rs.getString("pwd");
				//传来的用户名密码
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
