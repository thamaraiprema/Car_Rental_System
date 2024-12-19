package com.userdetails;

public class User {
	private String first_Name;
	private String last_Name;
	private Account account;
	public User() {
		this.account=new Account();
	}
	public User(String first_Name,String last_Name,String role,int userid,String password) {
		this.account=new Account(userid,password,role);
		this.first_Name=first_Name;
		this.last_Name=last_Name;
	}
	public String getfirst_Name() {
		return first_Name;
	}
	public String getlast_Name() {
		return last_Name;
	}
	public void setFirst_Name(String first_Name) {
		this.first_Name = first_Name;
	}
	public void setLast_Name(String last_Name) {
		this.last_Name = last_Name;
	}
	public int getUserid() {
		return account.getUserid();
	}
	
	
}
