package com.example.springchat.security.jwt;

import com.example.springchat.security.UserPrincipal;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class TokenProvider {

    private final Key key;

    private final long tokenValidityInMillisecondsForRememberMe;

    public TokenProvider() {
        byte[] keyBytes;
        String secret = "MWU2ODZkZGM1MTYyY2Y1NmQ2YmYyMzg2NmRmODk1MjY3MTMyMDg2ZmU4YjRmMmZlY2MzMzAzZTkzNzA1ZTc1YmE0MDIzODM5ZmZhNmE1ZmEyNTIwMjhmNjFkZDUxNjIxZmM4YjQ2ZWZjYzAyMmI1N2JhODYwZTQwY2UyOGVlMDE";
        keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        key = Keys.hmacShaKeyFor(keyBytes);
        this.tokenValidityInMillisecondsForRememberMe = 1000 * 60 * 100l;
    }

    public String createToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return generateToken(userPrincipal);
    }

    public String generateToken(UserPrincipal userPrincipal) {
        var now = new Date();
        var expiryDate = new Date(now.getTime() + this.tokenValidityInMillisecondsForRememberMe);
        return Jwts.builder()
                .setSubject(String.valueOf(userPrincipal.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public Long getUserIdFromToken(String token) {
        return Long.valueOf(Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject());
    }

    public Boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);
            return true;
        } catch (Exception ex) {
            return false;
        }

    }
}

