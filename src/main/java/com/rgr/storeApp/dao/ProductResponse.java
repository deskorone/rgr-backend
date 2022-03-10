package com.rgr.storeApp.dao;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    private MultipartFile maimImage;

    private List<MultipartFile> images;

    private Integer price;

    private String description;

    private String info;

    private List<String> categoryes;

}
