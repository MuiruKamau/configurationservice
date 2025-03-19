package com.school.configurationservice.security;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final JdbcTemplate jdbcTemplate;

    // Use constructor injection instead of @Autowired on fields
    public CustomUserDetailsService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Query user details and roles in a single query using a join
        String query = """
            SELECT u.username, u.password, r.role
            FROM users u
            LEFT JOIN user_roles r ON u.username = r.username
            WHERE u.username = ?
            """;

        try {
            // Fetch user details and roles
            List<UserWithRole> userWithRoles = jdbcTemplate.query(
                    query,
                    new Object[]{username},
                    (rs, rowNum) -> new UserWithRole(
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("role")
                    )
            );

            if (userWithRoles.isEmpty()) {
                throw new UsernameNotFoundException("User not found: " + username);
            }

            // Extract roles
            List<SimpleGrantedAuthority> authorities = userWithRoles.stream()
                    .map(userWithRole -> new SimpleGrantedAuthority("ROLE_" + userWithRole.getRole()))
                    .collect(Collectors.toList());

            // Create UserDetails object
            return new User(
                    userWithRoles.get(0).getUsername(),
                    userWithRoles.get(0).getPassword(),
                    authorities
            );
        } catch (Exception e) {
            throw new UsernameNotFoundException("Error loading user: " + username, e);
        }
    }

    // Helper class to represent user details with roles
    private static class UserWithRole {
        private final String username;
        private final String password;
        private final String role;

        public UserWithRole(String username, String password, String role) {
            this.username = username;
            this.password = password;
            this.role = role;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public String getRole() {
            return role;
        }
    }
}

//package com.school.configurationservice.security;
//
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.security.core.userdetails.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // Query user details directly from the database
//        String queryUser = "SELECT username, password FROM users WHERE username = ?";
//        List<UserDetails> users = jdbcTemplate.query(queryUser, new Object[]{username}, (rs, rowNum) ->
//                new org.springframework.security.core.userdetails.User(
//                        rs.getString("username"),
//                        rs.getString("password"),
//                        getAuthorities(username) // Fetch roles separately
//                )
//        );
//
//        if (users.isEmpty()) {
//            throw new UsernameNotFoundException("User not found");
//        }
//
//        return users.get(0);
//    }
//
//    private List<SimpleGrantedAuthority> getAuthorities(String username) {
//        String queryRoles = "SELECT role FROM user_roles WHERE username = ?";
//        return jdbcTemplate.query(queryRoles, new Object[]{username}, (rs, rowNum) ->
//                new SimpleGrantedAuthority("ROLE_" + rs.getString("role"))
//        );
//    }
//}
//
