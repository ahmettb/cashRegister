//package com.cashRegister.gateway.config;
//
//import lombok.AllArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//
//@Configuration
//@EnableWebSecurity
//@AllArgsConstructor
//public class AuthConfig {
//
//
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        System.out.println("Gelen istek Urlsi "+ http);
//        http.csrf(csrf -> csrf.disable())
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(auth ->
//                        auth.requestMatchers("/api/v6/auth/**").permitAll()
//                                .requestMatchers("/api/v3/sale","api/v3/campaign").hasAnyAuthority(ERole.ROLE_KASIYER.name())
//                                .requestMatchers("/api/v1/product/**").hasAnyAuthority(ERole.ROLE_KASIYER.name())
//                                .requestMatchers("/api/v2/report/**").hasAnyAuthority(ERole.ROLE_KASIYER.name())
//
//                                .anyRequest().authenticated()
//                );
//
//        return http.build();
//    }
//
//}
