package com.searchdetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.carrentalsystem.DbConnection;

public class SearchByLocation implements Search {
	Scanner sc=new Scanner(System.in);
	@Override
	public void searchFilter() {
		System.out.print("\tProvide the Location of your expectations :");
		String location = sc.next();

		System.out.println("\tYours Filteration has been generating.....\n\tPlease Wait for a while...!!");

		try {
		    Connection conn = DbConnection.getConn();
		    PreparedStatement statement = conn.prepareStatement("SELECT COUNT(*) FROM car.CAR WHERE location=? and status='Available'");
		    statement.setString(1, location);
		    ResultSet resultSet = statement.executeQuery();
		    resultSet.next(); 
		    int count = resultSet.getInt(1);

		    if (count == 0) {
		        System.out.println("\t\tNo cars found at the specified location.");
		        return;
		    } else {
		        
		        PreparedStatement fetchStatement = conn.prepareStatement("SELECT * FROM car.CAR WHERE location=? and status='Available'");
		        fetchStatement.setString(1, location);
		        ResultSet fetchResultSet = fetchStatement.executeQuery();
		        List<String> rows = new ArrayList<>();
		        System.out.println("\t\t----------------------------------------------------------------------------------------");
		        System.out.println("\t\tCar ID\t\tBrand\t\tModel\t\tRental Price\t\tStatus\t\tLocation");
		        System.out.println("\t\t----------------------------------------------------------------------------------------");
		        while (fetchResultSet.next()) {
		            String row = "\t\t" + fetchResultSet.getString(1) + "\t|\t" + fetchResultSet.getString(2) + "\t|\t"
		                         + fetchResultSet.getString(3) + "\t|\t" + fetchResultSet.getString(4) + "\t|\t"
		                         + fetchResultSet.getString(5) + "\t|\t" + fetchResultSet.getString(6);
		            rows.add(row);
		        }
		        // Convert List to Stream and print each row
		        rows.stream().forEach(System.out::println);
		        System.out.println("\t\t----------------------------------------------------------------------------------------");
		    }
		} catch (Exception e) {
		    System.out.println(e.getMessage());
		}

		
	}
}
