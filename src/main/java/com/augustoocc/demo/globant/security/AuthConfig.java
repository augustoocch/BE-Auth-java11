package com.augustoocc.demo.globant.security;

import com.augustoocc.demo.globant.service.impl.UserDetailedService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

@Configuration
public class AuthConfig {

    private final UserDetailedService userDetailsService;
    private final PasswordSecurity passwordSecurity;

    public AuthConfig(UserDetailedService userDetailsService, PasswordSecurity passwordSecurity) {
        this.userDetailsService = userDetailsService;
        this.passwordSecurity = passwordSecurity;
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordSecurity.passowrdEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
