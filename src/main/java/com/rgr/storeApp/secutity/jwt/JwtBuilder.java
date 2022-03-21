package com.rgr.storeApp.secutity.jwt;

import com.rgr.storeApp.dto.LoginResponse;
import com.rgr.storeApp.exceptions.api.NotFound;
import com.rgr.storeApp.exceptions.api.NotPrivilege;
import com.rgr.storeApp.models.RefreshToken;
import com.rgr.storeApp.models.User;
import com.rgr.storeApp.repo.RefreshTokenRepo;
import com.rgr.storeApp.repo.UsersRepo;
import com.rgr.storeApp.secutity.SecurityUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;


@Component
public class JwtBuilder {

    private UsersRepo usersRepo;
    private RefreshTokenRepo refreshTokenRepo;

    @Value("{secret.word.jwt}")
    private String secret;

    private int jwtExp= 3600000;


    public String generateToken(String email){
        return Jwts.builder().setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExp))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

    }
    public String generateRefreshToken(String email){
        return UUID.nameUUIDFromBytes((email + LocalDateTime.now()).getBytes()).toString();
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
