package com.rgr.storeApp.exceptions.api;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ExceptionsApi {

    @ExceptionHandler(value = {UserNotFound.class})
    public ResponseEntity<?> userNotFound(UserNotFound error){
        ExceptionMessage message = new ExceptionMessage(
                error.getMessage(),
                HttpStatus.FORBIDDEN,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(message, message.getStatus());
    }

}
