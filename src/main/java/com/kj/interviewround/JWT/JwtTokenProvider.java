package com.kj.interviewround.JWT;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwtSecret:mySecretKeyForJWT}")
    private String jwtSecret;

    @Value("${app.jwtExpirationMs:86400000}")
    private long jwtExpirationMs;

    public String generateToken(Long adminId, String email, String role) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject(String.valueOf(adminId))
                .claim("email", email)
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public Long getAdminIdFromToken(String token) {
        return Long.parseLong(Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject());
    }

    public String getEmailFromToken(String token) {
        return (String) Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .get("email");
    }

    public String getRoleFromToken(String token) {
        return (String) Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .get("role");
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
