package com.rgr.storeApp.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/")
public class HomePageController {

    @GetMapping("/hello")
    public ResponseEntity<?> logout(HttpServletRequest request) throws ServletException {

        return ResponseEntity.ok("HELLO");
    }

}
