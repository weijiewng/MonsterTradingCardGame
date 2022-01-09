package com.company.game.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.UUID;

public class Toolbox {

    public static String createHash(String toHash){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = md.digest(toHash.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder(2 * encodedHash.length);
            for (int i = 0; i < encodedHash.length; i++) {
                String hex = Integer.toHexString(0xff & encodedHash[i]);
                if(hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String createUUID(){
        return UUID.randomUUID().toString();
    }
}
