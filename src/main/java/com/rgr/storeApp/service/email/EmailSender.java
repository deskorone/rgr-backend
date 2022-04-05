package com.rgr.storeApp.service.email;


public interface EmailSender {
    void sendVerification(String to, String email, String username);
}
