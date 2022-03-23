package com.rgr.storeApp.controllers.profile;


import com.rgr.storeApp.dto.BalanceRequest;
import com.rgr.storeApp.secutity.jwt.JwtBuilder;
import com.rgr.storeApp.service.UserService;
import com.rgr.storeApp.service.favorites.profile.BalanceService;
import com.rgr.storeApp.service.favorites.profile.BasketService;
import com.rgr.storeApp.service.favorites.profile.UserProfileService;
import com.rgr.storeApp.service.product.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/profile")
public class UserProfileController {

    private final JwtBuilder jwtBuilder;
    private final UserProfileService userProfileService;
    private final BasketService basketService;
    private final ProductService productService;
    private final BalanceService balanceService;
    private final UserService userService;

    public UserProfileController(JwtBuilder jwtBuilder, UserProfileService userProfileService, BasketService basketService, ProductService productService, BalanceService balanceService, UserService userService) {
        this.jwtBuilder = jwtBuilder;
        this.userProfileService = userProfileService;
        this.basketService = basketService;
        this.productService = productService;
        this.balanceService = balanceService;
        this.userService = userService;
    }

    @GetMapping("/get")
    @PreAuthorize("hasRole('USER') or hasRole('SALESMAN')")
    public ResponseEntity<?> getGeneralInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getPrincipal().toString();
        return ResponseEntity.ok(
                userProfileService.getGeneral()
        );
    }

    @GetMapping("/get/awaitings")
    @PreAuthorize("hasRole('USER') or hasRole('SALESMAN')")
    public ResponseEntity<?> getAwaitings(){
        return ResponseEntity.ok(userProfileService.getAwaitings());
    }

    @GetMapping("/get/basket")
    @PreAuthorize("hasRole('USER') or hasRole('SALESMAN')")
    public ResponseEntity<?> getBasket(){
        return ResponseEntity.ok(userProfileService.getBasket());
    }

    @PostMapping("/basket/add/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('SALESMAN')")
    public ResponseEntity<?> addInbasket(@PathVariable("id") Long id){
        return ResponseEntity.ok(basketService.addProductInBasket(id));
    }


    @DeleteMapping("/basket/add/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('SALESMAN')")
    public  ResponseEntity<?> deleteFromBasket(@PathVariable("id") Long id){
        return ResponseEntity.ok(basketService.deleteProduct(id));

    }
    @PostMapping("/basket/buy")
    @PreAuthorize("hasRole('USER') or hasRole('SALESMAN')")
    public ResponseEntity<?> buyProductsFromBasket(){
        return ResponseEntity.ok(productService.buy());
    }

    @PostMapping("/balance/add")
    @PreAuthorize("hasRole('USER') or hasRole('SALESMAN')")
    public ResponseEntity<?> addBalance(@RequestBody BalanceRequest balanceRequest){
        return ResponseEntity.ok(balanceService.addBalance(balanceRequest));
    }

    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response){
        userService.userLogout(response);
        return ResponseEntity.ok().build();

    }




}
