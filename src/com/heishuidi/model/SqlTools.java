package com.heishuidi.model;

import com.heishuidi.common.User;
import com.heishuidi.db.MySqlDBHelper;

public class SqlTools {
	MySqlDBHelper mySqlDBHelper;
	//�˷���������ѯ���ݿ��û�������
	public boolean selectUser(User u){
		mySqlDBHelper=new MySqlDBHelper();
		String sql="SELECT user,pwd FROM User";
		boolean b=false;
		if(mySqlDBHelper.SerchUser(sql,u)){
			b=true;
		}
		return b;
	}
}
