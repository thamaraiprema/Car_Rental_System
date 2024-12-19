	package com.carrentalsystem;

import java.util.Scanner;

import com.exceptiondetails.InvalidChoiceException;
import com.exceptiondetails.InvalidValidationException;
import com.userdetails.Admin;

enum AvailabilityStatus{
		Available,Rental;
}
enum Visibility{
		ACTIVE,INACTIVE;
}
	
	public class Car {
		static Scanner sc=new Scanner(System.in);
		private int carId;
	    private String brand;
	    private String model;
	    private double rentalPricePerDay;
	    private AvailabilityStatus rent;
	    private String location;
	    private Visibility isVisible;
	    static Admin admin=new Admin();
	    public Car() {}
	  //Constructor
	    public Car(int carId, String brand, String model, double rentalPricePerDay, String location) {
	        this.carId = carId;
	        this.brand = brand;
	        this.model = model;
	        this.rentalPricePerDay = rentalPricePerDay;
	        this.location = location;
	        this.rent = AvailabilityStatus.Available; // Default to Available status
	        this.isVisible = Visibility.ACTIVE; // Default to Active visibility
	    }

	    public Car(int carId, String brand, String model, double rentalPricePerDay, String status, String location) {
	        this(carId, brand, model, rentalPricePerDay, location); // Call the main constructor
	        this.rent = AvailabilityStatus.valueOf(status); // Set the status
	    }
	    
	    //Getters
	    public int getCarId() {
	        return carId;
	    }
	
	    public String getBrand() {
	        return brand;
	    }
	
	    public String getModel() {
	        return model;
	    }
	    public String getLocation() {
			return location;
		}
		public String getIsVisible() {
			return isVisible.toString();
		}
		
		public void setCarID(int carid) {
			this.carId=carid;
		}
		public void setLocation(String location) {
			this.location = location;
		}
		public void setIsVisible(Visibility isVisible) {
			this.isVisible = isVisible;
		}
		public String isAvailable() {
	    	return rent.toString();
	    }
	    public double getRentalPricePerDay() {
	    	return rentalPricePerDay;
	    }
	
	    //Methods
	    public static double calculatePrice(int rentalDays,double rentalPricePerDay) {
	        return rentalPricePerDay * rentalDays;
	    }
	    
	    public void setRent() {
	    	AvailabilityStatus rent=AvailabilityStatus.Rental;
	    }
	
	    public void setAvailable() {
	    	AvailabilityStatus rent=AvailabilityStatus.Available;
	    }
	    @Override
	    public String toString() {
	        // Pad strings with spaces to achieve uniform column width
	        String formattedCarId = String.format("%-6d", carId);
	        String formattedBrand = String.format("%-12s", brand);
	        String formattedModel = String.format("%-12s", model);
	        String formattedStatus = String.format("%-10s", rent.toString());
	        String formattedPrice = String.format("$%.2f", rentalPricePerDay);
	        String formattedLocation = String.format("%-10s", this.location);

	        return String.format("%s | %s | %s | %s | %s | %s",
	                formattedCarId, formattedBrand, formattedModel, formattedStatus, formattedPrice, formattedLocation);
	    }
	    String ANSI_RESET = "\u001B[0m";
	    String ANSI_RED = "\u001B[31m";
	    public void carManagement() throws InvalidChoiceException, InvalidValidationException {
	    	System.out.print("\t**********************************************************************************************\n");
	        System.out.print("\t*                                                                                            *\n");
	        System.out.print("\t*                  1.Add new Cars                                                            *\n");
	        System.out.print("\t*                  2.Update car details.                                                     *\n");
	        System.out.print("\t*                  3.Remove cars                                                             *\n");
	        System.out.print("\t*                  4.View Car details                                                        *\n");
	        System.out.print("\t*                  5.Go to Previous page                                                     *\n");
	        System.out.print("\t*                                                                                            *\n");
	        System.out.print("\t**********************************************************************************************\n");
	        System.out.print("\t\tEnter your choice:");
			String choice = sc.next();
			switch(choice) {
				case "1":
				{
					//To add new cars
					admin.addCar();
					break;
				}
				case "2":
				{
					//To update the existing car details
					admin.updateCar();
					break;
				}
				case "3":
				{
					//To remove existing cars
					admin.removeCar();
					break;
				}
				case "4":
				{
					//To view all car details
					admin.viewCarDetails();
					break;
				}
				case "5":
				{
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
