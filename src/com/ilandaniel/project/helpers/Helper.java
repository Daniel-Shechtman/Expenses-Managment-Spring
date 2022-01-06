package com.ilandaniel.project.helpers;

import com.ilandaniel.project.classes.Account;

import javax.swing.*;
import java.util.HashMap;

public class Helper {

    public static Account loggedInAccount;
    public static HashMap<String,Float> currencies = new HashMap<>();

    static {
        currencies.put("ILS",1f);
        currencies.put("USD",3.11f);
        currencies.put("EURO",3.51f);
    }


    public static void showMessage(String title, String message) {
        JOptionPane.showMessageDialog(null, message, "InfoBox: " + title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean isNumeric(String message) {
        try {
            Float.parseFloat(message);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static String getIconPathByCategoryName(String categoryName) {
        String path = switch (categoryName) {
            case "Food" -> "/images/food_icon.png";
            case "Household" -> "/images/home_icon.png";
            case "Loans" -> "/images/loan_icon.png";
            case "Automobile" -> "/images/automobile_icon.png";
            case "Travel" -> "/images/travel_icon.png";
            default -> "/images/default_icon.png";
        };
        return path;
    }

}
