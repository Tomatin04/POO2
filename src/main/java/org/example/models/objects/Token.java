package org.example.models.objects;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import io.jsonwebtoken.security.Keys;

public class Token {
    
    private String token;
    private Date expiration;
    private static List<String> blakcList;

    public Token(String token, Date expiration) {
        this.token = token;
        this.expiration = expiration;
        blakcList = null;
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

    public void flushBlackList() {
        Token.blakcList = null;
    }

    public void addToBlackList(String token) {
        if (Token.blakcList == null) {
            Token.blakcList = List.of(token);
        } else {
            Token.blakcList.add(token);
        }
    }

    public boolean isBlackListed(String token) {
        if (Token.blakcList == null) {
            return false;
        }
        return Token.blakcList.contains(token);
    }
}
