package com.rgr.storeApp.controllers;


import com.rgr.storeApp.service.profile.favorites.FavoritesService;
import com.rgr.storeApp.service.profile.BalanceService;
import com.rgr.storeApp.service.profile.BasketService;
import com.rgr.storeApp.service.profile.delivery.DeliveryService;
import com.rgr.storeApp.service.product.ProductService;
import com.rgr.storeApp.service.reviews.ReviewsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth/test")
@CrossOrigin(origins = "*")
public class TestController {

    private final ProductService productService;
    private final BasketService basketService;
    private final BalanceService balanceService;
    private final ReviewsService reviewsService;
    private final FavoritesService favoritesService;
    private final DeliveryService deliveryService;

    public TestController(ProductService productService, BasketService basketService, BalanceService balanceService, ReviewsService reviewsService, FavoritesService favoritesService, DeliveryService deliveryService) {
        this.productService = productService;
        this.basketService = basketService;
        this.balanceService = balanceService;
        this.reviewsService = reviewsService;
        this.favoritesService = favoritesService;
        this.deliveryService = deliveryService;
    }
//
//    @PostMapping(value = "/add",  produces = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    public ResponseEntity<?> add(@RequestParam("mainimage") MultipartFile file, @RequestParam(name = "req") String json,
//                                    @RequestParam(value = "image", required = false) MultipartFile [] files) {
//        String email = "e1";
//        ObjectMapper mapper = new ObjectMapper();
//        ProductRequest productRequest;
//        try {
//            productRequest = mapper.readValue(json, ProductRequest.class);
//            productRequest.toString();
//        } catch (JsonProcessingException e) {
//            System.out.println("BAD JSON");
//            return ResponseEntity.badRequest().build();
//        }
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_VALUE))
//                .body(productService.addProduct(productRequest, email, file, files));
//
//    }






    @GetMapping(value = "/product/all")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(productService.getAllByStore());
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProduct(@PathVariable("id") Long id){
        return ResponseEntity.ok(productService.getProduct(id));
    }

//
//    @PostMapping("/basket/add/{id}")
//    public ResponseEntity<?> addInBasket(@PathVariable("id") Long id){
//        String email = "eee";
//        Basket basket = basketService.addProductInBasket("eee", id);
//        return ResponseEntity.ok(basket);
//    }

//
//    @DeleteMapping("/basket/add/{id}")
//    public ResponseEntity<?> deleteInBasket(@PathVariable("id") Long id){
//        String email = "eee";
//        Basket basket = basketService.deleteProduct("eee", id);
//        return ResponseEntity.ok(basket);
//    }

//    @PostMapping("/balance/add")
//    public ResponseEntity<?> addBalance(@RequestBody BalanceRequest balanceRequest){
//        String email = "NewUser";
//        return ResponseEntity.ok(balanceService.addBalance(balanceRequest, email));
//    }

//    @PostMapping("/product/buy")
//    public ResponseEntity<?> buy(){
//        String email = "eee";
//        productService.buy(email);
//        return ResponseEntity.ok().build();
//    }

//    @GetMapping("/product/store")
//    public ResponseEntity<?> getSklad(){
//        String email = "e1";
//        return ResponseEntity.ok(productService.getStoreInfo(email));
//    }

//    @DeleteMapping("/product/delete/{id}")
//    public ResponseEntity<?> delete(@PathVariable("id") Long id){
//        String email = "e1";
//        productService.deleteProduct(email, id);
//        return ResponseEntity.ok().build();
//
//    }
}
