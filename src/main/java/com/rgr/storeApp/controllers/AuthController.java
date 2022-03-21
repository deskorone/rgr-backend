package com.rgr.storeApp.controllers;


import com.rgr.storeApp.dto.LoginRequest;
import com.rgr.storeApp.dto.LoginResponse;
import com.rgr.storeApp.dto.RegistrationRequest;
import com.rgr.storeApp.secutity.jwt.JwtBuilder;
import com.rgr.storeApp.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Array;
import java.util.Arrays;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtBuilder jwtBuilder;

    public AuthController(UserService userService, JwtBuilder jwtBuilder) {
        this.userService = userService;
        this.jwtBuilder = jwtBuilder;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse httpServletResponse){
        LoginResponse loginResponse = userService.loginUser(loginRequest);
        Cookie access_token = new Cookie("access_token", loginResponse.getAccess_token());
        Cookie refresh_token = new Cookie("refresh_token", loginResponse.getRefresh_token());
        httpServletResponse.addCookie(access_token);
        httpServletResponse.addCookie(refresh_token);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/reg")
    public ResponseEntity<?> registration(@RequestBody RegistrationRequest request){
        return ResponseEntity.ok(userService.regUser(request));
    }


    @GetMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                          HttpServletRequest httpServletRequest,
                                          HttpServletResponse httpServletResponse){
        LoginResponse loginResponse = userService.refresh(token.substring(7), httpServletRequest, httpServletResponse);
        return ResponseEntity.ok(loginResponse);
    }





}
