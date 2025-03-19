package com.school.configurationservice.config;

import com.school.configurationservice.security.CustomUserDetailsService;
import com.school.configurationservice.security.UserInfoFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity(prePostEnabled = true) // Enable method security
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final UserInfoFilter userInfoFilter;

    public SecurityConfig(UserDetailsService userDetailsService, UserInfoFilter userInfoFilter) {
        this.userDetailsService = userDetailsService;
        this.userInfoFilter = userInfoFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(CustomUserDetailsService userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configure(http)) // Enable CORS
                .csrf(csrf -> csrf.disable()) // Disable CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/v3/api-docs/**", // Allow Swagger API docs
                                "/swagger-ui/**", // Allow Swagger UI
                                "/swagger-ui.html", // Allow Swagger UI HTML
                                "/auth/register", // Allow registration endpoint
                                "/auth/login", // Allow login endpoint
                                "/auth/public" // Allow public endpoint
                        ).permitAll() // Permit all access to the above endpoints
                        .anyRequest().authenticated() // All other endpoints require authentication
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless session
                .addFilterBefore(userInfoFilter, UsernamePasswordAuthenticationFilter.class); // Add UserInfoFilter

        return http.build();
    }
}
