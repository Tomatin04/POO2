package org.example.Contorlers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Properties;

import org.example.Model.objects.Token;
import org.example.Model.objects.User;

import java.io.FileOutputStream;
import java.io.InputStream;

public class TokenController {

    public static boolean initializeKey() {
        try (InputStream input = TokenController.class.getClassLoader().getResourceAsStream("props.properties")) {
            if (input == null) {
                System.out.println("Erro na inicialização da chave: arquivo não encontrado.");
                return false;
            }
            Properties props = new Properties();
            props.load(input);
            String keyGenerationDate = props.getProperty("KEY_CREATION");
            if (keyGenerationDate == null || keyGenerationDate.isEmpty()) {
                return createKey(props);
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            final long WEEK_IN_MILLIS = 7 * 24 * 60 * 60 * 1000L;
            Date currentTime = new Date();
            Date keyDate = new Date(sdf.parse(keyGenerationDate).getTime() + WEEK_IN_MILLIS);
            if (currentTime.after(keyDate) || props.getProperty("KEY") == null || props.getProperty("KEY").isEmpty()) {
                return createKey(props);
            }
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao inicializar a chave: " + e.getMessage());
            return false;
        }
    }

    private static boolean createKey(Properties props) {
            try {
                Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
                String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if (props.getProperty("KEY") != null && !props.getProperty("KEY").isEmpty()) {
                    props.setProperty("OLD_KEY", props.getProperty("KEY"));
                }
                props.setProperty("KEY", encodedKey);
                props.setProperty("KEY_CREATION", sdf.format(date));
                try (FileOutputStream fos = new FileOutputStream(System.getProperty("user.dir") +"/src/main/resources/props.properties")) {
                    props.store(fos, "Configurações Locais");
                }
                return true;
            } catch (Exception e) {
                System.out.println("Erro ao criar a chave: " + e.getMessage());
                return false;
            }	
        }
    
        public static Token createTokenModel(){
            try (InputStream input = TokenController.class.getClassLoader().getResourceAsStream("props.properties")) {
                if (input == null) {
                    System.out.println("Properties not found.");
                    return null;
                }else{
                    Properties props = new Properties();
                    props.load(input);
                    String key = props.getProperty("KEY");
                    String keyGenerationDate = props.getProperty("KEY_CREATION");
                    if (keyGenerationDate == null || keyGenerationDate.isEmpty()) {
                        if (createKey(props)) {
                        key = props.getProperty("KEY");
                        keyGenerationDate = props.getProperty("KEY_CREATION");
                    }else{
                        return null;
                    }
                }
                final long WEEK_IN_MILLIS = 7 * 24 * 60 * 60 * 1000L;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date keyDate = new Date(sdf.parse(keyGenerationDate).getTime() + WEEK_IN_MILLIS);
                if (key == null || key.isEmpty()) {
                    System.out.println("Key not found.");
                    return null;
                }else{
                    System.out.println("Key: " + key);
                    System.out.println("Key Date: " + keyDate);
                    return new Token(key, keyDate);
                }
            }
        }catch (Exception e) {
            System.out.println("Error while creating token model: " + e.getMessage());
            return null;
        }
    }

    public static String generateToken(User user, boolean isActive, Token token) {
        if (!validateTokenGenerateParameters(user, token)) {
            return null;
        }
        return Jwts.builder()
                .setSubject(user.getFullName().substring(0, 5) + "#" + user.getId())
                .claim("email", user.getEmail())
                .claim("role", user.getRole())
                .claim("isActive", isActive)
                .setIssuer("POO2_ServerEmiter")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 300000))
                .signWith(token.getKey())
                .compact();
    }   

    private static boolean validateTokenGenerateParameters(User user, Token token) {
        if (user == null || token == null || token.getKey() == null || token.getKey().getEncoded() == null || 
            token.getKey().getEncoded().length == 0 || user.getFullName() == null || user.getFullName().isEmpty() || 
            user.getId() == 0 || user.getEmail() == null || user.getEmail().isEmpty() || user.getRole() == null || user.getRole().isEmpty()) {
            return false;
        }
        return true;
    }
    
    private static boolean validateToken(String token, Token tokenModel) {
        if (token == null || token.isEmpty() || tokenModel == null || tokenModel.isBlackListed(token)) {
            System.out.println("Token inválido: token nulo ou vazio.");
            return false;
        }
        if (tokenModel.getKey() == null || tokenModel.getKey().getEncoded() == null || tokenModel.getKey().getEncoded().length == 0) {
            System.out.println("Token inválido: chave nula ou vazia.");
            return false; 
        }
        try {
            Jwts.parserBuilder().setSigningKey(tokenModel.getKey()).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ejee) {
            System.out.println("Token expirado: " + ejee.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Token inválido: " + e.getMessage());
            return false;
        }
    }

    
    public static User getUserDataFromToken(String token, Token tokenModel) {        
    if (!validateToken(token, tokenModel)) {
        return null;
    }
    Claims claims = Jwts.parserBuilder()
                        .setSigningKey(tokenModel.getKey())
                        .build()
                        .parseClaimsJws(token)
                        .getBody();
    User user = new User();
    user.setEmail(claims.get("email", String.class));
    user.setRole(claims.get("role", String.class));
    String subject = claims.getSubject();
    String[] subjectParts = subject.split("#");
    user.setFullName(subjectParts[0]);
    user.setId(Integer.parseInt(subjectParts[1]));
    return user;
}
   
}