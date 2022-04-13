package com.rgr.storeApp.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewRequest {
    private String reviewText;
    private Integer rating;
}
