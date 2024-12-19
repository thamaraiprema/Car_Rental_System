package com.userdetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bookingdetails.Booking;
import com.bookingdetails.DashBoard;
import com.carrentalsystem.Car;
import com.carrentalsystem.DbConnection;
import com.carrentalsystem.DriverCarRentalApp;
import com.carrentalsystem.ProfileActivity;
import com.carrentalsystem.Register;
import com.exceptiondetails.InvalidChoiceException;
import com.exceptiondetails.InvalidValidationException;
class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
}

/**
 * The Admin class represents the administrator of the car rental system.
 * It provides functionalities such as managing cars, customers, bookings, profiles, etc.
 * Admin can perform operations like adding, updating, removing cars/customers, viewing car details, etc.
 * Admin authentication is required for certain critical operations.
 * 
 * @author Sri Prasanna (Expleo)
 * @since 22 Feb 2024  
*/
public class Admin extends User{
	static Scanner sc=new Scanner(System.in);
	private String email;
	private int age;
	private long phoneNumber;
	private LinkedList<Car> carList;

    // Constructor
    public Admin() {
        carList = new LinkedList<Car>();
    }

    public Admin(String email, int age, long phoneNumber) {
        this.email = email;
        this.age = age;
        this.phoneNumber = phoneNumber;
        carList = new LinkedList<Car>();
    }


    //Getters
	public String getEmail() {
		return email;
	}
	public int getAge() {
		return age;
	}
	public long getPhoneNumber() {
		return phoneNumber;
	}
	
