package com.example.helloworld.helpers;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Security {

    /**
     * change data into sha-512 hash.
     */
    public static String sha512Encryption(String password) {


        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
            md.reset();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        assert md != null;
        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

        return new String(hashedPassword, StandardCharsets.UTF_8);
    }


}
