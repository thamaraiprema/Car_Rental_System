package com.carrentalsystem;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class CarStatusUpdater extends Thread {
    private Connection connection;

    public CarStatusUpdater() {
        this.connection = DbConnection.getConn();
    }

    @Override
    public void run() {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            while (true) {
                // Fetch the current system date
                LocalDate currentDate = LocalDate.now();

                // Query to fetch booking details with return date less than current date
                String query = "SELECT CARID FROM car.booking_history WHERE RETURN_DATE <= ?";
                statement = connection.prepareStatement(query);
                statement.setDate(1, Date.valueOf(currentDate));
                resultSet = statement.executeQuery();

                // Update car status to 'Available' for bookings that have passed the return date
                while (resultSet.next()) {
                    int carId = resultSet.getInt("CARID");
                    updateCarStatus(carId);
                }

                // Sleep for some time before checking again (e.g., once every 24 hours)
                Thread.sleep(1000); // 24 hours in milliseconds
            }
        } catch (SQLException | InterruptedException e) {
            System.out.println(e.getMessage());
        } finally {
            // Close ResultSet, PreparedStatement, and Connection
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
            	System.out.println(e.getMessage());
            }
        }
    }

    private void updateCarStatus(int carId) throws SQLException {
        PreparedStatement updateStatement = null;

        try {
            // Update car status to 'Available' in the car table
            String updateQuery = "UPDATE car.car SET STATUS = 'Available' WHERE CARID = ?";
            updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setInt(1, carId);
            updateStatement.executeUpdate();
        } 
        catch(Exception e){
        	System.out.println(e.getMessage());
        }	
        	finally {
        
            // Close PreparedStatement (updateStatement)
            if (updateStatement != null) updateStatement.close();
        }
    }
}
