package com.augustoocc.demo.globant.security;

import com.augustoocc.demo.globant.model.service.impl.UserDetailedService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

@Configuration
public class AuthConfig {

    private final UserDetailedService userDetailsService;
    private final EncryptionConfig encryptionConfig;

    public AuthConfig(UserDetailedService userDetailsService, EncryptionConfig encryptionConfig) {
        this.userDetailsService = userDetailsService;
        this.encryptionConfig = encryptionConfig;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encryptionConfig.getPasswordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
