package com.school.configurationservice.security;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;


@Component
public class JwtUtil {

    private final String SECRET_KEY = "b8bf6d705e12323b8ba6dc75cf5901e54054785994cc52e01a784e2867e0835fe641e81b06b8f07ce527d366ded74183a4af85577e5e60bacdf15917a541b43538d37ba9d1995cdc6b070b497c18e65716b1658f8597a5bf0d7c48f8b90d5c19832cd98cbaed65318c0d639f6fe178bd76873db273f1227a07f762aeb7689606792f2cd8e8e75115d37767015cce13b4f7265fde3351c9e3c9e4ee4624880fc2a48be32d90d5e9af1a7233eb7b290941563c0aba3d1064338aad3a21cc801b674ad8c50603b5fbefda1e12b6ef17871a31e39312859e1ba7f41f3b29ca476ee1fa445385b66a478a9802bb9c8ce309d3a8c2b41c9f9c47f50f7cb62d3e17906b";
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


