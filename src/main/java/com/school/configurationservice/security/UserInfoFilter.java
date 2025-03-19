package com.school.configurationservice.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserInfoFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Extract user information from headers
        String username = request.getHeader("X-User-Username");
        String rolesHeader = request.getHeader("X-User-Roles");

        logger.debug("Received headers - Username: {}, Roles: {}", username, rolesHeader);

        // If headers are present, set up the authentication
        if (username != null && rolesHeader != null) {
            // Convert roles from the header to a list of SimpleGrantedAuthority
            List<SimpleGrantedAuthority> authorities = Arrays.stream(rolesHeader.split(","))
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))  // Add ROLE_ prefix here
                    .collect(Collectors.toList());
//            List<SimpleGrantedAuthority> authorities = Arrays.stream(rolesHeader.split(","))
//                    .map(role -> new SimpleGrantedAuthority(role))
//                    .collect(Collectors.toList());

            logger.debug("Created authorities: {}", authorities);

            // Create UserDetails object
            UserDetails userDetails = new User(username, "", authorities);

            // Create an authentication token
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            // Set additional details (e.g., IP address, session ID)
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Set the authentication in the SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);

            logger.debug("Set authentication: {}", authentication);
        } else {
            logger.warn("Missing user information headers");
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}
