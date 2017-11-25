package com.heishuidi.model;

import com.heishuidi.common.User;
import com.heishuidi.db.MySqlDBHelper;

public class SqlTools {
	MySqlDBHelper mySqlDBHelper;
	//此方法用作查询数据库用户名密码
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
