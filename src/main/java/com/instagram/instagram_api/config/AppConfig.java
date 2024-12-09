package com.instagram.instagram_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class AppConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for simplicity
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/signup").permitAll() // Allow /signup endpoint
                        .anyRequest().authenticated()) // Protect all other endpoints
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Use stateless session policy
                .formLogin(form -> form // Enable form-based login
                        .loginPage("/login") // Specify custom login page (or use default)
                        .permitAll()) // Allow access to login page
                .httpBasic(httpBasic -> {
                })
                .addFilterAfter(new JwtTokenGenerator(), BasicAuthenticationFilter.class)
                .addFilterBefore(new JwtTokenValidationFilter(), BasicAuthenticationFilter.class);// Enable HTTP Basic Authentication for non-browser clients
        return http.build();
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
