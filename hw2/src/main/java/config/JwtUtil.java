package config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.util.Date;
import java.util.Properties;

public class JwtUtil {
    private static JwtUtil instance;
    private final int jwtExpiration;
    private final SecretKey key;

    private JwtUtil(){
        Properties properties=ConfigLoader.getInstance().getProperties();
        String jwtSecret = properties.getProperty("app.jwtSecret");
        jwtExpiration= Integer.parseInt(properties.getProperty("app.jwtExpiration"));
        key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

    }
    public static JwtUtil getInstance(){
        if (instance==null){
            instance=new JwtUtil();
        }
        return instance;
    }
    public String generateToken(String login){
        return Jwts.builder()
                .subject(login)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+jwtExpiration))
                .signWith(key)
                .compact();
    }
    public String getLoginFromJwtToken(String jwt)throws IllegalArgumentException,JwtException{
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(jwt)
                    .getBody();
            return claims.getSubject();





    }
    public boolean validateToken(String jwt){
        try{
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(jwt);
            return true;
        }catch (JwtException | IllegalArgumentException e){
            return false;
        }

    }

}
//TODOвозможно тут что то не так с исключениями, возможно надо сделать обраотку в getLogin  так как вызов validateToken может быть излишним
