package cn.shb.test;

import java.sql.*;

public class DBTools {
   // JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost/wx_test";

   //  Database credentials
   static final String USER = "root";
   static final String PASS = "12345678";
   
   public static int delRecord (){

	   Connection conn = null;
	   Statement stmt = null;
	   int res = 0 ;
	   try{
	      //STEP 2: Register JDBC driver
	      Class.forName("com.mysql.jdbc.Driver");

	      //STEP 3: Open a connection
	      System.out.println("Connecting to a selected database...");
	      conn = DriverManager.getConnection(DB_URL, USER, PASS);
	      System.out.println("Connected database successfully...");
	      
	      //STEP 4: Execute a query
	      System.out.println("Creating statement...");
	      stmt = conn.createStatement();
	      String sql = "DELETE  FROM t_dormbuild " ;
	       res = stmt.executeUpdate(sql);
	      // Now you can extract all the records
	      // to see the remaining records
	      
	   }catch(SQLException se){
	      //Handle errors for JDBC
	      se.printStackTrace();
	   }catch(Exception e){
	      //Handle errors for Class.forName
	      e.printStackTrace();
	   }finally{
	      //finally block used to close resources
	      try{
	         if(stmt!=null)
	            conn.close();
	      }catch(SQLException se){
	      }// do nothing
	      try{
	         if(conn!=null)
	            conn.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }//end finally try
	   }//end try
	   System.out.println("Goodbye!");
      return res;
   }
   
   public static int addData(String parkName,String num,int id){


	   Connection conn = null;
	   Statement stmt = null;
	   int res = 0 ;
	   try{
	      //STEP 2: Register JDBC driver
	      Class.forName("com.mysql.jdbc.Driver");

	      //STEP 3: Open a connection
	      System.out.println("Connecting to a selected database...");
	      conn = DriverManager.getConnection(DB_URL, USER, PASS);
	      System.out.println("Connected database successfully...");
	      
	      //STEP 4: Execute a query
	      System.out.println("Creating statement...");
	      stmt = conn.createStatement();
	    /*  String sql = "insert into t_dormbuild values("+id+",'"+parkName+"','"+num+"',0,0) ";*/
	      String sql = "update t_dormbuild set  dormBuildDetail = '"+num+"'  where  dormBuildName = '"+parkName+"' ";
	       res = stmt.executeUpdate(sql);
	      // Now you can extract all the records
	      // to see the remaining records
	      
	   }catch(SQLException se){
	      //Handle errors for JDBC
	      se.printStackTrace();
	   }catch(Exception e){
	      //Handle errors for Class.forName
	      e.printStackTrace();
	   }finally{
	      //finally block used to close resources
	      try{
	         if(stmt!=null)
	            conn.close();
	      }catch(SQLException se){
	      }// do nothing
	      try{
	         if(conn!=null)
	            conn.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }//end finally try
	   }//end try
      return res;
	   
   }
   
   public static void main(String[] args) {
	   System.out.println(delRecord());
	   System.out.println(addData("dwdw", "1122", 22));
   }//end main
}//end JDBC