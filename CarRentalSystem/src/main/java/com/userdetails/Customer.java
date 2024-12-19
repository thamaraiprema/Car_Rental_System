package com.userdetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.bookingdetails.Bill;
import com.bookingdetails.Booking;
import com.bookingdetails.Cancellation;
import com.bookingdetails.Returning;
import com.carrentalsystem.DbConnection;
import com.carrentalsystem.DriverCarRentalApp;
import com.carrentalsystem.ProfileActivity;
import com.exceptiondetails.InvalidChoiceException;
import com.exceptiondetails.InvalidUserIDException;
import com.exceptiondetails.InvalidValidationException;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Customer extends User{
	Scanner sc=new Scanner(System.in);
	private int custid;
	private String email_Address;
	private long phoneNumber;
	private int age;
	private String city;
	private String state;
	private String country;
	private String address;
	private long license_number;
	private String gender;
	private int userid;
	private Account account;
	//Constructor
	public Customer() {}
	public Customer(int userid, int custid, String first_Name, String last_Name, String email_Address, long phoneNumber, int age, String gender, String city, String state, String country, String address, long license_number) {
        // Initialize the account object with the provided userid
		this.account=new Account(userid);
        this.userid = account.getUserid();
        this.custid = custid;
        setFirst_Name(first_Name);
        setLast_Name(last_Name);
        this.email_Address = email_Address;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.gender = gender;
        this.city = city;
        this.state = state;
        this.country = country;
        this.address = address;
        this.license_number = license_number;
    }
	public Customer(int userId,int custId,String firstName,String lastName,String email,long phoneNo,int age,String address,long licenseNo,String gender) {
		this.account=new Account(userid);
        this.userid = account.getUserid();
	      this.custid = custId;
	        setFirst_Name(firstName);
	        setLast_Name(lastName);
	        account.setUserid(userId);
	        this.email_Address = email;
	        this.phoneNumber = phoneNo;
	        this.age = age;
	        this.gender = gender;
	        this.address = address;
	        this.license_number = licenseNo;
	}
	//Getters
	public String getEmail_Address() {
		return email_Address;
	}
	public long getPhoneNumber() {
		return phoneNumber;
	}
	public int getAge() {
		return age;
	}
	public String getCity() {
		return city;
	}
	public String getState() {
		return state;
	}
	public String getCountry() {
		return country;
	}
	public String getAddress() {
		return address;
	}
	public long getLicense_number() {
		return license_number;
	}
	
	public String getGender() {
		return gender;
	}
	
	public int getCustid() {
		return custid;
	}
	public void setCustid(int custid) {
		this.custid = custid;
	}
	//Setters
	public void setEmail_Address(String email_Address) {
		this.email_Address = email_Address;
	}
	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setCountry(String country) {
    	this.country = country;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setLicense_number(long license_number) {
		this.license_number = license_number;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	@Override
	public String toString() {
	    // Pad strings with spaces to achieve uniform column width
	    String formattedCustId = String.format("%-6d", custid);
	    String formattedUserid = String.format("%-12s", account.getUserid());
	    String formattedFirstName = String.format("%-12s", this.getfirst_Name());
	    String formattedLastName = String.format("%-12s", this.getlast_Name());
	    String formattedEmail = String.format("%-28s", email_Address); // Adjust width to accommodate the longest email address
	    String formattedPhoneNumber = String.format("%-12s", phoneNumber);
	    String formattedAge = String.format("%-3d", age);
	    String formattedAddress = String.format("%-18s", address); // Adjust width to accommodate longer addresses
	    String formattedLicenseNumber = String.format("%-15d", license_number);
	    String formattedGender = String.format("%-6s", gender);

	    return String.format("%s | %s | %s | %s | %s | %s | %s | %s | %s | %s",
	            formattedCustId, formattedUserid, formattedFirstName, formattedLastName, formattedEmail,
	            formattedPhoneNumber, formattedAge, formattedAddress, formattedLicenseNumber, formattedGender);
	}

	
	
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public void setA(ProfileActivity a) {
		this.a = a;
	}
	public static void setAdmin(Admin admin) {
		Customer.admin = admin;
	}
	public void setAcc(Account acc) {
		this.acc = acc;
	}
	
	static ProfileActivity a=new ProfileActivity();
    static Admin admin=new Admin();
    static Account acc=new Account();
	String ANSI_RESET = "\u001B[0m";
    String ANSI_RED = "\u001B[31m";
    
	public void customerEntry() throws InvalidChoiceException, InvalidValidationException {
		System.out.print("\t**********************************************************************************************\n");
        System.out.print("\t*                                                                                            *\n");
        System.out.print("\t*                        1.Already have account - LOGIN                                      *\n");
        System.out.print("\t*                        2.Register new account - SIGNUP                                     *\n");
        System.out.print("\t*                        3.Go to Home page                                                   *\n");
        System.out.print("\t*                        4.Exit                                                              *\n");
        System.out.print("\t*                                                                                            *\n");
        System.out.print("\t**********************************************************************************************\n");	
        System.out.print("\t\tEnter your choice:");String op=sc.next();
        switch(op) {
	        case "1":
	        	customerLogin();
		    	break;
	        case "2":
	        	System.out.println("\t\t*****************Welcome to Customer Registration portal***********************");
		    	System.out.println("\t\tFor Registartion !!");
		    	admin.addUser();
		    	break;
	        case "3":
	        	DriverCarRentalApp.main(null);
	        	break;
	        case "4":
	        	// Exit the application
		    	System.out.println("\t\t----------> Thank you for visiting our application :) <----------------");
		    	System.exit(0);
	        default:
	        	try {
		        	throw new InvalidChoiceException("\t\tYour choice is not allowed !! please provide values from 1-4 !!");
	        	}catch(InvalidChoiceException e) {
	        		System.out.println(ANSI_RED +"\t\t"+ e.getMessage() + ANSI_RESET);
	        	}
	        	
	        }
	}
	public void customerLogin() throws InvalidChoiceException, InvalidValidationException {
		List<Account> loginCredentialsList = new ArrayList<>();
    	boolean checkCustomer = false;
    	int flag=0;
    	System.out.println("\t\t*****************Welcome to Customer portal***********************");
    	String id="",pd="";
    	try {
    		System.out.print("\t\tUser name:");id=sc.next();
    		System.out.print("\t\tPassword:");pd=sc.next();
//        	throw new InvalidUserIDException("Invalid input provided !!");
    	}catch(Exception e) {
    		System.out.println("\tWrong input provided !!");
    	}
    	int userId=0;
    	try {
			Connection con=DbConnection.getConn();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("Select * from car.Login_credentials");
			 while (rs.next()) {
		            userId = rs.getInt(1);
		            String userPassword = rs.getString(2);
		            String userRole = rs.getString(3);
		            String status =rs.getString(4);
		            String userName= rs.getString(5);
		            loginCredentialsList.add(new Account(userId, userPassword, userRole,userName,status));
		     }
			 
			 for (Account loginCredentials : loginCredentialsList) {
			        if (loginCredentials.getUserName().equals(id) && loginCredentials.getPassword().equals(pd) && loginCredentials.getRole().equals("customer") && loginCredentials.getStatus().equals("ACTIVE")) {
			            flag=1;
			        }
			  }
		}catch(Exception e){
			System.out.println(ANSI_RED +"\t\t"+ e.getMessage() + ANSI_RESET);
		}
    	if(flag==1)
    	{
    		System.out.println("\t\t----> Welcome "+id+" !! you have logged in successfully !! <-------");
    		while(true)
    		{
    			System.out.print("\t**********************************************************************************************\n");
    	        System.out.print("\t*                                                                                            *\n");
    	        System.out.print("\t*                               1.View your Profile                                          *\n");
    	        System.out.print("\t*                               2.Edit Profile details                                       *\n");
    	        System.out.print("\t*                               3.View All car details                                       *\n");
    	        System.out.print("\t*                               4.Reserve the car                                            *\n");
    	        System.out.print("\t*                               5.Cancellation of car                                        *\n");
    	        System.out.print("\t*                               6.Return the car                                             *\n");
    	        System.out.print("\t*                               7.Generate bill                                              *\n");
    	        System.out.print("\t*                               8.Give FeedBack                                              *\n");
    	        System.out.print("\t*                               9.Logout                                                     *\n");
    	        System.out.print("\t*                                                                                            *\n");
    	        System.out.print("\t**********************************************************************************************\n");
    			System.out.print("\t\tEnter your choice:");String ch=sc.next();
    			try{
    				Connection con=DbConnection.getConn();
    				Statement st=con.createStatement();
    				ResultSet rs=st.executeQuery("Select customer_id from car.customer where user_id=(select user_id from car.login_credentials where username='"+id+"')");
    				rs.next();
    				custid = rs.getInt(1);
    			}catch(Exception e)
    			{
//    				System.out.println(e.getMessage());
    			}
    			switch(ch)
    			{
    				case "1":
    				{
    					
    					showProfileDetails(custid);
    					break;
    				}
    				case "2":
    				{
    					updateProfileDetails(custid);
    					break;
    				}
    				case "3":
    				{
    					admin.viewCarDetails();
    					break;
    				}
    				
    				case "4":
    				{
    					reserveCar(userId);
    					break;
    				}
				    case "5":
				    {
				    	cancelCar(userId) ;
				    	break;
				    }
				    case "6":
				    {
				    	Returning return1=new Returning();
						return1.returningCar(userId);
						break;
				    }
    				case "7":
    				{
    					Bill obj=new Bill();
    					obj.generateBill(userId);
    					break;
    				}
    				case "8":
    				{
    					giveFeedback();
    					break;
    				}
    				case "9":
    				{
    					System.out.println("\t\t\t ------->Logged out successfully <-------");
    					checkCustomer = true;
    					break;
    				}
    				
    				default:
    				{
    					try {
    					throw new InvalidChoiceException("Your choice is not allowed !! please select between 1-9!!");
    					}catch(InvalidChoiceException e) {
    						System.out.println(ANSI_RED +"\t\t"+ e.getMessage() + ANSI_RESET);
    					}
    				}
    			}
    			if(checkCustomer)
    				break;
    		}
    	}
    	else
		{
    		try {
    			throw new InvalidValidationException("\tSorry !! you have provide a wrong UserID or Password \n\t\tPlease recheck once and try again :)");
    		}catch(Exception e) {
    			System.out.println(ANSI_RED +"\n\t"+ e.getMessage() + ANSI_RESET);
    			System.out.println();
    			customerLogin();
    		}
		}
	}
	public void showProfileDetails(int custid) {
		a.viewProfileDetails(custid);
	}
	public void updateProfileDetails(int custid) {
		System.out.println("\t\t\t################################");
		System.out.println("\t\t\t#      1.Email                 #");
		System.out.println("\t\t\t#      2.Phone number          #");
		System.out.println("\t\t\t#      3.Age                   #");
		System.out.println("\t\t\t#      4.License number        #");
		System.out.println("\t\t\t#      5.Address               #");
		System.out.println("\t\t\t#      6.City                  #");
		System.out.println("\t\t\t#      7.State                 #");
		System.out.println("\t\t\t#      8.Country               #");
		System.out.println("\t\t\t#      9.Go to previous page   #");
		System.out.println("\t\t\t#################################");
		System.out.print("\tEnter your choice:");int choice=sc.nextInt();
		if(choice>=1 && choice <=8) {
			a.editProfileDetails(choice,custid);}
		else if(choice==9) {
			return;
		}
		else {
			try {
				throw new InvalidChoiceException("\tWrong choice!! please provide choice as from 1-9");
			}catch(InvalidChoiceException e) {
				System.out.println(ANSI_RED +"\t\t"+ e.getMessage() + ANSI_RESET);
			}
		}
	}
	public void viewRentalCars() {
		try {
			Connection conn=DbConnection.getConn();
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM CAR.car WHERE STATUS = 'Rental'");
			ResultSet resultSet = statement.executeQuery();
			System.out.println("Car ID\tBrand\tModel\tRental Price\tStatus");
            System.out.println("------------------------------------");
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1)+" | "+resultSet.getString(2)+" | "+resultSet.getString(3)+" | "+resultSet.getString(4)+" | "+resultSet.getString(5)+" | ");
            }
		}catch(Exception e) {
			System.out.println(ANSI_RED +"\t\t"+ e.getMessage() + ANSI_RESET);
		}
		
	}
	public void reserveCar(int userid) throws InvalidChoiceException {
		Booking book=new Booking();
		book.bookCar(userid);
	}
	public void cancelCar(int userid) {
		Cancellation cc=new Cancellation();
		cc.CancellationOfCar(userid);
	}
	public void giveFeedback() {
		System.out.println();
        System.out.print("\tPlease rate your experience with our car rental service (1 to 5): ");
        int rating = sc.nextInt();
        sc.nextLine();
        System.out.println();
        System.out.print("\tPlease provide any additional feedback or comments: ");
        String feedback = sc.nextLine();
        System.out.println();
        System.out.println("\tThank you for the feedback !! Your feedback values for us !!");
	}
	public void customerManagement() throws InvalidChoiceException, InvalidValidationException {
		System.out.print("\t**********************************************************************************************\n");
        System.out.print("\t*                                                                                            *\n");
		System.out.print("\t*                              1.Add new customers                                           *\n");
	    System.out.print("\t*                              2.Remove customers                                            *\n");
	    System.out.print("\t*                              3.Activate customers                                          *\n");
	    System.out.print("\t*                              4.View customers                                              *\n");
	    System.out.print("\t*                              5.Go to previous page                                         *\n");
	    System.out.print("\t*                                                                                            *\n");
	    System.out.print("\t**********************************************************************************************\n");
	    System.out.print("\t\tEnter your choice:");
	    String choice = sc.next();
	    switch(choice) {
	    case "1":
		{
			//To add new user login credentials
			admin.addUser();
			break;
		}
		case "2":
		{
			//To remove new user login credentials
			admin.removeUser();
			break;
		}
		case "3":
		{
			//To activate the customer credentials
			admin.activeCustomer();
			break;
		}
		case "4":
		{
			//To view the details of all customer
			admin.viewCustomers();
			break;
		}
		case "5":
		{
			//Go to previous page
			admin.adminLogin();
			break;
		}
		default:
	    	try {
	    	throw new InvalidChoiceException("\tYour choice is not allowed !! please provide values from 1-5 !!");
	    	}catch(Exception e) {
	    		System.out.println(ANSI_RED +"\t\t"+ e.getMessage() + ANSI_RESET);
	    	}
	    }
	}
}
