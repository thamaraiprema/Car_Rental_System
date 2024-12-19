	package com.carrentalsystem;
	
	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.util.concurrent.ConcurrentSkipListSet;

import com.userdetails.Customer;

	
	public class Register {
		public static int AutoLocationID()/*This Method Returns InvoiceID */
		{
			int locationId=0;
			try{
				Connection con=DbConnection.getConn();
				Statement st=con.createStatement();
				ResultSet rs=st.executeQuery("Select MAX(loc_id) from car.Location");
				rs.next();
				locationId = rs.getInt(1);
				if(rs.wasNull())
				{
					return 1;
				}
			}catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
			return locationId+2;
		}
		Scanner sc=new Scanner(System.in);
		public void Customer_Registration(Customer customer) {
			try 
			{
				  Connection con = DbConnection.getConn();				 
				  
				  PreparedStatement sts = con.prepareStatement("INSERT INTO CAR.Customer VALUES (?,?,?,?,?,?,?,?,?,?)");
				 
		          sts.setInt(1,customer.getUserid());
		          sts.setInt(2, customer.getCustid());
		          
		          sts.setString(3, customer.getfirst_Name());
		          sts.setString(4, customer.getlast_Name());
		          sts.setString(5, customer.getEmail_Address());
		          sts.setLong(6, customer.getPhoneNumber());
		          sts.setInt(7, customer.getAge());
		          sts.setString(8, customer.getAddress());
	              sts.setLong(9, customer.getLicense_number());
	              sts.setString(10, customer.getGender());
	              sts.execute();
	              
	              
	              PreparedStatement stsLocation = con.prepareStatement("INSERT INTO CAR.Location  VALUES (?,?, ?, ?)");
//	              System.out.println("Enter location id:");
	              int locid=AutoLocationID();
	              stsLocation.setInt(1, locid);
	              stsLocation.setString(2, customer.getCity());
	              stsLocation.setString(3, customer.getState());
	              stsLocation.setString(4, customer.getCountry());
	              stsLocation.executeUpdate(); // Use executeUpdate for INSERT, not execute

	              System.out.println("\t\tRegistered Successfully!!");
	
				
			}
			catch(Exception e)
			{ System.out.println(e.getMessage());} 
			
		}
		public void CarRegistration(Car car) {
			try {
				Connection con = DbConnection.getConn();
				PreparedStatement sts = con.prepareStatement("INSERT INTO CAR.Car VALUES (?,?,?,?,?,?,?)");
				sts.setInt(1,car.getCarId());
				sts.setString(2, car.getBrand());
				sts.setString(3, car.getModel());
				sts.setDouble(4, car.getRentalPricePerDay());
				sts.setString(5, car.isAvailable());
				sts.setString(6, car.getLocation());
				sts.setString(7, car.getIsVisible());
				
				sts.executeUpdate();
				System.out.println("\t\tNew car added successfully!!!");
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	
