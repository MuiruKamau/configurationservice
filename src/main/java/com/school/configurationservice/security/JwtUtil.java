package com.school.configurationservice.security;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;


@Component
public class JwtUtil {

    private final String SECRET_KEY = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJPbmxpbmUgSldUIEJ1aWxkZXIiLCJpYXQiOjE3NDA0NzEzMDAsImV4cCI6MTc3MjAwNzMwMCwiYXVkIjoid3d3LmV4YW1wbGUuY29tIiwic3ViIjoianJvY2tldEBleGFtcGxlLmNvbSIsIkdpdmVuTmFtZSI6IkpvaG5ueSIsIlN1cm5hbWUiOiJSb2NrZXQiLCJFbWFpbCI6Impyb2NrZXRAZXhhbwbwLmNvbSIsIlJvbGUiOlsiTWFuYWdlciIsIlByb2plY3QgQWRtaW5pc3RyYXRvciJdfQ.hHsXmdTJ37eVX4lFlHX9kzuTQvKPqHu28Dy17KSoq_o";
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());


    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(key)
                .parseClaimsJws(token).getBody().getSubject();
    }

    private boolean isTokenExpired(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }





    public String getSECRET_KEY() { // Add getter for SECRET_KEY
        return SECRET_KEY;
    }
    public SecretKey getSecretKey() { // Add getter for SECRET_KEY
        return key;
    }
}


