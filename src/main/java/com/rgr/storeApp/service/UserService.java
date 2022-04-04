package com.rgr.storeApp.service;


import com.rgr.storeApp.dto.LoginRequest;
import com.rgr.storeApp.dto.LoginResponse;
import com.rgr.storeApp.dto.RegistrationRequest;
import com.rgr.storeApp.exceptions.api.NotFound;
import com.rgr.storeApp.exceptions.api.NotPrivilege;
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
import com.rgr.storeApp.service.find.FindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
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
    private final UserDetailsServiceImpl userDetailsService;
    private final RefreshTokenRepo refreshTokenRepo;
    private final FindService findService;
    private final ConfirmationTokenService confirmationTokenService;
    @Autowired
    public UserService(UsersRepo usersRepo,
                       RolesRepo rolesRepo,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtBuilder jwtBuilder,
                       UserDetailsServiceImpl userDetailsService,
                       RefreshTokenRepo refreshTokenRepo, FindService findService, ConfirmationTokenService confirmationTokenService) {
        this.usersRepo = usersRepo;
        this.rolesRepo = rolesRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtBuilder = jwtBuilder;
        this.userDetailsService = userDetailsService;
        this.refreshTokenRepo = refreshTokenRepo;
        this.findService = findService;
        this.confirmationTokenService = confirmationTokenService;
    }

    public LoginResponse loginUser(LoginRequest loginRequest){

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                   loginRequest.getEmail(), loginRequest.getPassword()
                ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String access_token = jwtBuilder.generateToken(loginRequest.getEmail());
        String refresh_token = jwtBuilder.generateRefreshToken(loginRequest.getEmail());
        User user = usersRepo.findByEmail(loginRequest.getEmail()).orElseThrow(()->new NotFound("User not found"));
        RefreshToken refreshToken = new RefreshToken(refresh_token,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(15L));
        refreshToken.setUser(user);
        refreshTokenRepo.save(refreshToken);
        return new LoginResponse(access_token,
                refresh_token);
    }

    public LoginResponse refresh(String token, HttpServletRequest request, HttpServletResponse response){
        RefreshToken refreshToken = refreshTokenRepo.findByToken(token).orElseThrow(()-> new NotFound("Token not found"));
        if(refreshToken.getRefreshed() != null){
            throw new NotPrivilege("Token already refreshed");
        }
        if(refreshToken.getExpired().isBefore(LocalDateTime.now())){
            throw new NotPrivilege("Token expired");
        }
        User user = refreshToken.getUser();
        refreshToken.setRefreshed(LocalDateTime.now());
        refreshTokenRepo.save(refreshToken);
        RefreshToken newRefreshToken = new RefreshToken(jwtBuilder.generateRefreshToken(user.getEmail()),
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(15L));
        newRefreshToken.setUser(user);
        refreshTokenRepo.save(newRefreshToken);
        String access = jwtBuilder.generateToken(user.getEmail());
        Cookie access_token = new Cookie("access_token", access);
        Cookie refresh_token = new Cookie("refresh_token", newRefreshToken.getToken());

        response.addCookie(access_token);
        response.addCookie(refresh_token);
        return new LoginResponse(access, newRefreshToken.getToken());
    }


    @Transactional
    public void userLogout(HttpServletResponse httpServletResponse){
        User user = findService.getUser(findService.getEmailFromAuth());
        refreshTokenRepo.deleteAllByUser(user);
        Cookie cookie = new Cookie("logout", "");
        cookie.setMaxAge(0);
        httpServletResponse.addCookie(cookie);

    }







    public String regUser(RegistrationRequest registrationRequest){
        if (usersRepo.existsByEmail(registrationRequest.getEmail())){
            return "ERROR EMAIL EXIST";
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
            rolesReq  // TODO add all roles!
                    .forEach(r ->{
                        switch (r) {
                            case "salesman":
                                Role adminRole = rolesRepo
                                        .findByRole(ERole.ROLE_SALESMAN)
                                        .orElseThrow(() -> new RuntimeException("Role dont exist (SALESMAN)"));
                                roles.add(adminRole);
                                Store store = new Store();
                                SellHistory sellHistory = new SellHistory();;
                                sellHistory.setStore(store);
                                store.setUser(user);
                                store.setSellHistory(sellHistory);
                                user.setStore(store);
                                break;
                            default:
                                Role userRole = rolesRepo
                                        .findByRole(ERole.ROLE_USER)
                                        .orElseThrow(() -> new RuntimeException("Role dont exist"));
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

        User userC = usersRepo.save(user);
        return confirmationTokenService.createToken(user);
    }


}