	//Setters
	public void setEmail(String email) {
		this.email = email;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public static int AutoCarID()/*This Method Returns carid */
	{
		int carId=0;
		try{
			Connection con=DbConnection.getConn();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("Select MAX(carid) from car.car");
			rs.next();
			carId = rs.getInt(1);
			if(rs.wasNull())
			{
				return 1;
			}
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return carId+2;
	}
	public static int AutoUserID()/*This Method Returns userid */
	{
		int userId=0;
		try{
			Connection con=DbConnection.getConn();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("Select MAX(user_id) from car.login_credentials");
			rs.next();
			userId = rs.getInt(1);
			if(rs.wasNull())
			{
				return 1;
			}
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return userId+2;
	}
	static Admin admin=new Admin();
	static Car car=new Car();
	static Booking booking=new Booking();
	static Customer customer=new Customer();
	static ProfileActivity profile=new ProfileActivity();
	static DashBoard report=new DashBoard();
	 String COLOR_RESET = "\u001B[0m";
     String COLOR_RED = "\u001B[31m";
	
	/**
     * Method to handle admin login.
     * Admin can perform various operations like car management, customer management, etc.
     */
	 public void adminLogin() throws InvalidChoiceException, InvalidValidationException {
		boolean checkadmin = true;
	    		
	    		while(checkadmin)
	    		{
	    			System.out.print("\t**********************************************************************************************\n");
	    	        System.out.print("\t*                                                                                            *\n");
	    	        System.out.print("\t*                         1.Car Management                                                   *\n");
	    	        System.out.print("\t*                         2.Customer Management                                              *\n");
	    	        System.out.print("\t*                         3.Booking Management                                               *\n");
	    	        System.out.print("\t*                         4.Profile Management                                               *\n");
	    	        System.out.print("\t*                         5.Reports                                                          *\n");
	    	        System.out.print("\t*                         6.Logout                                                           *\n");
	    	        System.out.print("\t*                         7.Exit                                                             *\n");
	    	        System.out.print("\t*                                                                                            *\n");
	    	        System.out.print("\t**********************************************************************************************\n");
	    	        System.out.print("\t\tEnter your choice :");String option=sc.next();
	    	        switch(option) {
	    	        case "1":
	    	        	car.carManagement();
	    	        	break;
	    	        case "2":
	    	        	customer.customerManagement();
	    	        	break;
	    	        case "3":
	    	        	booking.bookingManagement();
	    	        	break;
	    	        case "4":
	    	        	profile.profileManagement();
	    	        	break;
	    	        case "5":
	    	        	report.generateReports();
	    	        	break;
	    	        	
	    	        case "6":
	    	        	checkadmin = false;
    					System.out.println("\t\txx - Logged out successfully - xx");
    					DriverCarRentalApp.main(null);
    					break;
	    	        case "7":
	    	        	// Exit the application
				    	System.out.println("\t\t----------> Thank you for visiting our application :) <----------------");
				    	System.exit(0);
	    	        default:
    					try {
    						throw new InvalidChoiceException("\tYour choice is not Allowed !! Please provide choice as from 1 to 7 !!");
    					}catch(Exception e) {
    						System.out.println(COLOR_RED +"\t\t"+ e.getMessage() + COLOR_RESET);
    						
    					}
	    	        }
	    	        
	    		}//end of while
	    		
	}
	    	
	/**
	* Method to add a new car to the system.
	*/    	        
	public void addCar() {
		try {
			String brand="";String model="";String loc="";double pricePerDay=0;
			System.out.println("\tEnter the following details!!");
//			System.out.print("\tEnter the car id:");
			int carId=AutoCarID();
			
			while (true) {
	            try {
	                System.out.print("\tEnter the name of the brand:");
	                brand = sc.next();
	                if (brand.matches("\\d+")) {
	                    throw new InvalidInputException("\tWrong brand name provided !!.");
	                } else {
	                    break;
	                }
	            } catch (InvalidInputException e) {
	            	System.out.println(COLOR_RED +"\t\t"+ e.getMessage() + COLOR_RESET);
	            }
	        }

	        // Model input
	        while (true) {
	            try {
	                System.out.print("\tEnter the model name:");
	                model = sc.next();
	                if (model.matches("\\d+")) {
	                    throw new InvalidInputException("\tWrong model name provided !!");
	                } else {
	                    break;
	                }
	            } catch (InvalidInputException e) {
	            	System.out.println(COLOR_RED +"\t\t"+ e.getMessage() + COLOR_RESET);
	            }
	        }

	        // Rental price input
	        while (true) {
	            try {
	                System.out.print("\tEnter the rental price for one day:");
	                pricePerDay = sc.nextDouble();
	                break;
	            } catch (Exception e) {
	                System.out.println(COLOR_RED +"\tInvalid price provided ! Please enter a valid number." + COLOR_RESET);
	                sc.nextLine(); // Clear the input buffer
	            }
	        }

	        // Location input
	        while (true) {
	            try {
	                System.out.print("\tEnter the location:");
	                loc = sc.next();
	                if (loc.matches("\\d+")) {
	                    throw new InvalidInputException("\tWrong location provided !!");
	                } else {
	                    break;
	                }
	            } catch (InvalidInputException e) {
	            	System.out.println(COLOR_RED +"\t\t"+ e.getMessage() + COLOR_RESET);
	            }
	        }
			
			Car car = new Car(carId, brand, model, pricePerDay,loc);
	        carList.add(car);
	        Register r=new Register();
	        r.CarRegistration(car);
	        car.carManagement();
			
		}catch(InputMismatchException e1) {
			System.out.println("Wrong input provided !!");
		}catch(Exception e) {
			System.out.println(COLOR_RED +"\t\t"+ e.getMessage() + COLOR_RESET);
		}
	}
	
    /**
     * Method to update details of an existing car.
     */
	public void updateCar() {
			
			try {
				int count =0;
				System.out.print("\tEnter the car id to update the details:");
				int carId=sc.nextInt();
				
				Connection con=DbConnection.getConn();
				Statement ss=con.createStatement();
				
				// Query to check if the car exists and is active
				ResultSet rs=ss.executeQuery("Select count(*) from car.car where carid="+carId+"and visibility='ACTIVE' ");
				if (rs.next()) {
	                count = rs.getInt(1);
	               
	            }
				
				// If car exists and is active, allow updates
				if(count>0) {
					System.out.println("\t\t\t****What details u have to update now ADMIN!!*****");
				 	System.out.println("\t\t\t\t*****************************");
			        System.out.println("\t\t\t\t*                           *");
			        System.out.println("\t\t\t\t*  1. Brand name            *");
			        System.out.println("\t\t\t\t*  2. Model name            *");
			        System.out.println("\t\t\t\t*  3. Rental price per day  *");
			        System.out.println("\t\t\t\t*  4. Location of car       *");
			        System.out.println("\t\t\t\t*  5. Visibility of car     *");
			        System.out.println("\t\t\t\t*  6. Go to previous page   *");
			        System.out.println("\t\t\t\t*                           *");
			        System.out.println("\t\t\t\t*****************************");
			        System.out.print("\t\tEnter your option:");String ch=sc.next();
			        switch(ch) {
			        case "1":
			        	System.out.print("\t\tProvide brand name to update:");String brand=sc.next();
			        	PreparedStatement sts = con.prepareStatement("update car.car set brand=? where carid=? and visibility='Active'");
			        	sts.setString(1, brand);
			        	sts.setInt(2, carId);
			        	sts.execute();
			        	System.out.println("\t\tBrand name updated successfully!!");
			        	break;
			        	
			        case "2":
			        	System.out.print("\t\tProvide model name to update:");String model=sc.next();
			        	PreparedStatement sts1 = con.prepareStatement("update car.car set model=? where carid=? and visibility='Active'");
			        	sts1.setString(1, model);
			        	sts1.setInt(2, carId);
			        	sts1.execute();
			        	System.out.println("\t\tModel name updated successfully!!");
			        	break;
			        case "3":
			        	System.out.print("\t\tProvide rental price per day to update:");double price=sc.nextDouble();
			        	PreparedStatement sts2 = con.prepareStatement("update car.car set rental_price=? where carid=? and visibility='Active'");
			        	sts2.setDouble(1, price);
			        	sts2.setInt(2, carId);
			        	sts2.execute();
			        	System.out.println("\t\tRental price updated successfully!!");
			        	break;
			        case "4":
			        	System.out.print("\t\tProvide new location to update:");String loc=sc.next();
			        	PreparedStatement sts4 = con.prepareStatement("update car.car set location=? where carid=? and visibility='Active'");
			        	sts4.setString(1, loc);
			        	sts4.setInt(2, carId);
			        	sts4.execute();
			        	System.out.println("\t\tLocation of the car updated successfully!!");
			        	break;
			        	
			        case "5":
			        	System.out.print("\t\tProvide Visibility(Active/Inactive) to update:");String visible=sc.next();
			        	PreparedStatement sts5 = con.prepareStatement("update car.car set visibility=? where carid=? and visibility='Active'");
			        	sts5.setString(1, visible);
			        	sts5.setInt(2, carId);
			        	sts5.execute();
			        	System.out.println("\t\tVisibility of the car updated successfully!!");
			        	break;  
			        case "6":
			        	break;
			        default:
			        	try {
			        		throw new InvalidChoiceException("Wrong option provided !! please provide option from 1-6 !!");
			        	}catch(Exception e) {
			        		System.out.println(COLOR_RED +"\t\t"+ e.getMessage() + COLOR_RESET);
			        	}
			        }
			        car.carManagement();
				}else {
					System.out.println("\tCar not found or Inactive !!!");
					updateCar();
				}
			}catch(InputMismatchException e1) {
				System.out.println("\tWrong input provided !!");
				updateCar();
			}catch(Exception e) {
				System.out.println(COLOR_RED +"\t\t"+ e.getMessage() + COLOR_RESET);
			}
	}
	
	/**
	 * Method to remove a car from the system. 
	 */
	public void removeCar() throws InvalidChoiceException, InvalidValidationException {
		System.out.print("\tEnter the car id to remove from the system:");

		try {
		    int carId = sc.nextInt();
		    Connection con = DbConnection.getConn();
		    
		    // Check if the record exists before updating visibility
		    PreparedStatement checkStatement = con.prepareStatement("SELECT COUNT(*) FROM car.car WHERE carid=? AND visibility='Active'");
		    checkStatement.setInt(1, carId);
		    ResultSet resultSet = checkStatement.executeQuery();
		    if (resultSet.next() && resultSet.getInt(1) > 0) {
		        // Record found, proceed with updating visibility
		        PreparedStatement sts = con.prepareStatement("UPDATE car.car SET visibility='INACTIVE' WHERE carid=?");
		        sts.setInt(1, carId);
		        sts.executeUpdate();
		        System.out.println("\tCar with ID number: " + carId + " has been removed now!!");
		    } else {
		        // Record not found
		        System.out.println("\tNo active car found with the provided ID: " + carId);
		    }
		} catch (InputMismatchException e1) {
		    System.out.println("\nWrong input provided!!");
		} catch (Exception e) {
		    System.out.println(COLOR_RED + "\t\t" + e.getMessage() + COLOR_RESET);
		}
		car.carManagement();
	}
	
	/**
	 * Method to view details of all cars in the system.
	 */
	public void viewCarDetails() throws InvalidChoiceException, InvalidValidationException {
		List<Car> carList=new ArrayList<Car>();
		try {
			Connection con=DbConnection.getConn();
			Statement sts=con.createStatement();
			ResultSet rs=sts.executeQuery("select * from CAR.car ");
			while (rs.next()) {
	            int carId = rs.getInt(1);
	            String brand=rs.getString(2);
	            String model=rs.getString(3);
	            double rentalprice=rs.getDouble(4);
	            String status=rs.getString(5);
	            String location=rs.getString(6);
	            

	            carList.add(new Car(carId,brand,model,rentalprice,status,location));
	     }
			Collections.sort(carList, Comparator.comparingInt(Car::getCarId));
			System.out.println("\t\t-----------------------------------------------------------------------------------");
			System.out.println ("\t\t CARID |    BRAND     |      MODEL   |    STATUS    | RENTAL_PRICE  |  LOCATION  |");
			System.out.println("\t\t-----------------------------------------------------------------------------------");

								
		 for (Car i : carList) {
			 System.out.println("\t\t"+i);
		 }
			System.out.println("\t\t-----------------------------------------------------------------------------------");

		}catch(Exception e){
			System.out.println(COLOR_RED +"\t\t"+ e.getMessage() + COLOR_RESET);
		}
	}
	
	/**
	 * Method to add a new user to the system (Admin operation).
	 */
	public void addUser()throws InvalidChoiceException, InvalidValidationException {
		
		try {
//			System.out.print("\tProvide new user id:");
			int userId=AutoUserID();
			String username,password,cpd;
			 usernameLoop:
			 while (true) {
			     System.out.print("\tEnter your username:");
			     username = sc.next();

			     // Define the regex pattern for the username
			     String regex = "^[a-zA-Z0-9_]{4,20}$";

			      // Compile the regex pattern
			     Pattern pattern = Pattern.compile(regex);

			     // Match the username against the pattern
			      Matcher matcher = pattern.matcher(username);

			      // Check if the username matches the pattern
			      if (matcher.matches()) {
			           break; // Exit the loop if the username is valid
			      } else {
			           System.out.println("Invalid username. Username must consist of 4 to 20 alphanumeric characters or underscores.");
			           continue usernameLoop; // Re-enter the username if it is invalid
			       }
			 }
			 
			 passwordLoop:
			 while (true) {
		            

		            System.out.print("\tProvide password for this id:");
		            password = sc.next();

		            // Validating password strength
		            Pattern passwordPattern = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$");
		            Matcher passwordMatcher = passwordPattern.matcher(password);

		            // Check if the password matches the pattern
		            if (!passwordMatcher.matches()) {
		                try {
		                    throw new InvalidValidationException("\tProvide a strong password!! Please retry again :)");
		                    
		                } catch (InvalidValidationException e) {
		                    System.out.println(e.getMessage());
		                    System.out.println("\t(Note:Your password must contain:");
				            System.out.println("\t\t- At least one digit (1, 2, 3,...)");
				            System.out.println("\t\t- At least one lowercase letter (s)");
				            System.out.println("\t\t- At least one uppercase letter (S, P)");
				            System.out.println("\t\t- At least 8 characters in total)");
		                    continue passwordLoop; // Re-enter the password if it is invalid
		                }
		            } else {
		                // Password meets the criteria, break out of the loop
		                break passwordLoop;
		            }
		        }

	        // Confirming the password
	        while (true) {
	            System.out.print("\tConfirm Password:");
	            cpd = sc.next();
	            if (password.equals(cpd))
	                break;
	            else
	                System.out.println("\tPasswords do not match! Please try again.");
	        }
			
			
			Connection con=DbConnection.getConn();
			Statement st=con.createStatement();
			
			// Inserting user credentials into the database 
			st.executeUpdate("insert into CAR.Login_Credentials values('"+userId+"','"+password+"','customer','ACTIVE','"+username+"')");
			Account acc=new Account();
			acc.registerUser(userId);
			
//			System.out.println("\tNew customer added Succesfully!!");
		}
		catch(InputMismatchException e1) {
			System.out.println("\tWrong input provided !!");
		}
		catch(Exception e){
			System.out.println(COLOR_RED +"\t\t"+ e.getMessage() + COLOR_RESET);
		}
		customer.customerManagement();
	}
	
	/**
	 * Method to remove a user from the system (Admin operation).
	 * @throws InvalidValidationException 
	 * @throws InvalidChoiceException 
	 */
	public void removeUser() throws InvalidChoiceException, InvalidValidationException {
		
		try {
			int count=0;
			System.out.print("\tEnter user id to remove their credentials:");int id=sc.nextInt();
			
			Connection con=DbConnection.getConn();
			Statement ss=con.createStatement();
			
			// Checking if user exists
			ResultSet rs=ss.executeQuery("Select count(*) from car.login_credentials where user_id="+id);
			if (rs.next()) {
	            count = rs.getInt(1);
	        }
			if(count>0) {
				PreparedStatement sts = con.prepareStatement("update car.login_credentials set visibility='INACTIVE' where user_id=?");
				sts.setInt(1, id);
				sts.execute();
				System.out.println("\tUser with ID number:"+id+" has been Inactived now");
				customer.customerManagement();
			}else {
				System.out.println("\tData not found on this Car ID!!");
				
			}
		}
		catch(InputMismatchException e1) {
			System.out.println("\tWrong input provided !!");
		}
		catch(Exception e) {
			System.out.println(COLOR_RED +"\t\t"+ e.getMessage() + COLOR_RESET);
		}
		customer.customerManagement();
	}
	
	/**
	 * Method to activate a customer's credentials (Admin operation).
	 * @throws InvalidValidationException 
	 * @throws InvalidChoiceException 
	 */
	public void activeCustomer() throws InvalidChoiceException, InvalidValidationException {
		try {
			int count=0;
			System.out.print("\tEnter user id to active their credentials:");int id=sc.nextInt();
			
			Connection con=DbConnection.getConn();
			Statement ss=con.createStatement();
			 // Checking if user exists and is inactive
			ResultSet rs=ss.executeQuery("Select count(*) from car.login_credentials where user_id="+id+"and visibility='INACTIVE'");
			if (rs.next()) {
	            count = rs.getInt(1);
	        }
			if(count>0) {
				PreparedStatement sts = con.prepareStatement("update car.login_credentials set visibility='ACTIVE' where user_id=?");
				sts.setInt(1, id);
				sts.execute();
				System.out.println("\tUser with ID number:"+id+" has been actived now!!");
				customer.customerManagement();
			}else {
				System.out.println("\tData not found on this Car ID or already in active status!!");
				customer.customerManagement();
			}
		}
		catch(InputMismatchException e1) {
			System.out.println("\tWrong input provided !!");
		}
		catch(Exception e) {
			System.out.println(COLOR_RED +"\t\t"+ e.getMessage() + COLOR_RESET);
		}
		customer.customerManagement();
	}
	
	/**
	 * Method to view details of all customers in the system.
	
	 */
	public void viewCustomers() throws InvalidChoiceException, InvalidValidationException {
		List<Customer> customerList=new ArrayList<Customer>();
		try {
			Connection con=DbConnection.getConn();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("Select * from car.Customer");
			 while (rs.next()) {
		            int userId = rs.getInt(1);
		            int custId=rs.getInt(2);
		            String firstName=rs.getString(3);
		            String lastName=rs.getString(4);
		            String email=rs.getString(5);
		            long phoneNo=rs.getLong(6);
		            int age=rs.getInt(7);
		            String address=rs.getString(8);
		            long licenseNo=rs.getLong(9);
		            String gender=rs.getString(10);
		            

		            customerList.add(new Customer(userId, custId, firstName,lastName,email,phoneNo,age,address,licenseNo,gender));
		     }
				Collections.sort(customerList, Comparator.comparingInt(Customer::getCustid));

			System.out.println("\t\t--------------------------------------------------------------------------------------------------------------------------------");
			System.out.println("\t\t CUSTOMER_ID  USER_ID   FIRSTNAME	LASTNAME	     EMAIL	      PHONENO   	AGE	      ADDRESS	     LICENSENO	     GENDER");
			System.out.println("\t\t--------------------------------------------------------------------------------------------------------------------------------");

			 for (Customer i : customerList) {
				 System.out.println("\t\t|"+i);
			 }
				
			 System.out.println("\t\t----------------------------------------------------------------------------------------------------------------------------");
			 customer.customerManagement();

		}catch(Exception e){
			System.out.println(COLOR_RED +"\t\t"+ e.getMessage() + COLOR_RESET);
		}
		
		customer.customerManagement();
	}
	
	/**
	 * Method to view booking history of all transactions.
	 */
	public void viewBookingHistory() {
		try {
			Connection con=DbConnection.getConn();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("Select * from car.Booking_History");
			System.out.println("_______________________________________________________________________________________________________________________________________________________________________");
			System.out.println("BOOKING_ID\t|\tCARID\t|\tBOOKING_DATE\t|\tPICKUP_DATE\t|\tRETURN_DATE\t|\tTOTAL_COST\t|\tUSER_ID");
			System.out.println("_______________________________________________________________________________________________________________________________________________________________________");
			while (rs.next()) {
				 System.out.println("\t"+rs.getInt(1)+"\t|\t"+rs.getString(2)+"\t|\t"+rs.getDate(3)+"\t|\t"+rs.getDate(4)+"\t|\t"+rs.getDate(5)+"\t|\t"+rs.getString(6)+"\t\t|\t"+rs.getString(7)+"\t|\t");
			 }
			System.out.println("_______________________________________________________________________________________________________________________________________________________________________");

		}catch(Exception e) {
			System.out.println(COLOR_RED +"\t\t"+ e.getMessage() + COLOR_RESET);
		}
	}
	
	/**
	 * Method to view profile of the admin.
	 */
	public void viewProfile() {
		try {
			Connection con=DbConnection.getConn();
			PreparedStatement sts = con.prepareStatement("select * from CAR.admin where admin_id=444");
			ResultSet result = sts.executeQuery();
			System.out.println("\t\t++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("\t\t+\t\t    Profile\t\t\t\t\t\t+");
			System.out.println("\t\t++++++++++++++++++++++++++++++++++++++++++++++++");
			while (result.next()) {
			    System.out.println("\t\t+\tAdminid      =\t" + result.getString(1));
			    System.out.println("\t\t+\tName         =\t" + result.getString(3));
			    System.out.println("\t\t+\tPhone        =\t" + result.getString(4));
			    System.out.println("\t\t+\tEmail        =\t" + result.getString(5));
			}
			System.out.println("\t\t++++++++++++++++++++++++++++++++++++++++++++++++");

			
			
		}catch(Exception e) {
			System.out.println(COLOR_RED +"\t\t"+ e.getMessage() + COLOR_RESET);
		}
	}
	
	
	/**
	 * Method for admin to approve access request.
	 * @param bookid The booking ID for which access is requested.
	 * @return True if access is approved, false otherwise.
	 */
	public boolean accessRequest(int bookid) {
		System.out.println("\t\tAccess needed from the admin to proceed for further payment process !!");
		System.out.println("\t\t\t------> Admin Authentication Required ! <------");
		System.out.print("\t\tUser ID -->");
        String username = sc.next();
        System.out.print("\t\tPassword-->");
        String password = sc.next();
    	if((username.compareTo("admin_5122")==0&&password.compareTo("2k20cse147!")==0))
	    {
			try {
		    	Connection con=DbConnection.getConn();
		    	// Retrieve pending access requests
		    	 PreparedStatement statement = con.prepareStatement("SELECT * FROM car.ACCESS_NOTIFICATION WHERE STATUS = 'PENDING'");
			      ResultSet resultSet = statement.executeQuery();// Admin view pending access requests
			      System.out.println("\n\t\tNOTIFICATIONID	|  BOOKID	|    STATUS");
			      while(resultSet.next()) {
			    	  System.out.println("\t\t\t"+resultSet.getInt(1)+"\t|\t"+resultSet.getString(2)+"\t|\t"+resultSet.getString(3));
			    	  
			      }
			      
			      System.out.println("\n\tWhat do u want to do now ADMIN ? \n\t1.Approve pending Request\n\t2.Deny pending request");
			      System.out.print("\n\tEnter your choice:");String ch=sc.next();
			      switch(ch) {
			      case "1":
			    	// Update notification status to 'APPROVED'
				        PreparedStatement approveStatement = con.prepareStatement("UPDATE car.ACCESS_NOTIFICATION SET STATUS = 'APPROVED' WHERE BOOKID = ?");
				        approveStatement.setInt(1, bookid);
				        approveStatement.executeUpdate();
				        System.out.println("\t\tAccess request approved successfully.");
				        break;
			      case "2":// Admin denies access request
			    	// Update notification status to 'DENIED'
				        PreparedStatement denyStatement = con.prepareStatement("UPDATE car.ACCESS_NOTIFICATION SET STATUS = 'DENIED' WHERE BOOKID = ?");
				        denyStatement.setInt(1, bookid);
				        denyStatement.executeUpdate();
				        System.out.println("\t\tAccess request denied.");
				        break;
			      default: 
			    	  try {
			    		  throw new InvalidChoiceException("Please provide a correct choice !! ");
			    	  }catch(InvalidChoiceException e) {
			    		  System.out.println(e.getMessage());
			    	  }
			      }
			      
			      String status="";
			      PreparedStatement statement1 = con.prepareStatement("SELECT STATUS FROM car.ACCESS_NOTIFICATION WHERE BOOKID = ?");
		        statement1.setInt(1, bookid);
		        ResultSet rs=statement1.executeQuery();
		        if(rs.next()) {
		        	status=rs.getString(1);
		        }
		        if(status.equals("APPROVED") ){
		        	return true;
		        }else {
		        	return false;
		        }
		        
		    } catch (SQLException e) {
		    	System.out.println(COLOR_RED +"\t\t"+ e.getMessage() + COLOR_RESET);
		        // Handle any exceptions
		    }
			
    	}else {
    		try {
    			throw new InvalidValidationException("\t\tSorry !! You have provided wrong UserId or password !!\n\t\tPlease retry again :)");
    		}catch(Exception e) {
    			System.out.println(COLOR_RED +"\t\t"+ e.getMessage() + COLOR_RESET);
    			accessRequest(bookid);
    		}
    	}
		return false;
	}

	
	

	

}
