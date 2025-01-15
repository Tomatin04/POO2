package org.example.Model.objects;

import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import io.jsonwebtoken.security.Keys;

public class Token {
    
    private String token;
    private Date expiration;

    public Token(String token, Date expiration) {
        this.token = token;
        this.expiration = expiration;
    }

    public String getToken() {
        return token;
    }

    public Date getExpiration() {
        return expiration;
    }

    public SecretKey getKey() {
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(this.token));
    }
}
