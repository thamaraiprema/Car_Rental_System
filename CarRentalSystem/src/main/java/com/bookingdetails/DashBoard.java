package com.bookingdetails;

import java.sql.*;

import com.carrentalsystem.DbConnection;

public class DashBoard {
	public void generateReports() {
        try {
        	Connection connection = DbConnection.getConn();
            Statement statement = connection.createStatement();

            //Rental activity
            String rentalActivityQuery = "SELECT COUNT(*) AS totalBookings FROM CAR.booking_history";
            ResultSet rentalActivityResult = statement.executeQuery(rentalActivityQuery);
            if (rentalActivityResult.next()) {
                int totalBookings = rentalActivityResult.getInt("totalBookings");
                System.out.println("\t\tTotal bookings: " + totalBookings);
            }

            //Revenue generated
            String revenueQuery = "SELECT SUM(TOTAL_COST) AS totalRevenue FROM CAR.booking_history";
            ResultSet revenueResult = statement.executeQuery(revenueQuery);
            if (revenueResult.next()) {
                double totalRevenue = revenueResult.getDouble("totalRevenue");
                System.out.println("\t\tTotal revenue generated: Rs:" + totalRevenue);
            }

            //Popular car models
            String popularModelsQuery = "SELECT CARID, totalBookings FROM ( SELECT CARID, COUNT(*) AS totalBookings FROM CAR.booking_history GROUP BY CARID ORDER BY COUNT(*) DESC ) WHERE ROWNUM <= 3";
            ResultSet popularModelsResult = statement.executeQuery(popularModelsQuery);
            System.out.println("\t\tPopular car models:");
            while (popularModelsResult.next()) {
                int carId = popularModelsResult.getInt("CARID");
                int totalBookings = popularModelsResult.getInt("totalBookings");
                System.out.println("\t\tCar ID: " + carId + ", Total bookings: " + totalBookings);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
