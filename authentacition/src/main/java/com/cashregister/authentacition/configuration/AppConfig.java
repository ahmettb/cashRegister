package com.cashregister.authentacition.configuration;


import jwt.AuthEntryPointJwt;
import jwt.JwtUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public JwtUtils jwtUtils() {
        return new JwtUtils();
    }


    @Bean
    public AuthEntryPointJwt authEntryPointJwt() {
        return new AuthEntryPointJwt();
    }
}
