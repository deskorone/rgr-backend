package com.rgr.storeApp.service.profile;


import com.rgr.storeApp.dto.LoginResponse;
import com.rgr.storeApp.exceptions.api.NotFound;
import com.rgr.storeApp.exceptions.api.NotPrivilege;
import com.rgr.storeApp.models.RefreshToken;
import com.rgr.storeApp.models.User;
import com.rgr.storeApp.repo.RefreshTokenRepo;
import com.rgr.storeApp.secutity.jwt.JwtBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Service
public class RefreshService {

    private final RefreshTokenRepo refreshTokenRepo;
    private final JwtBuilder jwtBuilder;

    @Autowired
    public RefreshService(RefreshTokenRepo refreshTokenRepo, JwtBuilder jwtBuilder) {
        this.refreshTokenRepo = refreshTokenRepo;
        this.jwtBuilder = jwtBuilder;
    }


    public LoginResponse refresh(String token, HttpServletResponse response){
        RefreshToken refreshToken = refreshTokenRepo.findByToken(token).orElseThrow(()-> new NotFound("Please authorize!"));
        if(refreshToken.getRefreshed() != null){
            throw new NotPrivilege("Token already refreshed");
        }
        if(refreshToken.getExpired().isBefore(LocalDateTime.now())){
            throw new NotPrivilege("Token expired");
        }
        User user = refreshToken.getUser();
        refreshToken.setRefreshed(LocalDateTime.now());
        refreshTokenRepo.save(refreshToken);
        RefreshToken newRefreshToken = new RefreshToken(jwtBuilder.generateRefreshToken(user.getEmail()),
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(15L));
        newRefreshToken.setUser(user);
        refreshTokenRepo.save(newRefreshToken);
        String access = jwtBuilder.generateToken(user.getEmail());
        Cookie access_token = new Cookie("access_token", access);
        Cookie refresh_token = new Cookie("refresh_token", newRefreshToken.getToken());
        access_token.setPath("/");
        refresh_token.setPath("/");
        response.addCookie(access_token);
        response.addCookie(refresh_token);
        return new LoginResponse(access, newRefreshToken.getToken());
    }


}
