package com.vlc2.assets.utils;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordGenerator {

    public static String generatePassword(String passwordToEncode){
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return passwordEncoder.encode(passwordToEncode);
    }
    public static void main(String[] args) {
        // TODO : enter the password to encrypt
        String encodedPassword = generatePassword("");
        System.out.println("Encoded password: " + encodedPassword);
    }
}
