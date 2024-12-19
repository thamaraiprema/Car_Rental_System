package com.bookingdetails;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import com.carrentalsystem.*;
import com.exceptiondetails.InvalidChoiceException;
import com.exceptiondetails.InvalidInputException;
import com.exceptiondetails.InvalidValidationException;
import com.paymentdetails.*;
import com.searchdetails.*;
import com.userdetails.Admin;


public class Booking {
	
	Scanner sc=new Scanner(System.in);
	static Admin admin=new Admin();
	static int bookId=1;
	static int paymentId=1;
	static int invoiceId=1;
	static int notifyId=100;
	public static int AutoBookingID()/*This Method Returns bookID */
	{
		
		try{
			Connection con=DbConnection.getConn();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("Select MAX(Booking_ID) from car.Booking_history");
			rs.next();
			bookId = rs.getInt(1);
			if(rs.wasNull())
			{
				return 1;
			}
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return bookId+2;
	}
	public static int AutoPaymentID()/*This Method Returns paymentID */
	{
		
		try{
			Connection con=DbConnection.getConn();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("Select MAX(PAYMENTID) from car.PAYMENT");
			rs.next();
			paymentId = rs.getInt(1);
			if(rs.wasNull())
			{
				return 1;
			}
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return paymentId+2;
	}
	public static int AutoInvoiceID()/*This Method Returns InvoiceID */
	{
		
		try{
			Connection con=DbConnection.getConn();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("Select MAX(INVOICEID) from car.INVOICE");
			rs.next();
			invoiceId = rs.getInt(1);
			if(rs.wasNull())
			{
				return 1;
			}
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return invoiceId+2;
	}
	public static int AutoNotificationID()/*This Method Returns InvoiceID */
	{
		
		try{
			Connection con=DbConnection.getConn();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("Select MAX(NOTIFICATIONID) from car.ACCESS_NOTIFICATION");
			rs.next();
			notifyId = rs.getInt(1);
			if(rs.wasNull())
			{
				return 100;
			}
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return notifyId+2;
	}
	public void bookCar(int userid) throws InvalidChoiceException {
		int bookid = Booking.AutoBookingID();
		Search search=null;
		System.out.println("\t\t\t--------------> BOOKING PORTAL <------------");
		System.out.println("\t\t\t....... Available Filteration .....\n\t\t\t1.Search By Brand \n\t\t\t2.Search By Price \n\t\t\t3.Search by location ");
		System.out.println("\t\t\t...................................");
		System.out.print("\tEnter your choice:");String choice=sc.next();
		switch(choice) {
		case "1":
			search=new SearchByBrand();
			search.searchFilter();
			break;
		case "2":
			search=new SearchByPrice();
			search.searchFilter();
			break;
		case "3":
			search=new SearchByLocation();
			search.searchFilter();
			break;
		default:
			try {
				throw new InvalidChoiceException("\tInvalid choice provided !! please provide choice as from 1-3");
			}catch(InvalidChoiceException e) {
				System.out.println(e.getMessage());
			}
		}
		
		System.out.print("\t\tWould you like to proceed further ? (y/n) :");char ch=sc.next().charAt(0);
		if(ch=='y') {
			System.out.print("\tPick a Car by entering respective Car ID: ");
	        int id = sc.nextInt();
	        double rentalPrice=0;
	        try {
	            Connection conn = DbConnection.getConn();
	            boolean isValidCarId=false;
				while (!isValidCarId) {
		            PreparedStatement statement = conn.prepareStatement("SELECT RENTAL_PRICE FROM car.CAR WHERE CARID = ?");
		            statement.setInt(1, id);
		            
		            ResultSet resultSet = statement.executeQuery();
	
		            if (resultSet.next()) {
		                // Car with the specified ID exists
		                rentalPrice = resultSet.getDouble("RENTAL_PRICE");
		                isValidCarId=true;
		            } else {
		                // Car with the specified ID does not exist
		                System.out.println("Car with ID " + id + " does not exist.");
		            }
				}
				System.out.print("\tHow Many days u want to take this car:");
				int days=sc.nextInt();
				
				LocalDate currentDate = LocalDate.now();
				LocalDate pickupDate;
				 while (true) {
			            System.out.print("\tWhen do you want to pick up this car?\n\tProvide date (\"yyyy-MM-dd\"): ");
			            String date = sc.next();
			            
			            try {
			                pickupDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			                if (pickupDate.isBefore(currentDate)) {
			                    throw new InvalidInputException("Pickup date cannot be before the current date.");
			                }
			                break; 
			            } catch (Exception e) {
			                System.out.println("\t\tInvalid date format or pickup date is before the current date. Please re-enter.");
			            }
			    }
				
				LocalDate returnDate = pickupDate.plusDays(days);
				double totalPrice=Car.calculatePrice(days,rentalPrice);	
				System.out.println("\t\tRental Price: " + rentalPrice);
		        System.out.println("\t\tTotal Price: " + totalPrice);
		        System.out.println("\t\tBooking Date: " + currentDate);
		        System.out.println("\t\tRental Date: " + pickupDate);
		        System.out.println("\t\tReturn Date: " + returnDate);
		        
		        System.out.print("\n\tAdditional charges included for the insurance:\n\t\t1.Car Insurance - 2098/-\n\t\t2.Personal Insurance - 2300/-");
		        System.out.print("\n\tDo u want to include insurance? (y/n):");char ch2=sc.next().charAt(0);
		        
		        if(ch2=='y') {
		        	System.out.print("\tWhich insurance do u need ? ->");int op=sc.nextInt();
		        	int invoiceId=AutoInvoiceID();
		        	switch(op) {
		        	case 1:
		        		totalPrice+=2098;
		        		try {
		        			Connection con=DbConnection.getConn();
		    				PreparedStatement insurance = conn.prepareStatement("INSERT INTO CAR.INVOICE VALUES (?,?,?,?)");
		    				insurance.setInt(1, invoiceId);
		    				insurance.setDate(2,java.sql.Date.valueOf(currentDate));
		    				insurance.setString(3, "Car Insurance");
		    				insurance.setString(4, "APPLIED");
		    				insurance.execute();
		    				
		    				ResultSet rs = insurance.executeQuery();
		        		}catch(Exception e) {
		        			System.out.println(e.getMessage());
		        		}
		        		System.out.println("\tYour updated price for rent this car : "+totalPrice);
		        		break;
		        	case 2:
		        		totalPrice+=2300;
		        		try {
		        			Connection con=DbConnection.getConn();
		    				PreparedStatement insurance = conn.prepareStatement("INSERT INTO CAR.INVOICE VALUES (?,?,?,?)");
		    				insurance.setInt(1, invoiceId);
		    				insurance.setDate(2,java.sql.Date.valueOf(currentDate));
		    				insurance.setString(3, "Personal Insurance");
		    				insurance.setString(4, "APPLIED");
		    				insurance.execute();
		    				
		    				ResultSet rs = insurance.executeQuery();
		    				System.out.println("\tYour updated price for rent this car : "+totalPrice);
		        		}catch(Exception e) {
		        			System.out.println(e.getMessage());
		        		}
		        		break;
		        	default:
		        		try {
		        			throw new InvalidChoiceException("\tInvalid choice !! please provide choice as 1 or 2");
		        		}catch(InvalidChoiceException e) {
		        			System.out.println(e.getMessage());
		        		}
		        	}
		        }
		        else {
		        	try {
	        			Connection con=DbConnection.getConn();
	    				PreparedStatement insurance = conn.prepareStatement("INSERT INTO CAR.INVOICE VALUES (?,?,?,?)");
	    				insurance.setInt(1, AutoInvoiceID());
	    				insurance.setDate(2,Date.valueOf(currentDate));
	    				insurance.setString(3, " NIL ");
	    				insurance.setString(4, "NOT APPLIED");
	    				insurance.execute();
	    				
	    				ResultSet rs = insurance.executeQuery();
	    				System.out.println("\t\t----->  No additional charges included for you <------------");
	        		}catch(Exception e) {
	        			System.out.println(e.getMessage());
	        		}
		        }
		        Connection con=DbConnection.getConn();
				PreparedStatement notify = conn.prepareStatement("INSERT INTO CAR.ACCESS_NOTIFICATION VALUES (?,?,?)");
				notify.setInt(1, AutoNotificationID());
				notify.setInt(2, bookid);
				notify.setString(3, "PENDING");
				notify.execute();
				
				
		        System.out.print ("\tDo u want to confirm your payment ?? (y/n):");char ch1=sc.next().charAt(0);
		        if(ch1=='y') {
		        	if(admin.accessRequest(bookid)) {
		        		System.out.println("\t\tNote: Cancellation available !! But it will be only within 7 days from booking !!");
		        		System.out.print("\t\t\tSelect the mode of payment:\n\t\t1.GPay\n\t\t2.Cash\n\t\t3.CreditCard\n");
			        	System.out.print("\t\t\tEnter your choice:");String cc=sc.next();
//			        	Payment payment=null;
			        	BookingDetails Bookdata=new BookingDetails(bookid,currentDate,pickupDate,returnDate,totalPrice,userid,id);
			        	switch(cc) {
			        	case "1":
			        		
			        		OnlineTransaction payment=new OnlineTransaction();
			        		payment.booking(conn,Bookdata);
			        		break;
			        	case "2":
			        		OnlineTransaction cash=new OnlineTransaction();
			        		cash.bookingbyCash(conn,Bookdata);
			        		break;
			        	case "3":
			        		CreditCard credit=new CreditCard();
			        		credit.booking(conn,Bookdata);
			        		break;
			        	default:
			        		try {
			        			throw new InvalidChoiceException("Invalid choice !! please provide choice as 1 or 2");
			        		}catch(InvalidChoiceException e) {
			        			System.out.println(e.getMessage());
			        		}
			        	}
		        	}
		        	else {
		        		System.out.println("\tSorry !! you doesn't have access to proceed further !! Try again sometimes later :)");
		        	}
		        }
			}
			catch(Exception e) {
				System.out.println(e.getMessage());
			}
		
	 }
	else {
		return;
//		 bookCar(userid);
	 }
	}
	public void bookingManagement() throws InvalidChoiceException, InvalidValidationException {
		System.out.print("\t**********************************************************************************************\n");
        System.out.print("\t*                                                                                            *\n");
        System.out.print("\t*                  1.View Booking history                                                    *\n");
        System.out.print("\t*                  2.Go to previous page                                                     *\n");
        System.out.print("\t*                                                                                            *\n");
	    System.out.print("\t**********************************************************************************************\n");
	    System.out.print("\t\tEnter your choice:");String ch=sc.next();
		switch(ch)
		{
			case "1":
			{
				//To view the Booking Histories done by the customers
				admin.viewBookingHistory();
				break;
			}
			case "2":
			{
				admin.adminLogin();
				break;
			}
		}
	}

}
