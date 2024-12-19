package com.bookingdetails;

import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

import com.carrentalsystem.DbConnection;

public class Bill {
	public void generateBill(int userId) {
		Scanner sc = new Scanner(System.in);
        LocalDate returnDate = null;
        int totalCost = 0;
        try {
            Connection conn = DbConnection.getConn();

            PreparedStatement statement = conn.prepareStatement("SELECT * FROM car.BOOKING_HISTORY WHERE USER_ID = ?");
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            
            // Check if there are bookings for the user
            if (!rs.isBeforeFirst()) {
                System.out.println("\t\tNo bookings found for you !! ");
                return;
            }

            while (rs.next()) {                                                                                                 
            	 System.out.println("\t\t___________________________________________________________________________________________");
            	    System.out.printf("\t\t| %-12s | %-12s | %-12s | %-12s | %-12s | %-12s |\n", "Booking ID", "Car ID", "Booking Date", "Pickup Date", "Return Date", "Total Cost");
            	    System.out.println("\t\t_________________________________________________________________________________________");
            	    System.out.printf("\t\t| %-12d | %-12s | %-12s | %-12s | %-12s | $%-11d |\n",
            	            rs.getInt("BOOKING_ID"),
            	            rs.getString("CARID"),
            	            rs.getDate("BOOKING_DATE"),
            	            rs.getDate("PICKUP_DATE"),
            	            rs.getDate("RETURN_DATE"),
            	            rs.getInt("TOTAL_COST"));
            	    System.out.println("\t\t__________________________________________________________________________________________");
            }
        }catch(Exception e) {
        	System.out.println(e.getMessage());
        }
	}
}
