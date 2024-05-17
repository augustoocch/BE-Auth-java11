package com.augustoocc.demo.globant.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class PasswordSecurity {

    @Bean
    public BCryptPasswordEncoder passowrdEncoder() {
        return new BCryptPasswordEncoder();
    }

    public String passowrdEncrypt(String passw) {
        String passwordE = null;
        try {
            passwordE = new String(passowrdEncoder().encode(passw).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return passwordE;
    }

    public boolean comparePassword(String password, String encodedPassword) {
        return passowrdEncoder().matches(password, encodedPassword);
    }
}