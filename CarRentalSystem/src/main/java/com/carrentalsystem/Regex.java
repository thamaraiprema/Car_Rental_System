package com.carrentalsystem;

public class Regex {
	public static boolean isValidName(String name) {
        return name.matches("\\d+");
    }

	public static boolean isValidEmail(String email) {
        return email.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$");
    }

	public static boolean isValidMobile(String mobile) {
        return mobile.matches("^[6-9]\\d{9}$");
    }

	public static boolean isValidAge(int age) {
        return age > 0 && age <= 150; // Assuming valid age range (1-150)
    }

	public static boolean isValidGender(String gender) {
        return gender.equalsIgnoreCase("male") || gender.equalsIgnoreCase("female") || gender.equalsIgnoreCase("transgender");
    }

	public static boolean isValidLicenseNumber(String licenseNumber) {
        return licenseNumber.matches("\\d+");
    }
}
