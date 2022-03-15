package com.rgr.storeApp.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rgr.storeApp.dao.BalanceRequest;
import com.rgr.storeApp.dao.ProductRequest;
import com.rgr.storeApp.models.basket.Basket;
import com.rgr.storeApp.service.profile.BalanceService;
import com.rgr.storeApp.service.profile.BasketService;
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
    private final BasketService basketService;
    private final BalanceService balanceService;

    public TestController(ProductService productService, BasketService basketService, BalanceService balanceService) {
        this.productService = productService;
        this.basketService = basketService;
        this.balanceService = balanceService;
    }

    @PostMapping(value = "/add",  produces = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> add(@RequestParam("mainimage") MultipartFile file, @RequestParam("req") String json,
                                    @RequestParam("image") MultipartFile [] files) {
        String email = "e1";
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


    @PostMapping("/basket/add/{id}")
    public ResponseEntity<?> addInBasket(@PathVariable("id") Long id){
        String email = "eee";
        Basket basket = basketService.addProductInBasket("eee", id);
        return ResponseEntity.ok(basket);
    }


    @DeleteMapping("/basket/add/{id}")
    public ResponseEntity<?> deleteInBasket(@PathVariable("id") Long id){
        String email = "eee";
        Basket basket = basketService.deleteProduct("eee", id);
        return ResponseEntity.ok(basket);
    }

    @PostMapping("/balance/add")
    public ResponseEntity<?> addBalance(@RequestBody BalanceRequest balanceRequest){
        String email = "eee";
        return ResponseEntity.ok(balanceService.addBalance(balanceRequest, email));
    }

    @PostMapping("/product/buy")
    public ResponseEntity<?> buy(){
        String email = "eee";
        productService.buy(email);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/product/store")
    public ResponseEntity<?> getSklad(){
        String email = "e1";
        return ResponseEntity.ok(productService.getProductsInfo(email));
    }

    @DeleteMapping("/product/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        String email = "e1";
        productService.deleteProduct(email, id);
        return ResponseEntity.ok().build();

    }



}
