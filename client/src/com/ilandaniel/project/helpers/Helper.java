package com.ilandaniel.project.helpers;

import com.ilandaniel.project.classes.Account;

import javax.swing.*;
import java.util.HashMap;

/**
 * Helper class with some static variables/methods that helped us throughout the project
 */
public class Helper {

    /**
     * Account variable that holds the current account signed in
     */
    public static Account loggedInAccount;
    /**
     * An hashMap that hold the value of the currencies in the project
     */
    public static HashMap<String, Float> currencies = new HashMap<>();

    /**
     * Static method the initializing the currencies name and value with float values
     */
    static {
        currencies.put("ILS", 1f);
        currencies.put("USD", 3.11f);
        currencies.put("EURO", 3.51f);
    }


    /**
     * Method that helps us to display all user the messages throughout the project
     */
    public static void showMessage(String title, String message) {
        JOptionPane.showMessageDialog(null, message, "InfoBox: " + title, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Method that receives a string and returns the value of that string is numeric
     */
    public static boolean isNumeric(String message) {
        try {
            Float.parseFloat(message);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Method that receives a category name as a string and returns the path to the icon of that category.
     * If the category is not in the initial category list the icon set to default icon.
     */
    public static String getIconPathByCategoryName(String categoryName) {
        return switch (categoryName) {
            case "Food" -> "/images/food_icon.png";
            case "Household" -> "/images/home_icon.png";
            case "Loans" -> "/images/loan_icon.png";
            case "Automobile" -> "/images/automobile_icon.png";
            case "Travel" -> "/images/travel_icon.png";
            default -> "/images/default_icon.png";
        };
    }

}
