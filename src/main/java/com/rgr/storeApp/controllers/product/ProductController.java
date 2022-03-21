package com.rgr.storeApp.controllers.product;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rgr.storeApp.dto.ProductRequest;
import com.rgr.storeApp.secutity.jwt.JwtBuilder;
import com.rgr.storeApp.service.UserService;
import com.rgr.storeApp.service.product.ProductService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final JwtBuilder jwtBuilder;
    private final ProductService productService;
    private final UserService userService;

    public ProductController(JwtBuilder jwtBuilder, ProductService productService, UserService userService) {
        this.jwtBuilder = jwtBuilder;
        this.productService = productService;
        this.userService = userService;
    }


    @PostMapping(value = "/add",  produces = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> add(@RequestParam("mainimage") MultipartFile file, @RequestParam("req") String json,
                                 @RequestParam(value = "image", required = false) MultipartFile [] files,
                                 @RequestParam("Authorization")String token) {
        String email = jwtBuilder.getEmailFromToken(token);
        ObjectMapper mapper = new ObjectMapper();
        ProductRequest productRequest;
        try {
            productRequest = mapper.readValue(json, ProductRequest.class);
            productRequest.toString();
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_VALUE))
                .body(productService.addProduct(productRequest, email, file, files));
    }

    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, HttpServletRequest httpServletRequest){
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        userService.userLogout(email, httpServletRequest);
        return ResponseEntity.ok().build();

    }

}
