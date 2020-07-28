package com.nineclient.qycloud.wcc.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbUtil {
	
	public Connection getCon() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/wx_test?useUnicode=true&characterEncoding=UTF8", 
				"root", 
				"12345678");
		return con;
	}
	
	public void closeCon(Connection con) throws Exception {
		if(con!=null) {
			con.close();
		}
	}
	
	public static void main(String[] args) {
		DbUtil dbUtil = new DbUtil();
		try {
			dbUtil.getCon();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("no");
			e.printStackTrace();
		}
	}
}
