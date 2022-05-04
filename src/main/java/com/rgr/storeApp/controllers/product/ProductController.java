package com.rgr.storeApp.controllers.product;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rgr.storeApp.dto.product.AwaibleDto;
import com.rgr.storeApp.dto.product.FindRequest;
import com.rgr.storeApp.dto.product.ProductRequest;
import com.rgr.storeApp.dto.ReviewRequest;
import com.rgr.storeApp.service.product.CategoryService;
import com.rgr.storeApp.service.product.MainPageInfoService;
import com.rgr.storeApp.service.product.ProductService;
import com.rgr.storeApp.service.reviews.ReviewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RestController
@RequestMapping("/api/products")
@CrossOrigin("*")
public class ProductController {

    private final CategoryService categoryService;
    private final ProductService productService;
    private final ReviewsService reviewsService;
    private final MainPageInfoService mainPageInfoService;

    @Autowired
    public ProductController(CategoryService categoryService, ProductService productService, ReviewsService reviewsService, MainPageInfoService mainPageInfoService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.reviewsService = reviewsService;
        this.mainPageInfoService = mainPageInfoService;
    }



    //make base 64
    @PreAuthorize("hasRole('SALESMAN')")
    @PostMapping(value = "/add",  produces = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> addProduct(@RequestParam(name = "mainimage") MultipartFile file, @RequestParam("req") String json,
                                 @RequestParam(value = "image", required = false) MultipartFile [] files) {
        System.out.print("LOG");
        ObjectMapper mapper = new ObjectMapper();
        ProductRequest productRequest;
        try {
            productRequest = mapper.readValue(json, ProductRequest.class);
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_VALUE))
                .body(productService.addProduct(productRequest, file, files));
    }

    @PreAuthorize("hasRole('SALESMAN') or hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }


    @PreAuthorize("hasRole('USER') or hasRole('SALESMAN')")
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
                .body(productService.getPhoto(path));
    }

    @GetMapping(value = "/find/name")
    public ResponseEntity<?> find(@RequestParam("count") int count,
                                  @RequestParam("size") int size,
                                  @RequestBody FindRequest findRequest){
        return ResponseEntity.ok(productService.findByName(findRequest.getText(), count, size));
    }



    @GetMapping(value = "/find/category")
    public ResponseEntity<?> findByCategory(@RequestParam("count") int count,
                                  @RequestParam("size") int size,
                                  @RequestBody FindRequest findRequest){
        return ResponseEntity.ok(productService.findByCategory(findRequest.getText(), count, size));
    }

    @GetMapping(value = "/find/description")
    public ResponseEntity<?> findByDescription(@RequestParam("count") int count,
                                            @RequestParam("size") int size,
                                            @RequestBody FindRequest findRequest){
        return ResponseEntity.ok(productService.findByDescription(findRequest.getText(), count, size));
    }


    @PreAuthorize("hasRole('SALESMAN') or hasRole('ADMIN')")
    @GetMapping("/get/store")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(productService.getAllByStore());
    }


    @GetMapping("/get/categories")
    public ResponseEntity<?> getAllCategories() { return  ResponseEntity.ok(categoryService.getAllCategoriesSorted());}


    @GetMapping("/main")
    public ResponseEntity<?> getMainPage(){
        return ResponseEntity.ok(mainPageInfoService.getActualProducts());
    }


    @PreAuthorize("hasRole('SALESMAN') or hasRole('ADMIN')")
    @PutMapping("/add/num/{id}")
    public ResponseEntity<?> addAwaible(@PathVariable("id") Long id, @RequestBody AwaibleDto awaibleDto){
        return ResponseEntity.ok(productService.addAwaible(id ,awaibleDto.getNumber()));
    }


    @PreAuthorize("hasRole('USER') or hasRole('SALESMAN') or hasRole('ADMIN')")
    @DeleteMapping("/review/delete")
    public ResponseEntity<?> deleteRev(@RequestParam Long id){
        return ResponseEntity.ok(productService.deleteReview(id));
    }


}
