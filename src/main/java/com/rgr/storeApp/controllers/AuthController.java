package com.rgr.storeApp.controllers;


import com.rgr.storeApp.dto.LoginRequest;
import com.rgr.storeApp.dto.LoginResponse;
import com.rgr.storeApp.dto.RegistrationRequest;
import com.rgr.storeApp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse httpServletResponse){
        LoginResponse loginResponse = userService.loginUser(loginRequest);
        Cookie cookie = new Cookie("token", loginResponse.getToken());
        //Cookie cookie1 = new Cookie("")
        httpServletResponse.addCookie(cookie);
        cookie.setSecure(true);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/reg")
    public ResponseEntity<?> registration(@RequestBody RegistrationRequest request){

        return ResponseEntity.ok(userService.regUser(request));
    }


    @GetMapping("/refresh")
    public ResponseEntity<?> refreshToken(){
        return null;
    }

}
