package com.userdetails;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.carrentalsystem.*;
import com.enumdetails.*;
import com.exceptiondetails.InvalidEmailIdException;
/**
 * The Account class represents user account details and provides methods for user registration.
 * This class allows users to register by providing necessary details such as user ID, password, role, etc.
 * It also includes validation for user inputs and email addresses.
 * 
 * @author Sri Prasanna D (Expleo)
 * @since 22 Feb 2024
 */
public class Account {
	private int userid;
	private String password;
	private String role;
	private String userName;
	private VisibilityStatus status;
	
	//Constructors
	public Account() {}
	public Account(int userid) {
		this.userid=userid;
	}
	public Account(int userid,String password,String role) {
		this.userid=userid;
		this.password=password;
		this.role=role;
		this.status=VisibilityStatus.ACTIVE;
	}
	public Account(int userid,String password,String role,String userName,String status) {
		this.userid=userid;
		this.password=password;
		this.role=role;
		this.userName=userName;
		this.status=VisibilityStatus.valueOf(status);
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	//Getters and setters
	public int getUserid() {
		return userid;
	}

	public String getPassword() {
		return password;
	}

	public String getRole() {
		return role;
	}
	public String getStatus() {
		return status.toString();
	}
	public void setStatus(VisibilityStatus status) {
		this.status = status;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	static Register reg=null;//Instance of Register class
    static Scanner sc = new Scanner(System.in);
     
     String ANSI_RESET = "\u001B[0m";
     String ANSI_RED = "\u001B[31m";
     static int customerId=1;
     public static int AutoCustomerID()/*This Method Returns userid */
 	{
 		try{
 			Connection con=DbConnection.getConn();
 			Statement st=con.createStatement();
 			ResultSet rs=st.executeQuery("Select MAX(customer_id) from car.customer");
 			rs.next();
 			customerId = rs.getInt(1);
 			if(rs.wasNull())
 			{
 				return 1;
 			}
 		}catch(Exception e)
 		{
 			System.out.println(e.getMessage());
 		}
 		return customerId+2;
 	}

    public void registerUser(int userid) { //Method to register a user
    	int custid=0;
    	String firstName, lastName, email, gender, city, state, country, address, password;
        long mobile, licenseNumber;
        int age;
    		System.out.print("\n\t\t\t----Please enter the following User details-----");

    		    custid = AutoCustomerID();        
    		    firstNameLoop:
    		    	while (true) {
    		    	    try {
    		    	        System.out.print("\n\tEnter your first name:");
    		    	        firstName = sc.next();

    		    	        if (Regex.isValidName(firstName)) {
    		    	            throw new InvalidInputException("\tInvalid first name! Please retry.");
    		    	        } else {
    		    	            break;
    		    	        }
    		    	    } catch (Exception e) {
    		    	        System.out.println(e.getMessage());
    		    	    }
    		    	}

    		    	// Last name validation loop
    		    	lastNameLoop:
    		    	while (true) {
    		    	    try {
    		    	        System.out.print("\n\tEnter your last name:");
    		    	        lastName = sc.next();

    		    	        if (Regex.isValidName(lastName)) {
    		    	            throw new InvalidInputException("Invalid last name! Please retry.");
    		    	        } else {
    		    	            break;
    		    	        }
    		    	    } catch (Exception e) {
    		    	        System.out.println(e.getMessage());
    		    	    }
    		    	}

    		    	// Email validation loop
    		    	emailLoop:
    		    	while (true) {
    		    	    try {
    		    	        System.out.print("\n\tEnter the email address:");
    		    	        email = sc.next();

    		    	        if (!Regex.isValidEmail(email)) {
    		    	            throw new InvalidInputException("Invalid email address! Please retry.");
    		    	        } else {
    		    	            break;
    		    	        }
    		    	    } catch (Exception e) {
    		    	        System.out.println(e.getMessage());
    		    	    }
    		    	}

    		    	// Mobile number validation loop
    		    	mobileLoop:
    		    	while (true) {
    		    	    try {
    		    	        System.out.print("\n\tEnter your mobile number:");
    		    	        mobile = sc.nextLong();

    		    	        if (!Regex.isValidMobile(String.valueOf(mobile))) {
    		    	            throw new InvalidInputException("Invalid mobile number! Please retry.");
    		    	        } else {
    		    	            break;
    		    	        }
    		    	    } catch (Exception e) {
    		    	        System.out.println(e.getMessage());
    		    	    }
    		    	}

    		    	// Age validation loop
    		    	ageLoop:
    		    	while (true) {
    		    	    try {
    		    	        System.out.print("\n\tEnter your age:");
    		    	        age = sc.nextInt();

    		    	        if (!Regex.isValidAge(age)) {
    		    	            throw new InvalidInputException("Invalid age! Please retry.");
    		    	        } else {
    		    	            break;
    		    	        }
    		    	    } catch (Exception e) {
    		    	        System.out.println(e.getMessage());
    		    	    }
    		    	}

    		    	// Gender validation loop
    		    	genderLoop:
    		    	while (true) {
    		    	    try {
    		    	        System.out.print("\n\tEnter your gender (Male/Female/Transgender):");
    		    	        gender = sc.next();

    		    	        if (!Regex.isValidGender(gender)) {
    		    	            throw new InvalidInputException("Invalid gender! Please retry.");
    		    	        } else {
    		    	            break;
    		    	        }
    		    	    } catch (Exception e) {
    		    	        System.out.println(e.getMessage());
    		    	    }
    		    	}
    		    	
    		    	// Address validation loop
    		    	addressLoop:
    		    	while (true) {
    		    	    try {
    		    	        System.out.print("\n\tEnter your address:");
    		    	        address = sc.next();
    		    	        
    		    	        if (Regex.isValidName(address)) {
    		    	            throw new InvalidInputException("Invalid Address! Please retry.");
    		    	        } else {
    		    	            break;
    		    	        }
    		    	    } catch (Exception e) {
    		    	        System.out.println(e.getMessage());
    		    	    }
    		    	}

    		    	// City validation loop
    		    	cityLoop:
    		    	while (true) {
    		    	    try {
    		    	        System.out.print("\n\tEnter your city:");
    		    	        city = sc.next();

    		    	        if (Regex.isValidName(city)) {
    		    	            throw new InvalidInputException("Invalid city name! Please retry.");
    		    	        } else {
    		    	            break;
    		    	        }
    		    	    } catch (Exception e) {
    		    	    System.out.println(e.getMessage());
    		    	    }
    		    	}

    		    	// State validation loop
    		    	stateLoop:
    		    	while (true) {
    		    	    try {
    		    	        System.out.print("\n\tEnter your state:");
    		    	        state = sc.next();

    		    	        if (Regex.isValidName(state)) {
    		    	            throw new InvalidInputException("Invalid state name! Please retry.");
    		    	        } else {
    		    	            break;
    		    	        }
    		    	    } catch (Exception e) {
    		    	        System.out.println(e.getMessage());
    		    	    }
    		    	}

    		    	// Country validation loop
    		    	countryLoop:
    		    	while (true) {
    		    	    try {
    		    	        System.out.print("\n\tEnter your country:");
    		    	        country = sc.next();

    		    	        if (Regex.isValidName(country)) {
    		    	            throw new InvalidInputException("Invalid country name! Please retry.");
    		    	        } else {
    		    	            break;
    		    	        }
    		    	    } catch (Exception e) {
    		    	        System.out.println(e.getMessage());
    		    	    }
    		    	}

    		    	// License number validation loop
    		    	licenseNumberLoop:
    		    	while (true) {
    		    	    try {
    		    	        System.out.print("\n\tEnter your license number:");
    		    	        licenseNumber = sc.nextLong();

    		    	        if (!Regex.isValidLicenseNumber(String.valueOf(licenseNumber))) {
    		    	            throw new InvalidInputException("Invalid license number! Please retry.");
    		    	        } else {
    		    	            break;
    		    	        }
    		    	    } catch (Exception e) {
    		    	        System.out.println(e.getMessage());
    		    	    }
    		    	}

    		    	

    		    	// Create Customer object with input details
    	    	    Customer customer = new Customer(userid, custid, firstName, lastName, email, mobile, age, gender, city, state, country, address, licenseNumber);
    	   
    	    	    // Register customer using Register class instance
    			    reg=new Register();
    			    reg.Customer_Registration(customer);                                                                                    
    		   

	}
}
