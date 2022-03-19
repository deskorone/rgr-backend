package com.rgr.storeApp.secutity.jwt;

import com.rgr.storeApp.secutity.SecurityUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class JwtBuilder {

    @Value("{secret.word.jwt}")
    private String secret;

    private int jwtExp= 36000000;


    public String generateToken(Authentication authentication){
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        return Jwts.builder().setSubject((securityUser.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExp))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

    }
    public String generateRefreshToken(Authentication authentication){
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        return Jwts.builder().setSubject((securityUser.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExp * 100))
                .signWith(SignatureAlgorithm.HS512, secret + "refresh")
                .compact();
    }


    public boolean validateRefreshToken(String token){
        try{
            Jwts.parser()
                    .setSigningKey(secret + "refresh")
                    .parseClaimsJws(token);
            return true;
        }  catch (MalformedJwtException e){
            return false;
        }  catch (IllegalArgumentException e){
            return false;
        }
    }


    public boolean validateToken(String token){
        try{
            Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token);
            System.out.println("LWasdasd:");
            return true;
        }  catch (MalformedJwtException e){
            return false;
        }  catch (IllegalArgumentException e){
            return false;
        }catch (Exception e){
            return false;
        }
    }

    public String getEmailFromToken(String jwt){
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();
    }




}
