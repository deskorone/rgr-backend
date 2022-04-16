package com.rgr.storeApp.service;


import com.rgr.storeApp.dto.LoginRequest;
import com.rgr.storeApp.dto.LoginResponse;
import com.rgr.storeApp.dto.RegistrationRequest;
import com.rgr.storeApp.dto.userProfile.UserResp;
import com.rgr.storeApp.exceptions.api.NotFound;
import com.rgr.storeApp.models.ERole;
import com.rgr.storeApp.models.RefreshToken;
import com.rgr.storeApp.models.Role;
import com.rgr.storeApp.models.User;
import com.rgr.storeApp.models.basket.Basket;
import com.rgr.storeApp.models.basket.BuyHistory;
import com.rgr.storeApp.models.basket.SellHistory;
import com.rgr.storeApp.models.delivery.AwaitingList;
import com.rgr.storeApp.models.product.Favorites;
import com.rgr.storeApp.models.product.Store;
import com.rgr.storeApp.models.profile.UserProfile;
import com.rgr.storeApp.repo.RefreshTokenRepo;
import com.rgr.storeApp.repo.RolesRepo;
import com.rgr.storeApp.repo.UsersRepo;
import com.rgr.storeApp.secutity.jwt.JwtBuilder;
import com.rgr.storeApp.service.email.EmailService;
import com.rgr.storeApp.service.find.FindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    private final UsersRepo usersRepo;
    private final RolesRepo rolesRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtBuilder jwtBuilder;
    private final RefreshTokenRepo refreshTokenRepo;
    private final FindService findService;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailService;

    @Autowired
    public UserService(UsersRepo usersRepo,
                       RolesRepo rolesRepo,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtBuilder jwtBuilder,
                       RefreshTokenRepo refreshTokenRepo,
                       FindService findService,
                       ConfirmationTokenService confirmationTokenService,
                       EmailService emailService) {
        this.usersRepo = usersRepo;
        this.rolesRepo = rolesRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtBuilder = jwtBuilder;
        this.refreshTokenRepo = refreshTokenRepo;
        this.findService = findService;
        this.confirmationTokenService = confirmationTokenService;
        this.emailService = emailService;
    }

    public UserResp loginUser(LoginRequest loginRequest, HttpServletResponse response){

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                   loginRequest.getEmail(), loginRequest.getPassword()
                ));
        String access_token = jwtBuilder.generateToken(loginRequest.getEmail());
        String refresh_token = jwtBuilder.generateRefreshToken(loginRequest.getEmail());
        User user = usersRepo.findByEmail(loginRequest.getEmail()).orElseThrow(()->new NotFound("User not found"));
        RefreshToken refreshToken = new RefreshToken(refresh_token,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(15L));
        refreshToken.setUser(user);
        refreshTokenRepo.save(refreshToken);

        Cookie access_cookie = new Cookie("access_token", access_token);
        Cookie refresh_cookie = new Cookie("refresh_token", refresh_token);
        access_cookie.setPath("/");
        refresh_cookie.setPath("/");
        response.addCookie(access_cookie);
        response.addCookie(refresh_cookie);
        return UserResp.build(user);
    }


    @Transactional
    public void userLogout(HttpServletResponse httpServletResponse){
        User user = findService.getUser(findService.getEmailFromAuth());
        refreshTokenRepo.deleteAllByUser(user);
        Cookie c = new Cookie("access_token", "-");
        Cookie cookie = new Cookie("refresh_token", "-");
        SecurityContextHolder.clearContext();
        c.setPath("/");
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);
        httpServletResponse.addCookie(c);
    }




    public UserResp regUser(RegistrationRequest registrationRequest){
        if (usersRepo.existsByEmail(registrationRequest.getEmail())){
            throw new UsernameNotFoundException("Email already registration");
        }
        User user = new User(registrationRequest.getEmail(),
                registrationRequest.getUsername(),
                passwordEncoder.encode(registrationRequest.getPassword()),
                true,
                false);
        user.setUsername(registrationRequest.getUsername());
        Set<String> rolesReq = registrationRequest.getRoles();
        Set<Role> roles = new HashSet<>();
        UserProfile userProfile = new UserProfile();
        if(rolesReq == null){
            Role role = rolesRepo
                    .findByRole(ERole.ROLE_USER)
                    .orElseThrow(()-> new IllegalStateException("Role not found"));
            roles.add(role);
        }else {
            rolesReq
                    .forEach(r ->{
                        switch (r) {
                            case "salesman":
                                Role role = rolesRepo
                                        .findByRole(ERole.ROLE_SALESMAN)
                                        .orElseThrow(() -> new NotFound("Role dont exist (SALESMAN)"));
                                roles.add(role);
                                Store store = new Store();
                                SellHistory sellHistory = new SellHistory();;
                                sellHistory.setStore(store);
                                store.setUser(user);
                                store.setSellHistory(sellHistory);
                                user.setStore(store);
                                break;
                            case "admin":
                                Role adminRole = rolesRepo.findByRole(ERole.ROLE_ADMIN)
                                        .orElseThrow(() -> new NotFound("Role dot exist"));
                                user.setEnabled(true);
                                roles.add(adminRole);
                            default:
                                Role userRole = rolesRepo
                                        .findByRole(ERole.ROLE_USER)
                                        .orElseThrow(() -> new NotFound("Role dont exist"));
                                roles.add(userRole);
                        }
                    });
        }

        Favorites favorites = new Favorites();
        userProfile.setFavorites(favorites);
        Basket basket = new Basket();
        basket.setUserProfile(userProfile);
        BuyHistory buyHistory = new BuyHistory();
        buyHistory.setUserProfile(userProfile);
        AwaitingList awaitingList = new AwaitingList();
        awaitingList.setUserProfile(userProfile);
        userProfile.setBalance(0);
        userProfile.setAwaitingList(awaitingList);
        userProfile.setBasket(basket);
        userProfile.setBuyHistory(buyHistory);
        userProfile.setUser(user);
        user.setUserProfile(userProfile);
        user.setRoles(roles);

        User u = usersRepo.save(user);
        try {
            String token = confirmationTokenService.createToken(u);
            emailService.sendVerification(token, u.getEmail(), u.getUsername());
        }catch (Exception e){
            usersRepo.delete(u);
        }
        return UserResp.build(u);
    }


}
