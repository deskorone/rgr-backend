package com.rgr.storeApp.controllers;


import com.rgr.storeApp.dto.LoginRequest;
import com.rgr.storeApp.dto.LoginResponse;
import com.rgr.storeApp.dto.RegistrationRequest;
import com.rgr.storeApp.exceptions.api.NotValide;
import com.rgr.storeApp.secutity.jwt.JwtBuilder;
import com.rgr.storeApp.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.lang.reflect.Array;
import java.util.Arrays;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials =  "true")
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }


    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse httpServletResponse){
        return ResponseEntity.ok(userService.loginUser(loginRequest, httpServletResponse));
    }

    @PostMapping("/reg")
    public ResponseEntity<?> registration(@Valid @RequestBody RegistrationRequest request, BindingResult result){
        if(result.hasErrors()){throw new NotValide(result.getFieldError().getCode() + "  " + result.getFieldError().getField());}
        return ResponseEntity.ok(userService.regUser(request));
    }

}
