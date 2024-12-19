package com.carrentalsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DbConnection {
	public static Connection getConn() {
		try {
			final String URL="jdbc:oracle:thin:@localhost:1521:xe";
			String UserName="SYSTEM";
			String Password="12345678";
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn=DriverManager.getConnection(URL, UserName, Password);
//			if(conn!=null) {System.out.println("Db connected!!");}
//			else {System.out.println("Db not yet connected!!");}
			return conn;
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	

}