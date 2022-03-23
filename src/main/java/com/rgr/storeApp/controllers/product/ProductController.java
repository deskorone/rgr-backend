package com.rgr.storeApp.controllers.product;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rgr.storeApp.dto.product.FindRequest;
import com.rgr.storeApp.dto.product.ProductRequest;
import com.rgr.storeApp.dto.ReviewRequest;
import com.rgr.storeApp.secutity.jwt.JwtBuilder;
import com.rgr.storeApp.service.product.CategoryService;
import com.rgr.storeApp.service.product.ProductService;
import com.rgr.storeApp.service.reviews.ReviewsService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final JwtBuilder jwtBuilder;
    private final ProductService productService;
    private final ReviewsService reviewsService;
    private final CategoryService categoryService;

    public ProductController(JwtBuilder jwtBuilder, ProductService productService, ReviewsService reviewsService, CategoryService categoryService) {
        this.jwtBuilder = jwtBuilder;
        this.productService = productService;
        this.reviewsService = reviewsService;
        this.categoryService = categoryService;
    }

    @PreAuthorize("hasRole('SALESMAN')")
    @PostMapping(value = "/add",  produces = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> add(@RequestParam("mainimage") MultipartFile file, @RequestParam("req") String json,
                                 @RequestParam(value = "image", required = false) MultipartFile [] files) {
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
                .body(productService.addProduct(productRequest, file, files));
    }

    @PreAuthorize("hasRole('SALESMAN') or hasRole('ADMIN')")
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }


    @PreAuthorize("permitAll()")
    @PostMapping("/review/add/{id}")
    public ResponseEntity<?> addReview(@PathVariable("id") Long id, @RequestBody ReviewRequest reviewRequest){
        return ResponseEntity.ok(reviewsService.addReview(reviewRequest, id));
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<?> getProductInfo(@PathVariable("id") Long id){
        return ResponseEntity.ok(productService.getProduct(id));

    }

    @GetMapping(value = "/get/photo/{path}", produces = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> getProductPhoto(@PathVariable("path") String path){
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(MediaType.IMAGE_PNG_VALUE))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\\${System.currentTimeMillis()}\\")
                .body(productService.getPhoto(path));
    }

    @GetMapping(value = "/find")
    public ResponseEntity<?> find(@RequestParam("count") int count,
                                  @RequestParam("size") int size,
                                  @RequestBody FindRequest findRequest){
        return ResponseEntity.ok(categoryService.findName(findRequest.getText(), count, size));
    }


}
