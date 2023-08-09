package com.example.spring.rest.api.security.jwt;

import com.example.core.entity.RefreshToken;
import com.example.core.repository.RefreshTokenRepository;
import com.example.spring.rest.api.error.TokenRefreshException;
import com.example.spring.rest.api.model.TokenRefreshResponse;
import com.example.spring.rest.api.security.UserPrincipal;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Configuration
@PropertySource("classpath:application.properties")
@Transactional
public class TokenProvider {

    private final Key key;

    private final long tokenValidityInMillisecondsForRememberMe;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    public TokenProvider(@Value("${token.secret}") String tokenSecret) {
        byte[] keyBytes;
        String secret = tokenSecret;
        keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        key = Keys.hmacShaKeyFor(keyBytes);
        this.tokenValidityInMillisecondsForRememberMe = 1000 * 60 * 1L;
    }

    public String createToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return generateToken(userPrincipal.getId());
    }

    public String generateToken(Long userId) {
        var now = new Date();
        var expiryDate = new Date(now.getTime() + this.tokenValidityInMillisecondsForRememberMe);
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
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

    public TokenRefreshResponse refreshToken(String refreshToken) {
        RefreshToken entity = refreshTokenRepository.findByRefreshToken(refreshToken);
        if (entity == null) {
            throw new TokenRefreshException(refreshToken, "Refresh token not found");
        }
        if (entity.getExpiryDate().compareTo(new Date()) < 0) {
            refreshTokenRepository.delete(entity);
            refreshTokenRepository.flush();
            throw new TokenRefreshException(refreshToken, "Refresh token was expired. Please make a new signin request");
        }
        return new TokenRefreshResponse(generateToken(entity.getUserId()), createRefreshToken(entity.getUserId()));
    }

    public String createRefreshToken(Long userId) {
        RefreshToken entity = refreshTokenRepository.findByUserId(userId);
        if (entity == null) {
            return generateRefreshToken(userId);
        }
        return entity.getRefreshToken();
    }

    public String generateRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUserId(userId);
        refreshToken.setExpiryDate(DateUtils.addHours(new Date(), 2));
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken.getRefreshToken();
    }

}

