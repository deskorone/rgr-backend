package com.rgr.storeApp.exceptions.api;


public class UserNotFound extends RuntimeException{

    public UserNotFound(String message){
        super(message);
    }
}
