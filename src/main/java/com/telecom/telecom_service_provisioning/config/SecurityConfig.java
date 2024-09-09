package com.telecom.telecom_service_provisioning.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.telecom.telecom_service_provisioning.service.implementations.CustomUserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Bean
        public UserDetailsService userDetailsService() {
                return new CustomUserDetailsServiceImpl(); // Your implementation of UserDetailsService
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable()) // Updated way to configure CSRF
                .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers("/signup", "/login", "/checkLoggedInUser", "/api/internet-services", "/api/tv-services").permitAll() // Updated
                                .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                                .requestMatchers("/user/**").hasAuthority("ROLE_USER")
                                .anyRequest().authenticated())
                .formLogin((form) -> form.defaultSuccessUrl("/home"))// Use defaults for form login
                .logout((logout) -> logout.logoutSuccessUrl("/home")); // Use defaults for logout
                return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationManager authManager(HttpSecurity http) throws Exception {
                AuthenticationManagerBuilder authenticationManagerBuilder = http
                                .getSharedObject(AuthenticationManagerBuilder.class);
                authenticationManagerBuilder.authenticationProvider(authenticationProvider());
                return authenticationManagerBuilder.build();
        }

        @Bean
        public DaoAuthenticationProvider authenticationProvider() {
                DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
                authProvider.setUserDetailsService(userDetailsService()); // Set your UserDetailsService
                authProvider.setPasswordEncoder(passwordEncoder()); // Set your PasswordEncoder
                return authProvider;
        }
}