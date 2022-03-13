package com.rgr.storeApp.service;


import com.rgr.storeApp.dao.LoginRequest;
import com.rgr.storeApp.dao.LoginResponse;
import com.rgr.storeApp.dao.RegistrationRequest;
import com.rgr.storeApp.models.ERole;
import com.rgr.storeApp.models.Role;
import com.rgr.storeApp.models.User;
import com.rgr.storeApp.models.basket.Basket;
import com.rgr.storeApp.models.basket.BuyHistory;
import com.rgr.storeApp.models.basket.SellHistory;
import com.rgr.storeApp.models.delivery.AwaitingList;
import com.rgr.storeApp.models.product.Producer;
import com.rgr.storeApp.models.profile.UserProfile;
import com.rgr.storeApp.repo.RolesRepo;
import com.rgr.storeApp.repo.UsersRepo;
import com.rgr.storeApp.secutity.SecurityUser;
import com.rgr.storeApp.secutity.jwt.JwtBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UsersRepo usersRepo;
    private final RolesRepo rolesRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtBuilder jwtBuilder;

    @Autowired
    public UserService(UsersRepo usersRepo,
                       RolesRepo rolesRepo,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtBuilder jwtBuilder) {
        this.usersRepo = usersRepo;
        this.rolesRepo = rolesRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtBuilder = jwtBuilder;
    }

    public LoginResponse loginUser(LoginRequest loginRequest){

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                   loginRequest.getEmail(), loginRequest.getPassword()
                ));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String newToken = jwtBuilder.generateToken(authentication);

        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();


        List<String> roles = securityUser.getAuthorities().stream()
                .map(i -> i.getAuthority()).collect(Collectors.toList());

        return new LoginResponse(newToken,
                securityUser.getId(),
                securityUser.getUsername(),
                roles);
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
                                Producer producer = new Producer();
                                SellHistory sellHistory = new SellHistory();;
                                sellHistory.setProducer(producer);
                                producer.setUser(user);
                                producer.setSellHistory(sellHistory);
                                user.setProducer(producer);
                                break;
                            default:
                                Role userRole = rolesRepo
                                        .findByRole(ERole.ROLE_USER)
                                        .orElseThrow(() -> new RuntimeException("Role dont exist"));
                                roles.add(userRole);
                        }
                    });
        }

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
        usersRepo.save(user);

        //TODO verification token save

        return null;
    }

}
