package com.rgr.storeApp.secutity.jwt;

import com.rgr.storeApp.secutity.SecurityUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class JwtBuilder {

    private String secret= "secretwet";

    private int jwtExp= 3600000;


    public String generateToken(Authentication authentication){
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        return Jwts.builder().setSubject((securityUser.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExp))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

    }

    public boolean validateToken(String token){
        try{
            Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token);
            return true;
        }  catch (MalformedJwtException e){
            System.err.println(e.getMessage());
        }  catch (IllegalArgumentException e){
            System.err.println(e.getMessage());
        }
        return true;
    }

    public String getEmailFromToken(String jwt){
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();
        //???
    }




}
