package com.rgr.storeApp.config.controllers;


import com.rgr.storeApp.dao.LoginRequest;
import com.rgr.storeApp.dao.RegistrationRequest;
import com.rgr.storeApp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(userService.loginUser(loginRequest));
    }

    @PostMapping("/reg")
    public ResponseEntity<?> login(@RequestBody RegistrationRequest request){
        System.out.println("WORK");

        return ResponseEntity.ok(userService.regUser(request));
    }


}
