package com.ilandaniel.project.helpers;

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


        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
        String hashPasswordStr = new String(hashedPassword, StandardCharsets.UTF_8);

        return hashPasswordStr;
    }

    public static String get_SHA_512_SecurePassword(String passwordToHash, String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }


}
