package com.rgr.storeApp.exceptions.api;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ExceptionsApi {

    @ExceptionHandler(value = {NotFound.class})
    public ResponseEntity<?> userNotFound(NotFound error){
        ExceptionMessage message = new ExceptionMessage(
                error.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(message, message.getStatus());
    }

    @ExceptionHandler(value = {NotPrivilege.class})
    public ResponseEntity<?> noPrivilege(NotPrivilege error){
        ExceptionMessage message = new ExceptionMessage(
                error.getMessage(),
                HttpStatus.FORBIDDEN,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(message, message.getStatus());
    }

    @ExceptionHandler(value = {NotValide.class})
    public ResponseEntity<?> notValide(NotValide notValide){
        ExceptionMessage message = new ExceptionMessage(
                notValide.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(message, message.getStatus());
    }

}
