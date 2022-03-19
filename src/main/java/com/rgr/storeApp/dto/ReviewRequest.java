package com.rgr.storeApp.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewRequest {
    String reviewText;
    Integer rating;

}
