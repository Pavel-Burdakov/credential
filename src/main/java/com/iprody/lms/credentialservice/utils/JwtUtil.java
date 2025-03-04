package com.iprody.lms.credentialservice.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

  private final SecretKey secretKey;

  public JwtUtil(@Value("${jwt.secret}")String secret) {
    this.secretKey = Keys.hmacShaKeyFor(secret.getBytes()); // Преобразуем строку в SecretKey
  }
  public String generateToken(String username, String role) {
    return Jwts.builder()
       .setSubject(username)
       .claim("role", role)
       .setIssuedAt(new Date())
       .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 час
       .signWith(secretKey) // Используем SecretKey для подписи
       .compact();
  }

  public Claims extractClaims(String token) {
    return Jwts.parserBuilder()
       .setSigningKey(secretKey) // Используем SecretKey для проверки
       .build()
       .parseClaimsJws(token) // Парсим токен
       .getBody();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder()
         .setSigningKey(secretKey)
         .build()
         .parseClaimsJws(token); // Парсим токен
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}