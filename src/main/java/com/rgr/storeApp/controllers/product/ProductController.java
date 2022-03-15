package com.rgr.storeApp.controllers.product;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rgr.storeApp.dao.ProductRequest;
import com.rgr.storeApp.secutity.jwt.JwtBuilder;
import com.rgr.storeApp.service.product.ProductService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final String url = "/api/products/photo";
    private final JwtBuilder jwtBuilder;
    private final ProductService productService;

    public ProductController(JwtBuilder jwtBuilder, ProductService productService) {
        this.jwtBuilder = jwtBuilder;
        this.productService = productService;
    }


    @PostMapping(value = "/add",  produces = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> add(@RequestParam("mainimage") MultipartFile file, @RequestParam("req") String json,
                                 @RequestParam("image") MultipartFile [] files,
                                 @RequestParam("Authorization")String token) {
        String email = jwtBuilder.getEmailFromToken(token);
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



}
