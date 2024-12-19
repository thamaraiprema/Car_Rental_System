package com.bookingdetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

import com.carrentalsystem.DbConnection;

public class Returning {
    public void returningCar(int userid) {
        Scanner sc = new Scanner(System.in);
        LocalDate returnDate = null;
        int totalprice = 0;
        try {
            Connection conn = DbConnection.getConn();

            PreparedStatement statement = conn.prepareStatement("SELECT * FROM car.BOOKING_HISTORY WHERE USER_ID = ?");
            statement.setInt(1, userid);
            ResultSet rs = statement.executeQuery();

            // Check if the user has any bookings
            if (!rs.next()) {
                System.out.println("\t\tSorry!! You have not booked any cars.");
                return; // Exit the method if there are no bookings
            }

            // If the user has bookings, proceed with returning process
            System.out.println("List of cars that you have booked :");
            System.out.println("_______________________________________________________________________________________________________________________________________________________________________");
            System.out.println("BOOKING_ID\t|\tCARID\t|\tBOOKING_DATE\t|\tPICKUP_DATE\t|\tRETURN_DATE\t|\tTOTAL_COST\t|\tUSER_ID");
            System.out.println("_______________________________________________________________________________________________________________________________________________________________________");
            do {
                System.out.println("\t" + rs.getInt(1) + "\t|\t" + rs.getString(2) + "\t|\t" + rs.getDate(3) + "\t|\t" + rs.getDate(4) + "\t|\t" + rs.getDate(5) + "\t|\t" + rs.getString(6) + "\t\t|\t" + rs.getString(7) + "\t|\t");
            } while (rs.next());
            System.out.println("_______________________________________________________________________________________________________________________________________________________________________");

            System.out.print("Select carId that you want to return now ->");
            int id = sc.nextInt();
            PreparedStatement carSelect = conn.prepareStatement("SELECT return_DATE FROM car.BOOKING_HISTORY WHERE carid = ?");
            carSelect.setInt(1, id);
            ResultSet ab = carSelect.executeQuery();
            if (ab.next()) {
                returnDate = ab.getDate(1).toLocalDate();
            }

            PreparedStatement price = conn.prepareStatement("SELECT TOTAL_COST FROM car.BOOKING_HISTORY WHERE carid = ?");
            price.setInt(1, id);
            ResultSet pp = price.executeQuery();
            if (pp.next()) {
                totalprice = pp.getInt(1);
            }

            LocalDate endDate = returnDate.plusDays(7);
            LocalDate currDate = LocalDate.now();
            if (currDate.isAfter(endDate)) {
                long daysDifference = ChronoUnit.DAYS.between(endDate, currDate);
                double finePerDay = 30.0;
                double totalFine = daysDifference * finePerDay;
                System.out.println("Sorry You are late for return!! Fine amount will be charged for each day after due date !!");
                totalprice -= totalFine;
                System.out.println("Your fine amount will be :" + totalFine + "\nYour final amount to be returned :" + totalprice);

            } else {
                System.out.println("You are within the due time !! No charges included !!");
                System.out.println("Your car is successfully returned :)");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}