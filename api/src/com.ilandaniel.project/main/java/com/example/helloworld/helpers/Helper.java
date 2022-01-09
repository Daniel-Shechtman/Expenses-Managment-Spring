package com.example.helloworld.helpers;

import java.util.HashMap;

public class Helper {

    public static HashMap<String,Float> currencies = new HashMap<>();

    static {
        currencies.put("ILS",1f);
        currencies.put("USD",3.11f);
        currencies.put("EURO",3.51f);
    }


    public static boolean isNumeric(String message) {
        try {
            Float.parseFloat(message);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
