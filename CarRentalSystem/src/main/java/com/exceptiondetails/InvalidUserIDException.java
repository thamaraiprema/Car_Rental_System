package com.exceptiondetails;

public class InvalidUserIDException extends Exception {
    public InvalidUserIDException(String message) {
        super(message);
    }
}