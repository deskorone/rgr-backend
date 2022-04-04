package com.rgr.storeApp.service;


import com.rgr.storeApp.exceptions.api.NotFound;
import com.rgr.storeApp.exceptions.api.NotPrivilege;
import com.rgr.storeApp.models.ConfirmationToken;
import com.rgr.storeApp.models.User;
import com.rgr.storeApp.repo.ConfirmationTokenRepo;
import com.rgr.storeApp.service.email.EmailBuilderService;
import com.rgr.storeApp.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ConfirmationTokenService {

    private final ConfirmationTokenRepo confirmationTokenRepo;
    private final EmailService emailService;
    private final EmailBuilderService emailBuilderService;

    @Autowired
    public ConfirmationTokenService(ConfirmationTokenRepo confirmationTokenRepo, EmailService emailService, EmailBuilderService emailBuilderService) {
        this.confirmationTokenRepo = confirmationTokenRepo;
        this.emailService = emailService;
        this.emailBuilderService = emailBuilderService;
    }

    public String createToken(User user){
        ConfirmationToken token = new ConfirmationToken(UUID.randomUUID().toString(),
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15L),
                user);

        confirmationTokenRepo.save(token);
        return token.getToken();
    }

    public void confirmation(String token){
        emailService.send("mam", emailBuilderService.getVerificationEmail(token, "aklsjf"));
//        ConfirmationToken confirmationToken = confirmationTokenRepo.findByToken(token).orElseThrow(()-> new NotFound("Token not found"));
//        if(!confirmationToken.getUser().isEnabled()) {
//            if (confirmationToken.getConfirmedAt() != null) {
//                throw new NotPrivilege("Token already confirmed");
//            }
//            if (confirmationToken.getExpired().isBefore(LocalDateTime.now())) {
//                throw new NotPrivilege("Token expired");
//            }
//
//            confirmationToken.setConfirmedAt(LocalDateTime.now());
//            confirmationTokenRepo.save(confirmationToken);
//            System.out.println("Good");
//        }else{
//            throw new NotPrivilege("User already enabled");
//        }
    }



}
