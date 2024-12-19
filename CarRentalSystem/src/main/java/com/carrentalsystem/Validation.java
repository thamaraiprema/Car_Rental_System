package com.carrentalsystem;

import java.sql.*;
import java.util.Scanner;

import com.exceptiondetails.*;
import com.userdetails.Account;
import com.userdetails.Admin;
import com.userdetails.Customer;

public class Validation {
	static Scanner sc=new Scanner(System.in);
	static Account account = new Account();
	public static void checkUserid(int id) throws InvalidChoiceException, InvalidValidationException {
		try {
			Connection con = DbConnection.getConn();	
            String query = "SELECT COUNT(*) FROM car.login_credentials WHERE user_id = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count == 0) {
                    throw new InvalidUserIDException("User ID '" + id + "' is not available.\nWe're sorry, but you don't currently have login credentials( For security purpose ).\n To obtain access, please contact the administrator to add your login credentials.");
                } else {
                    System.out.println();
                }
            }
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		catch (InvalidUserIDException e) {
			System.out.println("\t\tWe're sorry, but you don't currently have login credentials\n\t\t\tAdd Yours login credentials for further!");
//			System.out.println(e.getMessage());
			System.out.println("\tWhat do u want to do now?? \n\t1.Registering the user id\n\t2.Login using valid user id\n\t3.Go to previous page");
			System.out.print("\tEnter your choice:");int choice=sc.nextInt();
			Admin admin=new Admin();
			switch(choice) {
			case 1:
				admin.addUser();
				break;
			case 2:
				admin.addUser();
				break;
			case 3:
				Customer customer=new Customer();
				customer.customerEntry();
				
			default:
				System.out.println("\tProvide valid choice!!");
				break;
				
			}
			
	       
	    }
	}

}
