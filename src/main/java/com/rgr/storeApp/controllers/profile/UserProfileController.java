package com.rgr.storeApp.controllers.profile;


import com.rgr.storeApp.dto.BalanceRequest;
import com.rgr.storeApp.dto.product.FindRequest;
import com.rgr.storeApp.dto.userProfile.UserInfoRequest;
import com.rgr.storeApp.exceptions.api.NotPrivilege;
import com.rgr.storeApp.exceptions.api.NotValide;
import com.rgr.storeApp.models.profile.StoreUpdateRequest;
import com.rgr.storeApp.service.ConfirmationTokenService;
import com.rgr.storeApp.service.UserService;
import com.rgr.storeApp.service.email.EmailService;
import com.rgr.storeApp.service.profile.BalanceService;
import com.rgr.storeApp.service.profile.BasketService;
import com.rgr.storeApp.service.profile.UserProfileService;
import com.rgr.storeApp.service.product.ProductService;
import com.rgr.storeApp.service.profile.favorites.FavoritesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.executable.ValidateOnExecution;

@RestController
@RequestMapping("/api/profile")
@ValidateOnExecution
@CrossOrigin("*")
public class UserProfileController {

    private final UserProfileService userProfileService;
    private final BasketService basketService;
    private final ProductService productService;
    private final BalanceService balanceService;
    private final UserService userService;
    private final FavoritesService favoritesService;
    private final EmailService emailService;
    private final ConfirmationTokenService confirmationTokenService;

    public UserProfileController(UserProfileService userProfileService, BasketService basketService, ProductService productService, BalanceService balanceService, UserService userService, FavoritesService favoritesService, EmailService emailService, ConfirmationTokenService confirmationTokenService) {
        this.userProfileService = userProfileService;
        this.basketService = basketService;
        this.productService = productService;
        this.balanceService = balanceService;
        this.userService = userService;
        this.favoritesService = favoritesService;
        this.emailService = emailService;
        this.confirmationTokenService = confirmationTokenService;
    }

    @GetMapping("/get")
    @PreAuthorize("hasRole('USER') or hasRole('SALESMAN')")
    public ResponseEntity<?> getGeneralInfo(){
        return ResponseEntity.ok(userProfileService.getGeneral());
    }

    @PreAuthorize("hasRole('USER') or hasRole('SALESMAN')")
    @GetMapping("/awaitings")
    public ResponseEntity<?> getAwaitings(){
        return ResponseEntity.ok(userProfileService.refreshDeliveries());
    }


    @GetMapping("/basket")
    @PreAuthorize("hasRole('USER') or hasRole('SALESMAN')")
    public ResponseEntity<?> getBasket(){
        return ResponseEntity.ok(userProfileService.getBasket());
    }

    @PostMapping("/basket/add/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('SALESMAN')")
    public ResponseEntity<?> addInBasket(@PathVariable("id") Long id) {
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

    @PreAuthorize("hasRole('USER') or hasRole('SALESMAN')")
    @PostMapping("/favorites/add/{id}")
    public ResponseEntity<?> addInFavorites(@PathVariable("id") Long id){
        return ResponseEntity.ok(favoritesService.addFavoriteProduct(id));

    }

    @PreAuthorize("hasRole('USER') or hasRole('SALESMAN')")
    @DeleteMapping("/favorites/add/{id}")
    public ResponseEntity<?> deleteFromFavorites(@PathVariable("id") Long id){
        return ResponseEntity.ok(favoritesService.deleteProduct(id));
    }

    @PreAuthorize("hasRole('USER') or hasRole('SALESMAN')")
    @GetMapping("/buyHistory")
    public ResponseEntity<?> getBuyHistory(){
        return ResponseEntity.ok(userProfileService.getBuyHistory());
    }

    @PreAuthorize("hasRole('USER') or hasRole('SALESMAN')")
    @PutMapping("/update")
    public ResponseEntity<?> updateProfile(@RequestBody UserInfoRequest userInfoRequest){
        return ResponseEntity.ok(userProfileService.updateInfo(userInfoRequest));
    }

    @PreAuthorize("hasRole('SALESMAN') or hasRole('ADMIN')")
    @GetMapping("/sellhistory")
    public ResponseEntity<?> getSellHistory(@RequestParam("count") int count,
                                               @RequestParam("size") int size){
        return ResponseEntity.ok(userProfileService.getSellHistory(count, size));
    }

    @GetMapping("/info")
    public ResponseEntity<?> getProfile(@RequestParam("id") Long id){
        return ResponseEntity.ok(userProfileService.getProfile(id));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SALESMAN')")
    @PutMapping("/update/store")
    public ResponseEntity<?> updateStore(@Valid @RequestBody StoreUpdateRequest storeUpdateRequest, BindingResult result){
        if(result.hasErrors()){throw new NotValide(result.getFieldError().getDefaultMessage());}
        return ResponseEntity.ok(userProfileService.updateStore(storeUpdateRequest));
    }

}
