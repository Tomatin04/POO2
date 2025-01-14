package org.example.Model.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

import org.example.Model.objects.User;

public class token {
 
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static String generateToken(User user, boolean isActive) {
        return Jwts.builder()
                .setSubject(user.getFullName().substring(0, 5) + "#" + user.getId())
                .claim("email", user.getEmail())
                .claim("role", user.getRole())
                .claim("isActive", isActive)
                .setIssuer("POO2_ServerEmiter")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 300000))
                .signWith(key)
                .compact();
    }
    
}