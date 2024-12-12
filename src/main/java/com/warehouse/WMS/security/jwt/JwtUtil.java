package com.warehouse.WMS.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

public class JwtUtil {
    public static String generateToken(User user) {
        return Jwts
                .builder()
                .subject(user.getUsername())
                .claim("roles", user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList())
                .expiration(new Date(System.currentTimeMillis() + 900_000))
                .signWith(getSigningKey())
                .compact();
    }

    public static Claims getClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public static List<String> getRoles(String token) {
        Claims claims = getClaims(token);
        return claims.get("roles", List.class); // Преобразуйте список ролей из токена
    }

    public static boolean isTokenValid(String token) {
        return !isExpired(token);
    }

    private static boolean isExpired(String token) {
        return getClaims(token)
                .getExpiration()
                .before(new Date());
    }

    private static SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode("qwerghkjasdftyiubnjkercvxszoplbfemlpihvatxe");
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
