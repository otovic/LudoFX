package com.example.ludo.utility;

public class Utility {
    public static boolean isEmailValid(String email) {
        return email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    public static String getErrorMessage(String message) {
        switch (message) {
            case "Duplicate entry" -> {
                return "User already exists";
            }
            case "Invalid credentials" -> {
                return "Invalid credentials";
            }
            default -> {
                return "Something went wrong";
            }
        }
    }
}
