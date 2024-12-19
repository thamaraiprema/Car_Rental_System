package com.bookingdetails;

import java.time.LocalDate;

import com.carrentalsystem.Car;
import com.userdetails.Account;

public class BookingDetails {
	private int bookid;
	private LocalDate currentDate;
	private LocalDate pickupDate;
	private LocalDate returnDate;
	private double totalPrice;
	private Account account;
	private Car car;
	
	public BookingDetails(int bookid,LocalDate currentDate,LocalDate pickupDate,LocalDate returnDate,double totalPrice,int userid,int carid) {
		this.bookid=bookid;
		this.currentDate=currentDate;
		this.pickupDate=pickupDate;
		this.returnDate=returnDate;
		this.totalPrice=totalPrice;
		this.account=new Account();
		account.setUserid(userid);
		this.car=new Car();
		car.setCarID(carid);;
	}

	public int getBookid() {
		return bookid;
	}

	public LocalDate getCurrentDate() {
		return currentDate;
	}

	public LocalDate getPickupDate() {
		return pickupDate;
	}

	public LocalDate getReturnDate() {
		return returnDate;
	}

	public double getTotalPrice() {
		return totalPrice;
	}
	public int getCarId() {
		return car.getCarId();
	}
	public int getUserId() {
		return account.getUserid();
	}

	public void setCurrentDate(LocalDate currentDate) {
		this.currentDate = currentDate;
	}

	public void setPickupDate(LocalDate pickupDate) {
		this.pickupDate = pickupDate;
	}

	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

}
