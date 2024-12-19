package com.paymentdetails;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.bookingdetails.Booking;
import com.bookingdetails.BookingDetails;

public abstract class Payment {
	abstract void booking( Connection conn, BookingDetails Bookdata) throws SQLException;
	
	public void bookingbyCash(Connection conn, BookingDetails Bookdata) throws SQLException {

	
		int paymentid = Booking.AutoPaymentID();
        PreparedStatement insertStatement = conn.prepareStatement("INSERT INTO car.BOOKING_HISTORY (BOOKING_ID, CARID, BOOKING_DATE, PICKUP_DATE, RETURN_DATE, TOTAL_COST, USER_ID) VALUES (?, ?, ?, ?, ?, ?, ?)");
        insertStatement.setInt(1,Bookdata.getBookid());
        insertStatement.setInt(2, Bookdata.getCarId());
        insertStatement.setDate(3, Date.valueOf(Bookdata.getCurrentDate()));
        insertStatement.setDate(4, Date.valueOf(Bookdata.getPickupDate()));
        insertStatement.setDate(5, Date.valueOf(Bookdata.getReturnDate()));
        insertStatement.setDouble(6, Bookdata.getTotalPrice());
        insertStatement.setInt(7, Bookdata.getUserId());
        insertStatement.executeUpdate();

        PreparedStatement credit = conn.prepareStatement("INSERT INTO car.PAYMENT(PAYMENTID, MODEOFPAYMENT, STATUS, PAYMENT_DATE) VALUES (?,?,?,?)");
        credit.setInt(1, paymentid);
        credit.setString(2, "CREDIT CARD");
        credit.setString(3, "PAID");
        credit.setDate(4, Date.valueOf(Bookdata.getCurrentDate()));
        credit.execute();

        PreparedStatement car = conn.prepareStatement("Update car.car set status='Rental' where carid=?");
        car.setInt(1, Bookdata.getCarId());
        car.execute();
        System.out.println("\t\tCash collected successfully !!");
        System.out.println("\t\tCongrats !! Your have reserved a car !!");
        PreparedStatement customerStatement = conn.prepareStatement("SELECT * FROM car.CUSTOMER WHERE user_id = ?");
        customerStatement.setInt(1, Bookdata.getUserId());
        ResultSet customerResult = customerStatement.executeQuery();

        // Retrieve car details by carId
        PreparedStatement carStatement = conn.prepareStatement("SELECT * FROM car.CAR WHERE CARID = ?");
        carStatement.setInt(1, Bookdata.getCarId());
        ResultSet carResult = carStatement.executeQuery();

        // Format and print the bill
        System.out.println("======================================================================================================================");
        System.out.println("                                                BOOKING DETAILS           ");
        System.out.println("======================================================================================================================");
        System.out.println("Customer Details:");
        System.out.printf("%-20s %-20s %-20s %-20s %-20s%n", "Customer ID", "First Name", "Last Name", "Email", "Phone Number");
        if (customerResult.next()) {
            System.out.printf("%-20d %-20s %-20s %-20s %-20s%n",
                    customerResult.getInt("CUSTOMER_ID"),
                    customerResult.getString("FIRSTNAME"),
                    customerResult.getString("LASTNAME"),
                    customerResult.getString("EMAIL"),
                    customerResult.getString("PHONENO"));
        } else {
            System.out.println("Customer details not found for the provided custid.");
            return; // Exit method if customer details are not found
        }

        System.out.println("\nCar Details:");
        System.out.printf("%-15s %-20s %-20s %-15s %-15s%n", "Car ID", "Brand", "Model", "Rental Price", "Status");
        if (carResult.next()) {
            System.out.printf("%-15d %-20s %-20s %-15f %-15s%n",
                    carResult.getInt("CARID"),
                    carResult.getString("BRAND"),
                    carResult.getString("MODEL"),
                    carResult.getDouble("RENTAL_PRICE"),
                    carResult.getString("STATUS"));
        } else {
            System.out.println("Car details not found for the provided carId.");
            return; // Exit method if car details are not found
        }

        System.out.println("\nBooking Details:");
        System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s%n", "Booking ID", "Booking Date", "Pickup Date", "Return Date", "Total Cost", "User ID");
        System.out.printf("%-15d %-15s %-15s %-15s %-15f %-15d%n",
                Bookdata.getBookid(),
                Bookdata.getCurrentDate().toString(),
                Bookdata.getPickupDate().toString(),
                Bookdata.getReturnDate().toString(),
                Bookdata.getTotalPrice(),
                Bookdata.getUserId());
        System.out.println("===========================================================================================================================");

	}

}
