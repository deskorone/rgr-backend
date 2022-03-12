package com.rgr.storeApp.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rgr.storeApp.dao.ProductRequest;
import com.rgr.storeApp.service.product.ProductService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/auth/test")
@CrossOrigin(origins = "*")
public class TestController {

    private final ProductService productService;

    public TestController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(value = "/add",  produces = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> add(@RequestParam("mainimage") MultipartFile file, @RequestParam("req") String json,
                                    @RequestParam("image") MultipartFile [] files) {
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
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_VALUE))
                .body(productService.addProduct(productRequest, email, file, files));

    }



    @GetMapping(value = "/product/photo/{path}", produces = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> getProductPhoto(@PathVariable("path") String path){
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(MediaType.IMAGE_PNG_VALUE))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\\${System.currentTimeMillis()}\\")
                .body(productService.getPhoto(path));
    }


    @GetMapping(value = "/product/all")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(productService.getAll("e"));
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProduct(@PathVariable("id") Long id){
        return ResponseEntity.ok(productService.getProduct(id));
    }



}
