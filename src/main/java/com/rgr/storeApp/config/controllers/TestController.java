package com.rgr.storeApp.config.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.rgr.storeApp.dao.ProductRequest;
import com.rgr.storeApp.dao.ProductResponse;
import com.rgr.storeApp.models.product.Product;
import com.rgr.storeApp.service.product.ProductService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth/test")
@CrossOrigin(origins = "*")
public class TestController {

    private final ProductService productService;

    public TestController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(value = "/add",  produces = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> add(@RequestParam("mainimage") MultipartFile file, @RequestParam("req") String json) {
        String email = "e";
        ObjectMapper mapper = new ObjectMapper();
        ProductRequest productRequest;
        try {
            productRequest = mapper.readValue(json, ProductRequest.class);
            productRequest.toString();
        } catch (JsonProcessingException e) {
            System.out.println("BAD JSON");
            return ResponseEntity.badRequest().build();
        }
        //ProductResponse productResponse = productService.addProduct(productRequest, email, file);
        //System.out.println(productResponse);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_VALUE))
                .body(productService.addProduct(productRequest, email, file));

    }



    @GetMapping(value = "/product/photo/{path}", produces = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> getProductPhoto(@PathVariable("path") String path){
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(MediaType.IMAGE_JPEG_VALUE))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\\${System.currentTimeMillis()}\\")
                .body(productService.getPhoto(path));
    }



}
