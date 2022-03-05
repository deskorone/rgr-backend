package com.rgr.storeApp.exceptions.api;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionMessage {

    private String message;
    private HttpStatus status;
    private ZonedDateTime timestamp;

}
