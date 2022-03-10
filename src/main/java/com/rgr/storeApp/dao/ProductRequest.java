package com.rgr.storeApp.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.util.List;



@Data
@AllArgsConstructor
public class ProductRequest {

    private MultipartFile maimImage;

    private List<MultipartFile> images;

    private Integer price;

    private String description;

    private String info;

    private List<String> categoryes;



}
