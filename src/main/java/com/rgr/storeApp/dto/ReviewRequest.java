package com.rgr.storeApp.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class ReviewRequest {

    private String reviewText;

    private Integer rating;


    public ReviewRequest() {
    }
}
