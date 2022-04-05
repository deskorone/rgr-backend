package com.rgr.storeApp.service;


import com.rgr.storeApp.exceptions.api.NotFound;
import com.rgr.storeApp.models.ConfirmationToken;
import com.rgr.storeApp.models.User;
import com.rgr.storeApp.repo.ConfirmationTokenRepo;
import com.rgr.storeApp.service.email.EmailService;
import com.rgr.storeApp.service.find.FindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ConfirmationTokenService {

    private final ConfirmationTokenRepo confirmationTokenRepo;
    private final EmailService emailService;
    private final FindService findService;

    @Autowired
    public ConfirmationTokenService(ConfirmationTokenRepo confirmationTokenRepo, EmailService emailService, FindService findService) {
        this.confirmationTokenRepo = confirmationTokenRepo;
        this.emailService = emailService;
        this.findService = findService;
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
        emailService.sendVerification(token, null, null);
    }

    public boolean acceptToken(String token){
                ConfirmationToken confirmationToken = confirmationTokenRepo.findByToken(token).orElseThrow(()-> new NotFound("Token not found"));
        if(!confirmationToken.getUser().isEnabled()) {
            if (confirmationToken.getConfirmedAt() != null) {
                return false;
            }
            if (confirmationToken.getExpired().isBefore(LocalDateTime.now())) {
                return false;
            }
            confirmationToken.getUser().setEnabled(true);
            confirmationToken.setConfirmedAt(LocalDateTime.now());
            confirmationTokenRepo.save(confirmationToken);
            return true;
        }else {
            return false;
        }
    }


    public boolean sendNewConfirmation(String email){
        User user;
        try {
            user = findService.getUser(email);
            if (user.isEnabled()){
                return false;
            }
            emailService.sendVerification(createToken(user), email, user.getUsername());
        }catch (Exception e){
            return false;
        }
        return true;
    }



}
