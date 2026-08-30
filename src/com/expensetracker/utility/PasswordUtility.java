package com.expensetracker.utility;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtility {
    private PasswordUtility() {

    }
    public static String hashPassword(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hasedBytes = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder stringBuilder = new StringBuilder();
            for(byte hasedByte : hasedBytes) {
                stringBuilder.append(String.format("%02x", hasedByte));
            }
            return stringBuilder.toString();
        }catch (NoSuchAlgorithmException e){
            throw new RuntimeException("Unable to process password securely" , e);
        }
    }

}
