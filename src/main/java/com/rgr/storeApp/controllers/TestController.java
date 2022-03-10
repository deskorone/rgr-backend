package com.rgr.storeApp.controllers;


import com.rgr.storeApp.dao.ProductRequest;
import com.rgr.storeApp.models.product.Product;
import com.rgr.storeApp.service.product.ProductService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/auth/test")
public class TestController {

    private final ProductService productService;

    public TestController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(value = "/add", produces = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> add(@RequestParam("image")MultipartFile multipartFile, ProductRequest productRequest){
        String email = "e";
        //TODO добавлять колличество товара
        Product product = productService.addProduct(productRequest, email, multipartFile);

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(MediaType.IMAGE_JPEG_VALUE))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"${System.currentTimeMillis()}\"")
                .body(product.getProductInfo().getMainPhoto().getPhoto());
    }

}
