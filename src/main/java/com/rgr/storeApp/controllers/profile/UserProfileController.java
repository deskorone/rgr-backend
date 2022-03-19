package com.rgr.storeApp.controllers.profile;


import com.rgr.storeApp.dto.BalanceRequest;
import com.rgr.storeApp.secutity.jwt.JwtBuilder;
import com.rgr.storeApp.service.favorites.profile.BalanceService;
import com.rgr.storeApp.service.favorites.profile.BasketService;
import com.rgr.storeApp.service.favorites.profile.UserProfileService;
import com.rgr.storeApp.service.product.ProductService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile/")
public class UserProfileController {

    private final JwtBuilder jwtBuilder;
    private final UserProfileService userProfileService;
    private final BasketService basketService;
    private final ProductService productService;
    private final BalanceService balanceService;

    public UserProfileController(JwtBuilder jwtBuilder, UserProfileService userProfileService, BasketService basketService, ProductService productService, BalanceService balanceService) {
        this.jwtBuilder = jwtBuilder;
        this.userProfileService = userProfileService;
        this.basketService = basketService;
        this.productService = productService;
        this.balanceService = balanceService;
    }

    @GetMapping("/get")
    @PreAuthorize("hasRole('USER') or hasRole('SALESMAN')")
    public ResponseEntity<?> getGeneralInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getPrincipal().toString();
        return ResponseEntity.ok(
                userProfileService.getGeneral(email)
        );
    }

    @GetMapping("/get/awaitings")
    @PreAuthorize("hasRole('USER') or hasRole('SALESMAN')")
    public ResponseEntity<?> getAwaitings(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getPrincipal().toString();
        return ResponseEntity.ok(userProfileService.getAwaitings(email));
    }

    @GetMapping("/get/basket")
    @PreAuthorize("hasRole('USER') or hasRole('SALESMAN')")
    public ResponseEntity<?> getBasket(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getPrincipal().toString();
        return ResponseEntity.ok(userProfileService.getBasket(email));
    }

    @PostMapping("/basket/add/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('SALESMAN')")
    public ResponseEntity<?> addInbasket(@PathVariable("id") Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getPrincipal().toString();
        return ResponseEntity.ok(basketService.addProductInBasket(email, id));
    }


    @DeleteMapping("/basket/add/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('SALESMAN')")
    public  ResponseEntity<?> deleteFromBasket(@PathVariable("id") Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getPrincipal().toString();
        return ResponseEntity.ok(basketService.deleteProduct(email, id));

    }

    @PostMapping("/basket/buy")
    @PreAuthorize("hasRole('USER') or hasRole('SALESMAN')")
    public ResponseEntity<?> buyProductsFromBasket(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getPrincipal().toString();
        return ResponseEntity.ok(productService.buy(email));
    }

    @PostMapping("/balance/add")
    @PreAuthorize("hasRole('USER') or hasRole('SALESMAN')")
    public ResponseEntity<?> addBalance(@RequestBody BalanceRequest balanceRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getPrincipal().toString();
        return ResponseEntity.ok(balanceService.addBalance(balanceRequest, email));
    }









}
